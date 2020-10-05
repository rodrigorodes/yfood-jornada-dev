package br.com.yfood.features.payment.offline;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

import br.com.yfood.commons.ExistsId;
import br.com.yfood.entity.Payment;
import br.com.yfood.entity.PaymentMethod;
import br.com.yfood.entity.Restaurant;
import br.com.yfood.entity.StatusTransaction;
import br.com.yfood.entity.User;
import br.com.yfood.features.payment.commons.HasPaymentMethodUserRestaurantMatcher;

public class PaymentOfflineRequest implements HasPaymentMethodUserRestaurantMatcher {

	@NotNull
	private PaymentMethod paymentMethod;
	
	@NotNull
	@ExistsId(domainClass = Restaurant.class, fieldName = "id")
	private Long restaurantId;
	
	@NotNull
	@ExistsId(domainClass = User.class, fieldName = "id")
	private Long userId;

	public PaymentOfflineRequest(
			@NotNull PaymentMethod paymentMethod,
			@NotNull Long restaurantId, 
			@NotNull Long userId) {
		this.paymentMethod = paymentMethod;
		this.restaurantId = restaurantId;
		this.userId = userId;
	}

	public Payment toPayment(Long idPedido, BigDecimal valor, EntityManager manager) {

		User user = manager.find(User.class, userId);
		Restaurant restaurant = manager.find(Restaurant.class, restaurantId);

		return Payment.offline(idPedido, valor, paymentMethod, user, restaurant, StatusTransaction.WAITING_PAYMENT);
	}

	public boolean isOffline() {
		return !getPaymentMethod().isOnline();
	}

	@Override
	public Long getIdRestaurant() {
		return restaurantId;
	}

	@Override
	public Long getIdUser() {
		return userId;
	}

	@Override
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

}
