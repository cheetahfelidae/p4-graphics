package guiDelegate;

import model.Model;

import java.awt.*;

public class ModelSetting {
    private int x_res;
    private int y_res;
    private double min_real;
    private double max_real;
    private double min_img;
    private double max_img;
    private int max_iterations;
    private double radius_squared;
    private Color colour;

    ModelSetting(Model model) {
        this.x_res = model.getXResolution();
        this.y_res = model.getYResolution();
        this.min_real = model.getMinReal();
        this.max_real = model.getMaxReal();
        this.min_img = model.getMinImg();
        this.max_img = model.getMaxImg();
        this.max_iterations = model.getMaxIterations();
        this.radius_squared = model.getRadiusSquared();
        this.colour = model.getColour();
    }

    /**
     * Used to clone a class.
     *
     * @param modelSetting
     */
    ModelSetting(ModelSetting modelSetting) {
        this.x_res = modelSetting.getXResolution();
        this.y_res = modelSetting.getYResolution();
        this.min_real = modelSetting.getMin_real();
        this.max_real = modelSetting.getMax_real();
        this.min_img = modelSetting.getMin_img();
        this.max_img = modelSetting.getMax_img();
        this.max_iterations = modelSetting.getMax_iterations();
        this.radius_squared = modelSetting.getRadius_squared();
        this.colour = modelSetting.getColour();
    }

    public void setXResolution(int xResolution) {
        this.x_res = xResolution;
    }

    public void setYResolution(int yResolution) {
        this.y_res = yResolution;
    }

    public void setMin_real(double min_real) {
        this.min_real = min_real;
    }

    public void setMax_real(double max_real) {
        this.max_real = max_real;
    }

    public void setMin_img(double min_img) {
        this.min_img = min_img;
    }

    public void setMax_img(double max_img) {
        this.max_img = max_img;
    }

    public void setMax_iterations(int max_iterations) {
        this.max_iterations = max_iterations;
    }

    public void setRadius_squared(double radius_squared) {
        this.radius_squared = radius_squared;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public int getXResolution() {
        return x_res;
    }

    public int getYResolution() {
        return y_res;
    }

    public double getMin_real() {
        return min_real;
    }

    public double getMax_real() {
        return max_real;
    }

    public double getMin_img() {
        return min_img;
    }

    public double getMax_img() {
        return max_img;
    }

    public int getMax_iterations() {
        return max_iterations;
    }

    public double getRadius_squared() {
        return radius_squared;
    }

    public Color getColour() {
        return colour;
    }
}
