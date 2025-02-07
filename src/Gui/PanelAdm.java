package Gui;

import javax.swing.*;
import java.awt.*;
import Entidades.Usuario;
import Service.UsuarioAdministradorService;
import Service.ServiceException;

public class PanelAdm extends JPanel {
    private Usuario usuario;

    public PanelAdm(Usuario usuario) {
        this.usuario = usuario;
        setLayout(new GridLayout(4, 1, 10, 10)); // 4 filas, 1 columna, 10px de espacio

        // Fila 1: Título con el nombre y apellido del administrador
        JLabel titulo = new JLabel("Bienvenido Administrador - " + usuario.getNombre() + " " + usuario.getApellido(), JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo);

        // Fila 2: Botones de creación
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        JButton btnCrearUsuario = new JButton("Crear Usuario");
        JButton btnCrearTarjeta = new JButton("Crear Tarjeta");
        JButton btnCrearCuenta = new JButton("Crear Cuenta");

        panelBotones.add(btnCrearUsuario);
        panelBotones.add(btnCrearTarjeta);
        panelBotones.add(btnCrearCuenta);

        add(panelBotones);

        // Fila 3: Botón Auditoría (más pequeño, como los de la fila 2)
        JPanel panelAuditoria = new JPanel();
        panelAuditoria.setLayout(new FlowLayout());
        JButton btnAuditoria = new JButton("Auditoría");
        btnAuditoria.setPreferredSize(new Dimension(150, 30)); // Tamaño ajustado
        panelAuditoria.add(btnAuditoria);
        add(panelAuditoria);

        // Fila 4: Botón Volver
        JPanel panelVolver = new JPanel();
        panelVolver.setLayout(new FlowLayout());
        JButton btnVolver = new JButton("Volver");
        btnVolver.setPreferredSize(new Dimension(150, 30)); // Tamaño ajustado
        btnVolver.addActionListener(e -> volverAPanelInicio());
        panelVolver.add(btnVolver);
        add(panelVolver);

        // Acción del botón Crear Usuario
        btnCrearUsuario.addActionListener(e -> irACrearUsuario());

        // Acción del botón Crear Cuenta
        btnCrearCuenta.addActionListener(e -> irACrearCuenta());
        // Acción del botón Crear Tarjeta
        btnCrearTarjeta.addActionListener(e -> irACrearTarjeta());
        // Acción del botón Auditoría
        btnAuditoria.addActionListener(e -> irAAuditoria());




    }

    private void volverAPanelInicio() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelInicio());
        frame.revalidate();
        frame.repaint();
    }

    private void irACrearUsuario() {
        // Cambiar al PanelCreacionUsuario y pasar el usuario actual
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelCreacionUsuario(usuario));
        frame.revalidate();
        frame.repaint();
    }

    private void irACrearCuenta() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelCreacionCuenta(usuario));  // Pasamos el usuario
        frame.revalidate();
        frame.repaint();
    }

    private void irACrearTarjeta() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelCreacionTarjeta(usuario));
        frame.revalidate();
        frame.repaint();
    }
    private void irAAuditoria() {
        // Cambiar al PanelAuditoria y pasar el usuario actual
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PanelAuditoria(usuario)); // Pasamos el usuario
        frame.revalidate();
        frame.repaint();
    }


}
