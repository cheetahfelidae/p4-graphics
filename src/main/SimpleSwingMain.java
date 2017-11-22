package main;

import guiDelegate.SimpleGuiDelegate;
import model.SimpleModel;


/**
 * Main class to run the model-delegate example as a stand-alone GUI application. 
 * 
 * @author jonl
 *
 */
public class SimpleSwingMain {

	public static void main(String[] args){
		SimpleModel model = new SimpleModel();
		SimpleGuiDelegate delegate = new SimpleGuiDelegate(model); // pass the model object to the delegate, so that it can observe, display, and change the model
	}
}
