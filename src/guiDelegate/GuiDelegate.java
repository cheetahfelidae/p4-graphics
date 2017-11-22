package guiDelegate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

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
    private static final int TEXT_HEIGHT = 10;
    private static final int TEXT_WIDTH = 10;

    private JFrame mainFrame;

    private JToolBar toolbar;
    private JTextField inputField;
    private JButton change_colour_butt, reset_butt, undo_butt, redo_butt;
    private JTextArea outputField;
    private JMenuBar menu;
    private Panel panel;
    private Setting pre_setting, cur_setting, post_setting;

    private Model model;

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
        outputField = new JTextArea(TEXT_WIDTH, TEXT_HEIGHT);
        outputField.setEditable(false);
        setupComponents();
        cur_setting = new Setting(model);

        // add the delegate UI component as an observer of the model
        // so as to detect changes in the model and update the GUI view accordingly
        model.addObserver(this);
    }

    /**
     * Initialises the toolbar to contain the buttons, label, input field, etc. and adds the toolbar to the main frame.
     * Listeners are created for the buttons and text field which translate user events to model object method calls (controller aspect of the delegate)
     */
    private void setupToolbar() {
        change_colour_butt = new JButton("Change Colour");
        change_colour_butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // should  call method in model class if you want it to affect model
                JOptionPane.showMessageDialog(mainFrame, "Ooops, Button 2 not linked to model!");
            }
        });

        reset_butt = new JButton("Reset");
        reset_butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.reset();
            }
        });

        undo_butt = new JButton("Undo");
        undo_butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (pre_setting != null) {
                    post_setting = new Setting(cur_setting);
                    cur_setting = new Setting(pre_setting);
                    model.update(cur_setting);
                }
            }
        });

        redo_butt = new JButton("Redo");
        redo_butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (post_setting != null) {
                    pre_setting = new Setting(cur_setting);
                    cur_setting = new Setting(post_setting);
                    model.update(cur_setting);
                }
            }
        });

        JLabel label = new JLabel("Current iteration: ");

        JButton add_button = new JButton("Update");       // to translate event for this button into appropriate model method call
        add_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pre_setting = new Setting(cur_setting);

                cur_setting.setMaxIterations(Integer.parseInt(inputField.getText()));
                model.update(cur_setting);

                inputField.setText("");                     // clear the input box in the GUI view
            }
        });

        inputField.addKeyListener(new KeyListener() {        // to translate key event for the text filed into appropriate model method call
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
        toolbar.add(change_colour_butt);
        toolbar.add(reset_butt);
        toolbar.add(undo_butt);
        toolbar.add(redo_butt);
        toolbar.add(label);
        toolbar.add(inputField);
        toolbar.add(add_button);
        // add toolbar to north of main frame
        mainFrame.add(toolbar, BorderLayout.NORTH);
    }

    /**
     * Sets up File menu with Load and Save entries
     * The Load and Save actions would normally be translated to appropriate model method calls similar to the way the code does this
     * above in @see #setupToolbar(). However, load and save functionality is not implemented here, instead the code below merely displays
     * an error message.
     */
    private void setupMenu() {
        JMenu file = new JMenu("File");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        file.add(load);
        file.add(save);
        menu.add(file);
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // should call appropriate method in model class if you want it to do something useful
                JOptionPane.showMessageDialog(mainFrame, "Ooops, Load not linked to model!");
            }
        });
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // should call appropriate method in model class if you want it to do something useful
                JOptionPane.showMessageDialog(mainFrame, "Ooops, Save not linked to model!");
            }
        });
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
        // This is safer than executing outputField.setText(model.getText())
        // in the caller's thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                panel.repaint();
            }
        });
    }

}

