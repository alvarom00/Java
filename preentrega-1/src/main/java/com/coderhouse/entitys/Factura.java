package com.coderhouse.entitys;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.Table;

@Entity
@Table(name = "Facturas")
public class Factura {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Nro Factura", nullable = false, unique = true)
	private int nroFactura;
	
	@Column(name = "Fecha", nullable = false)
	private LocalDate fecha;
	
	@Column(name = "Monto total", nullable = false)
	private Double montoTotal;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "factura_cliente",
			joinColumns = @JoinColumn(name = "factura_id"),
			inverseJoinColumns = @JoinColumn(name = "cliente_id")
			)
	@JsonIgnore
	private List<Cliente> clientes = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Producto producto;
	
	public Factura() {
		super();
	}

	public Factura(int nroFactura, LocalDate fecha, Double montoTotal) {
		super();
		this.nroFactura = nroFactura;
		this.fecha = fecha;
		this.montoTotal = montoTotal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(Double montoTotal) {
		this.montoTotal = montoTotal;
	}
	
	public int getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(int nroFactura) {
		this.nroFactura = nroFactura;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", fecha=" + fecha + ", montoTotal=" + montoTotal + ", clientes=" + clientes + "]";
	}
	
}
