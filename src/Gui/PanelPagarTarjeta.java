package Gui;

import javax.swing.*;
import java.awt.*;
import Entidades.Usuario;
import Entidades.Tarjeta;
import Entidades.Cuenta;
import Entidades.Movimiento;
import Service.TarjetaService;
import Service.CuentaService;
import Service.MovimientoService;
import Service.ServiceException;
import java.util.Date;
import java.util.List;

public class PanelPagarTarjeta extends JPanel {
    private Usuario usuario;
    private TarjetaService tarjetaService;
    private CuentaService cuentaService;
    private MovimientoService movimientoService;

    public PanelPagarTarjeta(Usuario usuario) {
        this.usuario = usuario;
        this.tarjetaService = new TarjetaService(); // Inicializar el servicio de tarjetas
        this.cuentaService = new CuentaService(); // Inicializar el servicio de cuentas
        this.movimientoService = new MovimientoService(); // Inicializar el servicio de movimientos
        setLayout(new GridLayout(5, 1, 10, 10)); // 5 filas, 1 columna, 10px de espacio

        // Fila 1: Título "Pagar Tarjeta"
        JLabel titulo = new JLabel("Pagar Tarjeta", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo);

        // Fila 2: Desplegable con las tarjetas del usuario
        JPanel panelTarjetas = new JPanel();
        panelTarjetas.setLayout(new FlowLayout());
        JLabel lblTarjetas = new JLabel("Tarjetas de usuario:");
        JComboBox<String> comboTarjetas = new JComboBox<>();

        // Obtener las tarjetas del usuario y agregarlas al JComboBox
        try {
            List<Tarjeta> tarjetas = tarjetaService.obtenerTarjetasPorUsuario(usuario.getId());
            for (Tarjeta tarjeta : tarjetas) {
                // Mostrar el ID y la descripción de la tarjeta en el desplegable
                String tarjetaInfo = "ID: " + tarjeta.getId() + " - " + tarjeta.getDescripcion();
                comboTarjetas.addItem(tarjetaInfo);
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener las tarjetas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        panelTarjetas.add(lblTarjetas);
        panelTarjetas.add(comboTarjetas);
        add(panelTarjetas);

        // Fila 3: Desplegable con las cuentas del usuario
        JPanel panelCuentas = new JPanel();
        panelCuentas.setLayout(new FlowLayout());
        JLabel lblCuentas = new JLabel("Cuenta de origen:");
        JComboBox<String> comboCuentas = new JComboBox<>();

        // Obtener las cuentas del usuario y agregarlas al JComboBox
        try {
            List<Cuenta> cuentas = cuentaService.obtenerCuentasPorUsuario(usuario.getId());
            for (Cuenta cuenta : cuentas) {
                // Mostrar el CBU y el alias de la cuenta en el desplegable
                String cuentaInfo = "CBU: " + cuenta.getCbu() + " - " + cuenta.getAlias();
                comboCuentas.addItem(cuentaInfo);
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener las cuentas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        panelCuentas.add(lblCuentas);
        panelCuentas.add(comboCuentas);
        add(panelCuentas);

        // Fila 4: Campo para ingresar el monto
        JPanel panelMonto = new JPanel();
        panelMonto.setLayout(new FlowLayout());
        JLabel lblMonto = new JLabel("Monto:");
        JTextField txtMonto = new JTextField(10);
        panelMonto.add(lblMonto);
        panelMonto.add(txtMonto);
        add(panelMonto);

        // Fila 5: Botones Volver y Pagar Tarjeta
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> volverAPanelNormal());

        JButton btnPagarTarjeta = new JButton("Pagar Tarjeta");

        // Acción para el botón "Pagar Tarjeta"
        btnPagarTarjeta.addActionListener(e -> {
            try {
                // Obtener la tarjeta seleccionada
                String tarjetaSeleccionada = (String) comboTarjetas.getSelectedItem();
                int tarjetaId = Integer.parseInt(tarjetaSeleccionada.split(" - ")[0].replace("ID: ", ""));

                // Obtener la cuenta seleccionada
                String cuentaSeleccionada = (String) comboCuentas.getSelectedItem();
                String cbuCuenta = cuentaSeleccionada.split(" - ")[0].replace("CBU: ", "");

                // Obtener el monto ingresado
                double monto = Double.parseDouble(txtMonto.getText());

                // Verificar que el monto sea válido
                if (monto <= 0) {
                    JOptionPane.showMessageDialog(this, "El monto debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verificar que la cuenta tenga saldo suficiente
                Cuenta cuenta = cuentaService.mostrarInfo(cbuCuenta);
                if (cuenta.getSaldo() < monto) {
                    JOptionPane.showMessageDialog(this, "Saldo insuficiente en la cuenta seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Debitar el monto de la cuenta
                cuentaService.debitar(cbuCuenta, monto);

                // Acreditar el monto a la tarjeta (pagar la tarjeta)
                tarjetaService.debitarSaldo(tarjetaId, monto);

                // Registrar el movimiento de pago de tarjeta
                Movimiento movimiento = new Movimiento(
                        0, // El ID se generará automáticamente en la base de datos
                        "Pago de tarjeta",
                        monto,
                        new Date(),
                        usuario.getId(),
                        tarjetaId
                );
                movimientoService.crearMovimiento(movimiento);

                JOptionPane.showMessageDialog(this, "Pago realizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El monto ingresado no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(this, "Error al pagar la tarjeta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelBotones.add(btnVolver);
        panelBotones.add(btnPagarTarjeta);

        add(panelBotones);
    }

    // Método para volver al PanelNormal
    private void volverAPanelNormal() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelNormal(usuario)); // Se pasa el usuario a PanelNormal
        frame.revalidate();
        frame.repaint();
    }
}