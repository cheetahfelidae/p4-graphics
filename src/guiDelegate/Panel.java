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
        int[][] madelbrotData = model.getMandelbrot_data();
        int maxIterations = model.getMaxIterations();
        for (int i = 0; i < madelbrotData.length; i++) {
            for (int j = 0; j < madelbrotData[i].length; j++) {
                if (madelbrotData[i][j] >= maxIterations) {
                    g.setColor(new Color(0, 0, 0));
                    g.drawLine(j, i, j, i);
                } else {
//                	g.setColor(new Color(255, 0, 0));
//                	g.drawLine(j, i, j, i);
                }
            }
        }
    }
}
