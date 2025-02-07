package Gui;

import javax.swing.*;
import java.awt.*;


public class MainGui {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Sistema Home Banking");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);

                // Crear el panel de inicio
                PanelInicio panelInicio = new PanelInicio();

                // Añadir el panel al frame
                frame.add(panelInicio);

                // Hacer visible la ventana
                frame.setVisible(true);
            }
        });
    }
}
