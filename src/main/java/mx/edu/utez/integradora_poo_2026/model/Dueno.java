package mx.edu.utez.integradora_poo_2026.model;

import java.util.List;

public class Dueno {

    private int id;
    private String nombre;
    private String apellidos;
    private List<Mascota> mascotas;
    private String correo;
    private String contrasena;
    private String foto_perfil;

    public Dueno() {
    }

    public Dueno(int id, String nombre, String apellidos, List<Mascota> mascotas, String correo, String contrasena, String foto_perfil) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.mascotas = mascotas;
        this.correo = correo;
        this.contrasena = contrasena;
        this.foto_perfil = foto_perfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public String getMascotasId(){
        java.util.StringJoiner sj = new java.util.StringJoiner(",");
        for(Mascota m : mascotas){
            sj.add(String.valueOf(m.getId()));
        }
        return sj.toString();
    }

    @Override
    public String toString() {
        return nombre + ',' + apellidos + ',' + this.getMascotasId() +
                ',' + correo + ',' + contrasena + ',' + foto_perfil;
    }
}
