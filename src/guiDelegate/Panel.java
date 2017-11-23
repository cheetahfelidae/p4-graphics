package guiDelegate;

import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Panel extends JPanel implements MouseListener, MouseMotionListener {
    Model model;
    int x1, y1, x2, y2;
    boolean zoom;

    public Panel(Model model) {
        this.model = model;
        zoom = false;
        super.addMouseListener(this);
        super.addMouseMotionListener(this);
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

//        ModelSetting setting = settings.get(index_cur_setting);
//
//        // delete post settings if exist before adding a previous model setting
//        if (index_cur_setting < settings.size() - 1) {
//            for (int i = index_cur_setting + 1; i < settings.size(); i++) {
//                settings.remove(i);
//            }
//        }

        double real_range = model.getMax_real() - model.getMin_real();
        double img_range = model.getMax_img() - model.getMin_img();

        model.setMin_real(model.getMin_real()
                + ((double) x1 / model.getXResolution()) * real_range);
        model.setMin_img(model.getMin_img()
                + ((double) y1 / model.getYResolution()) * img_range);

        model.setMax_real(model.getMax_real()
                - ((double) (model.getXResolution() - x2) / model.getXResolution()) * real_range);
        model.setMax_img(model.getMax_img()
                - ((double) (model.getYResolution() - y2) / model.getYResolution()) * img_range);

//        settings.add(new ModelSetting(setting)); // save it as a current model setting
//        index_cur_setting = settings.size() - 1;

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
