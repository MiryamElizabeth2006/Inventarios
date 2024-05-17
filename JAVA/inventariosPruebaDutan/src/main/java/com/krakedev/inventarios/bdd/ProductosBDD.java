package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.UnidadDeMedida;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProductosBDD {
	public ArrayList<Producto> buscar(String subcadena) throws KrakeDevException {
		ArrayList<Producto> productos = new ArrayList<Producto>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Producto producto = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select prod.codigo_pro, prod.nombre as nombre_producto, "
					+ "udm.codigo_udm, udm.descripcion as descripcion_udm, "
					+ "cast(prod.precio_de_venta as decimal(9,2)), prod.tiene_iva, "
					+ "cast(prod.coste as decimal(9,2)), prod.categoria_cat, "
					+ "cat.nombre as nombre_categoria, prod.stock "
					+ "from productos prod, unidades_medida udm, categorias cat "
					+ "where prod.udm = udm.codigo_udm "
					+ "and prod.categoria_cat = cat.codigo_cat "
					+ "and upper(prod.nombre) like ?");
			ps.setString(1, "%" + subcadena.toUpperCase() + "%");
			rs = ps.executeQuery();

			while (rs.next()) {
				
				int codigoProducto = rs.getInt("codigo_pro");
				String nombreProducto = rs.getString("nombre_producto");
				String nombreUnidadMedida = rs.getString("codigo_udm");
				String descripcionUnidadMedida = rs.getString("descripcion_udm");
				BigDecimal precioVenta = rs.getBigDecimal("precio_de_venta");
				boolean tieneIva = rs.getBoolean("tiene_iva");
				BigDecimal coste = rs.getBigDecimal("coste");
				int codigoCategoria = rs.getInt("categoria_cat");
				String nombreCategoria = rs.getString("nombre_categoria");
				int stock = rs.getInt("stock");
				
				UnidadDeMedida udm = new UnidadDeMedida();
				udm.setCodigo(nombreUnidadMedida);
				udm.setDescripcion(descripcionUnidadMedida);
				
				Categoria categoria = new Categoria();
				categoria.setCodigo(codigoCategoria);
				categoria.setNombre(nombreCategoria);
				
				producto = new Producto();
				producto.setCodigo(codigoProducto);
				producto.setNombre(nombreProducto);
				producto.setUnidadMedida(udm);
				producto.setPrecioVenta(precioVenta);
				producto.setTieneIva(tieneIva);
				producto.setCoste(coste);
				producto.setCategoria(categoria);
				producto.setStock(stock);
				
				productos.add(producto);
				
			}
		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al consultar. Detalle: " + e.getMessage());
		}
		return productos;
	}
	
	public void insertar(Producto producto) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;
		con = ConexionBDD.obtenerConexion();
		try {
			ps = con.prepareStatement(
					"insert into productos(nombre, udm, precio_de_venta, tiene_iva, coste, categoria_cat, stock) "
					+ "values(?, ?, ?, ?, ?, ?, ?);");
			
			ps.setString(1, producto.getNombre());
			ps.setString(2, producto.getUnidadMedida().getCodigo());
			ps.setBigDecimal(3, producto.getPrecioVenta());
			ps.setBoolean(4, producto.isTieneIva());
			ps.setBigDecimal(5, producto.getCoste());
			ps.setInt(6, producto.getCategoria().getCodigo());
			ps.setInt(7, producto.getStock());
			ps.executeUpdate();
		}  catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al insertar Producto. Detalle: " + e.getMessage());
		}
	}
}
