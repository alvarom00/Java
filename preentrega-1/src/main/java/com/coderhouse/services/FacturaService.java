package com.coderhouse.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.apis.ClienteRestApi;
import com.coderhouse.dto.TimeResponseDTO;
import com.coderhouse.entitys.Cliente;
import com.coderhouse.entitys.DetalleFactura;
import com.coderhouse.entitys.Factura;
import com.coderhouse.entitys.Producto;
import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.repositories.ClienteRepository;
import com.coderhouse.repositories.FacturaRepository;
import com.coderhouse.repositories.ProductoRepository;
import com.coderhouse.requests.FacturaRequest;
import com.coderhouse.requests.ProductoRequest;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class FacturaService implements CRUDInterface<Factura, Long> {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private FacturaRepository facturaRepository;
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private FechaService fechaService;
	
	private void recalcularMontoTotal(Factura factura) {
	    BigDecimal total = factura.getProductos()
	        .stream()
	        .map(p -> p.getPrecio() != null ? p.getPrecio() : BigDecimal.ZERO)
	        .reduce(BigDecimal.ZERO, BigDecimal::add);
	    factura.setMontoTotal(total);
	}
	
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

	    if (nuevaFactura.getProductos() == null || nuevaFactura.getProductos().isEmpty()) {
	        throw new IllegalArgumentException("La factura debe tener al menos un producto.");
	    }

	    if (nuevaFactura.getCliente() == null) {
	        throw new IllegalArgumentException("La factura debe tener un cliente asignado.");
	    }

	    List<Producto> productosActualizados = nuevaFactura.getProductos().stream()
	        .map(producto -> productoRepository.findById(producto.getId())
	            .orElseThrow(() -> new IllegalArgumentException("Producto con ID " + producto.getId() + " no encontrado.")))
	        .toList();

	    nuevaFactura.setProductos(productosActualizados);

	    BigDecimal total = productosActualizados.stream()
	        .map(Producto::getPrecio)
	        .reduce(BigDecimal.ZERO, BigDecimal::add);

	    nuevaFactura.setMontoTotal(total);

	    TimeResponseDTO fechaActual = fechaService.obtenerFechaYHoraActuales();
	    if (fechaActual != null) {
	        String fechaFormateada = String.format("%s %s %d, %d %s",
	                fechaActual.getDayOfWeek(),
	                fechaActual.getMonth(),
	                fechaActual.getDay(),
	                fechaActual.getYear(),
	                fechaActual.getTime());
	        nuevaFactura.setFecha(fechaFormateada);
	    } else {
	        nuevaFactura.setFecha("Fecha no disponible");
	    }

	    return facturaRepository.save(nuevaFactura);
	}


	@Override
	@Transactional
	public Factura update(Long id, Factura facturaActualizada) {
	    Factura facturaExistente = facturaRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));

	    if (facturaActualizada.getProductos() != null && !facturaActualizada.getProductos().isEmpty()) {
	        facturaExistente.setProductos(facturaActualizada.getProductos());
	    }

	    if (facturaActualizada.getFecha() != null) {
	        facturaExistente.setFecha(facturaActualizada.getFecha());
	    }

	    recalcularMontoTotal(facturaExistente);

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
	public Factura createFactura(FacturaRequest request) {
		TimeResponseDTO fechaActual = fechaService.obtenerFechaYHoraActuales();
	    log.debug("Procesando factura con fecha: {}", fechaActual);

	    Cliente cliente = clienteRepository.findById(request.getCliente().getId())
	        .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + request.getCliente().getId()));

	    BigDecimal montoTotal = BigDecimal.ZERO;
	    List<DetalleFactura> detalles = new ArrayList<>();

	    for (ProductoRequest productoRequest : request.getProductos()) {
	        Producto producto = productoRepository.findById(productoRequest.getId())
	            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + productoRequest.getId()));

	        int unidades = productoRequest.getUnidades();
	        if (unidades <= 0) {
	            throw new IllegalArgumentException("Cantidad inválida para producto ID: " + productoRequest.getId());
	        }

	        if (producto.getStock() < unidades) {
	            throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
	        }

	        producto.setStock(producto.getStock() - unidades);
	        productoRepository.save(producto);

	        BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(unidades));
	        montoTotal = montoTotal.add(subtotal);

	        DetalleFactura detalle = new DetalleFactura();
	        detalle.setProducto(producto);
	        detalle.setCantidad(unidades);
	        detalle.setPrecioUnitario(producto.getPrecio());

	        detalles.add(detalle);
	    }

	    Factura factura = new Factura();
	    factura.setCliente(cliente);
	    factura.setMontoTotal(montoTotal);
	    factura.setDetalles(detalles);
	    
	    if (fechaActual != null) {
	        String fechaFormateada = String.format("%s %s %d, %d %s",
	                fechaActual.getDayOfWeek(),
	                fechaActual.getMonth(),
	                fechaActual.getDay(),
	                fechaActual.getYear(),
	                fechaActual.getTime());
	        factura.setFecha(fechaFormateada);
	    } else {
	        LocalDateTime ahora = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	        String fechaLocal = ahora.format(formatter);
	        factura.setFecha(fechaLocal);
	    }


	    for (DetalleFactura detalle : detalles) {
	        detalle.setFactura(factura);
	    }

	    return facturaRepository.save(factura);
	}

}
