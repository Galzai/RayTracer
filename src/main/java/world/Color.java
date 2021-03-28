package world;

/**
 *
 */
public class Color {
    private double red;
    private double green;
    private double blue;

    public Color(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color(int red, int green, int blue) {
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

    public int[] getIntRepresentation() {
        int r = (int)Math.round(red * 255.0);
        int g = (int)Math.round(green * 255.0);
        int b = (int)Math.round(blue * 255.0);
        return new int[] {r, g, b};
    }


    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is equal to the {@code obj} argument; {@code false} otherwise.
     */
    @Override public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Color) {
            Color other = (Color) obj;
            return red == other.red
                    && green == other.green
                    && blue == other.blue;
        } else return false;
    }


    /**
     * Returns a string representation of this {@code main.Color}.
     * @return the string representation
     */
    @Override public String toString() {
        int rgb[] = getIntRepresentation();
        return String.format("0x%02x%02x%02x" , rgb[0], rgb[1], rgb[2]);
    }

    private void trimColors() {
        this.red = Math.min(red, 1);
        this.green = Math.min(green, 1);
        this.blue = Math.min(blue, 1);
    }

    private static void checkRGB(int red, int green, int blue) {
        if (red < 0 || red > 255) {
            throw new IllegalArgumentException("main.Color.rgb's red parameter (" + red + ") expects color values 0-255");
        }
        if (green < 0 || green > 255) {
            throw new IllegalArgumentException("main.Color.rgb's green parameter (" + green + ") expects color values 0-255");
        }
        if (blue < 0 || blue > 255) {
            throw new IllegalArgumentException("main.Color.rgb's blue parameter (" + blue + ") expects color values 0-255");
        }
    }
}
