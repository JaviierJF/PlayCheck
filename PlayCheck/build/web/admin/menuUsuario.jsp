<%-- 
    Document   : menuUsuario
    Created on : 12 nov 2024, 18:22:28
    Author     : Javier
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <title>Menú de Usuarios</title>
    <link rel="stylesheet" type="text/css" href="assets/css/menuUsuario.css">
    
    <!-- Incluye el CSS de DataTables -->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/2.2.2/css/buttons.dataTables.min.css">
    
    <!-- Incluye el JavaScript de DataTables -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/2.2.2/js/dataTables.buttons.min.js"></script>

    <!-- Configuración de DataTables -->
    <script>
        $(document).ready(function() {
            $('#usuariosTable').DataTable({
                paging: true,
                searching: true,
                order: [[1, 'asc']],
                language: {
                    url: '//cdn.datatables.net/plug-ins/1.11.5/i18n/Spanish.json'
                }
            });
        });
    </script>
</head>
<body>

<div class="main-content">
    <h1>Gestión de Usuarios</h1>

    <!-- Tabla de usuarios -->
    <div id="usuariosTable_wrapper">
        <table id="usuariosTable" class="display" style="width:100%">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre de Usuario</th>
                    <th>Email</th>
                    <th>Fecha de Nacimiento</th>
                    <th>Fecha de Registro</th>
                    <th>Rol</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <!-- Iteramos sobre la lista de usuarios -->
                <c:forEach var="usuario" items="${usuarios}">
                    <tr>
                        <td>${usuario.userId}</td>
                        <td>${usuario.username}</td>
                        <td>${usuario.email}</td>
                        <td><fmt:formatDate value="${usuario.fechaNacimiento}" pattern="dd/MM/yyyy" /></td>
                        <td><fmt:formatDate value="${usuario.fechaRegistro}" pattern="dd/MM/yyyy" /></td>
                        <td>${usuario.rol}</td>
                        <td>
                            <!-- Botón para editar usuario -->
                            <form action="EditarUsuario" method="get" style="display:inline;">
                                <input type="hidden" name="userId" value="${usuario.userId}">
                                <button type="submit" class="btn-edit">Editar</button>
                            </form>
                            <!-- Botón para eliminar usuario -->
                            <form action="EliminarUsuario" method="post" style="display:inline;" onsubmit="return confirm('¿Está seguro de eliminar este usuario?');">
                                <input type="hidden" name="userId" value="${usuario.userId}">
                                <button type="submit" class="btn-delete">Eliminar</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="button-group">
    <a href="CrearUsuario" class="btn-create">Crear Usuario</a>
    <a href="HomePage" class="btn-back">Volver</a>
</div>
    </div>
</div>

</body>
</html>
