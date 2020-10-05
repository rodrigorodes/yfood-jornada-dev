package br.com.yfood.entity;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import br.com.yfood.commons.FacilitadorJackson;
import br.com.yfood.gateways.Gateway;

@Embeddable
public class Transaction {

	@NotNull
	private StatusTransaction statusTransaction;
	
	@NotBlank
	private String codigo;
	
	@NotNull
	@PastOrPresent
	private LocalDateTime instante;
	
	private String additionalInformation;

	@Deprecated
	public Transaction() {
	}

	public Transaction(@NotNull StatusTransaction statusTransaction) {
		this.statusTransaction = statusTransaction;
		this.codigo = UUID.randomUUID().toString();
		this.instante = LocalDateTime.now();
	}
	
	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public boolean hasStatus(StatusTransaction status) {
		return this.statusTransaction.equals(status);
	}

	public void setInfoAdicional(Map<String, Object> infoAdicional) {
		this.additionalInformation = FacilitadorJackson.serializa(infoAdicional);
	}

	public static Transaction finished(Gateway gateway) {
		Transaction transaction = new Transaction(StatusTransaction.FINISHED);
		transaction.additionalInformation = FacilitadorJackson.serializa(Map.of("gateway", gateway.toString()));
		return transaction;
	}

	@Override
	public String toString() {
		return "Transaction [statusTransaction=" + statusTransaction + ", codigo=" + codigo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
