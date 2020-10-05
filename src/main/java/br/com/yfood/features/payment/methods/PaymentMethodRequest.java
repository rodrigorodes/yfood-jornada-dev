package br.com.yfood.features.payment.methods;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

import br.com.yfood.entity.Restaurant;
import br.com.yfood.entity.User;
import br.com.yfood.entity.UserRestaurantRL;

public class PaymentMethodRequest {

	@NotNull
	private Long restaurantId;

	@NotNull
	private Long userId;

	public PaymentMethodRequest(@NotNull Long restaurantId, @NotNull Long userId) {
		this.restaurantId = restaurantId;
		this.userId = userId;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public Long getUserId() {
		return userId;
	}

	public UserRestaurantRL toModel(EntityManager manager) {
		
		User usuario = manager.find(User.class, this.userId);
		Restaurant restaurante = manager.find(Restaurant.class, this.restaurantId);
		
		return new UserRestaurantRL(usuario, restaurante);
	}

}
