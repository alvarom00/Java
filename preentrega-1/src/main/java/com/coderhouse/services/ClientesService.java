package com.coderhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.entitys.Cliente;
import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.repositories.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClientesService implements CRUDInterface<Cliente, Long> {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente findById(Long id) {
		return clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado."));
	}

	@Override
	@Transactional
	public Cliente save(Cliente clienteActualizado) {
		return clienteRepository.save(clienteActualizado);
	}

	@Override
	@Transactional
	public Cliente update(Long id, Cliente clienteActualizado) {
		Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado."));
		if(clienteActualizado.getNombre() != null && !clienteActualizado.getNombre().isEmpty()) {
			cliente.setNombre(clienteActualizado.getNombre());
		}
		if(clienteActualizado.getApellido() != null && !clienteActualizado.getApellido().isEmpty()) {
			cliente.setApellido(clienteActualizado.getApellido());
		}
		
		if(clienteActualizado.getDni() != 0) {
			cliente.setDni(clienteActualizado.getDni());
		}
		
		if(clienteActualizado.getDni() != 0) {
			cliente.setDni(clienteActualizado.getDni());
		}
		return clienteRepository.save(cliente);
	}


	@Override
	public void deleteById(Long id) {
		if(!clienteRepository.existsById(id)) {
			throw new IllegalArgumentException("Cliente no encontrado.");
		}
		clienteRepository.deleteById(id);
	}
	
	

}
