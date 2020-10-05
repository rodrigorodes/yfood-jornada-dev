package br.com.yfood.features.payment.commons;

import br.com.yfood.entity.PaymentMethod;

public interface HasPaymentMethodUserRestaurantMatcher {

	public Long getIdRestaurant();

	public Long getIdUser();

	public PaymentMethod getPaymentMethod();

}
