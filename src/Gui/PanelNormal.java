package Gui;

import javax.swing.*;
import java.awt.*;
import Entidades.Usuario;

public class PanelNormal extends JPanel {
    private Usuario usuario;

    public PanelNormal(Usuario usuario) {
        this.usuario = usuario;
        setLayout(new GridLayout(5, 1, 10, 10)); // 5 filas, 1 columna, 10px de espacio

        // Fila 1: Título con el nombre y apellido del usuario
        JLabel titulo = new JLabel("Bienvenido, " + usuario.getNombre() + " " + usuario.getApellido(), JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo);

        // Fila 2: Botones para Usar Tarjeta y Pagar Tarjeta
        JPanel panelTarjetas = new JPanel();
        panelTarjetas.setLayout(new FlowLayout());

        JButton btnUsarTarjeta = new JButton("Usar Tarjeta");
        btnUsarTarjeta.addActionListener(e -> irAPanelTarjeta()); // Conectar al PanelTarjeta

        JButton btnPagarTarjeta = new JButton("Pagar Tarjeta");
        btnPagarTarjeta.addActionListener(e -> irAPanelPagarTarjeta()); // Conectar al PanelPagarTarjeta

        panelTarjetas.add(btnUsarTarjeta);
        panelTarjetas.add(btnPagarTarjeta);
        add(panelTarjetas);

        // Fila 3: Botón para Transferir
        JPanel panelTransferir = new JPanel();
        panelTransferir.setLayout(new FlowLayout());
        JButton btnTransferir = new JButton("Transferir");
        btnTransferir.addActionListener(e -> irAPanelTransferencia()); // Conectar al PanelTransferencia
        panelTransferir.add(btnTransferir);
        add(panelTransferir);

        // Fila 4: Botones para ver cuentas y ver tarjetas
        JPanel panelVerCuentasTarjetas = new JPanel();
        panelVerCuentasTarjetas.setLayout(new FlowLayout());

        JButton btnVerCuentas = new JButton("Ver Cuentas");
        btnVerCuentas.addActionListener(e -> verCuentas());

        JButton btnVerTarjetas = new JButton("Ver Tarjetas");
        btnVerTarjetas.addActionListener(e -> verTarjetas());

        panelVerCuentasTarjetas.add(btnVerCuentas);
        panelVerCuentasTarjetas.add(btnVerTarjetas);

        add(panelVerCuentasTarjetas);

        // Fila 5: Botón Volver
        JPanel panelVolver = new JPanel();
        panelVolver.setLayout(new FlowLayout());
        JButton btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(150, 30)); // Tamaño ajustado
        btnVolver.addActionListener(e -> volverAPanelInicio());
        panelVolver.add(btnVolver);
        add(panelVolver);
    }

    // Método para cambiar a PanelTarjeta
    private void irAPanelTarjeta() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelTarjeta(usuario)); // Se pasa el usuario a PanelTarjeta
        frame.revalidate();
        frame.repaint();
    }

    // Método para cambiar a PanelPagarTarjeta
    private void irAPanelPagarTarjeta() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelPagarTarjeta(usuario)); // Se pasa el usuario a PanelPagarTarjeta
        frame.revalidate();
        frame.repaint();
    }

    // Método para cambiar a PanelTransferencia
    private void irAPanelTransferencia() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelTransferencia(usuario)); // Se pasa el usuario a PanelTransferencia
        frame.revalidate();
        frame.repaint();
    }

    // Método para cambiar a PanelVerCuentas
    private void verCuentas() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelVerCuentas(usuario)); // Se pasa el usuario a PanelVerCuentas
        frame.revalidate();
        frame.repaint();
    }

    // Método para cambiar a PanelVerTarjetas
    private void verTarjetas() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelVerTarjetas(usuario)); // Se pasa el usuario a PanelVerTarjetas
        frame.revalidate();
        frame.repaint();
    }

    private void volverAPanelInicio() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelInicio());
        frame.revalidate();
        frame.repaint();
    }
}