<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar Sesión</title>
</head>
<body>
    <h2>Iniciar Sesión</h2>
    <!-- En action se coloca el nombre del servlet LoginServlet.java -->
    <form action="${pageContext.request.contextPath}/login_servlet" method="post">
        <label for="username">Matricula:</label>
        <input type="text" id="matricula" name="matricula" required><br>

        <label for="password">Contraseña:</label>
        <input type="password" id="password" name="password" required><br>

        <button type="submit">Iniciar Sesión</button>
    </form>

    <!-- Mostrar mensajes de error si existen -->
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <p style="color: red;"><%= error %></p>
    <%
        }
    %>
</body>
</html>
