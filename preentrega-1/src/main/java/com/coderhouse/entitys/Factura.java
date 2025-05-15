package com.coderhouse.entitys;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Schema(description = "Modelo de Factura")
@Table(name = "Facturas")
public class Factura {
	
	@Schema(description = "ID de la factura", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Schema(description = "Fecha de creacion de la factura", requiredMode = Schema.RequiredMode.REQUIRED, example = "14/5/2025")
	@Column(name = "Fecha", nullable = false)
	private String fecha;
	
	@Schema(description = "Monto total de la factura", requiredMode = Schema.RequiredMode.REQUIRED, example = "$60")
	@Column(name = "monto_total", nullable = false)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private BigDecimal montoTotal;
	
	@Schema(description = "Cliente que realizo la compra", requiredMode = Schema.RequiredMode.REQUIRED)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_id", nullable = false)
	@JsonBackReference
	private Cliente cliente;
	
	@Schema(description = "Productos facturados", requiredMode = Schema.RequiredMode.REQUIRED)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	    name = "factura_producto",
	    joinColumns = @JoinColumn(name = "factura_id"),
	    inverseJoinColumns = @JoinColumn(name = "producto_id")
	)
	@JsonIgnore
	private List<Producto> productos;
	
	@OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<DetalleFactura> detalles = new ArrayList<>();
	
}
