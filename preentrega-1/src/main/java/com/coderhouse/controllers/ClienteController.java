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

import com.coderhouse.entitys.Cliente;
import com.coderhouse.services.ClientesService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	@Autowired
	private ClientesService clientesService;
	
	@GetMapping
	public List<Cliente> getAllClientes() {
		return clientesService.findAll();
	}

	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> getClienteById(@PathVariable Long clienteId) {
		try {
			Cliente cliente = clientesService.findById(clienteId);
			return ResponseEntity.ok(cliente); // 200
		} catch(IllegalArgumentException e) {		
			return ResponseEntity.notFound().build(); // 404
		} catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
		try {
			Cliente clienteCreado = clientesService.save(cliente);
			return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreado);
		} catch (Exception err) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> updateClienteById(
			@PathVariable Long clienteId,
			@RequestBody Cliente clienteActualizado
			)
	{
		try {
			Cliente cliente = clientesService.update(clienteId, clienteActualizado);
			return ResponseEntity.ok(cliente);
		} catch(IllegalArgumentException e) {		
			return ResponseEntity.notFound().build(); // 404
		} catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
		}
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> deleteClienteById(@PathVariable Long clienteId) {
		try {
			clientesService.deleteById(clienteId);
			return ResponseEntity.noContent().build(); // 204
		} catch(IllegalArgumentException e) {		
			return ResponseEntity.notFound().build(); // 404
		} catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
		}
	}
}
