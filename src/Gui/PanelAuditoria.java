package Gui;

import javax.swing.*;
import java.awt.*;
import Service.MovimientoService;
import Service.ServiceException;
import Entidades.Movimiento;
import Entidades.Usuario;
import java.util.List;

public class PanelAuditoria extends JPanel {
    private Usuario usuario;
    private JComboBox<String> cbTipoAuditoria;
    private JTextField tfUsuarioId;
    private JTextArea taResultadoAuditoria;
    private MovimientoService movimientoService;

    public PanelAuditoria(Usuario usuario) {
        this.usuario = usuario;
        this.movimientoService = new MovimientoService(); // Inicializar el servicio de movimientos
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 1: Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Auditoría", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, gbc);

        gbc.gridwidth = 1;

        // Fila 2: Desplegable de tipo de auditoría
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Seleccione tipo de auditoría:"), gbc);
        gbc.gridx = 1;
        cbTipoAuditoria = new JComboBox<>(new String[]{"Auditoría por Usuario", "Auditoría Total"});
        cbTipoAuditoria.addActionListener(e -> actualizarFilaUsuario());
        add(cbTipoAuditoria, gbc);

        // Fila 3: Texto para ingresar el usuario si se selecciona "Auditoría por Usuario"
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Ingrese usuario a auditar:"), gbc);
        gbc.gridx = 1;
        tfUsuarioId = new JTextField(10);
        tfUsuarioId.setEnabled(false);  // Inicialmente deshabilitado
        add(tfUsuarioId, gbc);

        // Fila 4: Cuadro de texto para mostrar la auditoría
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        taResultadoAuditoria = new JTextArea(10, 30);
        taResultadoAuditoria.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taResultadoAuditoria);
        add(scrollPane, gbc);

        // Fila 5: Botones
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        JPanel panelBotones = new JPanel();
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> volverAAdm());
        JButton btnGenerarAuditoria = new JButton("Generar Auditoría");
        btnGenerarAuditoria.addActionListener(e -> generarAuditoria());
        panelBotones.add(btnVolver);
        panelBotones.add(btnGenerarAuditoria);
        add(panelBotones, gbc);
    }

    private void actualizarFilaUsuario() {
        // Activar o desactivar el campo de ID de usuario dependiendo de la selección
        if ("Auditoría por Usuario".equals(cbTipoAuditoria.getSelectedItem())) {
            tfUsuarioId.setEnabled(true);
        } else {
            tfUsuarioId.setEnabled(false);
            tfUsuarioId.setText(""); // Limpiar el campo cuando no sea necesario
        }
    }

    private void generarAuditoria() {
        try {
            taResultadoAuditoria.setText(""); // Limpiar el área de texto antes de mostrar nuevos resultados

            if ("Auditoría por Usuario".equals(cbTipoAuditoria.getSelectedItem())) {
                // Auditoría por Usuario
                String usuarioIdStr = tfUsuarioId.getText();
                if (usuarioIdStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID de usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int usuarioId = Integer.parseInt(usuarioIdStr);
                List<Movimiento> movimientos = movimientoService.emitirMovimientosUsuario(usuarioId);

                if (movimientos.isEmpty()) {
                    taResultadoAuditoria.setText("No se cuentan con movimientos del usuario.");
                } else {
                    StringBuilder resultado = new StringBuilder();
                    for (Movimiento movimiento : movimientos) {
                        resultado.append("ID Movimiento: ").append(movimiento.getId())
                                .append(", Tipo: ").append(movimiento.getTipoMovimiento())
                                .append(", Monto: ").append(movimiento.getMonto())
                                .append(", Fecha: ").append(movimiento.getFecha())
                                .append(", ID Usuario: ").append(movimiento.getUsuarioId()) // Mostrar el ID del usuario
                                .append("\n");
                    }
                    taResultadoAuditoria.setText(resultado.toString());
                }
            } else {
                // Auditoría Total
                List<Movimiento> movimientos = movimientoService.emitirTodosLosMovimientos(); // Obtener todos los movimientos

                if (movimientos.isEmpty()) {
                    taResultadoAuditoria.setText("No se cuentan con movimientos registrados.");
                } else {
                    StringBuilder resultado = new StringBuilder();
                    for (Movimiento movimiento : movimientos) {
                        resultado.append("ID Movimiento: ").append(movimiento.getId())
                                .append(", Tipo: ").append(movimiento.getTipoMovimiento())
                                .append(", Monto: ").append(movimiento.getMonto())
                                .append(", Fecha: ").append(movimiento.getFecha())
                                .append(", ID Usuario: ").append(movimiento.getUsuarioId()) // Mostrar el ID del usuario
                                .append("\n");
                    }
                    taResultadoAuditoria.setText(resultado.toString());
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID de usuario debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, "Error al generar la auditoría: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volverAAdm() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelAdm(usuario)); // Regresar al panel de administración
        frame.revalidate();
        frame.repaint();
    }
}