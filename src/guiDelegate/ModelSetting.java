package guiDelegate;

import model.Model;

import java.awt.*;
import java.io.Serializable;

public class ModelSetting implements Serializable {
    private int xRes;
    private int yRes;
    private double minReal;
    private double maxReal;
    private double minImg;
    private double maxImg;
    private int maxIterations;
    private double radiusSquared;
    private Color colour;

    ModelSetting(Model model) {
        this.xRes = model.getXResolution();
        this.yRes = model.getYResolution();
        this.minReal = model.getMinReal();
        this.maxReal = model.getMaxReal();
        this.minImg = model.getMinImg();
        this.maxImg = model.getMaxImg();
        this.maxIterations = model.getMaxIterations();
        this.radiusSquared = model.getRadiusSquared();
        this.colour = model.getColour();
    }

    public void setXResolution(int xResolution) {
        this.xRes = xResolution;
    }

    public void setYResolution(int yResolution) {
        this.yRes = yResolution;
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

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public void setRadiusSquared(double radiusSquared) {
        this.radiusSquared = radiusSquared;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public int getXResolution() {
        return xRes;
    }

    public int getYResolution() {
        return yRes;
    }

    public double getMinReal() {
        return minReal;
    }

    public double getMaxReal() {
        return maxReal;
    }

    public double getMinImg() {
        return minImg;
    }

    public double getMaxImg() {
        return maxImg;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public double getRadiusSquared() {
        return radiusSquared;
    }

    public Color getColour() {
        return colour;
    }
}
