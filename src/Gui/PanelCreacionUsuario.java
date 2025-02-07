package Gui;

import javax.swing.*;
import java.awt.*;
import Service.UsuarioAdministradorService;
import Service.ServiceException;
import Entidades.Usuario;



public class PanelCreacionUsuario extends JPanel {
    private Usuario usuario;

    public PanelCreacionUsuario(Usuario usuario) {
        this.usuario = usuario;
        setLayout(new GridLayout(7, 1, 10, 10));

        // Fila 1: Título
        JLabel titulo = new JLabel("Crear Usuario", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo);

        // Fila 2: ID
        JPanel panelId = new JPanel();
        panelId.setLayout(new FlowLayout());
        JLabel lblId = new JLabel("ID:");
        JTextField tfId = new JTextField(15);
        panelId.add(lblId);
        panelId.add(tfId);
        add(panelId);

        // Fila 3: Nombre
        JPanel panelNombre = new JPanel();
        panelNombre.setLayout(new FlowLayout());
        JLabel lblNombre = new JLabel("Nombre:");
        JTextField tfNombre = new JTextField(15);
        panelNombre.add(lblNombre);
        panelNombre.add(tfNombre);
        add(panelNombre);

        // Fila 4: Apellido
        JPanel panelApellido = new JPanel();
        panelApellido.setLayout(new FlowLayout());
        JLabel lblApellido = new JLabel("Apellido:");
        JTextField tfApellido = new JTextField(15);
        panelApellido.add(lblApellido);
        panelApellido.add(tfApellido);
        add(panelApellido);

        // Fila 5: Clave
        JPanel panelClave = new JPanel();
        panelClave.setLayout(new FlowLayout());
        JLabel lblClave = new JLabel("Clave:");
        JTextField tfClave = new JTextField(15);
        panelClave.add(lblClave);
        panelClave.add(tfClave);
        add(panelClave);

        // Fila 6: Botones Volver y Crear Usuario
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> volverAAdmin());
        JButton btnCrearUsuario = new JButton("Crear Usuario");
        btnCrearUsuario.addActionListener(e -> crearUsuario(tfId, tfNombre, tfApellido, tfClave));

        panelBotones.add(btnVolver);
        panelBotones.add(btnCrearUsuario);
        add(panelBotones);
    }

    private void volverAAdmin() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelAdm(usuario));  // Volver al PanelAdm con el mismo usuario
        frame.revalidate();
        frame.repaint();
    }

    private void crearUsuario(JTextField tfId, JTextField tfNombre, JTextField tfApellido, JTextField tfClave) {
        try {
            // Obtenemos el ID desde el campo de texto
            int id = Integer.parseInt(tfId.getText());  // Asegúrate de que el ID sea un número válido

            // Obtenemos el nombre, apellido y clave
            String nombre = tfNombre.getText();
            String apellido = tfApellido.getText();
            String clave = tfClave.getText();

            // Llamada al servicio para crear el usuario
            UsuarioAdministradorService service = new UsuarioAdministradorService();
            service.crearUsuario(id, nombre, apellido, clave);  // Pasamos el ID junto con los demás datos

            // Mostramos mensaje de éxito
            JOptionPane.showMessageDialog(this, "Usuario creado con éxito.");
            volverAAdmin(); // Volver al panel administrador

        } catch (ServiceException ex) {
            // Si ocurre un error en el servicio, mostramos el mensaje
            JOptionPane.showMessageDialog(this, "Error al crear usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            // Si el ID no es un número válido, mostramos un mensaje de error
            JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
