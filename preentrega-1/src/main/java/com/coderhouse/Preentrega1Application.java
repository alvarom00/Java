package com.coderhouse;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.coderhouse.dao.DaoFactory;
import com.coderhouse.entitys.Cliente;
import com.coderhouse.entitys.Factura;

@SpringBootApplication
public class Preentrega1Application implements CommandLineRunner {
	
	@Autowired
	private DaoFactory dao;

	public static void main(String[] args) {
		SpringApplication.run(Preentrega1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		try {
			
			Factura factura1 = new Factura(1, LocalDate.of(2025, 12, 20), 60.0);
			Factura factura2 = new Factura(2, LocalDate.of(2025, 12, 21), 30.0);
			Factura factura3 = new Factura(3, LocalDate.of(2025, 12, 22), 40.0);
			
			Cliente cliente1 = new Cliente("Alvaro", "Manterola", 42399945);
			Cliente cliente2 = new Cliente("Pedro", "Manterola", 49434827);
			Cliente cliente3 = new Cliente("Francisco", "Manterola", 26074272);
			
			dao.persistirFactura(factura1);
			dao.persistirFactura(factura2);
			dao.persistirFactura(factura3);
			
			dao.persistirCliente(cliente1);
			dao.persistirCliente(cliente2);
			dao.persistirCliente(cliente3);

		}catch(Exception err) {
			err.getMessage();
		}
		
	}
	

}
