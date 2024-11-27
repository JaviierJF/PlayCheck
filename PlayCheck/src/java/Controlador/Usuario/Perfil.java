/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador.Usuario;

import Modelo.Entidades.Usuario;
import Modelo.dao.UsuarioJpaController;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Javier
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB

public class Perfil extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vista = "/Usuario/perfil.jsp";  
        int id = Integer.parseInt(request.getParameter("userId"));
        String error = "";

        // Crear EntityManager para acceder a la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PlayCheckPU");
        UsuarioJpaController ujc = new UsuarioJpaController(emf);
        Usuario usuario = ujc.findUsuario(id);

        if (request.getParameter("username") != null) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            usuario.setUsername(username);
            usuario.setEmail(email);

            // Verifica si el formulario tiene un avatar (imagen)
            Part avatarUrl = request.getPart("imagenCliente"); // Nombre del campo de imagen en el formulario
            if (avatarUrl != null && avatarUrl.getSize() > 0) {
                // El usuario ha subido una nueva imagen
                String nombreArchivo = usuario.getUserId() + "_" + avatarUrl.getSubmittedFileName();
                
                // Asegurarse de que la ruta del archivo es correcta
                String directorioDestino = getServletContext().getRealPath("/assets/imgUsuarios/");
                File rutaCompleta = new File(directorioDestino, nombreArchivo);

                // Crear el directorio si no existe
                if (!rutaCompleta.getParentFile().exists()) {
                    rutaCompleta.getParentFile().mkdirs(); // Crear el directorio si no existe
                }

                // Guardar la imagen con el nombre único en la ubicación deseada
                try (InputStream inputStream = avatarUrl.getInputStream();
                     OutputStream outputStream = new FileOutputStream(rutaCompleta)) {

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    error = "Error al guardar la imagen.";
                }

                // Después de guardar la imagen, se actualiza la URL en el usuario
                if (error.isEmpty()) {
                    usuario.setAvatarUrl(nombreArchivo); // Guardamos la ruta relativa
                }
            }

            // Setear la contraseña si no es vacía
            if (password != null && !password.isEmpty()) {
                usuario.setPassword(password);
            }

            try {
                ujc.edit(usuario);  // Actualizamos el usuario en la base de datos
                request.setAttribute("usuario", usuario);
                response.sendRedirect("RecargarUsuario");  // Redirige al home después de guardar los cambios
                return;
            } catch (Exception e) {
                request.setAttribute("error", "Error al actualizar usuario");
                request.setAttribute("usuario", usuario);
            }
        } else {
            request.setAttribute("usuario", usuario);
        }

        // Redirigir al JSP para mostrar los detalles del usuario
        getServletContext().getRequestDispatcher(vista).forward(request, response);
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
