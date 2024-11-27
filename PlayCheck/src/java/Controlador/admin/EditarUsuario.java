/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador.admin;

import Modelo.Entidades.Usuario;
import Modelo.dao.UsuarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Javier
 */
public class EditarUsuario extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String vista = "/admin/editarUsuario.jsp";
        int id = Integer.parseInt(request.getParameter("userId"));
        String error = "";

        // Formato de fecha, puedes ajustar según el formato esperado (e.g., "yyyy-MM-dd")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PlayCheckPU");
        UsuarioJpaController ujc = new UsuarioJpaController(emf);
        Usuario usuario = ujc.findUsuario(id);
        Date fechaNacimiento = null;
        if (request.getParameter("username") != null) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            // Parsear la fecha de nacimiento desde el parámetro
            try {
               fechaNacimiento = dateFormat.parse(request.getParameter("fechaNacimiento"));
            } catch (ParseException e) {
                error = "Formato de fecha de nacimiento incorrecto";
            }
            String avatar_url = request.getParameter("avatar_url");
            String rol = request.getParameter("rol");

            usuario.setUsername(username);
            usuario.setEmail(email);
            usuario.setFechaNacimiento((Date) fechaNacimiento);
            usuario.setAvatarUrl(avatar_url);
            usuario.setRol(rol);
            try {
                ujc.edit(usuario);
                response.sendRedirect("MenuUsuario");
                return;
            } catch (Exception e) {
                request.setAttribute("error", "Error al actualizar usuario");
                request.setAttribute("usuario", usuario);
            }
        } else {
            request.setAttribute("usuario", usuario);
        }
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
