package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.ServiciosVariosBDD;
import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("categoria")
public class ServiciosVarios {
	@Path("actualizarProducto")
	@PUT
	@Consumes("application/json")
	public Response actualizarProducto(Producto producto) {
		ServiciosVariosBDD servBDD = new ServiciosVariosBDD();
		try {
			servBDD.actualizarProducto(producto);;
			return Response.ok().build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("insertar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear(Categoria categoria) {
		ServiciosVariosBDD servBDD = new ServiciosVariosBDD();
		try {
			servBDD.insertar(categoria);
			return Response.ok().build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("actualizar")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizarCategoria(Categoria categoria) {
		ServiciosVariosBDD servBDD = new ServiciosVariosBDD();
		try {
			servBDD.actualizar(categoria);
			return Response.ok().build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("buscar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerCategorias() {
		ServiciosVariosBDD servBDD = new ServiciosVariosBDD();
		ArrayList<Categoria> categorias = null;
		try {
			categorias = servBDD.buscar();
			return Response.ok(categorias).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("buscarPedido/{sub}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@PathParam("sub")String proveedor){
		ServiciosVariosBDD servBDD = new ServiciosVariosBDD();
		ArrayList<Pedido> listaPedidos = null;
		try {
			listaPedidos = servBDD.buscarPorProveedor(proveedor);
			return Response.ok(listaPedidos).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("buscarProveedor/{cadena}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarProveedor(@PathParam("cadena")String identificador){
		ServiciosVariosBDD servBDD = new ServiciosVariosBDD();
		Proveedor proveedor= null;
		try {
			proveedor = servBDD.buscarPorIdentificador(identificador);
			return Response.ok(proveedor).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("buscarProducto/{sub}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarProducto(@PathParam("sub")int cadena){
		ServiciosVariosBDD servBDD = new ServiciosVariosBDD();
		Producto producto = null;
		try {
			producto = servBDD.buscarPorId(cadena);
			return Response.ok(producto).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
