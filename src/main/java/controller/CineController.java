package controller;

import java.io.IOException;
import java.util.*;

import entidades.CineService;
import entidades.Pelicula;
import entidades.UsuarioPelicula;
import jakarta.servlet.*;
import jakarta.servlet.http.*;



public class CineController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
    private CineService service;
	
    @Override
    public void init() throws ServletException {
        super.init();
        service = new CineService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String operacion = request.getParameter("operacion");

        String alias = request.getParameter("alias");
        String genero = request.getParameter("genero");
        String valoracionStr = request.getParameter("valoracion");
        double valoracion = 0;
        if (valoracionStr != null && !valoracionStr.isEmpty()) {
            try {
                valoracion = Double.parseDouble(valoracionStr);
            } catch (NumberFormatException e) {
                valoracion = 0;
            }
        }

        // PRG → Redirigir según operación
        HttpSession sesion = request.getSession();

        switch (operacion) {
            case "HOME":
                response.sendRedirect(request.getContextPath() + "/cine?view=home");
                break;

            case "REGISTRAR":
                if (alias != null && !alias.isEmpty() && valoracion > 0 && genero != null) {
                    service.registrarPreferencia(alias, genero, valoracion);
                    sesion.setAttribute("mensaje", "✅ Preferencia registrada correctamente.");
                } else {
                    sesion.setAttribute("mensaje", "⚠️ Datos incompletos. No se registró la preferencia.");
                }
                response.sendRedirect(request.getContextPath() + "/cine?view=home");
                break;

            case "RECOMENDAR":
                if (genero != null) {
                    Pelicula recomendada = service.recomendarPorGenero(genero);
                    sesion.setAttribute("recomendada", recomendada);
                    sesion.setAttribute("generoSel", genero);
                }
                response.sendRedirect(request.getContextPath() + "/cine?view=home");
                break;

            case "CONSULTAR_HISTORIAL":
                if (alias != null && !alias.isEmpty()) {
                    List<UsuarioPelicula> historial = service.consultarHistorial(alias);
                    sesion.setAttribute("historial", historial);
                    sesion.setAttribute("aliasSel", alias);
                    response.sendRedirect(request.getContextPath() + "/cine?view=historial");
                } else {
                    sesion.setAttribute("mensaje", "⚠️ Introduce un alias para consultar tu historial.");
                    response.sendRedirect(request.getContextPath() + "/cine?view=home");
                }
                break;

            case "BORRAR_HISTORIAL":
                if (alias != null && !alias.isEmpty()) {
                    int borradas = service.borrarHistorial(alias);
                    sesion.setAttribute("mensaje", "🗑️ " + borradas + " valoraciones eliminadas.");
                }
                response.sendRedirect(request.getContextPath() + "/cine?view=home");
                break;

            case "TOP_PELICULAS":
                List<Pelicula> top = service.obtenerTopPeliculas();
                sesion.setAttribute("topPeliculas", top);
                response.sendRedirect(request.getContextPath() + "/cine?view=top");
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/cine?view=home");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String view = request.getParameter("view");
        HttpSession sesion = request.getSession();

        // Pasar mensajes o datos desde la sesión al request y limpiar
        if (sesion.getAttribute("mensaje") != null) {
            request.setAttribute("mensaje", sesion.getAttribute("mensaje"));
            sesion.removeAttribute("mensaje");
        }

        RequestDispatcher rd;

        switch (view != null ? view : "home") {
            case "historial":
                request.setAttribute("historial", sesion.getAttribute("historial"));
                request.setAttribute("aliasSel", sesion.getAttribute("aliasSel"));
                rd = request.getRequestDispatcher("/WEB-INF/views/historial.jsp");
                break;

            case "top":
                request.setAttribute("topPeliculas", sesion.getAttribute("topPeliculas"));
                rd = request.getRequestDispatcher("/WEB-INF/views/top.jsp");
                break;

            default:
                request.setAttribute("recomendada", sesion.getAttribute("recomendada"));
                request.setAttribute("generoSel", sesion.getAttribute("generoSel"));
                rd = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
                break;
        }

        rd.forward(request, response);
    }
}
