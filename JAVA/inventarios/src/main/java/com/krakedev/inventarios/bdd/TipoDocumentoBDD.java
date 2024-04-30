package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.TipoDocumento;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class TipoDocumentoBDD {
	public ArrayList<TipoDocumento> recuperar() throws KrakeDevException {
		ArrayList<TipoDocumento> documentos = new ArrayList<TipoDocumento>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TipoDocumento tipoDoc = null;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("SELECT codigo_doc, descripcion "
					+ "FROM tipo_documento");
			rs = ps.executeQuery();
	
			while (rs.next()) {
				String codigo_doc = rs.getString("codigo_doc");
				String descripcion = rs.getString("descripcion");
				

				tipoDoc = new TipoDocumento(codigo_doc, descripcion);
				documentos.add(tipoDoc);
			}

		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al Consultar. Detalle: " + e.getMessage());
		}
		return documentos;
	}
}
