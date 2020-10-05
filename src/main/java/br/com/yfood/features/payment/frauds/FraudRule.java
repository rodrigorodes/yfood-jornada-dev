package br.com.yfood.features.payment.frauds;

import br.com.yfood.entity.PaymentMethod;
import br.com.yfood.entity.User;

public interface FraudRule {
	
	boolean accept(PaymentMethod paymentMethod, User user);


}
