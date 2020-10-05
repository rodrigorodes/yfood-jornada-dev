package br.com.yfood.gateways;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import br.com.yfood.entity.Payment;

public abstract class Gateway {

	public boolean accept(@NotNull @Valid Payment payment) {
		Assert.isTrue(!payment.isFinished(), "Por algum motivo um motivo já concluído está tentando ser processado de novo");
		return specificAccepted(payment);
	}

	protected abstract boolean specificAccepted(@NotNull @Valid Payment payment);

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract int hashCode();

	public BigDecimal cost(@NotNull @Valid Payment payment) {
		this.accept(payment);
		return specificCost(payment);
	}

	protected abstract BigDecimal specificCost(@NotNull @Valid Payment payment);

}
