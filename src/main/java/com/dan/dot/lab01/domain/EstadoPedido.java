package com.dan.dot.lab01.domain;

public class EstadoPedido {

	public EstadoPedido() {
		
	}
	
	public EstadoPedido(Integer id, String estado) {
		super();
		this.id = id;
		this.estado = estado;
	}
	
	private Integer id;
	private String estado;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "EstadoPedido{" +
				"id=" + id +
				", estado='" + estado + '\'' +
				'}';
	}
}
