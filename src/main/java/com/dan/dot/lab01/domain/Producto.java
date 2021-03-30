package com.dan.dot.lab01.domain;

public class Producto {

	private Integer id;
	private String descripcion;
	private Double precio;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Producto{" +
				"id=" + id +
				", descripcion='" + descripcion + '\'' +
				", precio=" + precio +
				'}';
	}
}
