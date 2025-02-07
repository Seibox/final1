package Gui;

import javax.swing.*;
import java.awt.*;
import Service.UsuarioAdministradorService;
import Service.ServiceException;
import Entidades.Usuario;

// Este es el panel de creación de cuenta
public class PanelCreacionCuenta extends JPanel {
    private Usuario usuario;
    private JTextField tfCbu;
    private JTextField tfAlias;
    private JComboBox<String> cbTipoCuenta;
    private JComboBox<String> cbMoneda;
    private JTextField tfSaldo;
    private JTextField tfUsuarioId;

    public PanelCreacionCuenta(Usuario usuario) {
        this.usuario = usuario;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 1: Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Crear Nueva Cuenta", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, gbc);

        gbc.gridwidth = 1;

        // Fila 2: CBU y Alias
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("CBU:"), gbc);
        gbc.gridx = 1;
        tfCbu = new JTextField(10);
        add(tfCbu, gbc);

        gbc.gridx = 2;
        add(new JLabel("Alias:"), gbc);
        gbc.gridx = 3;
        tfAlias = new JTextField(10);
        add(tfAlias, gbc);

        // Fila 3: Tipo de cuenta y Moneda
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Tipo de Cuenta:"), gbc);
        gbc.gridx = 1;
        cbTipoCuenta = new JComboBox<>(new String[]{"Caja de Ahorro", "Cuenta Corriente"});
        add(cbTipoCuenta, gbc);

        gbc.gridx = 2;
        add(new JLabel("Moneda:"), gbc);
        gbc.gridx = 3;
        cbMoneda = new JComboBox<>(new String[]{"Pesos", "USD"});
        add(cbMoneda, gbc);

        // Fila 4: Saldo
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Saldo:"), gbc);
        gbc.gridx = 1;
        tfSaldo = new JTextField(10);
        add(tfSaldo, gbc);

        // Fila 5: ID de usuario
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("ID Usuario:"), gbc);
        gbc.gridx = 1;
        tfUsuarioId = new JTextField(10);
        add(tfUsuarioId, gbc);

        // Fila 6: Botones
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JPanel panelBotones = new JPanel();
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> volverAAdm());
        JButton btnCrearCuenta = new JButton("Crear Cuenta");
        btnCrearCuenta.addActionListener(e -> crearCuenta());
        panelBotones.add(btnVolver);
        panelBotones.add(btnCrearCuenta);
        add(panelBotones, gbc);
    }

    private void volverAAdm() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelAdm(usuario));
        frame.revalidate();
        frame.repaint();
    }

    private void crearCuenta() {
        try {
            String cbu = tfCbu.getText();
            String alias = tfAlias.getText();
            String tipoCuenta = (String) cbTipoCuenta.getSelectedItem();
            String moneda = (String) cbMoneda.getSelectedItem();
            double saldo = Double.parseDouble(tfSaldo.getText());
            int usuarioId = Integer.parseInt(tfUsuarioId.getText());

            UsuarioAdministradorService service = new UsuarioAdministradorService();
            service.crearCuenta(cbu, alias, tipoCuenta, moneda, saldo, usuarioId);

            JOptionPane.showMessageDialog(this, "Cuenta creada con éxito.");
            volverAAdm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos. Verifique que los campos sean correctos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, "Error al crear cuenta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
