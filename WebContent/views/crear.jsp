<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Crear Producto</title>
</head>
<body>
	<h1>Crear Producto</h1>
	<form action="producto" method="post">
		<input type="hidden" name="opcion" value="guardar">
			<table border="1">
				<tr>
					<td>Nombre :</td><td> <input type="text" name="nombre" size="50"> </td>
				</tr>
				<tr>
					<td>Cantidad :</td><td> <input type="text" name="cantidad"> </td>
				</tr>
				<tr>
					<td>Precio :</td><td> <input type="text" name="precio"> </td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="2"> <input type="submit" value="GUARDAR"> </td>
				</tr>
			</table>
	</form>
</body>
</html>