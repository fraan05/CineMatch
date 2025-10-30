package controller;

import java.io.IOException;
import java.util.*;

import entidades.CineService;
import entidades.Pelicula;
import entidades.UsuarioPelicula;
import jakarta.servlet.*;
import jakarta.servlet.http.*;



public class CineController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CineService cineService;

    @Override
    public void init() throws ServletException {
        cineService = new CineService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String alias = request.getParameter("alias");
        String genero = request.getParameter("genero");
        String operacion = request.getParameter("operacion");
        double valoracion = 0;
        try {
            valoracion = Double.parseDouble(request.getParameter("valoracion"));
        } catch (Exception e) {
            valoracion = 0;
        }

        HttpSession session = request.getSession();
        String redirectUrl = "CineController?view=home"; // vista por defecto

        switch (operacion) {

            case "REGISTRAR":
                cineService.registrarPreferencia(alias, genero, valoracion);
                session.setAttribute("mensaje", "Preferencia registrada correctamente.");
                redirectUrl = "CineController?view=home";
                break;

            case "RECOMENDAR":
                Pelicula recomendada = cineService.recomendarPorGenero(genero);
                session.setAttribute("recomendada", recomendada);
                redirectUrl = "CineController?view=home";
                break;

            case "CONSULTAR_HISTORIAL":
                List<UsuarioPelicula> historial = cineService.consultarHistorial(alias);
                session.setAttribute("historial", historial);
                redirectUrl = "CineController?view=historial";
                break;

            case "BORRAR_HISTORIAL":
                cineService.borrarHistorial(alias);
                session.setAttribute("mensaje", "Historial borrado correctamente.");
                redirectUrl = "CineController?view=home";
                break;

            case "TOP_PELICULAS":
                List<Pelicula> top = cineService.obtenerTopPeliculas();
                session.setAttribute("top", top);
                redirectUrl = "CineController?view=top";
                break;

            case "HOME":
            default:
                redirectUrl = "CineController?view=home";
                break;
        }


        response.sendRedirect(redirectUrl);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String view = request.getParameter("view");
        String destino = "/WEB-INF/views/home.jsp"; // vista por defecto

        if (view == null) view = "home";

        switch (view) {
            case "HISTORIAL":
                request.setAttribute("historial", session.getAttribute("historial"));
                destino = "/WEB-INF/views/historial.jsp";
                break;

            case "TOP":
                request.setAttribute("top", session.getAttribute("top"));
                destino = "/WEB-INF/views/top.jsp";
                break;

            default:
                request.setAttribute("mensaje", session.getAttribute("mensaje"));
                request.setAttribute("recomendada", session.getAttribute("recomendada"));
                destino = "/WEB-INF/views/home.jsp";
                break;
        }

        getServletContext().getRequestDispatcher(destino).forward(request, response);
    }
}