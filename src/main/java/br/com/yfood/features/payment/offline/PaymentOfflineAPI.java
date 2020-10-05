package br.com.yfood.features.payment.offline;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.yfood.commons.TransctionHandler;
import br.com.yfood.entity.Payment;
import br.com.yfood.features.payment.offline.validators.PaymentOfflineValidator;

@RestController
@RequestMapping("v1/payments")
public class PaymentOfflineAPI {

	@PersistenceContext
	private EntityManager manager;

	private GetOrderValueHandler getOrderValue;

	private PaymentOfflineValidator newOrderOfflineValidator;

	private TransctionHandler transctionHandler;
	
	private PaymentRepository paymentRepository;

	public PaymentOfflineAPI(
			PaymentRepository paymentRepository,
			GetOrderValueHandler getOrderValue,
			PaymentOfflineValidator newOrderOfflineValidator, 
			TransctionHandler transctionHandler) {
		this.paymentRepository = paymentRepository;
		this.getOrderValue = getOrderValue;
		this.newOrderOfflineValidator = newOrderOfflineValidator;
		this.transctionHandler = transctionHandler;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(newOrderOfflineValidator);
	}

	@PostMapping(value = "offline/{orderId}")
	public String pay(
			@PathVariable("orderId") Long orderId, 
			@RequestBody @Valid PaymentOfflineRequest newOrderOfflineRequest) throws Exception {

		BigDecimal orderValue = getOrderValue.execute(orderId, () -> {
			BindException bindException = new BindException("", "");
			bindException.reject(null, "Olha, esse id de pedido não existe");
			return bindException;
		});

		return transctionHandler.execute(() -> {
			Payment newPaymentOffline = newOrderOfflineRequest.toPayment(orderId, orderValue, manager);
			Payment savePaymentOffline = paymentRepository.save(newPaymentOffline);
			return savePaymentOffline.getCode();
		});
	}

}
