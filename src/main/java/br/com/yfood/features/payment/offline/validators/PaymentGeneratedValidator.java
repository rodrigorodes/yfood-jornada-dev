package br.com.yfood.features.payment.offline.validators;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.HandlerMapping;

import br.com.yfood.entity.Payment;
import br.com.yfood.features.payment.offline.PaymentRepository;

@Component
public class PaymentGeneratedValidator implements Validator {

	private HttpServletRequest request;
	private PaymentRepository paymentRepository;

	public PaymentGeneratedValidator(HttpServletRequest request, PaymentRepository paymentRepository) {
		this.request = request;
		this.paymentRepository = paymentRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return StringUtils.hasText(orderId());
	}

	@SuppressWarnings("unchecked")
	private String orderId() {
		Map<String, String> variaveisUrl = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		return variaveisUrl.get("orderId");
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (errors.hasErrors()) {
			return;
		}

		String paramIdPedido = orderId();
		// super acoplado com o endereco
		Assert.state(StringUtils.hasText(paramIdPedido),
				"Para este validator funcionar, o PathVariable que representa o pedido precisa se chamar orderId");
		Long idPedido = Long.valueOf(paramIdPedido);

		Optional<Payment> possivelPagamento = paymentRepository.findByOrderId(idPedido);
		
		if (possivelPagamento.isPresent()) {
			errors.reject(null, "Já existe um pagamento iniciado para esse pedido");
		}
	}

}