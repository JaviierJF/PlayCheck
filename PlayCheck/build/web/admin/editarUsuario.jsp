<%-- 
    Document   : editarUsuario
    Created on : 12 nov 2024, 19:29:55
    Author     : Javier
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Editar Usuario</title>
        <link rel="stylesheet" type="text/css" href="assets/css/editarUsuario.css">
    </head>
    <body>

        <div class="container">
            <h2>Editar Usuario</h2>

            <!-- Mostrar error si existe -->
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>

            <!-- Formulario de edición de usuario -->
            <form action="EditarUsuario" method="post">
                <input type="hidden" name="userId" value="${usuario.userId}" />

                <!-- Campo de Nombre de Usuario -->
                <div class="form-group">
                    <label for="username">Nombre de Usuario:</label>
                    <input type="text" id="username" name="username" value="${usuario.username}" required>
                </div>

                <!-- Campo de Correo Electrónico -->
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="${usuario.email}" required>
                </div>

                <!-- Campo de Fecha de Nacimiento -->
                <div class="form-group">
                    <label for="fechaNacimiento">Fecha de Nacimiento:</label>
                    <input type="date" id="fechaNacimiento" name="fechaNacimiento" 
                           value="<fmt:formatDate value='${usuario.fechaNacimiento}' pattern='yyyy-MM-dd' />">
                </div>

                <!-- Campo de Avatar URL -->
                <div class="form-group">
                    <label for="avatar_url">Avatar URL:</label>
                    <div class="input-with-button">
                        <input type="url" id="avatar_url" name="avatar_url" value="${usuario.avatarUrl}" disabled>
                        <button type="button" onclick="clearInput('avatar_url')" class="btn-clear">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"/>
                            <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"/>
                            </svg>
                        </button>
                    </div>
                </div>

                <!-- Campo de Rol -->
                <div class="form-group">
                    <label for="rol">Rol:</label>
                    <select id="rol" name="rol">
                        <option value="user" ${usuario.rol == 'user' ? 'selected' : ''}>Usuario</option>
                        <option value="admin" ${usuario.rol == 'admin' ? 'selected' : ''}>Administrador</option>
                    </select>
                </div>

                <!-- Botón de enviar -->
                <div class="form-group">
                    <button type="submit" class="btn-save">Guardar Cambios</button>
                    <a href="MenuUsuario" class="btn-cancel">Cancelar</a>
                </div>
            </form>
        </div>

        <script>
            function clearInput(inputId) {
                document.getElementById(inputId).value = '';
            }
        </script>
    </body>
</html>

