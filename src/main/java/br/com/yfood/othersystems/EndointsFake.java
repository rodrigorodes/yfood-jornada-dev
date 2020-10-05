package br.com.yfood.othersystems;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class EndointsFake {
	
	private AtomicInteger ids = new AtomicInteger();

	@GetMapping("/api/pedidos/{orderId}")
	public Map<String, Object> valorPedido(@PathVariable("orderId") Long orderId) {
		if(ids.getAndIncrement() % 3 == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		String[] valores = new String[]{"50.00","90","100","150.00","60","200","700","800","900","1000","2000"};
		int posicao = new Random().nextInt(11);
		
		return Map.of("orderId",new BigDecimal(valores[posicao])); 
	}

}
