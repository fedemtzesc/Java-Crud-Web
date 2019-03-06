package com.fdxsoft.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fdxsoft.conexion.Conexion;
import com.fdxsoft.model.Producto;

public class ProductoDAO {
	private Connection connection;
	private PreparedStatement statement;
	private boolean estadoOperacion;

	public boolean guardar(Producto producto) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();
		// Inicializamos una transaccion atomica
		try {
			connection.setAutoCommit(false);
			// Creamos sentencia SQL
			sql = " INSERT INTO productos (id, nombre, cantidad, precio, fecha_crear, fecha_actualizar)";
			sql += " VALUES(?,?,?,?,?,?)";
			statement = connection.prepareStatement(sql);
			// Le metemos los valores de los parametros al statement sql
			statement.setString(1, null);
			statement.setString(2, producto.getNombre());
			statement.setDouble(3, producto.getCantidad());
			statement.setDouble(4, producto.getPrecio());
			statement.setDate(5, producto.getFechaCrear());
			statement.setDate(6, producto.getFechaActualizar());
			// Ejecutamos la sentencia sql
			estadoOperacion = statement.executeUpdate() > 0;
			// Ahora si aplicamos las instrucciones sql a la BD
			connection.commit();
			// Cerramos el statement
			statement.close();
			// Regresamos la conexion al pool por que no se cierra
			connection.close();
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		}
		// Finalmente devolvemos el estado de la operacion
		return estadoOperacion;
	}

	public boolean editar(Producto producto) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			connection.setAutoCommit(false);
			sql = "UPDATE productos ";
			sql += " SET nombre=? ";
			sql += " ,cantidad=? ";
			sql += " ,precio=? ";
			sql += " ,fecha_actualizar=? ";
			sql += " WHERE id=? ";
			statement = connection.prepareStatement(sql);
			statement.setString(1, producto.getNombre());
			statement.setDouble(2, producto.getCantidad());
			statement.setDouble(3, producto.getPrecio());
			statement.setDate(4, producto.getFechaActualizar());
			statement.setInt(5, producto.getId());
			// Ejecutamos la sentencia sql
			estadoOperacion = statement.executeUpdate() > 0;
			// Ahora si aplicamos las instrucciones sql a la BD
			connection.commit();
			// Cerramos el statement
			statement.close();
			// Regresamos la conexion al pool por que no se cierra
			connection.close();
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		}

		return estadoOperacion;
	}

	public boolean eliminar(int idProducto) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			connection.setAutoCommit(false);
			sql = "DELETE FROM productos ";
			sql += " WHERE id=? ";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, idProducto);
			// Ejecutamos la sentencia sql
			estadoOperacion = statement.executeUpdate() > 0;
			// Ahora si aplicamos las instrucciones sql a la BD
			connection.commit();
			// Cerramos el statement
			statement.close();
			// Regresamos la conexion al pool por que no se cierra
			connection.close();
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		}

		return estadoOperacion;
	}

	public List<Producto> obtenerProductos() throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();
		ResultSet rs = null;
		List<Producto> lstProductos = new ArrayList();

		try {
			sql = "SELECT * FROM productos";
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				Producto p = new Producto();
				p.setId(rs.getInt(1));
				p.setNombre(rs.getString(2));
				p.setCantidad(rs.getDouble(3));
				p.setPrecio(rs.getDouble(4));
				p.setFechaCrear(rs.getDate(5));
				p.setFechaActualizar(rs.getDate(6));
				lstProductos.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return lstProductos;
	}

	public Producto obtenerProducto(int idProducto) throws SQLException {
		String sql = null;
		Producto p = new Producto();
		estadoOperacion = false;
		connection = obtenerConexion();
		ResultSet rs = null;

		try {
			sql = "SELECT * FROM productos WHERE id=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, idProducto);
			rs = statement.executeQuery();
			if (rs.next()) {
				p.setId(rs.getInt("id"));
				p.setNombre(rs.getString("nombre"));
				p.setCantidad(rs.getDouble("cantidad"));
				p.setPrecio(rs.getDouble("precio"));
				p.setFechaCrear(rs.getDate("fecha_crear"));
				p.setFechaActualizar(rs.getDate("fecha_actualizar"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return p;
	}

	// Obtiene una conexion del Pool
	private Connection obtenerConexion() throws SQLException {
		return Conexion.getConnection();
	}

}
