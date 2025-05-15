package com.coderhouse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Gestion de clientes", description = "Endpoints para gestionar clientes")
public class ClienteController {
	
	@Autowired
	private ClientesService clientesService;
	
	@Operation(summary = "Obtener la lista de todos los clientes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
			}),
			@ApiResponse(responseCode = "404", description = "Error al intentar obtener la lista", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			
	})
	@GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Cliente>> getAllClientes() {
		try {
			List<Cliente> clientes = clientesService.findAll();
			return ResponseEntity.ok(clientes); // 200
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build(); //404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); //500
		}
	}

	@Operation(summary = "Obtener un cliente por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente obtenido correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
			}),
			@ApiResponse(responseCode = "404", description = "Error al intentar obtener el cliente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			
	})
	@GetMapping(value = "/{clienteId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Cliente> getClienteById(@PathVariable Long clienteId) {
	    try {
	        Cliente cliente = clientesService.findById(clienteId);
	        return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build(); // 200 / 404
	    } catch(Exception err) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
	    }
	}
	
	@Operation(summary = "Registar un nuevo cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente registrado correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
			}),
			@ApiResponse(responseCode = "404", description = "Error al intentar registrar el cliente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			
	})
	@PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
		try {
			Cliente clienteCreado = clientesService.save(cliente);
			return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreado);
		} catch (IllegalArgumentException e){
			return ResponseEntity.notFound().build();
		} catch (Exception err) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@Operation(summary = "Editar un cliente por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente editado correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
			}),
			@ApiResponse(responseCode = "404", description = "Error al intentar editar el cliente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			
	})
	@PutMapping(value = "/{clienteId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
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
	
	@Operation(summary = "Eliminar cliente por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
			}),
			@ApiResponse(responseCode = "404", description = "Error al intentar eliminar el cliente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			
	})
	@DeleteMapping(value = "/{clienteId}")
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
