<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registrar Usuario</title>
</head>
<body>
    <h2>Registrar Usuario</h2>
    <form action="${pageContext.request.contextPath}/registro_servlet" method="post">
        <label for="matricula">Matrícula:</label>
        <input type="text" id="matricula" name="matricula" required><br>

        <label for="nombre">Nombre Completo:</label>
        <input type="text" id="nombre" name="nombre" required><br>

        <label for="password">Contraseña:</label>
        <input type="password" id="password" name="password" required><br>

        <button type="submit">Registrar</button>
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
