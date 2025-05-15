package com.coderhouse.entitys;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class DetalleFactura {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@JsonIgnore
    @ManyToOne
    private Factura factura;

    @ManyToOne
    private Producto producto;

    private Integer cantidad;

    private BigDecimal precioUnitario;
    
}
