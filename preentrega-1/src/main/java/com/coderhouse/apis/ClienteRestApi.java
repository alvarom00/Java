package com.coderhouse.apis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.coderhouse.entitys.Cliente;
import com.coderhouse.interfaces.CRUDInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ClienteRestApi implements CRUDInterface<Cliente, Long> {

	private final String BASE_URL = "https://6825143a0f0188d7e72be8f7.mockapi.io/clientes";
	
	@Autowired
	private RestTemplate rt;
	
	@Override
	public List<Cliente> findAll() {
		try {
			@SuppressWarnings("unchecked")
			List<Cliente> clientes = rt.exchange(BASE_URL, HttpMethod.GET, null, List.class).getBody();
			return clientes;
		} catch (Exception e) {
			throw new RuntimeException("Error, no se pueden obtener los datos" + e.getMessage());
		}
	}

	@Override
	public Cliente findById(Long id) {
	    try {
	        String url = BASE_URL + '/' + id;
	        ResponseEntity<String> response = rt.getForEntity(url, String.class);
	        
	        System.out.println("Respuesta de MockAPI (findById): " + response.getBody());

	        ObjectMapper mapper = new ObjectMapper();
	        Cliente cliente = mapper.readValue(response.getBody(), Cliente.class);
	        return cliente;
	    } catch (Exception e) {
	        throw new RuntimeException("Error al obtener datos del cliente: " + e.getMessage(), e);
	    }
	}


	@Override
	public Cliente save(Cliente cliente) {
		try {
			return rt.postForObject(BASE_URL, cliente, Cliente.class);
		} catch (Exception e) {
			throw new RuntimeException("Error, no se pueden obtener los datos" + e.getMessage());
		}
	}

	@Override
	public Cliente update(Long id, Cliente cliente) {
		try {
			String url = BASE_URL + '/' + id;
			rt.put(url, cliente);
			return cliente;
		} catch (Exception e) {
			throw new RuntimeException("Error, no se pueden obtener los datos" + e.getMessage());
		}
	}

	@Override
	public void deleteById(Long id) {
		try {
			String url = BASE_URL + '/' + id;
			rt.delete(url);
		} catch (Exception e) {
			throw new RuntimeException("Error, no se pueden obtener los datos" + e.getMessage());
		}
	}
}
