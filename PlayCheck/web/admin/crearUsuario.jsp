<%-- 
    Document   : crearUsuario
    Created on : 12 nov 2024, 19:53:15
    Author     : Javier
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Crear Usuario</title>
    <link rel="stylesheet" type="text/css" href="assets/css/crearUsuario.css"> <!-- Ruta a tu CSS -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>
    <div class="form-container">
        <h1>Crear Nuevo Usuario</h1>

        <!-- Mostrar error si lo hay -->
        <c:if test="${not empty error}">
            <div class="error-message">
                <strong>Error:</strong> ${error}
            </div>
        </c:if>

        <!-- Formulario para crear usuario -->
        <form action="CrearUsuario" method="POST" class="form">
            <div class="form-group">
                <label for="username">Nombre de Usuario:</label>
                <input type="text" id="username" name="username" value="${email}" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${email}" required>
            </div>
            <div class="form-group">
                <label for="password">Contraseña:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="fechaNacimiento">Fecha de Nacimiento:</label>
                <input type="date" id="fechaNacimiento" name="fechaNacimiento" value="${fechaNacimiento != null ? fechaNacimiento : ''}" required>
            </div>
            <div class="form-group">
                <label for="rol">Rol:</label>
                <select id="rol" name="rol">
                    <option value="registrado" selected>Registrado</option>
                    <option value="administrador">Administrador</option>
                </select>
            </div>

            <!-- Botones de acción -->
            <div class="button-group">
                <button type="submit" class="btn-create">Crear Usuario</button>
                <a href="MenuUsuario" class="btn-back">Volver</a>
            </div>
        </form>
    </div>
</body>
</html>
