package br.com.yfood.features.payment.methods;

import br.com.yfood.entity.PaymentMethod;

public class PaymentMethodResponse {

	private String id;
	private String description;

	public PaymentMethodResponse(PaymentMethod paymentMethod) {
		this.id = paymentMethod.name();
		this.description = paymentMethod.getDescription();
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
}
