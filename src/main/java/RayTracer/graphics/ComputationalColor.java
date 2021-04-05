package RayTracer.graphics;

import RayTracer.math.Vector3D;

import java.awt.*;
import java.util.Objects;

/**
 * represent RGB color
 */

//TODO add verifiers to the constructors (check if the parameters are in the correct range)
public class ComputationalColor {
    private Vector3D rgb;
    public static final double RGB_MAX = 255;
    public static final ComputationalColor BLACK = new ComputationalColor(0,0,0);

    public ComputationalColor(double red, double green, double blue) {
        this.rgb = new Vector3D(red, green, blue);
    }

    public ComputationalColor(int red, int green, int blue) {
        this.rgb = new Vector3D(red / RGB_MAX, green / RGB_MAX, blue / RGB_MAX);
    }

    /**
     * Copy constructor
     * @param color ComputationalColor object
     */
    public ComputationalColor(ComputationalColor color) {
        this.rgb = color.rgb;
    }

    public ComputationalColor(Vector3D rgb) {
        this.rgb = new Vector3D(rgb.getFirst(), rgb.getSecond(), rgb.getThird());
    }

    /**
     * Copy constructor
     * @param color java.awt.Color object
     */
    public ComputationalColor(Color color) {
        this(color.getRed(), color.getGreen(), color.getBlue());
    }
    public double getRed() {
        return this.rgb.getFirst();
    }

    public double getGreen() {
        return this.rgb.getSecond();
    }

    public double getBlue() {
        return this.rgb.getThird();
    }

    /**
     * @return array of int representing the color's values in integer format ( 0 <= r,g,b <= 255)
     */
    public int[] getIntRepresentation() {
        int r = (int) Math.round(this.rgb.getFirst() * RGB_MAX);
        int g = (int) Math.round(this.rgb.getSecond() * RGB_MAX);
        int b = (int) Math.round(this.rgb.getThird() * RGB_MAX);
        return new int[]{r, g, b};
    }

    /**
     *TODO inplace!
     *
     * @param scaleFactor the scale factor of the output color
     * @return ComputationalColor with scaled rgb values
     */
    public static ComputationalColor scaleColor(ComputationalColor color, double scaleFactor) {
        if (scaleFactor <= 0 || scaleFactor > 1) {
            throw new IllegalArgumentException("fraction parameter's value should be between 0 to 1");
        }
        return new ComputationalColor(color.rgb.scalarMult(scaleFactor));
    }

    /**
     * TODO add getRGB() method instead
     * @return java.awt.Color object
     */
    public Color toColor() {
        this.clipColor();
        int[] rgb = getIntRepresentation();
        return new Color(rgb[0], rgb[1], rgb[2]);
    }

    /** TODO inplace
     *
     * Clips colors values to be a floating point number between 0 and 1
     */
    public ComputationalColor clipColor() {
        return new ComputationalColor(Math.min(getRed(), 1.0),Math.min(getGreen(), 1.0), Math.min(getBlue(), 1.0));
    }

    /**
     *  Multiply colors component by component
     * @param otherColor
     * @return
     */
    public ComputationalColor mult(ComputationalColor otherColor) {
        return new ComputationalColor(rgb.componentMult(otherColor.rgb));
    }

     /**
     *  add colors component by component
     * @param otherColor
     * @return
     */
    public ComputationalColor add(ComputationalColor otherColor) {
        return new ComputationalColor(rgb.add(otherColor.rgb));
    }

     /**TODO inplace
      *
     *  Multiplies color by scale
     * @return
     */
    public ComputationalColor scale(Double scale) {
        return new ComputationalColor(rgb.scalarMult(scale));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComputationalColor that = (ComputationalColor) o;
        return rgb.getFirst() == that.rgb.getFirst() && rgb.getSecond() == that.rgb.getSecond() && rgb.getThird() == that.rgb.getThird();
    }


}
