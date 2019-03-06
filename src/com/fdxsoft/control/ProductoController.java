package com.fdxsoft.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;

import com.fdxsoft.dao.ProductoDAO;
import com.fdxsoft.model.Producto;

/**
 * Servlet implementation class ProductoController
 * https://www.youtube.com/watch?v=vlmkUCw0LQk&list=PL3vxkSlW2FvWzjf0NdxcivmyhrW6CdxhB&index=29
 * 
 */
@WebServlet(description = "Administra Peticiones para la tabla Productos", urlPatterns = { "/producto" })
public class ProductoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductoController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String opcion = request.getParameter("opcion");
		if (opcion.equals("crear")) {
			System.out.println("Usted ha presionado opcion crear");
			RequestDispatcher rd = request.getRequestDispatcher("/views/crear.jsp");
			rd.forward(request, response);

		} else if (opcion.equals("listar")) {
			System.out.println("Usted ha presionado opcion listar");
			ProductoDAO dao = new ProductoDAO();
			List<Producto> lstProducto = new ArrayList();
			try {
				lstProducto = dao.obtenerProductos();
				for (Producto producto : lstProducto) {
					System.out.println(producto);
				}
				request.setAttribute("lstProducto", lstProducto);
				RequestDispatcher rd = request.getRequestDispatcher("/views/listar.jsp");
				rd.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR: NO FUE POSIBLE OBTENER LA LISTA DE PRODUCTOS DEBIDO A:");
				e.printStackTrace();
			}
		} else if (opcion.equals("meditar")) {
			try {
				int id = Integer.parseInt(request.getParameter("id"));
				System.out.println("Vamos a editar producto con Id = " + id);
				ProductoDAO dao = new ProductoDAO();
				Producto p = dao.obtenerProducto(id);
				System.out.println("Vamos a editar el producto: " + p.toString());
				request.setAttribute("producto", p);
				RequestDispatcher rd = request.getRequestDispatcher("/views/editar.jsp");
				rd.forward(request, response);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (opcion.equals("eliminar")) {
			ProductoDAO dao = new ProductoDAO();
			int id = Integer.parseInt(request.getParameter("id"));
			try {
				if(dao.eliminar(id)) {
					System.out.println("Se elimino exitosamente el producto con id = " + id);
				}else {
					System.out.println("No se pudo eliminar el producto con id = " + id);
				}
				RequestDispatcher rd = request.getRequestDispatcher("producto?opcion=listar");
				rd.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String opcion = request.getParameter("opcion");
		Date hoy = new Date();
		ProductoDAO dao = new ProductoDAO();
		Producto p = new Producto();

		if (opcion.equals("guardar")) {
			p.setNombre(request.getParameter("nombre"));
			p.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
			p.setPrecio(Double.parseDouble(request.getParameter("precio")));
			p.setFechaCrear(new java.sql.Date(hoy.getTime()));
			// p.setFechaActualizar(new java.sql.Date(hoy.getTime()));
			try {
				dao.guardar(p);
				System.out.println("Producto guardado con exito!");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR: NO FUE POSIBLE GUARDAR EL PRODUCTO!");
			}
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}else if(opcion.equals("editar")) {
			p.setId(Integer.parseInt(request.getParameter("id")));
			p.setNombre(request.getParameter("nombre"));
			p.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
			p.setPrecio(Double.parseDouble(request.getParameter("precio")));
			p.setFechaActualizar(new java.sql.Date(hoy.getTime()));
			try {
				dao.editar(p);
				System.out.println("Se edito exitosamente el producto con Id=" + p.getId());
			} catch (SQLException e) {
				System.out.println("No se pudo editar el producto con Id=" + p.getId() + " debido a:");
				e.printStackTrace();
			}
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}

	}

}
