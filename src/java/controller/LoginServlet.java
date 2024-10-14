package controller;

import configuration.ConnectionBD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Servlet que maneja el inicio de sesión de usuarios
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login_servlet"})
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    Connection conn;
    PreparedStatement ps;
    Statement statement;
    ResultSet rs;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener la matrícula y password del formulario
        String matricula = request.getParameter("matricula");
        String password = request.getParameter("password");

        // Operaciones con la base de datos
        try {
            ConnectionBD conexion = new ConnectionBD();
            conn = conexion.getConnectionBD();

            // Consulta a la base de datos
            String sql = "SELECT password FROM autenticacion WHERE matricula = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, matricula);

            rs = ps.executeQuery();

            if (rs.next()) {
                String hashPassword = rs.getString("password");
                if (BCrypt.checkpw(password, hashPassword)) {
                    // Crear una sesión
                    HttpSession session = request.getSession();
                    // Guardar la matrícula del usuario en la sesión
                    session.setAttribute("matricula", matricula);
                    session.setMaxInactiveInterval(30); // Sesión de 10 minutos
                    // Redirigir a la página de bienvenida
                    response.sendRedirect("jsp/usuario.jsp");
                } else {
                    // Credenciales incorrectas, volver a login.jsp
                    request.setAttribute("error", "Credenciales incorrectas");
                    request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
                }
            } else {
                // Usuario no encontrado, volver a login.jsp
                request.setAttribute("error", "Usuario no encontrado");
                request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar los recursos: " + e);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener la sesión actual
        HttpSession session = request.getSession(false); // No crear nueva sesión

        if (session != null) {
            // Invalidar la sesión actual
            session.invalidate();
        }

        // Redirigir al usuario a la página de login
        response.sendRedirect("jsp/login.jsp");
    }

}
