package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.CabeceraVentas;
import com.krakedev.inventarios.entidades.DetalleVentas;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class VentasBDD {
	public void insertar(CabeceraVentas venta) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement psDet = null;
		PreparedStatement psAct = null;
		PreparedStatement psHis = null;
		ResultSet rsClave = null;
		int codigoCabecera = 0;

		Date fechaActual = new Date();
		java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());
		Timestamp fechaHoraActual = new Timestamp(fechaActual.getTime());
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement(
					"insert into cabecera_ventas(fecha, total_sin_iva, iva, total) " + "values(?, 0, 0, 0);",
					Statement.RETURN_GENERATED_KEYS);
			ps.setDate(1, fechaSQL);

			ps.executeUpdate();

			rsClave = ps.getGeneratedKeys();
			if (rsClave.next()) {
				codigoCabecera = rsClave.getInt(1);
			}

			ArrayList<DetalleVentas> detallesVenta = venta.getDetalles();
			DetalleVentas det = null;
			for (int i = 0; i < detallesVenta.size(); i++) {
				det = detallesVenta.get(i);
				psDet = con.prepareStatement(
						"insert into detalle_de_ventas(cabecera_ventas, producto, cantidad, precio_venta, subtotal, subtatal_con_iva)"
								+ "values(?, ?, ?, ?, ?, ?);");
				psDet.setInt(1, codigoCabecera);
				psDet.setInt(2, det.getProducto().getCodigo());
				psDet.setInt(3, det.getCantidad());
				BigDecimal cantidad = new BigDecimal(det.getCantidad());
				psDet.setBigDecimal(4, det.getProducto().getPrecioVenta());
				BigDecimal subtotal = det.getProducto().getPrecioVenta().multiply(cantidad);
				psDet.setBigDecimal(5, subtotal);
				detallesVenta.get(i).setSubtotal(subtotal);
				if (det.getProducto().isTieneIva()) {
					BigDecimal iva = new BigDecimal(1.12);
					BigDecimal subtotalConIva = subtotal.multiply(iva);
					psDet.setBigDecimal(6, subtotalConIva);
					detallesVenta.get(i).setSubtotalConIva(subtotalConIva);
				} else {
					psDet.setBigDecimal(6, subtotal);
				}

				psDet.executeUpdate();
				System.out.println("Insertando ventas");

			}

			System.out.println(detallesVenta);
			BigDecimal totalSinIva = BigDecimal.ZERO;
			BigDecimal iva = BigDecimal.ZERO;
			for (int i = 0; i < detallesVenta.size(); i++) {
				det = detallesVenta.get(i);
				if(det.getSubtotal() != null) {
					if(det.getProducto().isTieneIva()) {
						totalSinIva = totalSinIva.add(det.getSubtotal());
						iva = iva.add(det.getSubtotal().multiply(BigDecimal.valueOf(0.12)));
					} else {
						totalSinIva = totalSinIva.add(det.getSubtotal());
					}
				}
				BigDecimal total = totalSinIva.add(iva);
				psAct = con.prepareStatement("Update cabecera_ventas set total_sin_iva = ?, iva = ?, total = ? where codigo = ?" );
				psAct.setBigDecimal(1, totalSinIva);
				psAct.setBigDecimal(2, iva);
				psAct.setBigDecimal(3, total);
				psAct.setInt(4, venta.getCodigo());
				psAct.executeUpdate();
			}
			psHis = con.prepareStatement("insert into historial_stock(fecha, referencia, producto, cantidad) "
			 		+ "values(?, ?, ?, ?)");
			 psHis.setTimestamp(1, fechaHoraActual);
			 psHis.setString(2, "VENTA "+venta.getCodigo());
			 psHis.setInt(3, det.getProducto().getCodigo());
			 psHis.setInt(4, det.getCantidad());
			 
			 psHis.executeUpdate();
			

		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al consultar. Detalle: " + e.getMessage());
		}
	}
}
