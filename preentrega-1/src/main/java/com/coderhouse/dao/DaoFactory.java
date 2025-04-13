package com.coderhouse.dao;

import org.springframework.stereotype.Service;

import com.coderhouse.entitys.Cliente;
import com.coderhouse.entitys.Factura;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class DaoFactory {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void persistirCliente(Cliente cliente) {
		em.persist(cliente);
	}
	
	@Transactional
	public void persistirFactura(Factura factura) {
		em.persist(factura);
	}
	
}
