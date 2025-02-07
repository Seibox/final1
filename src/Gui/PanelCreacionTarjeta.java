package Gui;

import javax.swing.*;
import java.awt.*;
import Service.UsuarioAdministradorService;
import Service.ServiceException;
import Entidades.Usuario;

public class PanelCreacionTarjeta extends JPanel {
    private Usuario usuario;
    private JTextField tfNumeroTarjeta;
    private JComboBox<String> cbDescripcion;
    private JTextField tfUsuarioId;

    public PanelCreacionTarjeta(Usuario usuario) {
        this.usuario = usuario;
        setLayout(new GridLayout(5, 2, 10, 10));

        // Fila 1: Título
        JLabel titulo = new JLabel("Crear Nueva Tarjeta", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo);
        add(new JLabel());

        // Fila 2: Número de Tarjeta
        JLabel lblNumeroTarjeta = new JLabel("Número de Tarjeta:");
        tfNumeroTarjeta = new JTextField(16);
        add(lblNumeroTarjeta);
        add(tfNumeroTarjeta);

        // Fila 3: Descripción (Tipo de Tarjeta)
        JLabel lblDescripcion = new JLabel("Descripción:");
        cbDescripcion = new JComboBox<>(new String[]{"Visa Débito", "Visa Crédito", "Master Débito", "Master Crédito"});
        add(lblDescripcion);
        add(cbDescripcion);

        // Fila 4: ID Usuario
        JLabel lblUsuarioId = new JLabel("ID Usuario:");
        tfUsuarioId = new JTextField(10);
        add(lblUsuarioId);
        add(tfUsuarioId);

        // Fila 5: Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> volverAAdm());
        JButton btnCrearTarjeta = new JButton("Crear Tarjeta");
        btnCrearTarjeta.addActionListener(e -> crearTarjeta());

        panelBotones.add(btnVolver);
        panelBotones.add(btnCrearTarjeta);
        add(panelBotones);
    }

    private void volverAAdm() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelAdm(usuario));
        frame.revalidate();
        frame.repaint();
    }

    private void crearTarjeta() {
        try {
            int numeroTarjeta = Integer.parseInt(tfNumeroTarjeta.getText());
            String descripcion = (String) cbDescripcion.getSelectedItem();
            int usuarioId = Integer.parseInt(tfUsuarioId.getText());

            // Llamada al servicio para crear la tarjeta
            UsuarioAdministradorService service = new UsuarioAdministradorService();
            service.crearTarjeta(numeroTarjeta, descripcion, usuarioId);

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, "Tarjeta creada con éxito.");
            volverAAdm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos. Verifique que los campos sean correctos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, "Error al crear tarjeta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
