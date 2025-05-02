package com.coderhouse.services;

import java.util.List;

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
		return productoRepository.save(nuevoProducto);
	}

	@Override
	@Transactional
	public Producto update(Long id, Producto productoActualizado) {
		Producto producto = productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));
		if(productoActualizado.getNombre() != null && !productoActualizado.getNombre().isEmpty()) {
			producto.setNombre(productoActualizado.getNombre());
		}
		return productoRepository.save(producto);
	}

	@Override
	public void deleteById(Long id) {
		if(!productoRepository.existsById(id)) {
			throw new IllegalArgumentException("Producto no encontrado.");
		}
		productoRepository.deleteById(id);
	}

}
