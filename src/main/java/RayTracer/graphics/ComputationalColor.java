package RayTracer.graphics;

import RayTracer.math.Vector3D;

import java.awt.*;

/**
 * represent RGB color
 */

//TODO add verifiers to the constructors (check if the parameters are in the correct range)
public class ComputationalColor {
    private Vector3D rgb;
    public static final double RGB_MAX = 255;

    public ComputationalColor(double red, double green, double blue) {
        rgb = new Vector3D(red, green, blue);
    }

    public ComputationalColor(int red, int green, int blue) {
        rgb = new Vector3D(red / RGB_MAX, green / RGB_MAX, blue / RGB_MAX);
    }

    /**
     * Copy constructor
     * @param color ComputationalColor object
     */
    public ComputationalColor(ComputationalColor color) {
        rgb = color.rgb;
    }

    public ComputationalColor(Vector3D rgb) {
        rgb = new Vector3D(rgb.get(0), rgb.get(1), rgb.get(2));
    }

    /**
     * Copy constructor
     * @param color java.awt.Color object
     */
    public ComputationalColor(Color color) {
        this(color.getRed(), color.getGreen(), color.getBlue());
    }

    public double getRed() {
        return rgb.get(0);
    }

    public double getGreen() {
        return rgb.get(1);
    }

    public double getBlue() {
        return rgb.get(2);
    }

    /**
     * @return array of int representing the color's values in integer format ( 0 <= r,g,b <= 255)
     */
    public int[] getIntRepresentation() {
        int r = (int) Math.round(rgb.get(0) * RGB_MAX);
        int g = (int) Math.round(rgb.get(1) * RGB_MAX);
        int b = (int) Math.round(rgb.get(2) * RGB_MAX);
        return new int[]{r, g, b};
    }

    /**
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
     * @param c1 first color
     * @param c2 second color
     * @return Color with its rgb values are the sum of c1 and c2 values.
     */
    public static ComputationalColor sumColors(ComputationalColor c1, ComputationalColor c2) {
        Vector3D sum = c1.rgb.add(c2.rgb);
        return new ComputationalColor(sum);
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

    /**
     * Clips colors values to be a floating point number between 0 and 1
     */
    private void clipColor() {
        rgb = new Vector3D(Math.min(getRed(), 1),Math.min(getGreen(), 1), Math.min(getBlue(), 1));
    }
}
