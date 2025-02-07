package Entidades;

public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String clave;
    private String tipoUsuario; // "normal" o "administrador"

    // Constructor
    public Usuario(int id, String nombre, String apellido, String clave, String tipoUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.clave = clave;
        this.tipoUsuario = tipoUsuario;
    }

    // Métodos abstractos (serán implementados en las subclases)

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
