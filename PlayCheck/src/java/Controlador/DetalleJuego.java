/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Modelo.Entidades.Videojuego;
import Modelo.Entidades.ListaJuegos;
import Modelo.Entidades.Usuario;
import Modelo.dao.ListaJuegosJpaController;
import Modelo.dao.VideojuegoJpaController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Javier
 */
public class DetalleJuego extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String API_KEY = "68d7aeff861b4efaa3e4b58847eb456c";
    private static final String API_URL = "https://api.rawg.io/api/games";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ruta del JSP que mostrará los detalles del juego
        String vista = "/detalleJuego.jsp";

// Variables auxiliares para manejo de datos y errores
        String aux = "";
        String error = "";
        String storeDomain = "";

// Obtener el parámetro 'id' del juego desde la solicitud
        String gameIdParam = request.getParameter("id");

// Obtener la sesión del usuario
        HttpSession session = request.getSession();

        try {
            // Si el parámetro 'id' está presente en la solicitud, guardarlo en la sesión
            if (gameIdParam != null) {
                session.setAttribute("id2", gameIdParam);
            } else {
                // Recuperar el 'id' desde la sesión si no está en la solicitud
                gameIdParam = (String) session.getAttribute("id2");
            }

            // Validar que el 'id' del juego esté presente
            if (gameIdParam == null) {
                request.setAttribute("error", "ID de juego no proporcionada.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            // Convertir el 'id' del juego a un número entero
            int gameId = Integer.parseInt(gameIdParam);

            // Llamada a la API para obtener detalles del juego por su ID
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                // Configuración de la conexión a la API
                URL urlGameDetails = new URL(API_URL + "/" + gameId + "?key=" + API_KEY);
                connection = (HttpURLConnection) urlGameDetails.openConnection();
                connection.setRequestMethod("GET");

                // Comprobar si la respuesta de la API es exitosa
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Leer y construir la respuesta JSON de la API
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder json = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        json.append(line);
                    }
                    String jsonResponse = json.toString();

                    // Parsear la respuesta JSON para extraer datos del juego
                    JSONObject gameJson = new JSONObject(jsonResponse);
                    Videojuego game = new Videojuego();
                    game.setJuegoId(gameJson.getInt("id"));
                    game.setNombre(gameJson.getString("name"));
                    game.setFechaLanzamiento(convertirStringAFecha(gameJson.getString("released")));
                    game.setImagenUrl(gameJson.getString("background_image"));
                    game.setDescripcion(gameJson.getString("description"));

                    // Manejo de la puntuación de Metacritic (si existe)
                    if (gameJson.has("metacritic") && !gameJson.isNull("metacritic")) {
                        game.setMetacritic(String.valueOf(gameJson.getInt("metacritic")));
                    } else {
                        game.setMetacritic("No disponible");
                    }

                    // Obtener enlaces de compra del juego desde las tiendas disponibles
                    if (gameJson.has("stores") && !gameJson.isNull("stores")) {
                        JSONArray storesArray = gameJson.getJSONArray("stores");
                        for (int i = 0; i < storesArray.length(); i++) {
                            JSONObject storeObject = storesArray.getJSONObject(i);
                            if (storeObject.has("store") && !storeObject.isNull("store")) {
                                JSONObject storeDetails = storeObject.getJSONObject("store");
                                String storeName = storeDetails.optString("name", "Tienda desconocida");
                                storeDomain = storeDetails.optString("domain", "#");
                                aux += "<a href='https://" + storeDomain + "' target='_blank'>" + storeName + "</a> ";
                            }
                        }
                        game.setEnlaceCompra(aux);
                    } else {
                        game.setEnlaceCompra("<p>Tienda no disponible</p>");
                    }

                    // Obtener las etiquetas (tags) del juego
                    String tagsNames = "";
                    if (gameJson.has("tags") && !gameJson.isNull("tags")) {
                        JSONArray tagsArray = gameJson.getJSONArray("tags");
                        for (int i = 0; i < tagsArray.length(); i++) {
                            JSONObject tagObject = tagsArray.getJSONObject(i);
                            tagsNames += tagObject.getString("name");
                            if (i < tagsArray.length() - 1) {
                                tagsNames += ", ";
                            }
                        }
                    } else {
                        tagsNames = "Etiquetas no disponibles";
                    }
                    game.setEtiquetas(tagsNames);

                    // Obtener la plataforma principal del juego
                    JSONObject platformObject = gameJson.getJSONArray("platforms").getJSONObject(0);
                    String plataforma = platformObject.getJSONObject("platform").getString("name");
                    game.setDesarrollador(plataforma);

                    // Obtener los géneros del juego
                    String genreName = "";
                    if (gameJson.has("genres") && !gameJson.isNull("genres")) {
                        JSONArray genresArray = gameJson.getJSONArray("genres");
                        for (int i = 0; i < genresArray.length(); i++) {
                            JSONObject genreObject = genresArray.getJSONObject(i);
                            genreName += genreObject.getString("name");
                            if (i < genresArray.length() - 1) {
                                genreName += ", ";
                            }
                        }
                    } else {
                        genreName = "Géneros no disponibles";
                    }
                    game.setGenero(genreName);

                    // Persistir los datos del juego en la base de datos si no existen
                    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PlayCheckPU");
                    VideojuegoJpaController vjc2 = new VideojuegoJpaController(emf);
                    if (vjc2.findVideojuegoByNombre(game.getNombre()) == null) {
                        vjc2.create(game);
                    }

                    // Pasar los datos del juego al JSP
                    request.setAttribute("game", game);

                } else {
                    // Manejar error si la API no devuelve una respuesta exitosa
                    request.setAttribute("error", "No se pudo obtener los detalles del juego.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }

            } finally {
                // Cerrar recursos de conexión
                if (reader != null) {
                    reader.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }

            // Manejo del estado del juego y asociación con el usuario
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("PlayCheckPU");
            ListaJuegosJpaController ljc = new ListaJuegosJpaController(emf);
            VideojuegoJpaController vjc = new VideojuegoJpaController(emf);
            ListaJuegos listajuego = new ListaJuegos();

            String estado = request.getParameter("estadoJuego");

            
            listajuego.setListaId(ljc.findListaJuegosByVideojuegoId(vjc.findVideojuego(Integer.parseInt((String) session.getAttribute("id2")))).getListaId());
            listajuego.setEstado(estado);
            session.setAttribute("estadoJuego", ljc.findListaJuegosByVideojuegoId(vjc.findVideojuego(Integer.parseInt((String) session.getAttribute("id2")))).getEstado());
            listajuego.setJuegoId(vjc.findVideojuego(Integer.parseInt((String) session.getAttribute("id2"))));
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            listajuego.setUserId(usuario);
                      
            if (ljc.findListaJuegosByVideojuegoId(vjc.findVideojuego(Integer.parseInt((String) session.getAttribute("id2")))) == null) {
                try {
                    // Guardar la relación del juego con el estado en la base de datos
                    ljc.create(listajuego);
                    response.sendRedirect("DetalleJuego");
                    return;
                } catch (RollbackException e) {
                    error += e.getMessage();
                }
            } else {
                try {
                    ljc.edit(listajuego);
                    response.sendRedirect("DetalleJuego");
                    return;
                } catch (RollbackException e) {
                    error += e.getMessage();
                }
            }
        } catch (Exception e) {
            // Manejo de errores generales
            request.setAttribute("error", "Error al procesar la solicitud.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

// Redirigir al JSP con los detalles del juego
        getServletContext().getRequestDispatcher(vista).forward(request, response);
    }

    // Método para convertir la fecha de String a Date
    private Date convertirStringAFecha(String fechaString) {
        Date fecha = null;
        try {
            // Asumimos que la fecha en formato String sigue el patrón "yyyy-MM-dd"
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            fecha = formatoFecha.parse(fechaString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fecha;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
