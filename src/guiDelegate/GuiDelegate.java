package guiDelegate;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import model.Model;

/**
 * The GuiDelegate class whose purpose is to render relevant state information stored in the model and make changes to the model state based on user events.
 * <p>
 * This class uses Swing to display the model state when the model changes. This is the view aspect of the delegate class.
 * It also listens for user input events (in the listeners defined below), translates these to appropriate calls to methods
 * defined in the model class so as to make changes to the model. This is the controller aspect of the delegate class.
 * The class implements Observer in order to permit it to be added as an observer of the model class.
 * When the model calls notifyObservers() (after executing setChanged())
 * the update(...) method below is called in order to update the view of the model.
 */
public class GuiDelegate implements Observer {
    private static final int FRAME_HEIGHT = 800;
    private static final int FRAME_WIDTH = 800;
    private static final int TEXT_WIDTH = 10;

    private JFrame mainFrame;

    private JToolBar toolbar;
    private JTextField inputField;
    private JButton changeColourButton, resetButton, undoButton, redoButton;
    private JMenuBar menu;
    private Panel panel;
    private Model model;

    private Stack<ModelSetting> undoStack;
    private Stack<ModelSetting> redoStack;
    private ArrayDeque<ModelSetting> settingFrames;

    /**
     * Instantiate a new GuiDelegate object
     *
     * @param model the Model to observe, render, and update according to user events
     */
    public GuiDelegate(Model model) {
        this.model = model;
        this.mainFrame = new JFrame();  // set up the main frame for this GUI

        menu = new JMenuBar();
        toolbar = new JToolBar();
        inputField = new JTextField(TEXT_WIDTH);
        setupComponents();

        undoStack = model.getUndoStack();
        redoStack = model.getRedoStack();

        settingFrames = model.getSettingFrames();

        // add the delegate UI component as an observer of the model
        // so as to detect changes in the model and update the GUI view accordingly
        model.addObserver(this);
    }

    /**
     * Initialises the toolbar to contain the buttons, label, input field, etc. and adds the toolbar to the main frame.
     * Listeners are created for the buttons and text field which translate user events to model object method calls (controller aspect of the delegate)
     */
    private void setupToolbar() {
        createChangeColourButton();

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.reset();
            }
        });

        undoButton = new JButton("Undo");
        undoButton.setEnabled(false);
        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeRedoSetting();

                model.update(undoStack.pop());
            }
        });

        redoButton = new JButton("Redo");
        redoButton.setEnabled(false);
        redoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeUndoSetting(false);

                model.update(redoStack.pop());
            }
        });

        JLabel label = new JLabel("Change Max Iterations: ");

        JButton add_button = new JButton("Update");
        add_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                storeUndoSetting(true);

                model.setMaxIterations(Integer.parseInt(inputField.getText()));

                model.update();

                inputField.setText("");// clear the input box in the GUI view
            }
        });

        inputField.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    add_button.doClick();
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

        // add buttons, label, and textfield to the toolbar
        toolbar.add(changeColourButton);
        toolbar.addSeparator();
        toolbar.add(resetButton);
        toolbar.add(undoButton);
        toolbar.add(redoButton);
        toolbar.addSeparator();
        toolbar.add(label);
        toolbar.add(inputField);
        toolbar.add(add_button);
        // add toolbar to north of main frame
        mainFrame.add(toolbar, BorderLayout.NORTH);
    }

    /**
     * Create a change colour button which four colour pop-up options.
     */
    private void createChangeColourButton() {
        JPopupMenu popup = new JPopupMenu();
        popup.add(new JMenuItem(new AbstractAction("Red") {
            public void actionPerformed(ActionEvent e) {
                storeUndoSetting(true);

                model.setColour(Color.RED);

                model.update();
            }
        }));
        popup.add(new JMenuItem(new AbstractAction("Green") {
            public void actionPerformed(ActionEvent e) {
                storeUndoSetting(true);

                model.setColour(Color.GREEN);

                model.update();
            }
        }));
        popup.add(new JMenuItem(new AbstractAction("Blue") {
            public void actionPerformed(ActionEvent e) {
                storeUndoSetting(true);

                model.setColour(Color.BLUE);

                model.update();
            }
        }));
        popup.add(new JMenuItem(new AbstractAction("Gray") {
            public void actionPerformed(ActionEvent e) {
                storeUndoSetting(true);

                model.setColour(Color.GRAY);

                model.update();
            }
        }));
        popup.add(new JMenuItem(new AbstractAction("White") {
            public void actionPerformed(ActionEvent e) {
                storeUndoSetting(true);

                model.setColour(Color.WHITE);

                model.update();
            }
        }));

        changeColourButton = new JButton("Change Colour");
        changeColourButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }

    /**
     * store the most recently used parameter settings for undo operation.
     */
    private void storeUndoSetting(boolean clearRedoStack) {
        undoStack.push(new ModelSetting(model));

        if (clearRedoStack) {
            redoStack.clear();
        }
    }

    /**
     * store the most recently used parameter settings for redo operation.
     */
    private void storeRedoSetting() {
        redoStack.push(new ModelSetting(model));
    }

    /**
     * Sets up File menu with Load and Save entries
     * <p>
     * Save and Load : permit parameter settings and potentially the computed image to be saved
     * and loaded to/from file thereby permitting a saved image to be re-loaded
     * and the user to continue exploring the Mandelbrot set from that position onward.
     */
    private void setupMenu() {
        JMenu file = new JMenu("File");
        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                File workingDirectory = new File(System.getProperty("user.dir"));
                fc.setCurrentDirectory(workingDirectory);
                int returnVal = fc.showOpenDialog(fc);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        storeUndoSetting(true);

                        FileInputStream fi = new FileInputStream(file);
                        ObjectInputStream oi = new ObjectInputStream(fi);

                        model.update((ModelSetting) oi.readObject());

                        oi.close();
                        fi.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                File workingDirectory = new File(System.getProperty("user.dir"));
                fc.setCurrentDirectory(workingDirectory);
                int returnVal = fc.showSaveDialog(fc);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        FileOutputStream f = new FileOutputStream(file);
                        ObjectOutputStream o = new ObjectOutputStream(f);
                        o.writeObject(new ModelSetting(model));

                        o.close();
                        f.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        file.add(load);
        file.add(save);
        menu.add(file);

        // add menu bar to frame
        mainFrame.setJMenuBar(menu);
    }

    /**
     * Method to setup the menu and toolbar components
     */
    private void setupComponents() {
        setupMenu();
        setupToolbar();

        panel = new Panel(model);
        panel.setBackground(Color.WHITE);
        mainFrame.add(panel, BorderLayout.CENTER);
        mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This method contains code to update the GUI view when the model changes
     * The method is called when the model changes (i.e. when the model executes setChanged() and notifyObservers())
     * Any parameters passed to notifyObservers @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     * are passed to update.
     * The code in update should get hold of the model state it requires and update the relevant GUI components so that
     * an updated view of the model is displayed on screen.
     * For this simple example, the only state information we need from the model is what is in the model's text buffer and the
     * only GUI view element we need to update is the text area used for output.
     * <p>
     * NOTE: In a more complex program, the model may hold information on a variety of objects, such as various shapes, their positions, etc.
     * and the GUI view would then have to get hold of all that state info and produce a graphical representation of theses objects.
     * As a result, the update method would have to get hold of various bits of model state and then
     * call the relevant methods (defined in the GUI code) to render the objects.
     */
    public void update(Observable o, Object arg) {

        // Tell the SwingUtilities thread to update the GUI components.
        // in the caller's thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (undoStack.empty()) {
                    undoButton.setEnabled(false);
                } else {
                    undoButton.setEnabled(true);
                }

                if (redoStack.empty()) {
                    redoButton.setEnabled(false);
                } else {
                    redoButton.setEnabled(true);
                }

                panel.repaint();

                if (settingFrames.size() > 0) {
                    model.update(settingFrames.remove());
                }
            }
        });
    }

}

