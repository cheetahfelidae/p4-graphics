package guiDelegate;

import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayDeque;
import java.util.Stack;

public class Panel extends JPanel implements MouseListener, MouseMotionListener {
    private static int NUM_SETTING_FRAMES = 20;

    private Model model;
    private int x1, y1, x2, y2;
    private boolean doneZoom; // used to check if the zoom operation has done

    private Stack<ModelSetting> undoStack;
    private Stack<ModelSetting> redoStack;
    private ArrayDeque<ModelSetting> setting_frames;

    public Panel(Model model) {
        this.model = model;
        doneZoom = false;

        undoStack = model.getUndoStack();
        redoStack = model.getRedoStack();

        setting_frames = model.getSetting_frames();

        super.addMouseListener(this);
        super.addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        draw_image(g);

        if (!doneZoom) {
            draw_dragging_line(g);
        }
    }

    /**
     * Different colour mappings - map the iteration values to different shades of a colour
     * i.e. higher numbers are mapped to brighter/whiter shades of the colour until the iteration limit is reached.
     *
     * @param g
     */
    private void draw_image(Graphics g) {
        int[][] madelBrotData = model.getMandelbrotData();

        for (int i = 0; i < madelBrotData.length; i++) {
            for (int j = 0; j < madelBrotData[i].length; j++) {

                if (madelBrotData[i][j] >= model.getMaxIterations()) {
                    g.setColor(Color.BLACK);

                } else {
                    float value = (float) madelBrotData[i][j] / model.getMaxIterations();

                    if (model.getColour().equals(Color.RED)) {
                        g.setColor(new Color(value, 0, 0));
                    } else if (model.getColour().equals(Color.GREEN)) {
                        g.setColor(new Color(0, value, 0));
                    } else if (model.getColour().equals(Color.BLUE)) {
                        g.setColor(new Color(0, 0, value));
                    } else if (model.getColour().equals(Color.GRAY)) {
                        g.setColor(new Color(value, value, value));
                    } else {
                        g.setColor(model.getColour());
                    }
                }

                g.drawLine(j, i, j, i);
            }
        }
    }

    /**
     * create a rectangle when dragging.
     *
     * @param g
     */
    private void draw_dragging_line(Graphics g) {
        // set an appropriate colour for a specific colour of the background.
        if (model.getColour().equals(Color.GRAY)) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.WHITE);
        }

        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        if (x2 > x1) {
            if (y2 > y1) {
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

        doneZoom = false;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        makeSquare();

        // store the most recently used parameter settings for undo operation.
        undoStack.push(new ModelSetting(model));
        redoStack.clear();

        setting_frames.clear();
        for (int i = 0; i < NUM_SETTING_FRAMES; i++) {
            ModelSetting setting = new ModelSetting(undoStack.peek());

            double realRange = model.getMaxReal() - model.getMinReal(),
                    imgRange = model.getMaxImg() - model.getMinImg(),
                    ratio = (double) (i + 1) / NUM_SETTING_FRAMES;

            setting.setMinReal(model.getMinReal()
                    + ((double) x1 / model.getXResolution()) * realRange * ratio);
            setting.setMinImg(model.getMinImg()
                    + ((double) y1 / model.getYResolution()) * imgRange * ratio);

            setting.setMaxReal(model.getMaxReal()
                    - ((double) (model.getXResolution() - x2) / model.getXResolution()) * realRange * ratio);
            setting.setMaxImg(model.getMaxImg()
                    - ((double) (model.getYResolution() - y2) / model.getYResolution()) * imgRange * ratio);

            setting_frames.add(setting);
        }

        doneZoom = true;

        if (setting_frames.size() > 0) {
            model.update(setting_frames.remove());
        }
    }

    /**
     * force the two coordinates of rectangle to be a square if it is not.
     */
    private void makeSquare() {
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
