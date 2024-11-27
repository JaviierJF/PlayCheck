/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Modelo.Entidades.Videojuego;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Javier
 */
public class HomePage extends HttpServlet {

    private static final String API_KEY = "68d7aeff861b4efaa3e4b58847eb456c";
    private static final String API_URL = "https://api.rawg.io/api/games";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vista = "/homepage.jsp";
        try (PrintWriter out = response.getWriter()) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                // Obtener los juegos en tendencia(Se puede ordenar por: name, released, added, created, updated, rating, metacritic)
                URL urlTrending = new URL(API_URL + "?key=" + API_KEY + "&page_size=12&ordering=+rating");
                connection = (HttpURLConnection) urlTrending.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder json = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        json.append(line);
                    }
                    String jsonResponse = json.toString();

                    // Parsear la respuesta JSON
                    JSONArray results = new JSONObject(jsonResponse).getJSONArray("results");

                    // Crear una lista de objetos Game a partir de los resultados
                    List<Videojuego> trendingGames = new ArrayList<>();
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject gameJson = results.getJSONObject(i);
                        Videojuego game = new Videojuego();
                        game.setJuegoId(gameJson.getInt("id"));
                        game.setNombre(gameJson.getString("name"));
                        game.setFechaLanzamiento(convertirStringAFecha(gameJson.getString("released")));
                        game.setImagenUrl(gameJson.getString("background_image"));                      
                        trendingGames.add(game);
                    }

                    // Pasar la lista de juegos en tendencia a la solicitud
                    request.setAttribute("trendingGames", trendingGames);
                } else {
                    request.setAttribute("error", "No se pudo obtener la lista de juegos.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }

                LocalDate now = LocalDate.now();
                LocalDate oneYearAgo = now.minusYears(1); // Fecha de hace un año
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Máscara de la fecha
                String oneYearAgoStr = oneYearAgo.format(formatter); //Transformamos la fecha en string para usarlo en la url

                // Obtener los próximos juegos del próximo año(Se puede ordenar por: name, released, added, created, updated, rating, metacritic)
                URL urlNew = new URL(API_URL + "?key=" + API_KEY + "&page_size=12&ordering=-released&dates=" + oneYearAgoStr + ",2025-09-01");
                connection = (HttpURLConnection) urlNew.openConnection();
                connection.setRequestMethod("GET");

                responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder jsonNew = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonNew.append(line);
                    }
                    String jsonResponseNew = jsonNew.toString();

                    // Parsear la respuesta JSON para los juegos nuevos
                    JSONArray resultsNew = new JSONObject(jsonResponseNew).getJSONArray("results");

                    // Crear una lista de objetos Game a partir de los resultados
                    List<Videojuego> newGames = new ArrayList<>();
                    for (int i = 0; i < resultsNew.length(); i++) {
                        JSONObject gameJson = resultsNew.getJSONObject(i);
                        Videojuego game = new Videojuego();
                        game.setJuegoId(gameJson.getInt("id"));
                        game.setNombre(gameJson.getString("name"));
                        game.setFechaLanzamiento(convertirStringAFecha(gameJson.getString("released")));

                        // Comprobar si el campo 'background_image' existe y tiene un valor
                        String imageUrl = gameJson.optString("background_image", null);
                        if (imageUrl == null || imageUrl.isEmpty()) {
                            imageUrl = "URL_DE_IMAGEN_DEFAULT";  // Aquí pones una URL de imagen predeterminada o una cadena vacía
                        }
                        game.setImagenUrl(imageUrl);

                        newGames.add(game);
                    }

                    // Pasar la lista de juegos nuevos a la solicitud
                    request.setAttribute("newGames", newGames);
                } else {
                    request.setAttribute("error", "No se pudo obtener la lista de juegos nuevos.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }

            } finally {
                if (reader != null) {
                    reader.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            getServletContext().getRequestDispatcher(vista).forward(request, response);
        }
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

// Para mostrar la fecha en formato "dd/MM/yyyy"
    private String formatearFecha(Date fecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        return formatoFecha.format(fecha);
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
