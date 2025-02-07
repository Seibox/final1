package Gui;

import javax.swing.*;
import java.awt.*;
import Service.CuentaService;
import DAO.DAOException;
import Entidades.Cuenta;
import Entidades.Usuario;
import Service.ServiceException;

public class PanelVerCuentas extends JPanel {
    private JComboBox<String> comboCbu;
    private JLabel labelAlias;
    private JLabel labelMoneda;
    private JLabel labelSaldo;

    private Usuario usuario;

    public PanelVerCuentas(Usuario usuario) {
        this.usuario = usuario;
        setLayout(new GridLayout(7, 1, 10, 10)); // 7 filas, 1 columna, 10px de espacio

        // Fila 1: Título
        JLabel titulo = new JLabel("Cuentas del usuario", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo);

        // Fila 2: Desplegable para seleccionar el CBU
        JPanel panelDesplegable = new JPanel();
        panelDesplegable.setLayout(new FlowLayout(FlowLayout.CENTER));

        comboCbu = new JComboBox<>();
        cargarCbus();

        panelDesplegable.add(new JLabel("Seleccionar CBU:"));
        panelDesplegable.add(comboCbu);
        add(panelDesplegable);

        // Fila 3: Alias de la cuenta
        JPanel panelAlias = new JPanel();
        panelAlias.setLayout(new FlowLayout(FlowLayout.CENTER));

        labelAlias = new JLabel("Alias: ");
        panelAlias.add(labelAlias);
        add(panelAlias);

        // Fila 4: Moneda de la cuenta
        JPanel panelMoneda = new JPanel();
        panelMoneda.setLayout(new FlowLayout(FlowLayout.CENTER));

        labelMoneda = new JLabel("Moneda: ");
        panelMoneda.add(labelMoneda);
        add(panelMoneda);

        // Fila 5: Saldo de la cuenta
        JPanel panelSaldo = new JPanel();
        panelSaldo.setLayout(new FlowLayout(FlowLayout.CENTER));

        labelSaldo = new JLabel("Saldo: ");
        panelSaldo.add(labelSaldo);
        add(panelSaldo);

        // Fila 6: Botones de acción
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> volverAInicio());

        JButton btnVerInfo = new JButton("Ver Info");
        btnVerInfo.addActionListener(e -> mostrarInfoCuenta());

        panelBotones.add(btnVolver);
        panelBotones.add(btnVerInfo);
        add(panelBotones);
    }

    private void cargarCbus() {
        try {
            // Obtengo las cuentas del usuario
            CuentaService cuentaService = new CuentaService();
            java.util.List<Cuenta> cuentas = cuentaService.obtenerCuentasPorUsuario(usuario.getId());

            for (Cuenta cuenta : cuentas) {
                comboCbu.addItem(cuenta.getCbu()); // Cargamos los CBUs en el comboBox
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las cuentas del usuario.");
        }
    }

    private void mostrarInfoCuenta() {
        String cbuSeleccionado = (String) comboCbu.getSelectedItem();

        if (cbuSeleccionado != null && !cbuSeleccionado.isEmpty()) {
            try {
                // Llamar al servicio para obtener la información de la cuenta
                CuentaService cuentaService = new CuentaService();
                Cuenta cuenta = cuentaService.mostrarInfo(cbuSeleccionado);

                // Mostrar la información de la cuenta en las etiquetas
                if (cuenta != null) {
                    labelAlias.setText("Alias: " + cuenta.getAlias());
                    labelMoneda.setText("Moneda: " + cuenta.getMoneda());
                    labelSaldo.setText("Saldo: " + cuenta.getSaldo());
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró información para el CBU seleccionado.");
                }
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al obtener la información de la cuenta.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un CBU.");
        }
    }

    private void volverAInicio() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelNormal(usuario));
        frame.revalidate();
        frame.repaint();
    }
}
