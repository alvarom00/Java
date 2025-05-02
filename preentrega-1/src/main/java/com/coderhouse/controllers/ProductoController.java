package com.coderhouse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.entitys.Producto;
import com.coderhouse.services.ProductoService;


@RestController
@RequestMapping("/api/productos")
public class ProductoController {

	@Autowired
	private ProductoService productoService;
	
	@GetMapping
	public List<Producto> getAllProductos() {
		return productoService.findAll();
	}
	
	@GetMapping("/{productoId}")
	public ResponseEntity<Producto> getProductoById(@PathVariable Long productoId) {
		try {
			Producto producto = productoService.findById(productoId);
			return ResponseEntity.ok(producto); // 200
		} catch(IllegalArgumentException e) {		
			return ResponseEntity.notFound().build(); // 404
		} catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
		try {
			Producto productoCreado = productoService.save(producto);
			return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
		} catch (Exception err) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PutMapping("/{productoId}")
	public ResponseEntity<Producto> updateProductoById(
			@PathVariable Long productoId,
			@RequestBody Producto productoActualizado
			)
	{
		try {
			Producto producto = productoService.update(productoId, productoActualizado);
			return ResponseEntity.ok(producto);
		} catch(IllegalArgumentException e) {		
			return ResponseEntity.notFound().build(); // 404
		} catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
		}
	}
	
	@DeleteMapping("/{productoId}")
	public ResponseEntity<Void> deleteProductoById(@PathVariable Long productoId) {
		try {
			productoService.deleteById(productoId);
			return ResponseEntity.noContent().build(); // 204
		} catch(IllegalArgumentException e) {		
			return ResponseEntity.notFound().build(); // 404
		} catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
		}
	}
}