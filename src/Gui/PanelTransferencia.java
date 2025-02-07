package Gui;

import javax.swing.*;
import java.awt.*;
import Entidades.Usuario;
import Entidades.Cuenta;
import Entidades.Movimiento;
import Service.CuentaService;
import Service.MovimientoService;
import Service.ServiceException;
import java.util.Date;
import java.util.List;

public class PanelTransferencia extends JPanel {
    private Usuario usuario;
    private CuentaService cuentaService;
    private MovimientoService movimientoService;

    public PanelTransferencia(Usuario usuario) {
        this.usuario = usuario;
        this.cuentaService = new CuentaService(); // Inicializar el servicio de cuentas
        this.movimientoService = new MovimientoService(); // Inicializar el servicio de movimientos
        setLayout(new GridLayout(6, 1, 10, 10)); // 6 filas, 1 columna, 10px de espacio

        // Fila 1: Título "Transferir"
        JLabel titulo = new JLabel("Transferir", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo);

        // Fila 2: Campo para ingresar el monto
        JPanel panelMonto = new JPanel();
        panelMonto.setLayout(new FlowLayout());
        JLabel lblMonto = new JLabel("Monto:");
        JTextField txtMonto = new JTextField(10);
        panelMonto.add(lblMonto);
        panelMonto.add(txtMonto);
        add(panelMonto);

        // Fila 3: Desplegable para seleccionar la cuenta de origen
        JPanel panelCuentaOrigen = new JPanel();
        panelCuentaOrigen.setLayout(new FlowLayout());
        JLabel lblCuentaOrigen = new JLabel("Cuenta origen:");
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

        panelCuentaOrigen.add(lblCuentaOrigen);
        panelCuentaOrigen.add(comboCuentas);
        add(panelCuentaOrigen);

        // Fila 4: Campo para ingresar el CBU de destino
        JPanel panelCbuDestino = new JPanel();
        panelCbuDestino.setLayout(new FlowLayout());
        JLabel lblCbuDestino = new JLabel("CBU destino:");
        JTextField txtCbuDestino = new JTextField(10);
        panelCbuDestino.add(lblCbuDestino);
        panelCbuDestino.add(txtCbuDestino);
        add(panelCbuDestino);

        // Fila 5: Campo para ingresar el alias de destino
        JPanel panelAliasDestino = new JPanel();
        panelAliasDestino.setLayout(new FlowLayout());
        JLabel lblAliasDestino = new JLabel("Alias destino:");
        JTextField txtAliasDestino = new JTextField(10);
        panelAliasDestino.add(lblAliasDestino);
        panelAliasDestino.add(txtAliasDestino);
        add(panelAliasDestino);

        // Fila 6: Botones Volver, Transferir con CBU y Transferir con Alias
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> volverAPanelNormal());

        JButton btnTransferirCbu = new JButton("Transferir con CBU");
        JButton btnTransferirAlias = new JButton("Transferir con Alias");

        // Acción para el botón "Transferir con CBU"
        btnTransferirCbu.addActionListener(e -> {
            try {
                // Obtener la cuenta de origen
                String cuentaOrigenSeleccionada = (String) comboCuentas.getSelectedItem();
                String cbuOrigen = cuentaOrigenSeleccionada.split(" - ")[0].replace("CBU: ", "");

                // Obtener el CBU de destino
                String cbuDestino = txtCbuDestino.getText();

                // Obtener el monto ingresado
                double monto = Double.parseDouble(txtMonto.getText());

                // Verificar que el monto sea válido
                if (monto <= 0) {
                    JOptionPane.showMessageDialog(this, "El monto debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Obtener la moneda de la cuenta de origen y la cuenta de destino
                Cuenta cuentaOrigen = cuentaService.mostrarInfo(cbuOrigen);
                Cuenta cuentaDestino = cuentaService.mostrarInfo(cbuDestino);

                // Verificar que ambas cuentas tengan la misma moneda
                if (!cuentaOrigen.getMoneda().equals(cuentaDestino.getMoneda())) {
                    JOptionPane.showMessageDialog(this, "No se pueden transferir fondos entre cuentas con monedas diferentes.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verificar que la cuenta de origen tenga saldo suficiente
                if (cuentaOrigen.getSaldo() < monto) {
                    JOptionPane.showMessageDialog(this, "Saldo insuficiente en la cuenta de origen.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Realizar la transferencia
                cuentaService.debitar(cbuOrigen, monto); // Descontar de la cuenta de origen
                cuentaService.acreditar(cbuDestino, monto); // Acreditar a la cuenta de destino

                // Registrar el movimiento de transferencia
                Movimiento movimiento = new Movimiento(
                        0, // El ID se generará automáticamente en la base de datos
                        "Transferencia con CBU",
                        monto,
                        new Date(),
                        usuario.getId(),
                        0 // No hay tarjeta involucrada en una transferencia
                );
                movimientoService.crearMovimiento(movimiento);

                JOptionPane.showMessageDialog(this, "Transferencia realizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El monto ingresado no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(this, "Error al realizar la transferencia: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción para el botón "Transferir con Alias"
        btnTransferirAlias.addActionListener(e -> {
            try {
                // Obtener la cuenta de origen
                String cuentaOrigenSeleccionada = (String) comboCuentas.getSelectedItem();
                String cbuOrigen = cuentaOrigenSeleccionada.split(" - ")[0].replace("CBU: ", "");

                // Obtener el alias de destino
                String aliasDestino = txtAliasDestino.getText();

                // Obtener el CBU de destino a partir del alias
                Cuenta cuentaDestino = cuentaService.mostrarInfoPorAlias(aliasDestino);
                if (cuentaDestino == null) {
                    JOptionPane.showMessageDialog(this, "No se encontró una cuenta con el alias proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String cbuDestino = cuentaDestino.getCbu();

                // Obtener el monto ingresado
                double monto = Double.parseDouble(txtMonto.getText());

                // Verificar que el monto sea válido
                if (monto <= 0) {
                    JOptionPane.showMessageDialog(this, "El monto debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Obtener la moneda de la cuenta de origen y la cuenta de destino
                Cuenta cuentaOrigen = cuentaService.mostrarInfo(cbuOrigen);

                // Verificar que ambas cuentas tengan la misma moneda
                if (!cuentaOrigen.getMoneda().equals(cuentaDestino.getMoneda())) {
                    JOptionPane.showMessageDialog(this, "No se pueden transferir fondos entre cuentas con monedas diferentes.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verificar que la cuenta de origen tenga saldo suficiente
                if (cuentaOrigen.getSaldo() < monto) {
                    JOptionPane.showMessageDialog(this, "Saldo insuficiente en la cuenta de origen.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Realizar la transferencia
                cuentaService.debitar(cbuOrigen, monto); // Descontar de la cuenta de origen
                cuentaService.acreditar(cbuDestino, monto); // Acreditar a la cuenta de destino

                // Registrar el movimiento de transferencia
                Movimiento movimiento = new Movimiento(
                        0, // El ID se generará automáticamente en la base de datos
                        "Transferencia con Alias",
                        monto,
                        new Date(),
                        usuario.getId(),
                        0 // No hay tarjeta involucrada en una transferencia
                );
                movimientoService.crearMovimiento(movimiento);

                JOptionPane.showMessageDialog(this, "Transferencia realizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El monto ingresado no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(this, "Error al realizar la transferencia: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelBotones.add(btnVolver);
        panelBotones.add(btnTransferirCbu);
        panelBotones.add(btnTransferirAlias);

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