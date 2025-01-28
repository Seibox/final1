package Entidades;

public abstract class Usuario {
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
    public abstract void transferir();
    public abstract void usarTarjeta();
    public abstract void pagarTarjeta();

    // Métodos adicionales (solo para administradores, implementados en UsuarioAdministrador)
    public void crearUsuario() {
        throw new UnsupportedOperationException("Operación no soportada para este tipo de usuario.");
    }

    public void crearTarjeta() {
        throw new UnsupportedOperationException("Operación no soportada para este tipo de usuario.");
    }

    public void crearCuenta() {
        throw new UnsupportedOperationException("Operación no soportada para este tipo de usuario.");
    }

    // Getters y Setters (se agregarán más adelante si los necesitas)
}
