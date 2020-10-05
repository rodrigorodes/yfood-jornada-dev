package br.com.yfood.entity;

import java.util.stream.Stream;

public enum PaymentMethod {

	VISA(true, "cartao visa"), 
	MASTER(true, "cartao master"), 
	ELO(true, "cartao elo"), 
	HIPERCARD(true, "cartao hiper"),
	MAQUINETA(false, "maquina para passar cartao"), 
	MONEY(false, "dinheiro para pagar o pedidod");

	public final boolean online;
	
	private String description;

	PaymentMethod(boolean online, String descricao) {
		this.online = online;
		this.description = descricao;
	}

	public String getDescription() {
		return description;
	}

	public boolean match(PaymentMethod... grupo) {
		return Stream.of(grupo).anyMatch(forma -> forma.equals(this));
	}

	public boolean isOnline() {
		return online;
	}

}
