package model;

import guiDelegate.ModelSetting;

import java.awt.*;
import java.util.*;

public class Model extends Observable {
    private static final int DEFAULT_X_RESOLUTION = 800;
    private static final int DEFAULT_Y_RESOLUTION = 800;

    private int[][] mandelData;
    private MandelbrotCalculator mandelCal;
    private int xRes, yRes, maxIterations;
    private double minReal, maxReal, minImg, maxImg, radiusSquared;
    private Color colour;

    private Stack<ModelSetting> undoStack;
    private Stack<ModelSetting> redoStack;
    private ArrayDeque<ModelSetting> settingFrames;

    public Model() {
        mandelCal = new MandelbrotCalculator();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        settingFrames = new ArrayDeque<>();

        reset();
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

        this.mandelData = mandelCal.calcMandelbrotSet(xRes,
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

        this.mandelData = mandelCal.calcMandelbrotSet(xRes,
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

    /**
     * reset all parameter settings back to their defaults and display the corresponding image.
     */
    public void reset() {
        this.xRes = DEFAULT_X_RESOLUTION;
        this.yRes = DEFAULT_Y_RESOLUTION;
        this.minReal = MandelbrotCalculator.INITIAL_MIN_REAL;
        this.maxReal = MandelbrotCalculator.INITIAL_MAX_REAL;
        this.minImg = MandelbrotCalculator.INITIAL_MIN_IMAGINARY;
        this.maxImg = MandelbrotCalculator.INITIAL_MAX_IMAGINARY;
        this.maxIterations = MandelbrotCalculator.INITIAL_MAX_ITERATIONS;
        this.radiusSquared = MandelbrotCalculator.DEFAULT_RADIUS_SQUARED;
        this.colour = Color.GREEN;

        undoStack.clear();
        redoStack.clear();
        settingFrames.clear();

        this.mandelData = mandelCal.calcMandelbrotSet(xRes,
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

    public void setMandelData(int[][] mandelData) {
        this.mandelData = mandelData;
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

    public int[][] getMandelData() {
        return this.mandelData;
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

    public ArrayDeque<ModelSetting> getSettingFrames() {
        return settingFrames;
    }
}
