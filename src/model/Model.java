package model;

import guiDelegate.ModelSetting;

import java.awt.*;
import java.util.Observable;
import java.util.Stack;

public class Model extends Observable {
    private static final int DEFAULT_X_RESOLUTION = 800;
    private static final int DEFAULT_Y_RESOLUTION = 800;

    private int[][] mandelbrotData;
    private MandelbrotCalculator mandCal;
    private int xRes, yRes, maxIterations;
    private double minReal, maxReal, minImg, maxImg, radiusSquared;
    private Color colour;

    private Stack<ModelSetting> undoStack;
    private Stack<ModelSetting> redoStack;

    public Model() {
        mandCal = new MandelbrotCalculator();

        this.xRes = DEFAULT_X_RESOLUTION;
        this.yRes = DEFAULT_Y_RESOLUTION;
        this.minReal = MandelbrotCalculator.INITIAL_MIN_REAL;
        this.maxReal = MandelbrotCalculator.INITIAL_MAX_REAL;
        this.minImg = MandelbrotCalculator.INITIAL_MIN_IMAGINARY;
        this.maxImg = MandelbrotCalculator.INITIAL_MAX_IMAGINARY;
        this.maxIterations = MandelbrotCalculator.INITIAL_MAX_ITERATIONS;
        this.radiusSquared = MandelbrotCalculator.DEFAULT_RADIUS_SQUARED;
        this.colour = Color.BLUE;

        undoStack = new Stack<>();
        redoStack = new Stack<>();

        this.mandelbrotData = mandCal.calcMandelbrotSet(
                xRes,
                yRes,
                MandelbrotCalculator.INITIAL_MIN_REAL,
                MandelbrotCalculator.INITIAL_MAX_REAL,
                MandelbrotCalculator.INITIAL_MIN_IMAGINARY,
                MandelbrotCalculator.INITIAL_MAX_IMAGINARY,
                MandelbrotCalculator.INITIAL_MAX_ITERATIONS,
                MandelbrotCalculator.DEFAULT_RADIUS_SQUARED);
    }

    public void update(ModelSetting modelSetting) {
        this.xRes = modelSetting.getXResolution();
        this.yRes = modelSetting.getYResolution();
        this.minReal = modelSetting.getMin_real();
        this.maxReal = modelSetting.getMax_real();
        this.minImg = modelSetting.getMin_img();
        this.maxImg = modelSetting.getMax_img();
        this.maxIterations = modelSetting.getMax_iterations();
        this.radiusSquared = modelSetting.getRadius_squared();
        this.colour = modelSetting.getColour();

        this.mandelbrotData = mandCal.calcMandelbrotSet(xRes,
                yRes,
                minReal,
                maxReal,
                minImg,
                maxImg,
                maxIterations,
                radiusSquared);

        setChanged();
        notifyObservers();
    }

    public void update() {
        this.mandelbrotData = mandCal.calcMandelbrotSet(xRes,
                yRes,
                minReal,
                maxReal,
                minImg,
                maxImg,
                maxIterations,
                radiusSquared);

        setChanged();
        notifyObservers();
    }

    public void reset() {
        this.xRes = DEFAULT_X_RESOLUTION;
        this.yRes = DEFAULT_Y_RESOLUTION;
        this.minReal = MandelbrotCalculator.INITIAL_MIN_REAL;
        this.maxReal = MandelbrotCalculator.INITIAL_MAX_REAL;
        this.minImg = MandelbrotCalculator.INITIAL_MIN_IMAGINARY;
        this.maxImg = MandelbrotCalculator.INITIAL_MAX_IMAGINARY;
        this.maxIterations = MandelbrotCalculator.INITIAL_MAX_ITERATIONS;
        this.radiusSquared = MandelbrotCalculator.DEFAULT_RADIUS_SQUARED;
        this.colour = Color.BLUE;

        undoStack.clear();
        redoStack.clear();

        this.mandelbrotData = mandCal.calcMandelbrotSet(xRes,
                yRes,
                minReal,
                maxReal,
                minImg,
                maxImg,
                maxIterations,
                radiusSquared);

        setChanged();
        notifyObservers();
    }

    public void setMandelbrotData(int[][] mandelbrotData) {
        this.mandelbrotData = mandelbrotData;
    }

    public void setMandCal(MandelbrotCalculator mandCal) {
        this.mandCal = mandCal;
    }

    public void setxRes(int xRes) {
        this.xRes = xRes;
    }

    public void setyRes(int yRes) {
        this.yRes = yRes;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public void setMinReal(double minReal) {
        this.minReal = minReal;
    }

    public void setMaxReal(double maxReal) {
        this.maxReal = maxReal;
    }

    public void setMinImg(double minImg) {
        this.minImg = minImg;
    }

    public void setMaxImg(double maxImg) {
        this.maxImg = maxImg;
    }

    public void setRadiusSquared(double radiusSquared) {
        this.radiusSquared = radiusSquared;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public int[][] getMandelbrotData() {
        return this.mandelbrotData;
    }

    public int getXResolution() {
        return this.xRes;
    }

    public int getYResolution() {
        return this.yRes;
    }

    public double getMinReal() {
        return this.minReal;
    }

    public double getMaxReal() {
        return this.maxReal;
    }

    public double getMinImg() {
        return this.minImg;
    }

    public double getMaxImg() {
        return this.maxImg;
    }

    public int getMaxIterations() {
        return this.maxIterations;
    }

    public double getRadiusSquared() {
        return this.radiusSquared;
    }

    public Color getColour() {
        return colour;
    }

    public Stack<ModelSetting> getUndoStack() {
        return undoStack;
    }

    public Stack<ModelSetting> getRedoStack() {
        return redoStack;
    }
}
