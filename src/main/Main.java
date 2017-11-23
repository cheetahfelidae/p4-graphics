package main;

import guiDelegate.GuiDelegate;
import model.Model;


/**
 * Main class to run the model-delegate example as a stand-alone GUI application.
 *
 * @author StudentID: 160026335
 */
public class Main {

    public static void main(String[] args) {
        new GuiDelegate(new Model()); // pass the model object to the delegate, so that it can observe, display, and change the model
    }
}
