package com.coderhouse.entitys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "Nombre", nullable = false)
    private String nombre;

	@Column(name = "Apellido", nullable = false)
    private String apellido;

	@Column(name = "DNI", nullable = false, unique = true)
    private int dni;
    
	@ManyToMany(mappedBy = "clientes", fetch = FetchType.EAGER)
    private List<Factura> facturas = new ArrayList<>();

	public Cliente() {
		super();
	}

	public Cliente(String nombre, String apellido, int dni) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apellido, dni, id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(apellido, other.apellido) && dni == other.dni && Objects.equals(id, other.id)
				&& Objects.equals(nombre, other.nombre);
	}
    
    
}
