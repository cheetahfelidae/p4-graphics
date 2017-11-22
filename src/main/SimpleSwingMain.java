package main;

import guiDelegate.GuiDelegate;
import model.Model;


/**
 * Main class to run the model-delegate example as a stand-alone GUI application.
 *
 * @author StudentID: 160026335
 */
public class SimpleSwingMain {

    public static void main(String[] args) {
        Model model = new Model();
        GuiDelegate delegate = new GuiDelegate(model); // pass the model object to the delegate, so that it can observe, display, and change the model
    }
}
