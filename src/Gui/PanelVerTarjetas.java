package Gui;

import Entidades.Tarjeta;
import Entidades.Usuario;
import Service.ServiceException;
import Service.TarjetaService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelVerTarjetas extends JPanel {
    private JTextArea textAreaInfo;
    private JButton btnVerInfo;
    private JButton btnVolver;
    private TarjetaService tarjetaService;
    private int usuarioId;
    private Usuario usuario;

    public PanelVerTarjetas(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioId = usuario.getId(); // Aseguramos que estamos usando el ID del usuario
        this.tarjetaService = new TarjetaService();

        setLayout(new GridLayout(4, 1, 10, 10)); // Definimos 4 filas

        // Fila 1: Título
        JLabel titulo = new JLabel("Ver Tarjetas", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo);

        // Fila 2: Cuadro de texto para mostrar información
        textAreaInfo = new JTextArea(5, 20);
        textAreaInfo.setEditable(false); // El cuadro de texto no será editable por el usuario
        add(new JScrollPane(textAreaInfo)); // Añadimos un JScrollPane para manejar el texto largo

        // Fila 3: Botones de acción
        JPanel panelBotones = new JPanel();
        btnVolver = new JButton("Volver");
        btnVerInfo = new JButton("Ver Información");

        // Acción para el botón "Volver"
        btnVolver.addActionListener(e -> volverAInicio());

        // Acción para el botón "Ver Información"
        btnVerInfo.addActionListener(e -> mostrarInfoTarjetas());

        panelBotones.add(btnVolver);
        panelBotones.add(btnVerInfo);
        add(panelBotones);
    }

    private void mostrarInfoTarjetas() {
        try {
            List<Tarjeta> tarjetas = tarjetaService.obtenerTarjetasPorUsuario(usuarioId);
            StringBuilder info = new StringBuilder(); // Usamos StringBuilder para construir la cadena de texto

            // Iteramos sobre las tarjetas y las agregamos al StringBuilder
            for (Tarjeta tarjeta : tarjetas) {
                info.append("ID: ").append(tarjeta.getId())
                        .append("\nDescripción: ").append(tarjeta.getDescripcion())
                        .append("\nSaldo: ").append(tarjeta.getSaldo())
                        .append("\nLímite: ").append(tarjeta.getLimite())
                        .append("\n\n"); // Separar cada tarjeta por una línea en blanco
            }

            // Actualizamos el cuadro de texto con la información de las tarjetas
            textAreaInfo.setText(info.toString());

        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener la información de las tarjetas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volverAInicio() {
        // Volver al panel anterior (o al panel de inicio)
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelNormal(usuario)); // Suponiendo que PanelNormal es el panel de inicio
        frame.revalidate();
        frame.repaint();
    }
}
