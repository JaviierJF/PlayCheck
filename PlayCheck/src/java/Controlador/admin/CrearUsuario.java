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
import javax.persistence.RollbackException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Javier
 */
public class CrearUsuario extends HttpServlet {

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
     String vista = "/admin/crearUsuario.jsp";
     String error = "";

        String username = "";
        String email = "";
        String password = "";
        Date fechaNacimiento = null;
        Date fechaRegistro = null;
        String avatarUrl = "";
        String rol = "";

        // Formato de fecha, puedes ajustar según el formato esperado (e.g., "yyyy-MM-dd")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PlayCheckPU");
        if (request.getParameter("username") != null) {
            username = request.getParameter("username");
            email = request.getParameter("email");
            password = request.getParameter("password");

            // Parsear la fecha de nacimiento desde el parámetro
            try {
                fechaNacimiento = dateFormat.parse(request.getParameter("fechaNacimiento"));
            } catch (ParseException e) {
                error = "Formato de fecha de nacimiento incorrecto";
            }

            // Asignar la fecha actual a fechaRegistro
            fechaRegistro = new Date(); // Date ahora contiene la fecha y hora actual

            avatarUrl = "";
            rol = "registrado";

            if (username.isEmpty()) {
                error = "Los campos del usuario no pueden estar vacíos";
            } else {
                Modelo.Entidades.Usuario u = new Modelo.Entidades.Usuario();
                u.setUsername(username);
                u.setEmail(email);
                u.setPassword(Modelo.Entidades.Usuario.getMD5(username));
                u.setFechaNacimiento(fechaNacimiento);
                u.setFechaRegistro(fechaRegistro);
                u.setAvatarUrl(avatarUrl);
                u.setRol(rol);

                UsuarioJpaController ujc = new UsuarioJpaController(emf);
                try {
                    ujc.create(u);
                    response.sendRedirect("MenuUsuario");
                    return;
                } catch (RollbackException e) {
                    error += e.getMessage();
                }
            }
        }

        if (!error.isEmpty()) {
            request.setAttribute("error", error);
            request.setAttribute("email", email);
            request.setAttribute("fechaNacimiento", fechaNacimiento);
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
