package com.coderhouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO de Cagar Producto a Factura")
public class CargarProductoAFacturaDTO {

	@Schema(description = "ID del producto", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	private Long productoId;
	@Schema(description = "ID de la factura", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	private Long facturaId;
	
}
