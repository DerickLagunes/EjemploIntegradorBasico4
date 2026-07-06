package mx.edu.utez.integradora_poo_2026.model.dao;

import mx.edu.utez.integradora_poo_2026.model.Dueno;
import mx.edu.utez.integradora_poo_2026.model.Mascota;
import mx.edu.utez.integradora_poo_2026.utils.SQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DuenoDao implements Dao<Dueno, Integer> {

    @Override
    public boolean create(Dueno entidad) {
        String sql = "INSERT INTO DUENOS(nombre, apellidos, correo, contrasena, foto_perfil, codigo_recuperacion) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection con = SQLConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getApellidos());
            ps.setString(3, entidad.getCorreo());
            ps.setString(4, entidad.getContrasena());
            ps.setString(5, entidad.getFoto_perfil());
            ps.setString(6, entidad.getCodigo_recuperacion());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Dueno> getAll() {
        List<Dueno> listaDuenos = new ArrayList<>();
        String sql = "SELECT * FROM DUENOS";

        try (Connection con = SQLConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Dueno d = new Dueno();
                d.setId(rs.getInt("id"));
                d.setNombre(rs.getString("nombre"));
                d.setApellidos(rs.getString("apellidos"));
                d.setCorreo(rs.getString("correo"));
                d.setContrasena(rs.getString("contrasena"));
                d.setFoto_perfil(rs.getString("foto_perfil"));
                d.setCodigo_recuperacion(rs.getString("codigo_recuperacion"));

                // Cargar mascotas del dueño usando la misma conexión activa
                d.setMascotas(getMascotasByDuenoId(d.getId(), con));

                listaDuenos.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDuenos;
    }

    @Override
    public Dueno getById(Integer id) {
        String sql = "SELECT * FROM DUENOS WHERE id = ?";
        try (Connection con = SQLConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Dueno d = new Dueno();
                    d.setId(rs.getInt("id"));
                    d.setNombre(rs.getString("nombre"));
                    d.setApellidos(rs.getString("apellidos"));
                    d.setCorreo(rs.getString("correo"));
                    d.setContrasena(rs.getString("contrasena"));
                    d.setFoto_perfil(rs.getString("foto_perfil"));
                    d.setCodigo_recuperacion(rs.getString("codigo_recuperacion"));

                    // Cargar mascotas del dueño
                    d.setMascotas(getMascotasByDuenoId(d.getId(), con));

                    return d;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Dueno entidad) {
        String sql = "UPDATE DUENOS SET nombre = ?, apellidos = ?, correo = ?, contrasena = ?, foto_perfil = ?, codigo_recuperacion = ? WHERE id = ?";
        try (Connection con = SQLConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getApellidos());
            ps.setString(3, entidad.getCorreo());
            ps.setString(4, entidad.getContrasena());
            ps.setString(5, entidad.getFoto_perfil());
            ps.setString(6, entidad.getCodigo_recuperacion());
            ps.setInt(7, entidad.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM DUENOS WHERE id = ?";
        try (Connection con = SQLConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String correo, String contrasena) {
        // Buscamos si existe un registro que coincida exactamente con ambos campos
        String sql = "SELECT COUNT(*) FROM DUENOS WHERE correo = ? AND contrasena = ?";

        try (Connection con = SQLConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo.trim());
            ps.setString(2, contrasena);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Si el conteo es mayor a 0, las credenciales son correctas
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al intentar realizar el login.");
            e.printStackTrace();
        }
        return false;
    }

    // Método auxiliar interno para poblar la relación 1:N de las mascotas
    private List<Mascota> getMascotasByDuenoId(int duenoId, Connection con) throws SQLException {
        List<Mascota> listaMascotas = new ArrayList<>();
        String sql = "SELECT * FROM MASCOTAS WHERE id_dueno = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, duenoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Mascota m = new Mascota();
                    m.setId(rs.getInt("id"));
                    m.setNombre(rs.getString("nombre"));
                    m.setEspecie(rs.getString("especie"));
                    m.setEdad(rs.getInt("edad"));
                    m.setPersonalidad(rs.getString("personalidad"));
                    m.setFoto(rs.getString("foto"));
                    m.setVacunada(rs.getInt("vacunada") == 1);
                    listaMascotas.add(m);
                }
            }
        }
        return listaMascotas;
    }
}