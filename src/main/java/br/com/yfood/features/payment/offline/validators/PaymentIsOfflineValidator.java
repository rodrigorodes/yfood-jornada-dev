package br.com.yfood.features.payment.offline.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.yfood.features.payment.offline.PaymentOfflineRequest;

public class PaymentIsOfflineValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return PaymentOfflineRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		PaymentOfflineRequest request = (PaymentOfflineRequest) target;
		
		if(!request.isOffline()) {
			errors.rejectValue("formaPagamento",null, "A forma de pagamento deve ser offline");
		}
	}
}
