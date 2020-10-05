package br.com.yfood.features.payment.offline;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.yfood.entity.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long>{

	public Optional<Payment> findByOrderId(Long idPedido);

	public Optional<Payment> findByCode(String codigoPagamento);

}
