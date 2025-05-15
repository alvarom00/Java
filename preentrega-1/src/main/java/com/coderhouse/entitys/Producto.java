package com.coderhouse.entitys;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Schema(description = "Modelo de Producto")
@Table(name = "Productos")
public class Producto {

	@Schema(description = "ID del Producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Schema(description = "Nombre del producto", requiredMode = Schema.RequiredMode.REQUIRED)
	@Column(name = "Nombre", nullable = false)
    private String nombre;
	
	@Schema(description = "Precio del producto por unidad", requiredMode = Schema.RequiredMode.REQUIRED)
	@Column(name = "Precio", nullable = false)
	private BigDecimal precio;
	
	@Schema(description = "Unidades del producto en stock", requiredMode = Schema.RequiredMode.REQUIRED)
	@Column(name = "Stock", nullable = false)
	private Integer stock;

	@OneToMany(mappedBy = "productos", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Factura> facturas = new ArrayList<>(); 
	
}
