<%-- 
    Document   : homepage
    Created on : 10 nov 2024, 16:52:35
    Author     : Javier
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PlayCheck</title>
        <link rel="stylesheet" href="assets/css/homepage.css">
    </head>
    <body>
        <!-- Navegación -->
        <nav class="navbar">
            <div class="logo">PlayCheck</div>
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

        <!-- Contenido principal con grid de categorías -->
        <div class="main-content">
            <!-- Grid de juegos en tendencia -->
            <div class="category trend">
                <h3>Juegos en Tendencia</h3>
                <div class="item-grid">
                    <!-- Iteramos sobre la lista de juegos en tendencia -->
                    <c:forEach var="game" items="${trendingGames}">
                        <div class="item">                      
                            <img src="${game.imagenUrl}" alt="${game.nombre}" />
                            <h4>${game.nombre}</h4>
                            <p>${game.descripcion}</p>
                            <p><strong>Lanzamiento:</strong> 
                                <fmt:formatDate value="${game.fechaLanzamiento}" pattern="dd/MM/yyyy" />
                            </p>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- Grid de Próximos Juegos -->
            <div class="category new">
                <h3>Próximos Juegos</h3>
                <div class="item-grid">
                    <!-- Iteramos sobre la lista de juegos nuevos -->
                    <c:forEach var="gameNew" items="${newGames}">
                        <div class="item">                      
                            <img src="${gameNew.imagenUrl}" alt="${gameNew.nombre}" />
                            <h4>${gameNew.nombre}</h4>
                            <p>${gameNew.descripcion}</p>
                            <p><strong>Lanzamiento:</strong> 
                                <fmt:formatDate value="${gameNew.fechaLanzamiento}" pattern="dd/MM/yyyy" />
                            </p>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
</html>
