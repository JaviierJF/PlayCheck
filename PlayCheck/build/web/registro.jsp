<%-- 
    Document   : registro.jsp
    Created on : 4 nov 2024, 17:58:52
    Author     : Javier
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registro de Usuario</title>
        <link rel="stylesheet" href="assets/css/registro.css">
    </head>
    <body>
        <div class="app-name">PlayCheck</div>
        <div class="register-container">
            <h2>Registro de Usuario</h2>
            <form method="post">
                <!-- Campo de nombre de usuario -->
                <div class="form-group">
                    <label for="username">Nombre de Usuario:</label>
                    <input type="text" id="username" name="username" value="${username != null ? username : ''}" required>
                </div>

                <!-- Campo de correo electrónico -->
                <div class="form-group">
                    <label for="email">Correo Electrónico:</label>
                    <input type="email" id="email" name="email" value="${email != null ? email : ''}" required>
                </div>

                <!-- Campo de contraseña -->
                <div class="form-group">
                    <label for="password">Contraseña:</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <!-- Campo de fecha de nacimiento -->
                <div class="form-group">
                    <label for="birthDate">Fecha de Nacimiento:</label>
                    <input type="date" id="fechaNacimiento" name="fechaNacimiento" 
                           value="${fechaNacimiento != null ? fechaNacimiento : ''}" required>
                </div>

                <!-- Botón de envío -->
                <div class="form-group">
                    <button type="submit">Registrarse</button>
                </div>
    
            </form>
            <%-- Mensaje de error desde el controlador --%>
            <c:if test="${not empty error}">
                <p class="error-message">${error}</p>
            </c:if>
            <a href='login.jsp' class="back-button">Volver al Login</a>
        </div>
    </body>
</html>


