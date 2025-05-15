package com.coderhouse.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.entitys.Producto;
import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.repositories.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductoService implements CRUDInterface<Producto, Long> {

	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public List<Producto> findAll() {
		return productoRepository.findAll();
	}

	@Override
	public Producto findById(Long id) {
		return productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));
	}

	@Override
	@Transactional
	public Producto save(Producto nuevoProducto) {
		Optional<Producto> existente = productoRepository.findByNombre(nuevoProducto.getNombre());
	    
	    if (existente.isPresent()) {
	        throw new IllegalArgumentException("Ya existe un producto con ese nombre");
	    }
		return productoRepository.save(nuevoProducto);
	}

	@Override
	@Transactional
	public Producto update(Long productoId, Producto productoActualizado) {
	    Producto productoExistente = productoRepository.findById(productoId)
	            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

	    if (productoActualizado.getNombre() != null) {
	        productoExistente.setNombre(productoActualizado.getNombre());
	    }

	    if (productoActualizado.getPrecio() != null) {
	        productoExistente.setPrecio(productoActualizado.getPrecio());
	    }

	    if (productoActualizado.getStock() != null) {
	        productoExistente.setStock(productoActualizado.getStock());
	    }

	    return productoRepository.save(productoExistente);
	}

	@Override
	public void deleteById(Long id) {
		if(!productoRepository.existsById(id)) {
			throw new IllegalArgumentException("Producto no encontrado.");
		}
		productoRepository.deleteById(id);
	}


}
