<%@page import="co.mintic.login.database.UsuarioDao"%>
<%@page import="co.mintic.login.database.TipoDocumentoDao"%>
<%@page import="co.mintic.login.bean.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Adminsitar usuarios</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>
<link rel="stylesheet" href="assets/css/css.css" />
</head>
<body>


	<h1>Gestionar Usuarios</h1>

	<div class="container">
		<div class=" col-xl-2 col-lg-3 col-md-4 col-sm-6" id="formulario">
			<form method="post" action="loginsuccess.jsp">
				<div class="form-group">
					<label>tipo Documento :</label> 
						<select name="tipo"  class="form-select">
						<option value="0">Seleccionar</option>
						<%
						TipoDocumentoDao tipo = new TipoDocumentoDao();
						List<TipoDocumento> listaDocumento = tipo.selectAllDocumentType();
						Iterator<TipoDocumento> iteracion = listaDocumento.iterator();
						TipoDocumento td = null;

						while (iteracion.hasNext()) {
							td = iteracion.next();
						%>
						<option value="<%=td.getId()%>"><%=td.getTipo()%></option>
						<%
						}
						%>
					</select>
				</div>


				<div class="form-group">
					<label for="numero"> numero:</label><input type="text" name="numero" id="numero" class="form-control"/>
				</div>
				<div class="form-group">
					<label>nombre:</label><input type="text" name="nombre" class="form-control" />
				</div>
				<div class="form-group">
					<label>password:</label> <input type="text" name="password" class="form-control" />
				</div>
				<div class="form-group">
					<label> nombre usuario:</label> <input type="text" name="usuario" class="form-control" />
				</div><br>
			<button type="submit" class="btn btn-primary">Guardar</button>
			</form>
		</div>

		<br> <br>

		<table class="table">
			<thead>
				<tr>
					<td>#</td>
					<td>Tipo documento</td>
					<td>Numero</td>
					<td>Nombre</td>
					<td colspan="2">Operaciones</td>

				</tr>
			</thead>
			<tbody>
				<%
				UsuarioDao dao = new UsuarioDao();
				List<Usuario> list = dao.selectAllUsers();
				Iterator<Usuario> iter = list.iterator();
				Usuario u = null;
				int numeral = 1;
				while (iter.hasNext()) {
					u = iter.next();
				%>
				<tr>
					<td><%=numeral%></td>
					<td><%=u.getTipoDocumento().getTipo()%></td>
					<td><%=u.getNumeroDocumento()%></td>
					<td><%=u.getNombre()%></td>
					<td><a class="btn  btn-danger" href="#">Eliminar</a></td>
					<td><a  class="btn btn-success"href="#">Actualizar</a></td>
					<%
					numeral++;
					}
					%>

				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>