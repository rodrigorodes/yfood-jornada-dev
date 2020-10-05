package br.com.yfood.features.payment.offline;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Supplier;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class GetOrderValueHandler {

	private RestTemplate restTemplate;
	private String endpointOrderValue;

	public GetOrderValueHandler(RestTemplate restTemplate,
			@Value("${endpoint.order.value}") 
	        @URL String endpointValorPedido) {
		this.restTemplate = restTemplate;
		this.endpointOrderValue = endpointValorPedido;
	}

	@SuppressWarnings("unchecked")
	public <E extends Exception> BigDecimal execute(Long orderId, Supplier<E> orderValueNotFoundCallback)
			throws E {
		
		try {
			
			Map<String, Object> order = restTemplate.getForObject(endpointOrderValue, Map.class, orderId);
			Number orderValue = (Number) order.get("orderId");

			return new BigDecimal(orderValue.doubleValue());
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				throw orderValueNotFoundCallback.get();
			}
			throw e;
		}
	}

}
