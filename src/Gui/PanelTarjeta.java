package Gui;

import javax.swing.*;
import java.awt.*;
import Entidades.Usuario;
import Entidades.Tarjeta;
import Entidades.Movimiento;
import Service.TarjetaService;
import Service.MovimientoService;
import Service.ServiceException;
import java.util.Date;
import java.util.List;

public class PanelTarjeta extends JPanel {
    private Usuario usuario;
    private TarjetaService tarjetaService;
    private MovimientoService movimientoService;

    public PanelTarjeta(Usuario usuario) {
        this.usuario = usuario;
        this.tarjetaService = new TarjetaService(); // Inicializar el servicio de tarjetas
        this.movimientoService = new MovimientoService(); // Inicializar el servicio de movimientos
        setLayout(new GridLayout(4, 1, 10, 10)); // 4 filas, 1 columna, 10px de espacio

        // Fila 1: Título "Tarjetas"
        JLabel titulo = new JLabel("Tarjetas", JLabel.CENTER);
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

        // Fila 3: Campo para ingresar el monto
        JPanel panelMonto = new JPanel();
        panelMonto.setLayout(new FlowLayout());
        JLabel lblMonto = new JLabel("Monto:");
        JTextField txtMonto = new JTextField(10);
        panelMonto.add(lblMonto);
        panelMonto.add(txtMonto);
        add(panelMonto);

        // Fila 4: Botones Volver y Usar Tarjeta
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> volverAPanelNormal());

        JButton btnUsarTarjeta = new JButton("Usar Tarjeta");

        // Acción para el botón "Usar Tarjeta"
        btnUsarTarjeta.addActionListener(e -> {
            try {
                // Obtener la tarjeta seleccionada
                String tarjetaSeleccionada = (String) comboTarjetas.getSelectedItem();
                int tarjetaId = Integer.parseInt(tarjetaSeleccionada.split(" - ")[0].replace("ID: ", ""));

                // Obtener el monto ingresado
                double monto = Double.parseDouble(txtMonto.getText());

                // Obtener la tarjeta completa para verificar el límite
                List<Tarjeta> tarjetas = tarjetaService.obtenerTarjetasPorUsuario(usuario.getId());
                Tarjeta tarjeta = tarjetas.stream()
                        .filter(t -> t.getId() == tarjetaId)
                        .findFirst()
                        .orElseThrow(() -> new ServiceException("Tarjeta no encontrada"));

                // Verificar que el monto no supere el límite
                if (tarjeta.getSaldo() + monto > tarjeta.getLimite()) {
                    JOptionPane.showMessageDialog(this, "El monto ingresado supera el límite de la tarjeta.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Acreditar el monto a la tarjeta
                tarjetaService.acreditarSaldo(tarjetaId, monto);

                // Registrar el movimiento
                Movimiento movimiento = new Movimiento(
                        0, // El ID se generará automáticamente en la base de datos
                        "Uso de tarjeta",
                        monto,
                        new Date(),
                        usuario.getId()
                );
                movimientoService.crearMovimiento(movimiento);

                JOptionPane.showMessageDialog(this, "Monto acreditado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El monto ingresado no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(this, "Error al usar la tarjeta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelBotones.add(btnVolver);
        panelBotones.add(btnUsarTarjeta);

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