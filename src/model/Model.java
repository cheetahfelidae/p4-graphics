package model;

import guiDelegate.ModelSetting;

import java.awt.*;
import java.util.Observable;
import java.util.Stack;

public class Model extends Observable {
    private static final int DEFAULT_X_RESOLUTION = 800;
    private static final int DEFAULT_Y_RESOLUTION = 800;

    private int[][] mandelbrotData;
    private MandelbrotCalculator mandelCal;
    private int xRes, yRes, maxIterations;
    private double minReal, maxReal, minImg, maxImg, radiusSquared;
    private Color colour;

    private Stack<ModelSetting> undoStack;
    private Stack<ModelSetting> redoStack;

    public Model() {
        mandelCal = new MandelbrotCalculator();

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

        this.mandelbrotData = mandelCal.calcMandelbrotSet(
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
        this.minReal = modelSetting.getMinReal();
        this.maxReal = modelSetting.getMaxReal();
        this.minImg = modelSetting.getMinImg();
        this.maxImg = modelSetting.getMaxImg();
        this.maxIterations = modelSetting.getMaxIterations();
        this.radiusSquared = modelSetting.getRadiusSquared();
        this.colour = modelSetting.getColour();

        this.mandelbrotData = mandelCal.calcMandelbrotSet(xRes,
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
        this.mandelbrotData = mandelCal.calcMandelbrotSet(xRes,
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

        this.mandelbrotData = mandelCal.calcMandelbrotSet(xRes,
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

    public void setMandelCal(MandelbrotCalculator mandelCal) {
        this.mandelCal = mandelCal;
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
