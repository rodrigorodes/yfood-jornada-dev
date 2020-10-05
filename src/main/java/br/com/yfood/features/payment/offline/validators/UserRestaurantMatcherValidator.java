package br.com.yfood.features.payment.offline.validators;

import java.util.Collection;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.yfood.entity.Restaurant;
import br.com.yfood.entity.User;
import br.com.yfood.features.payment.commons.HasPaymentMethodUserRestaurantMatcher;
import br.com.yfood.features.payment.frauds.FraudRule;

@Component
public class UserRestaurantMatcherValidator implements Validator {

	private EntityManager manager;
	private Collection<FraudRule> regrasFraude;

	public UserRestaurantMatcherValidator(EntityManager manager,
			Collection<FraudRule> regrasFraude) {
		this.manager = manager;
		this.regrasFraude = regrasFraude;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return HasPaymentMethodUserRestaurantMatcher.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (errors.hasErrors()) {
			return;
		}

		HasPaymentMethodUserRestaurantMatcher request = (HasPaymentMethodUserRestaurantMatcher) target;
		User user = manager.find(User.class, request.getIdUser());
		Restaurant restaurant = manager.find(Restaurant.class, request.getIdRestaurant());

		boolean isCanPay = user.isCanPay(restaurant, request.getPaymentMethod(), regrasFraude);
		if (!isCanPay) {
			errors.reject(null, "A combinacao entre usuario, restaurante e forma de pagamento nao eh valida");
		}
	}

}