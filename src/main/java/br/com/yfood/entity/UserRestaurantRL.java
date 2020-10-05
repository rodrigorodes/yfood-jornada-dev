package br.com.yfood.entity;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import br.com.yfood.features.payment.frauds.FraudRule;

@Entity
public class UserRestaurantRL {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Valid
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@NotNull
	@Valid
	@ManyToOne(fetch = FetchType.EAGER)
	private Restaurant restaurant;
	
	@Deprecated
	public UserRestaurantRL() {
	}

	public UserRestaurantRL(
			@NotNull @Valid User user,
			@NotNull @Valid Restaurant restaurant) {
		this.user = user;
		this.restaurant = restaurant;
	}

	public boolean equalRestaurant(Restaurant restaurant) {
		return this.restaurant.equals(restaurant);
	}

	public Set<PaymentMethod> filterByUserPaymentMethod(
			Collection<FraudRule> fraudRules) {
		return user.filterPaymentMethod(this.restaurant, fraudRules);
	}

	private Long getUserId() {
		Assert.isTrue(user.getId().isPresent(),
				"Neste ponto o usuario precisa de id");
		return user.getId().get();
	}

	private Long getRestaurantId() {
		Assert.isTrue(restaurant.getId().isPresent(),
				"Neste ponto o restaurante precisa de id");
		return restaurant.getId().get();
	}
}
