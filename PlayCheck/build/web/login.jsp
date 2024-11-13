<%-- 
    Document   : Login
    Created on : 4 nov 2024, 16:45:56
    Author     : Javier
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar Sesión - PlayCheck</title>
    <link rel="stylesheet" href="assets/css/login.css">
</head>
<body>
    <!-- Nombre de la aplicación fuera del contenedor del formulario -->
    <h1 class="app-name">PlayCheck</h1>
    
    <div class="login-container">
        <h2>Iniciar Sesión</h2>

        <!-- Mensaje de error si las credenciales son incorrectas -->
        <c:if test="${not empty errorMessage}">
            <p class="error-message">${errorMessage}</p>
        </c:if>

        <!-- Formulario de inicio de sesión -->
        <form method="post">
            <label for="username">Nombre de usuario:</label>
            <input type="text" id="username" name="username" required>

            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" required>

            <button type="submit" class="login-button">Iniciar Sesión</button>
        </form>

        <!-- Botón de registro -->
        <button onclick="location.href='Registro'" class="register-button">Registro</button>
    </div>
</body>
</html>

