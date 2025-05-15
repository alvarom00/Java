package com.coderhouse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.coderhouse.entitys.Producto;
import com.coderhouse.services.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/productos")
@Tag(name = "Gestion de productos", description = "Endpoints para gestionar productos")
public class ProductoController {

	@Autowired
	private ProductoService productoService;
	
	@Operation(summary = "Obtener la lista de todos los productos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
			}),
			@ApiResponse(responseCode = "404", description = "Error al intentar obtener la lista", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			
	})
	@GetMapping("/")
	public List<Producto> getAllProductos() {
		return productoService.findAll();
	}
	
	@Operation(summary = "Obtener un producto por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Producto obtenido correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
			}),
			@ApiResponse(responseCode = "404", description = "Error al intentar obtener el producto", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			
	})
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
	
	@Operation(summary = "Cargar un nuevo producto")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Producto cargado correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
			}),
			@ApiResponse(responseCode = "404", description = "Error al intentar cargar el producto", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			
	})
	@PostMapping("/create")
	public ResponseEntity<?> createProducto(@RequestBody Producto producto) {
	    try {
	        Producto creado = productoService.save(producto);
	        return ResponseEntity.ok(creado);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); // 409 Conflict
	    }
	}
	
	@Operation(summary = "Editar un producto por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Producto editado correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
			}),
			@ApiResponse(responseCode = "404", description = "Error al intentar editar el producto", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			
	})
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
	
	@Operation(summary = "Eliminar producto por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Producto eliminado correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
			}),
			@ApiResponse(responseCode = "404", description = "Error al intentar eliminar el producto", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			
	})
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