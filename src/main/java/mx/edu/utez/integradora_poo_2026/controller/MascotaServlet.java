package mx.edu.utez.integradora_poo_2026.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import mx.edu.utez.integradora_poo_2026.model.Mascota;
import mx.edu.utez.integradora_poo_2026.model.dao.MascotaDao;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MascotaServlet", value = "/mascota")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 5,   // 5 MB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
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

        String action = request.getParameter("action");

        try {
            if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                mascotaDao.delete(id);
            } else if ("create".equals(action) || "update".equals(action)) {
                // Recuperar datos del formulario
                String nombre = request.getParameter("nombre");
                String especie = request.getParameter("especie");
                int edad = Integer.parseInt(request.getParameter("edad"));
                String personalidad = request.getParameter("personalidad");
                boolean vacunada = request.getParameter("vacunada") != null;

                // Lógica de subida de imagen
                Part filePart = request.getPart("foto");
                String fileName = filePart.getSubmittedFileName();
                String fotoRuta = request.getParameter("fotoActual"); // Recupera la foto anterior en caso de update

                if (fileName != null && !fileName.isEmpty()) {
                    // Generar un nombre único para evitar sobreescrituras
                    String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                    // Definir la ruta física dentro de la app desplegada
                    String uploadPath = getServletContext().getRealPath("") + File.separator + "assets" + File.separator + "img";

                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) uploadDir.mkdirs();

                    filePart.write(uploadPath + File.separator + uniqueFileName);
                    fotoRuta = "assets/img/" + uniqueFileName;
                }

                Mascota mascota = new Mascota();
                mascota.setNombre(nombre);
                mascota.setEspecie(especie);
                mascota.setEdad(edad);
                mascota.setPersonalidad(personalidad);
                mascota.setFoto(fotoRuta);
                mascota.setVacunada(vacunada);

                if ("create".equals(action)) {
                    mascotaDao.create(mascota);
                } else if ("update".equals(action)) {
                    mascota.setId(Integer.parseInt(request.getParameter("id")));
                    mascotaDao.update(mascota);
                }
            }
        } catch (Exception e) {
            System.err.println("Error procesando la solicitud: " + e.getMessage());
            e.printStackTrace();
        }

        // Patrón PRG: Redirigir al GET evita que al recargar la página se repita la operación
        response.sendRedirect("mascota");
    }
}