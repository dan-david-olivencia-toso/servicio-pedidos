package com.dan.dot.pedidos.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

	public Integer getId() { return id; }
	public void setId(Integer id) {
		this.id = id;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Pedido getPedido() { return pedido; }
	public void setPedido(Pedido pedido) { this.pedido = pedido; }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_producto", referencedColumnName = "id")
	private Producto producto;
	@NotNull
	private Integer cantidad;
	@NotNull
	private Double precio;


	@ManyToOne
	@JoinColumn(name = "id_pedido", referencedColumnName = "id")
	@JsonBackReference
	private Pedido pedido;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id: ").append(this.id).append("\n");
		sb.append("Producto: ").append(this.producto).append("\n");
		sb.append("Cantidad: ").append(this.cantidad).append("\n");
		sb.append("Precio: ").append(this.precio).append("\n");
		sb.append(this.producto.toString());
		return sb.toString();
	}
}
