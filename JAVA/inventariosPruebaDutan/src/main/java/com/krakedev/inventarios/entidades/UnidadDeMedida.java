package com.krakedev.inventarios.entidades;

public class UnidadDeMedida {
	private String codigo;
	private String descripcion;
	private CategoriaUDM categoriaUnidadMedida;

	public UnidadDeMedida() {

	}

	public UnidadDeMedida(String codigo, String descripcion, CategoriaUDM categoriaUnidadMedida) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.categoriaUnidadMedida = categoriaUnidadMedida;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public CategoriaUDM getCategoriaUnidadMedida() {
		return categoriaUnidadMedida;
	}

	public void setCategoriaUnidadMedida(CategoriaUDM categoriaUnidadMedida) {
		this.categoriaUnidadMedida = categoriaUnidadMedida;
	}

	@Override
	public String toString() {
		return "UnidadDeMedida [codigo=" + codigo + ", descripcion=" + descripcion + ", categoriaUnidadMedida="
				+ categoriaUnidadMedida + "]";
	}

}
