package guiDelegate;

import model.SimpleModel;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    SimpleModel model;

    public Panel(SimpleModel model) {
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] madelbrotData = model.getMadelbrotData();
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
