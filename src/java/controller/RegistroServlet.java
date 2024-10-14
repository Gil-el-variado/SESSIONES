package controller;

import configuration.ConnectionBD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Servlet que maneja el registro de usuarios
 */
@WebServlet(name = "RegistroServlet", urlPatterns = {"/registro_servlet"})
public class RegistroServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Obtener los datos del formulario
        String matricula = request.getParameter("matricula");
        String nombre = request.getParameter("nombre");
        String password = request.getParameter("password");
        
        // Generar el hash de la contraseña usando BCrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        
        // Insertar el usuario en la base de datos
        try (Connection conn = new ConnectionBD().getConnectionBD()) {
            String sql = "INSERT INTO autenticacion (matricula, password, nombre) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, matricula);
            ps.setString(2, hashedPassword);
            ps.setString(3, nombre);
            ps.executeUpdate();
            
            // Redirigir a una página de éxito o login
            response.sendRedirect("/http_session/jsp/usuario_registrado.jsp");
        } catch (Exception e) {
            // Si ocurre un error, mostrar un mensaje de error
            request.setAttribute("error", "Error al registrar el usuario: " + e.getMessage());
            request.getRequestDispatcher("/http_session/jsp/registro.jsp").forward(request, response);
        }
    }
}
