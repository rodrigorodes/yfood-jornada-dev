package br.com.yfood.features.payment.methods;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.yfood.entity.UserRestaurantRL;

@RestController
@RequestMapping("v1/payments")
public class PaymentMethodAPI {
	
	@PersistenceContext
	private EntityManager manager;
	
	private PaymentMethodHandler chooseMehodPaymentHandler;
	
	public PaymentMethodAPI(PaymentMethodHandler chooseMehodPaymentHandler) {
		this.chooseMehodPaymentHandler = chooseMehodPaymentHandler;
	}

	@GetMapping("method/details")
	public ResponseEntity<Collection<PaymentMethodResponse>> find (@Valid PaymentMethodRequest request){
		
		UserRestaurantRL userRestaurantRL = request.toModel(manager);
		
		Set<PaymentMethodResponse> paymentDetailsResponse = chooseMehodPaymentHandler.handle(userRestaurantRL);
		
		return ResponseEntity.ok(
				paymentDetailsResponse
				.stream()
				.map(payment -> payment)
				.collect(Collectors.toList()));
	}
}
