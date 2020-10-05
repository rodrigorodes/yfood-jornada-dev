package br.com.yfood.features.payment.methods;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.yfood.entity.PaymentMethod;
import br.com.yfood.entity.UserRestaurantRL;
import br.com.yfood.features.payment.frauds.FraudRule;

@Service
public class PaymentMethodHandler {

	@Autowired
	private Collection<FraudRule> fraudRules;

	public Set<PaymentMethodResponse> handle(UserRestaurantRL userRestaurantRL) {
		
		Set<PaymentMethod> paymentMethods = userRestaurantRL.filterByUserPaymentMethod(fraudRules);
		
		return paymentMethods
				.stream()
				.map(PaymentMethodResponse::new)
				.collect(Collectors.toSet());
	}
}
