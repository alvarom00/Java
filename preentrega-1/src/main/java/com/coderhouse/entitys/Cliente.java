package com.coderhouse.entitys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Data
@Entity
@Schema(description = "Modelo de Cliente")
@Table(name = "Clientes")
public class Cliente {
	
	@Schema(description = "ID del Cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Schema(description = "Nombre del Cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "Alvaro")
	@Column(name = "Nombre", nullable = false)
    private String nombre;

	@Schema(description = "Apellido del Cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "Manterola")
	@Column(name = "Apellido", nullable = false)
    private String apellido;

	@Schema(description = "DNI del Cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "12345678")
	@Column(name = "DNI", nullable = false, unique = true)
    private String dni;
    
	@Schema(description = "Facturas del Cliente", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Factura> facturas = new ArrayList<>();

	@Override
	public int hashCode() {
		return Objects.hash(apellido, dni, id, nombre);
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
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
