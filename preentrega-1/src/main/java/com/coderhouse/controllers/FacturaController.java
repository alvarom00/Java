package com.coderhouse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.dto.CargarProductoAFacturaDTO;
import com.coderhouse.entitys.Factura;
import com.coderhouse.services.FacturaService;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {
	
	@Autowired
	private FacturaService facturaService;
	
	@GetMapping
	public ResponseEntity<List<Factura>> getAllFacturas() {
		try {
			List<Factura> facturas = facturaService.findAll();
			return ResponseEntity.ok(facturas); // 200
		} catch (Exception err) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
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
	
	@PostMapping("/create")
	public ResponseEntity<Factura> createFactura(@RequestBody Factura factura) {
		try {
			Factura facturaCreada = facturaService.save(factura);
			return ResponseEntity.status(HttpStatus.CREATED).body(facturaCreada);
		} catch (Exception err) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
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
	
	@PostMapping("/cargar-productos")
	public ResponseEntity<Factura> cargarProductoAFactura(@RequestBody CargarProductoAFacturaDTO dto) {
		try {
			
			Factura facturaActualizada = facturaService.cargarProductoAFactura(
					dto.getFacturaId(),
					dto.getProductoId()
					);
					return ResponseEntity.ok(facturaActualizada); // 200
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build(); // 404
		} catch (Exception err) {
			return ResponseEntity.internalServerError().build(); // 500
		}
	}
}