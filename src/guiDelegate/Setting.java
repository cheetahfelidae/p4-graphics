package guiDelegate;

import model.Model;

public class Setting {
    private int xResolution;
    private int yResolution;
    private double minReal;
    private double maxReal;
    private double minImaginary;
    private double maxImaginary;
    private int maxIterations;
    private double radiusSquared;

    Setting(Model model) {
        this.xResolution =  model.getXResolution();
        this.yResolution = model.getYResolution();
        this.minReal = model.getMinReal();
        this.maxReal = model.getMaxReal();
        this.minImaginary = model.getMinImaginary();
        this.maxImaginary = model.getMaxImaginary();
        this.maxIterations = model.getMaxIterations();
        this.radiusSquared = model.getRadiusSquared();
    }

    public void setXResolution(int xResolution) {
        this.xResolution = xResolution;
    }

    public void setYResolution(int yResolution) {
        this.yResolution = yResolution;
    }

    public void setMinReal(double minReal) {
        this.minReal = minReal;
    }

    public void setMaxReal(double maxReal) {
        this.maxReal = maxReal;
    }

    public void setMinImaginary(double minImaginary) {
        this.minImaginary = minImaginary;
    }

    public void setMaxImaginary(double maxImaginary) {
        this.maxImaginary = maxImaginary;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public void setRadiusSquared(double radiusSquared) {
        this.radiusSquared = radiusSquared;
    }

    public int getXResolution() {
        return xResolution;
    }

    public int getYResolution() {
        return yResolution;
    }

    public double getMinReal() {
        return minReal;
    }

    public double getMaxReal() {
        return maxReal;
    }

    public double getMinImaginary() {
        return minImaginary;
    }

    public double getMaxImaginary() {
        return maxImaginary;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public double getRadiusSquared() {
        return radiusSquared;
    }
}
