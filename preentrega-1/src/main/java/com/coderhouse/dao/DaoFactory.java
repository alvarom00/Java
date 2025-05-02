package com.coderhouse.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coderhouse.entitys.Cliente;
import com.coderhouse.entitys.Factura;
import com.coderhouse.entitys.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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
	
	@Transactional
	public void persistirProducto(Producto producto) {
		em.persist(producto);
	}
	
	@Transactional
	public Factura getFacturaById(Long facturaId) throws Exception {
		try {
			TypedQuery<Factura> query = em.createQuery("SELECT f FROM Factura f WHERE f.id = :id", Factura.class);
			return query.setParameter("id", facturaId).getSingleResult();
		} catch(Exception f) {
			throw new Exception("La factura no existe.");
		}
	}
	
	@Transactional
	public Producto getProductoById(Long productoId) throws Exception {
		try {
			TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p WHERE p.id = :id", Producto.class);
			return query.setParameter("id", productoId).getSingleResult();
		} catch(Exception p) {
			throw new Exception("El producto no existe.");
		}
	}
	
	@Transactional
	public Cliente getClienteById(Long clienteId) throws Exception {
		try {
			TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c WHERE c.id = :id", Cliente.class);
			return query.setParameter("id", clienteId).getSingleResult();
		} catch(Exception c) {
			throw new Exception("El cliente no existe.");
		}
	}
	
	@Transactional
	public void asignarProductoAFactura(Long facturaId, Long productoId) throws Exception {
		
		Factura factura = getFacturaById(facturaId);
		if(factura == null) {
			throw new Exception("La factura con ID: " + facturaId + " no existe.");
		}
		
		Producto producto = getProductoById(productoId);
		if(producto == null) {
			throw new Exception("El producto con ID: " + productoId + " no existe.");
		}
		
		factura.setProducto(producto);
		
		em.merge(factura);
	}
	
	@Transactional
	public void asignarFacturasACliente(Long clienteId, List<Long> facturaIds) throws Exception {
	    
	    Cliente cliente = getClienteById(clienteId);
	    if (cliente == null) {
	        throw new Exception("El cliente con ID: " + clienteId + " no existe.");
	    }

	    for (Long facturaId : facturaIds) {
	        Factura factura = getFacturaById(facturaId);
	        if (factura == null) {
	            throw new Exception("La factura con ID: " + facturaId + " no existe.");
	        }

	        if (!factura.getClientes().contains(cliente)) {
	            factura.getClientes().add(cliente);
	        }

	        em.merge(factura);
	    }
	}

}
