package br.com.yfood.features.payment.frauds;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import br.com.yfood.entity.PaymentMethod;
import br.com.yfood.entity.User;

@Service
public class UserMailFraudRule implements FraudRule {
	
	private Set<String> mailBlackList = Set.of("shun@deveficiente.com");

	@Override
	public boolean accept(
			@NotNull PaymentMethod paymentMethod,
			@NotNull @Valid User user) {
		if(!paymentMethod.isOnline()) {
			return true;
		}
		
		return !mailBlackList.contains(user.getMail());
	}
}
