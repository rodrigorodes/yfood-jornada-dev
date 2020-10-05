package br.com.yfood.commons;

import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransctionHandler {

	@Autowired
	private EntityManager manager;

	@Transactional
	public <T> T execute(Supplier<T> supplier) {
		return supplier.get();
	}

	@Transactional
	public <T> T commit(T object) {
		manager.persist(object);
		return object;
	}

}
