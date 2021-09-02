package com.dan.dot.pedidos.domain;

import javax.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {

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
	public boolean isHabilitado() { return habilitado; }
	public void setHabilitado(boolean habilitado) { this.habilitado = habilitado; }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String descripcion;
	private Double precio;
	private boolean habilitado;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id: ").append(this.id).append("\n");
		sb.append("Descripción: ").append(this.descripcion).append("\n");
		sb.append("Precio: ").append(this.precio).append("\n");
		sb.append("Habilitado: ").append(this.habilitado).append("\n");
		return sb.toString();
	}
}
