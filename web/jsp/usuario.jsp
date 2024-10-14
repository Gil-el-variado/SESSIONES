<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usser Page</title>
    </head>
    <body>
        
        <%
            if (session != null) {
                String matricula = (String) session.getAttribute("matricula");
                out.println("Bienvenido Usuario con la matricula:"+matricula);
                
                //Obtenemos el tiempo de la session
                int sessionTime = session.getMaxInactiveInterval(); 
                
                //Hacer la operacion para saber cuando tiempo tenemos restante
                long creacion = session.getCreationTime(); 
                long actual = System.currentTimeMillis(); //en ms
                long termina = (actual - creacion) / 1000;
                long restante = sessionTime - termina; //TIempo restante
                
                //Mostrar tiempo restante
                out.println("<p> Tiempo restante de la sesion: <span id='restante'>" + restante + "</span> segundos </p>");
               
                
            } else {
                out.println("No hay una sesión activa.");
            }
        %>
         <form action="${pageContext.request.contextPath}/login_servlet" method="get">
            <button type="submit">Cerrar sesión</button>
        </form>
            
            <script>
                //Automatizar que al terminar la session direccione al login de nuevo
                let restante = parseInt(document.getElementById('restante').textContent);
                
                const intervalo = setInterval( () => {
                    restante--; 
                    
                    //Actualizar restante
                    document.getElementById('restante').textContent = restante; 
                    
                    //validacion
                    
                    if(restante <= 0){
                        clearInterval(intervalo); 
                        alert("Sesion expirada, adios"); 
                        window.location.href = '<%= request.getContextPath() %>/login_servlet';
                    }
                }, 1000);
            </script>
    </body>
</html>