package com.dan.dot.pedidos.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getFechaPedido() {
		return fechaPedido;
	}
	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
	public Obra getObra() {
		return obra;
	}
	public void setObra(Obra obra) {
		this.obra = obra;
	}
	public List<DetallePedido> getDetalle() {
		return detalle;
	}
	public void setDetalle(List<DetallePedido> detalle) {
		this.detalle = detalle;
	}
	public EstadoPedido getEstado() {
		return estado;
	}
	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "fecha_pedido")
	private Date fechaPedido;

	@ManyToOne
	@JoinColumn(name = "id_obra", referencedColumnName = "id")
	@NotNull
	private Obra obra;

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "pedido")
	private List<DetallePedido> detalle;

	@ManyToOne
	@JoinColumn(name = "id_estado", referencedColumnName = "id")
	private EstadoPedido estado;
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id: ").append(this.id).append("\n");
		sb.append("Fecha Pedido: ").append(this.fechaPedido).append("\n");
		sb.append("Obra: ").append(this.obra).append("\n");
		sb.append("Estado: ").append(this.estado).append("\n");
		sb.append("Detalle: ").append("\n");
		sb.append(this.detalle.toString());
		return sb.toString();
	}
}
