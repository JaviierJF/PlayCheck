<%-- 
    Document   : detalleJuego
    Created on : 23 nov 2024, 17:29:51
    Author     : Javier
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="es">
    <head>
        <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Detalles del Juego</title>
                <link rel="stylesheet" href="assets/css/detalleJuego.css">
                    </head>
                    <body>
                        <!-- Navegación -->
                        <nav class="navbar">
                            <div class="logo"><a href="HomePage"><img src="assets/img/PlayCheck.png" width="70px" height="70px" alt="alt"/></a></div>
                            <div class="nav-links">
                                <a href="HomePage">Home</a>
                                <a href="#">Lista de Juegos</a>
                                <a href="#">Tops</a>
                                <a href="#">Blog</a>
                            </div>
                            <div class="nav-right">
                                <input type="text" class="search-bar" placeholder="Buscar...">

                                    <!-- Menú de perfil con desplegable -->
                                    <div class="profile-dropdown">
                                        <c:choose>
                                            <c:when test="${usuario != null}">
                                                <span class="text-light"><c:out value="${usuario.username}"/>&nbsp;&nbsp;</span>
                                                <c:choose>
                                                    <c:when test="${usuario.avatarUrl != '' && usuario.avatarUrl != null}">
                                                        <img src="assets/imgUsuarios/<c:out value="${usuario.avatarUrl}"/>" 
                                                             class="profile-pic" alt="${usuario.avatarUrl}" width="36" height="36"/>
                                                        <span></span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" fill="currentColor" 
                                                             class="bi bi-person-circle profile-icon" viewBox="0 0 16 16">
                                                            <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                                            <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                                                        </svg>
                                                    </c:otherwise>
                                                </c:choose>
                                                <!-- Opciones del menú cuando el usuario está autenticado -->
                                                <div class="dropdown-menu">
                                                    <a href="perfil.jsp">Perfil</a>
                                                    <a href="MenuUsuario">Menu Usuario</a>
                                                    <a href="CerrarSesion">Cerrar sesión</a>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" fill="currentColor" 
                                                     class="bi bi-person-circle profile-icon" viewBox="0 0 16 16">
                                                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                                    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                                                </svg>
                                                <!-- Opciones del menú cuando el usuario no está autenticado -->
                                                <div class="dropdown-menu">
                                                    <a href="Login">Login</a>
                                                    <a href="Registro">Registrarse</a>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                            </div>
                        </nav>
                        <div id="successMessage" class="success-message" style="display:none;">
                            Juego añadido a tu lista correctamente
                        </div>
                        <div class="container">
                            <!-- Mostrar los detalles del juego -->
                            <c:if test="${not empty game}">
                                <div class="game-detail">
                                    <h1>${game.nombre}</h1>
                                    <img src="${game.imagenUrl}" alt="${game.nombre}">
                                        <c:choose>
                                            <c:when test="${usuario != null && (usuario.rol == 'admin' || usuario.rol == 'registrado')}">
                                                <!-- Formulario de estado del juego -->
                                                <form action="DetalleJuego" method="post">
                                                    <input type="hidden" name="gameId" value="${game.juegoId}"/>
                                                    <select name="estadoJuego" id="estadoJuego" class="select-status" onchange="this.form.submit()">
                                                        <option value="" disabled selected>Selecciona el estado:</option>
                                                        <c:choose>
                                                            <c:when test="${estadoJuego eq 'Jugando'}">
                                                                <option value="Jugando" selected>Jugando</option>
                                                                <option value="Completado">Completado</option>
                                                                <option value="Pendiente">Pendiente</option>
                                                            </c:when>
                                                            <c:when test="${estadoJuego eq 'Completado'}">
                                                                <option value="Jugando">Jugando</option>
                                                                <option value="Completado" selected>Completado</option>
                                                                <option value="Pendiente">Pendiente</option>
                                                            </c:when>
                                                            <c:when test="${estadoJuego eq 'Pendiente'}">
                                                                <option value="Jugando">Jugando</option>
                                                                <option value="Completado">Completado</option>
                                                                <option value="Pendiente" selected>Pendiente</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="Jugando">Jugando</option>
                                                                <option value="Completado">Completado</option>
                                                                <option value="Pendiente">Pendiente</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </select>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <h2>Si quieres añadir este juego a tu lista de juego <a href="Login">inicia sesión</h2></p>
                                            </c:otherwise>
                                        </c:choose>
                                        <p><strong>Plataforma:</strong> ${game.desarrollador}</p>
                                        <p><strong>Metacritic:</strong> ${game.metacritic}</p>
                                        <p><strong>Género:</strong> ${game.genero}</p>
                                        <p><strong>Enlace de compra: </strong> ${game.enlaceCompra}</p>
                                        <p><strong>Etiquetas: </strong> ${game.etiquetas}</p>
                                        <p><strong>Fecha de lanzamiento:</strong> 
                                            <fmt:formatDate value="${game.fechaLanzamiento}" pattern="dd/MM/yyyy" />
                                        </p>
                                        <p><strong>Descripción:</strong> ${game.descripcion}</p>

                                </div>
                            </c:if>
                        </div>
                        <script>
                            // Función para mostrar el mensaje
                            function showSuccessMessage() {
                                var successMessage = document.getElementById('successMessage');
                                successMessage.style.display = 'block';

                                // Después de 3 segundos, ocultar el mensaje
                                setTimeout(function () {
                                    successMessage.style.display = 'none';
                                }, 3000);
                            }

                            // Llamamos a la función cuando el estado del juego cambie
                            document.getElementById('estadoJuego').addEventListener('change', showSuccessMessage);
                        </script>
                    </body>
                    </html>
