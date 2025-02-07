package Gui;

import javax.swing.*;
import java.awt.*;
import Service.UsuarioService;
import DAO.DAOException;
import Entidades.Usuario;

public class PanelInicio extends JPanel {
    private JTextField textFieldId;
    private JPasswordField passwordField;

    public PanelInicio() {
        setLayout(new GridLayout(4, 1, 10, 10)); // 4 filas, 1 columna, 10px de espacio entre los componentes

        // Fila 1: Título
        JLabel titulo = new JLabel("Bienvenido al Sistema Home Banking", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo);

        // Fila 2: ID Usuario
        JPanel panelId = new JPanel();
        panelId.setLayout(new FlowLayout(FlowLayout.CENTER)); // Cambio aquí para centrar
        JLabel labelId = new JLabel("ID Usuario:");
        textFieldId = new JTextField(20);
        panelId.add(labelId);
        panelId.add(textFieldId);
        add(panelId);

        // Fila 3: PWD Usuario
        JPanel panelPwd = new JPanel();
        panelPwd.setLayout(new FlowLayout(FlowLayout.CENTER)); // Cambio aquí para centrar
        JLabel labelPwd = new JLabel("PWD Usuario:");
        passwordField = new JPasswordField(20);
        panelPwd.add(labelPwd);
        panelPwd.add(passwordField);
        add(panelPwd);

        // Fila 4: Botón Ingresar
        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.addActionListener(e -> ingresar());
        add(btnIngresar);
    }

    private void ingresar() {
        String idUsuario = textFieldId.getText();
        String password = new String(passwordField.getPassword());

        UsuarioService usuarioService = new UsuarioService();

        try {
            Usuario usuario = usuarioService.verificarCredenciales(idUsuario, password);

            if (usuario != null) {
                String tipoUsuario = usuario.getTipoUsuario().trim(); // Asegurar que no haya espacios adicionales
                System.out.println("Tipo de usuario obtenido: " + tipoUsuario); // Verificar en consola

                if ("Normal".equalsIgnoreCase(tipoUsuario)) {
                    mostrarPanel(new PanelNormal(usuario));
                } else if ("Administrador".equalsIgnoreCase(tipoUsuario)) {
                    mostrarPanel(new PanelAdm(usuario));
                } else {
                    JOptionPane.showMessageDialog(this, "Tipo de usuario no reconocido: " + tipoUsuario);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
            }

        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "Error al verificar las credenciales.");
        }
    }

    private void mostrarPanel(JPanel panel) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        if (frame != null) {
            frame.setContentPane(panel);
            frame.revalidate();
            frame.repaint();
            panel.setVisible(true);
        } else {
            System.err.println("Error: No se encontró el JFrame principal.");
        }
    }
}
