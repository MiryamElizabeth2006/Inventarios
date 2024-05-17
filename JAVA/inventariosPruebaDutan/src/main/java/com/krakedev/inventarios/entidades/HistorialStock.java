package com.krakedev.inventarios.entidades;

import java.util.Date;

public class HistorialStock {
	private int codigo;
	private Date fechaActual;
	private String referencia;
	private Producto producto;
	private int cantidad;

	public HistorialStock() {

	}

	public HistorialStock(int codigo, Date fechaActual, String referencia, Producto producto, int cantidad) {
		super();
		this.codigo = codigo;
		this.fechaActual = fechaActual;
		this.referencia = referencia;
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "HistorialStock [codigo=" + codigo + ", fechaActual=" + fechaActual + ", referencia=" + referencia
				+ ", producto=" + producto + ", cantidad=" + cantidad + "]";
	}

}
