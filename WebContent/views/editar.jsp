<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar Producto</title>
</head>
<body>
	<h1>Editar Producto</h1>

	<form action="producto" method="post">
	<c:set var="producto" value="${producto }"></c:set>
		<input type="hidden" name="opcion" value="editar">
		<input type="hidden" name="id" value="${producto.id }">
		
		<table border="1">
			<tr>
				<td>Nombre :</td>
				<td><input type="text" name="nombre" size="50" value="${producto.nombre }"></td>
			</tr>
			<tr>
				<td>Cantidad :</td>
				<td><input type="text" name="cantidad" value="${producto.cantidad }"></td>
			</tr>
			<tr>
				<td>Precio :</td>
				<td><input type="text" name="precio" value="${producto.precio }"></td>
			</tr>
			<tr style="text-align: center;">
				<td colspan="2"><input type="submit" value="GUARDAR CAMBIOS"></td>
			</tr>
		</table>
	</form>
</body>
</html>