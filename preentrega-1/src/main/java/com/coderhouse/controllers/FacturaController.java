package com.coderhouse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.dto.CargarProductoAFacturaDTO;
import com.coderhouse.entitys.Cliente;
import com.coderhouse.entitys.Factura;
import com.coderhouse.requests.FacturaRequest;
import com.coderhouse.services.FacturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/facturas")
@Tag(name = "Gestion de facturas", description = "Endpoints para gestionar facturas")
public class FacturaController {
	
	@Autowired
	private FacturaService facturaService;
	
	@Operation(summary = "Obtener la lista de todas las facturas")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de facturas obtenida correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
			}),
			@ApiResponse(responseCode = "404", description = "Error al intentar obtener la lista", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			
	})
	@GetMapping("/")
	public ResponseEntity<List<Factura>> getAllFacturas() {
	    try {
	        List<Factura> facturas = facturaService.findAll();
	        return ResponseEntity.ok(facturas); // 200
	    } catch (Exception err) {
	        err.printStackTrace();
	        return ResponseEntity.internalServerError().build();
	    }
	}

	
	@Operation(summary = "Obtener una factura por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Factura obtenida correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
			}),
			@ApiResponse(responseCode = "404", description = "Error al intentar obtener la factura", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			
	})
	@GetMapping("/{facturaId}")
	public ResponseEntity<Factura> getFacturaById(@PathVariable Long facturaId) {
		try {
			Factura factura = facturaService.findById(facturaId);
			return ResponseEntity.ok(factura); // 200
		} catch(IllegalArgumentException e) {		
			return ResponseEntity.notFound().build(); // 404
		} catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
		}
	}
	
	@Operation(summary = "Crear una nueva factura")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Factura creada correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
			}),
			@ApiResponse(responseCode = "404", description = "Error al intentar crear la factura", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			
	})
	@PostMapping("/create")
	public ResponseEntity<?> createFactura(@RequestBody FacturaRequest request) {
        try {
            Factura factura = facturaService.createFactura(request);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la factura: " + e.getMessage());
        }
    }
	
	
	@Operation(summary = "Editar una factura por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Factura editada correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
			}),
			@ApiResponse(responseCode = "404", description = "Error al intentar editar la factura", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			
	})
	@PatchMapping("/{facturaId}")
    public ResponseEntity<Factura> updateFacturaById(
        @PathVariable Long facturaId,
        @RequestBody Factura facturaActualizada
    ) {
        try {
            Factura factura = facturaService.update(facturaId, facturaActualizada);
            return ResponseEntity.ok(factura);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
		
	
	@Operation(summary = "Eliminar factura por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Factura eliminada correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))
			}),
			@ApiResponse(responseCode = "404", description = "Error al intentar eliminar la factura", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			
	})
	@DeleteMapping("/{facturaId}")
	public ResponseEntity<Void> deleteFacturaById(@PathVariable Long facturaId) {
		try {
			facturaService.deleteById(facturaId);
			return ResponseEntity.noContent().build(); // 204
		} catch(IllegalArgumentException e) {		
			return ResponseEntity.notFound().build(); // 404
		} catch(Exception err) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
		}
	}
}