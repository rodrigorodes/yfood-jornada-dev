package br.com.yfood.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.yfood.features.payment.frauds.FraudRule;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Email
	private String mail;

	@Size(min = 1)
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<PaymentMethod> paymentMethods = new HashSet<>();

	@Deprecated
	public User() {
	}

	public User(String mail, PaymentMethod... paymentMethod) {	
		this.mail = mail;
		this.paymentMethods.addAll(Set.of(paymentMethod));
	}

	public Optional<Long> getId() {
		return Optional.ofNullable(id);
	}

	public String getMail() {
		return mail;
	}

	public Set<PaymentMethod> filterPaymentMethod(
			@NotNull @Valid Restaurant restaurant,
			Collection<FraudRule> fraudRules) {
		
		return this.paymentMethods
				.stream()
				.filter(restaurant::accept)
				.filter(paymentMethod -> {
						return fraudRules
								.stream()
								.allMatch(rule -> rule.accept(paymentMethod, this));
		}).collect(Collectors.toSet());
	}

	public boolean isCanPay(
			Restaurant restaurant, 
			PaymentMethod paymentMethod, 
			Collection<FraudRule> fraudRules) {
		return filterPaymentMethod(restaurant, fraudRules).contains(paymentMethod);
	}
}
