package RayTracer.Elements;

/**
 * represent RGB color
 */
public class ComputationalColor {
    private double red;
    private double green;
    private double blue;

    public ComputationalColor(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public ComputationalColor(int red, int green, int blue) {
        checkRGB(red, green, blue);
        this.red = red / 255.0;
        this.green = green / 255.0;
        this.blue = blue / 255.0;
    }

    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }

    /**
     * @return array of int representing the color's values in integer format ( 0 <= r,g,b <= 255)
     */
    public int[] getIntRepresentation() {
        int r = (int) Math.round(red * 255.0);
        int g = (int) Math.round(green * 255.0);
        int b = (int) Math.round(blue * 255.0);
        return new int[]{r, g, b};
    }

    /**
     * @param c1 first color
     * @param c2 second color
     * @return Color with its rgb values are the sum of c1 and c2 values.
     */
    public static ComputationalColor sumColors(ComputationalColor c1, ComputationalColor c2) {
        return new ComputationalColor(c1.getRed() + c2.getRed(), c1.getGreen() + c2.getGreen(), c1.getBlue() + c2.getBlue());
    }

    /**
     *
     * @param scaleFactor the scale factor of the output color
     * @return ComputationalColor with scaled rgb values
     */
    public static ComputationalColor multiplyByFraction(ComputationalColor color, double scaleFactor) {
        if (scaleFactor <= 0 || scaleFactor > 1) {
            throw new IllegalArgumentException("fraction parameter's value should be between 0 to 1");
        }
        return new ComputationalColor(color.getRed() * scaleFactor, color.green * scaleFactor, color.getBlue() * scaleFactor);
    }


    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is equal to the {@code obj} argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof ComputationalColor) {
            ComputationalColor other = (ComputationalColor) obj;
            return red == other.red
                    && green == other.green
                    && blue == other.blue;
        } else return false;
    }


    /**
     * Returns a string representation of this {@code main.Color}.
     *
     * @return the string representation
     */
    //TODO change serialization and add deserialization
    @Override
    public String toString() {
        int rgb[] = getIntRepresentation();
        return "r: " + String.valueOf(rgb[0]) + ", g: " + String.valueOf(rgb[1]) + ", b: " + String.valueOf(rgb[2]);
    }

    private void trimColors() {
        this.red = Math.min(red, 1);
        this.green = Math.min(green, 1);
        this.blue = Math.min(blue, 1);
    }

    /**
     * Check if the integer arguments values are in range [0, 255]. if not, Throws IllegalArgumentException.
     *
     * @param red
     * @param green
     * @param blue
     */

    private static void checkRGB(int red, int green, int blue) {
        if (red < 0 || red > 255) {
            throw new IllegalArgumentException("Color's red parameter (" + red + ") expects color values 0-255");
        }
        if (green < 0 || green > 255) {
            throw new IllegalArgumentException("Color's green parameter (" + green + ") expects color values 0-255");
        }
        if (blue < 0 || blue > 255) {
            throw new IllegalArgumentException("Color's blue parameter (" + blue + ") expects color values 0-255");
        }
    }
}
