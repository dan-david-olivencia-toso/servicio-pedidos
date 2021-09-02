package com.dan.dot.lab01.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estado_pedido")
public class EstadoPedido {

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

	@Id
	private Integer id;
	private String estado;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Id: ").append(this.id).append("\n");
		sb.append("Estado: ").append(this.estado).append("\n");

		return sb.toString();
	}
}
