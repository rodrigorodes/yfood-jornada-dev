package br.com.yfood;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import br.com.yfood.entity.PaymentMethod;
import br.com.yfood.entity.Restaurant;
import br.com.yfood.entity.User;

@SpringBootApplication
public class YfoodDevApplication implements CommandLineRunner {
	
	@PersistenceContext
	private EntityManager manager;

	public static void main(String[] args) {
		SpringApplication.run(YfoodDevApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		
		var seya = new User("seya@deveficiente.com", PaymentMethod.VISA,
				PaymentMethod.MAQUINETA,PaymentMethod.HIPERCARD);
		manager.persist(seya);

		var shun = new User("shun@deveficiente.com", PaymentMethod.VISA,
				PaymentMethod.ELO, PaymentMethod.MONEY);
		manager.persist(shun);

		var saori = new User("saori@deveficiente.com",
				PaymentMethod.VISA, PaymentMethod.MASTER,
				PaymentMethod.MAQUINETA);
		manager.persist(saori);

		var sagaSantuario = new Restaurant("saga santuario",
				PaymentMethod.VISA, PaymentMethod.MASTER, PaymentMethod.ELO);
		manager.persist(sagaSantuario);
		
		var sagaDeuses = new Restaurant("saga deuses",
				PaymentMethod.ELO,PaymentMethod.HIPERCARD, PaymentMethod.ELO);
		manager.persist(sagaDeuses);
		
		var sagaPoseydon = new Restaurant("saga poseydon",
				PaymentMethod.ELO,PaymentMethod.MONEY,PaymentMethod.MAQUINETA);
		manager.persist(sagaPoseydon);
	}

}
