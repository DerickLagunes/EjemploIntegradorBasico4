package mx.edu.utez.integradora_poo_2026.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.utez.integradora_poo_2026.model.Mascota;
import mx.edu.utez.integradora_poo_2026.model.dao.MascotaDao;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "MascotaServlet", value = "/mascota")
public class MascotaServlet extends HttpServlet {

    private final MascotaDao mascotaDao = new MascotaDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Mascota> lista = mascotaDao.getAll();
        request.setAttribute("listaMascotas", lista);
        request.getRequestDispatcher("gestion-mascotas.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            String nombre = request.getParameter("nombre");
            String especie = request.getParameter("especie");
            int edad = Integer.parseInt(request.getParameter("edad"));
            String personalidad = request.getParameter("personalidad");
            String foto = request.getParameter("foto");
            boolean vacunada = request.getParameter("vacunada") != null;

            Mascota nuevaMascota = new Mascota();
            nuevaMascota.setNombre(nombre);
            nuevaMascota.setEspecie(especie);
            nuevaMascota.setEdad(edad);
            nuevaMascota.setPersonalidad(personalidad);
            nuevaMascota.setFoto(foto);
            nuevaMascota.setVacunada(vacunada);

            mascotaDao.create(nuevaMascota);
        } catch (NumberFormatException e) {
            System.err.println("Error al transformar datos numéricos en el registro: " + e.getMessage());
            e.printStackTrace();
        }

        response.sendRedirect("mascota");
    }
}