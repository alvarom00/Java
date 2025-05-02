package com.coderhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.entitys.Factura;
import com.coderhouse.entitys.Producto;
import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.repositories.FacturaRepository;
import com.coderhouse.repositories.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class FacturaService implements CRUDInterface<Factura, Long> {

	@Autowired
	private FacturaRepository facturaRepository;
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public List<Factura> findAll() {
		return facturaRepository.findAll();
	}

	@Override
	public Factura findById(Long id) {
		return facturaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Factura no encontrada."));
	}

	@Override
	@Transactional
	public Factura save(Factura nuevaFactura) {
		return facturaRepository.save(nuevaFactura);
	}

	@Override
	@Transactional
	public Factura update(Long id, Factura facturaActualizada) {
        Factura facturaExistente = facturaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

        if (facturaActualizada.getProducto() != null) {
            facturaExistente.setProducto(facturaActualizada.getProducto());
        }

        if (facturaActualizada.getFecha() != null) {
            facturaExistente.setFecha(facturaActualizada.getFecha());
        }

        if (facturaActualizada.getMontoTotal() != null) {
            facturaExistente.setMontoTotal(facturaActualizada.getMontoTotal());
        }

        if (facturaActualizada.getNroFactura() != 0) {
            facturaExistente.setNroFactura(facturaActualizada.getNroFactura());
        }

        return facturaRepository.save(facturaExistente);
    }

	@Override
	public void deleteById(Long id) {
		if(!facturaRepository.existsById(id)) {
			throw new IllegalArgumentException("Factura no encontrada.");
		}
		facturaRepository.deleteById(id);
	}

	@Transactional
	public Factura cargarProductoAFactura(Long facturaId, Long productoId) {
		Producto producto = productoRepository.findById(productoId)
				.orElseThrow(() -> new IllegalArgumentException("Producto inexistente."));
		Factura factura = facturaRepository.findById(facturaId)
				.orElseThrow(() -> new IllegalArgumentException("Curso no encontrado."));
		
		factura.setProducto(producto);
		return facturaRepository.save(factura);
	}
}
