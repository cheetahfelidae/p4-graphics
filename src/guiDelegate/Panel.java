package guiDelegate;

import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Stack;

public class Panel extends JPanel implements MouseListener, MouseMotionListener {
    private Model model;
    private int x1, y1, x2, y2;
    private boolean zoom;

    private Stack<ModelSetting> undoStack;
    private Stack<ModelSetting> redoStack;

    public Panel(Model model) {
        this.model = model;
        zoom = false;

        undoStack = model.getUndoStack();
        redoStack = model.getRedoStack();

        super.addMouseListener(this);
        super.addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int[][] madelbrot_data = model.getMandelbrotData();

        for (int i = 0; i < madelbrot_data.length; i++) {
            for (int j = 0; j < madelbrot_data[i].length; j++) {

                if (madelbrot_data[i][j] >= model.getMaxIterations()) {
                    g.setColor(Color.BLACK);

                } else {
                    float value = (float) madelbrot_data[i][j] / model.getMaxIterations();

                    if (model.getColour().equals(Color.RED)) {
                        g.setColor(new Color(value, 0, 0));
                    } else if (model.getColour().equals(Color.GREEN)) {
                        g.setColor(new Color(0, value, 0));
                    } else if (model.getColour().equals(Color.BLUE)) {
                        g.setColor(new Color(0, 0, value));
                    } else if (model.getColour().equals(Color.WHITE)) {
                        g.setColor(Color.WHITE);
                    } else {
                        g.setColor(model.getColour());
                    }
                }

                g.drawLine(j, i, j, i);
            }
        }

        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        if (!zoom) {
            if (x2 > x1) {
                if (y2 > y1) {
                    g.setColor(Color.WHITE);
                    g.drawRect(x1, y1, width, height);
                } else {
                    g.setColor(Color.WHITE);
                    g.drawRect(x1, y2, width, height);
                }
            } else {
                if (y2 > y1) {
                    g.setColor(Color.WHITE);
                    g.drawRect(x2, y1, width, height);
                } else {
                    g.setColor(Color.WHITE);
                    g.drawRect(x2, y2, width, height);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
        zoom = false;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (x1 > x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
        }

        if (y1 > y2) {
            int temp = y1;
            y1 = y2;
            y2 = temp;
        }

        y2 = y1 + (x2 - x1);

        undoStack.push(new ModelSetting(model));// save new previous setting
        redoStack.clear();

        double real_range = model.getMaxReal() - model.getMinReal();
        double img_range = model.getMaxImg() - model.getMinImg();

        model.setMinReal(model.getMinReal()
                + ((double) x1 / model.getXResolution()) * real_range);
        model.setMinImg(model.getMinImg()
                + ((double) y1 / model.getYResolution()) * img_range);

        model.setMaxReal(model.getMaxReal()
                - ((double) (model.getXResolution() - x2) / model.getXResolution()) * real_range);
        model.setMaxImg(model.getMaxImg()
                - ((double) (model.getYResolution() - y2) / model.getYResolution()) * img_range);

        zoom = true;
        model.update();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
