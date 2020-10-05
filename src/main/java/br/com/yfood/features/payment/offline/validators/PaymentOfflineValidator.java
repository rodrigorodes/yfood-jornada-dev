package br.com.yfood.features.payment.offline.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.yfood.features.payment.offline.PaymentOfflineRequest;

@Component
public class PaymentOfflineValidator implements Validator {

	@Autowired
	private UserRestaurantMatcherValidator paymentMethodUserRestaurantMatcher;

	@Autowired
	private PaymentGeneratedValidator paymentGeneratedValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return PaymentOfflineRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		new PaymentIsOfflineValidator().validate(target, errors);
		
		if (errors.hasErrors()) {
			return;
		}

		paymentMethodUserRestaurantMatcher.validate(target, errors);
		paymentGeneratedValidator.validate(target, errors);

	}

}
