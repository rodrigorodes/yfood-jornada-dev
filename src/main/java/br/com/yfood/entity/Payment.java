package br.com.yfood.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private @NotNull 
	Long orderId;
	
	@ElementCollection
	private Set<Transaction> transactions = new HashSet<>();
	
	private @NotNull @Positive 
	BigDecimal value;
	
	@ManyToOne
	private @NotNull @Valid 
	User buy;
	
	@ManyToOne
	private @NotNull @Valid 
	Restaurant restaurant;
	
	@NotNull
	private String code;
	
	private @NotNull 
	PaymentMethod paymentMethod;
	
	@Deprecated
	public Payment() {
	}

	private Payment(
			@NotNull Long orderId, 
			@NotNull @Positive BigDecimal value,
			@NotNull @Valid User buy,
			@NotNull @Valid Restaurant restaurant,
			@NotNull PaymentMethod paymentMethod,
			@NotNull StatusTransaction statusInitial) {
		this.orderId = orderId;
		this.value = value;
		this.buy = buy;
		this.restaurant = restaurant;
		this.paymentMethod = paymentMethod;
		this.transactions.add(new Transaction(statusInitial));
		this.code = UUID.randomUUID().toString();
	}

	public String getCode() {
		return code;
	}

	public static Payment offline(
			Long orderId, 
			BigDecimal value,
			@NotNull PaymentMethod paymentMethod,
			@NotNull @Valid User buy,
			@NotNull @Valid Restaurant restaurant,
			@NotNull @Valid StatusTransaction statusInitial) {
		
		Payment payment = new Payment(orderId, value, buy, restaurant, paymentMethod, statusInitial);
		
		return payment;
	}

	public boolean isFinished() {
		return this.transactions
				.stream()
				.anyMatch(transaction -> transaction.hasStatus(StatusTransaction.FINISHED));
	}
}
