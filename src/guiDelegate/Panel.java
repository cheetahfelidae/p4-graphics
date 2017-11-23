package guiDelegate;

import model.Model;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    Model model;

    public Panel(Model model) {
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int[][] madelbrot_data = model.getMandelbrot_data();

        for (int i = 0; i < madelbrot_data.length; i++) {
            for (int j = 0; j < madelbrot_data[i].length; j++) {
                if (madelbrot_data[i][j] >= model.getMax_iterations()) {
                    g.setColor(Color.BLACK);
                } else {
                    float value = (float) madelbrot_data[i][j] / model.getMax_iterations();
                    if (model.getColour() == Color.RED) {
                        g.setColor(new Color(value, 0, 0));
                    } else if (model.getColour() == Color.GREEN) {
                        g.setColor(new Color(0, value, 0));
                    } else if (model.getColour() == Color.BLUE) {
                        g.setColor(new Color(0, 0, value));
                    } else {
                        g.setColor(Color.WHITE);
                    }
                }

                g.drawLine(j, i, j, i);
            }
        }
    }
}
