<%-- 
    Document   : perfil
    Created on : 24 nov 2024, 17:43:39
    Author     : Javier
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Perfil</title>
    <link rel="stylesheet" type="text/css" href="assets/css/editarUsuario.css">
</head>
<body>

<div class="container">
    <h2>Perfil</h2>

    <!-- Mostrar error si existe -->
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <!-- Formulario de edición de usuario -->
    <form action="Perfil" method="post" enctype="multipart/form-data">
        <input type="hidden" name="userId" value="${usuario.userId}" />

        <!-- Campo de Nombre de Usuario -->
        <div class="form-group">
            <label for="username">Nombre de Usuario:</label>
            <input type="text" id="username" name="username" value="${usuario.username}" required>
        </div>

        <!-- Campo de Contraseña-->
        <div class="form-group">
            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" value="" required>
        </div>

        <!-- Campo de Correo Electrónico -->
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${usuario.email}" required>
        </div>

        <!-- Campo de Avatar URL -->
        <div class="form-group">
            <c:choose>
                <c:when test="${usuario.avatarUrl != '' && usuario.avatarUrl != null}">
                    <img id="imagenPerfil" src="<c:out value="assets/imgUsuarios/${usuario.avatarUrl}"/>" class="rounded-circle" width="250px" height="250px" alt="Imagen de perfil"/>
                </c:when>
                <c:otherwise>
                    <svg xmlns="http://www.w3.org/2000/svg" width="250px" height="250px" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                        <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                    </svg>
                </c:otherwise>
            </c:choose>
            <input class="form-control form-control" type="file" name="imagenCliente" id="file" accept=".png, .jpg, .jpeg, .gif" style="margin-right: 32px;padding-right: 0px;padding-left: 10px; display: none;">
            <label for="file" class="btn btn-secondary ms-5 mt-3" style="cursor: pointer;">Cambiar Imagen</label>
        </div>

        <!-- Botón de enviar -->
        <div class="form-group">
            <button type="submit" class="btn-save">Guardar Cambios</button>
            <a href="HomePage" class="btn-cancel">Cancelar</a>
        </div>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function () {
        // Obtener el campo input file
        var inputImagen = document.getElementById('file');

        // Agregar un evento de escucha al campo input file
        inputImagen.addEventListener('change', function (e) {
            // Obtener el archivo seleccionado
            var archivo = e.target.files[0];

            // Crear un objeto URL con el archivo seleccionado
            var avatarUrl = URL.createObjectURL(archivo);

            // Actualizar el atributo src del elemento img con la URL de la imagen
            $('#imagenPerfil').attr('src', avatarUrl);
        });
    });
</script>

</body>
</html>