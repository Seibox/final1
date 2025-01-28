package DAO;

public class DAOException extends Exception {
    // Constructor con mensaje de error
    public DAOException(String message) {
        super(message);
    }

    // Constructor con mensaje de error y causa (otra excepción)
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}