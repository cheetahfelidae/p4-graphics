package model;

import guiDelegate.ModelSetting;

import java.awt.*;
import java.util.Observable;

public class Model extends Observable {
    private static final int DEFAULT_X_RESOLUTION = 800;
    private static final int DEFAULT_Y_RESOLUTION = 800;

    private int[][] mandelbrot_data;
    private MandelbrotCalculator mand_cal;
    private int x_res, y_res, max_iterations;
    private double min_real, max_real, min_img, max_img, radius_squared;
    private Color colour;

    public Model() {
        mand_cal = new MandelbrotCalculator();

        this.x_res = DEFAULT_X_RESOLUTION;
        this.y_res = DEFAULT_Y_RESOLUTION;
        this.min_real = MandelbrotCalculator.INITIAL_MIN_REAL;
        this.max_real = MandelbrotCalculator.INITIAL_MAX_REAL;
        this.min_img = MandelbrotCalculator.INITIAL_MIN_IMAGINARY;
        this.max_img = MandelbrotCalculator.INITIAL_MAX_IMAGINARY;
        this.max_iterations = MandelbrotCalculator.INITIAL_MAX_ITERATIONS;
        this.radius_squared = MandelbrotCalculator.DEFAULT_RADIUS_SQUARED;
        this.colour = Color.BLUE;

        this.mandelbrot_data = mand_cal.calcMandelbrotSet(
                x_res,
                y_res,
                MandelbrotCalculator.INITIAL_MIN_REAL,
                MandelbrotCalculator.INITIAL_MAX_REAL,
                MandelbrotCalculator.INITIAL_MIN_IMAGINARY,
                MandelbrotCalculator.INITIAL_MAX_IMAGINARY,
                MandelbrotCalculator.INITIAL_MAX_ITERATIONS,
                MandelbrotCalculator.DEFAULT_RADIUS_SQUARED);
    }

    public void update(ModelSetting modelSetting) {
        this.x_res = modelSetting.getXResolution();
        this.y_res = modelSetting.getYResolution();
        this.min_real = modelSetting.getMin_real();
        this.max_real = modelSetting.getMax_real();
        this.min_img = modelSetting.getMin_img();
        this.max_img = modelSetting.getMax_img();
        this.max_iterations = modelSetting.getMax_iterations();
        this.radius_squared = modelSetting.getRadius_squared();
        this.colour = modelSetting.getColour();

        this.mandelbrot_data = mand_cal.calcMandelbrotSet(x_res,
                y_res,
                min_real,
                max_real,
                min_img,
                max_img,
                max_iterations,
                radius_squared);

        setChanged();
        notifyObservers();
    }

    public void reset() {
        this.x_res = DEFAULT_X_RESOLUTION;
        this.y_res = DEFAULT_Y_RESOLUTION;
        this.min_real = MandelbrotCalculator.INITIAL_MIN_REAL;
        this.max_real = MandelbrotCalculator.INITIAL_MAX_REAL;
        this.min_img = MandelbrotCalculator.INITIAL_MIN_IMAGINARY;
        this.max_img = MandelbrotCalculator.INITIAL_MAX_IMAGINARY;
        this.max_iterations = MandelbrotCalculator.INITIAL_MAX_ITERATIONS;
        this.radius_squared = MandelbrotCalculator.DEFAULT_RADIUS_SQUARED;
        this.colour = Color.BLUE;

        mandelbrot_data = mand_cal.calcMandelbrotSet(
                x_res,
                y_res,
                MandelbrotCalculator.INITIAL_MIN_REAL,
                MandelbrotCalculator.INITIAL_MAX_REAL,
                MandelbrotCalculator.INITIAL_MIN_IMAGINARY,
                MandelbrotCalculator.INITIAL_MAX_IMAGINARY,
                MandelbrotCalculator.INITIAL_MAX_ITERATIONS,
                MandelbrotCalculator.DEFAULT_RADIUS_SQUARED);

        setChanged();
        notifyObservers();
    }

    public int[][] getMandelbrot_data() {
        return this.mandelbrot_data;
    }

    public int getXResolution() {
        return this.x_res;
    }

    public int getYResolution() {
        return this.y_res;
    }

    public double getMin_real() {
        return this.min_real;
    }

    public double getMax_real() {
        return this.max_real;
    }

    public double getMin_img() {
        return this.min_img;
    }

    public double getMax_img() {
        return this.max_img;
    }

    public int getMax_iterations() {
        return this.max_iterations;
    }

    public double getRadius_squared() {
        return this.radius_squared;
    }

    public Color getColour() {
        return colour;
    }
}
