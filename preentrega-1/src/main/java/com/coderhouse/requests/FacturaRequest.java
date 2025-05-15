package com.coderhouse.requests;

import java.util.List;

import lombok.Data;

@Data
public class FacturaRequest {

    private ClienteRequest cliente;
    private List<ProductoRequest> productos;
}