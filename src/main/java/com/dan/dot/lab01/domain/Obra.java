package com.dan.dot.lab01.domain;

public class Obra {
	
	private Integer id;
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Obra{" +
				"id=" + id +
				", descripcion='" + descripcion + '\'' +
				'}';
	}
}
