package RayTracer.graphics;

import RayTracer.math.Vector3D;

public class Viewport {

    // Screen dimensions
    private Double width;
    private Double aspectRatio;
    private Double height;

    // Screen Vectors
    private Vector3D lowerLeftVec;

    // Used to get point on screen
    Integer imageWidth;
    Integer imageHeight;

    Vector3D u;
    Vector3D v;
    Vector3D w;

    public Viewport(int imageWidth, int imageHeight, Camera camera) {

        // Dimensions
        this.width = camera.screenWidth();
        this.aspectRatio = (double) imageWidth / imageHeight;
        this.height =  width / aspectRatio;

        // Pixel dimensions
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.u = camera.u();
        this.v = camera.v();
        this.w = camera.w();

        // This is where we start moving from the screen
        this.lowerLeftVec = camera.position().subtract(camera.w().scalarMult(camera.focalLength()));  // forward to the center of the screen
        this.lowerLeftVec = this.lowerLeftVec.subtract(camera.u().scalarMult(this.width / 2));  // left to the left bound of the screen
        this.lowerLeftVec = this.lowerLeftVec.subtract(camera.v().scalarMult(this.height / 2));  // down to the lower bound of the screen

        camera.setViewport(this);
    }

    /**
     * Takes a pixel value and returns the point relative to the camera on the screen of the pixel
     *
     * @param widthPixel  width pixel
     * @param heightPixel height pixel
     * @return point on the screen corresponding to pixel
     */
    public Vector3D pixelToScreenPoint(Integer widthPixel, Integer heightPixel) {
        double widthRatio = widthPixel.doubleValue() / imageWidth;
        double heightRatio = heightPixel.doubleValue() / imageHeight;
        return this.lowerLeftVec.add(this.u.scalarMult(widthRatio * this.width)).add(this.v.scalarMult(heightRatio * this.height));
    }

    public Integer getImageWidth() {
        return this.imageWidth;
    }

    public Integer getImageHeight() {
        return this.imageWidth;
    }

    public Vector3D getLowerLeftVec() {
        return this.lowerLeftVec;
    }
}
