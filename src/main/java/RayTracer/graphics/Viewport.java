package RayTracer.graphics;

import RayTracer.math.Vector3D;

public class Viewport {

    // Screen dimensions
    private Double width;
    private Double aspectRatio;
    private Double height;

    // Screen Vectors
    private Vector3D lowerLeftVec;
    private Vector3D screenCenter;

    // Used to get point on screen
    Integer imageWidth;
    Integer imageHeight;

    Vector3D right;
    Vector3D up;

    Vector3D rightNormalized;
    Vector3D upNormalized;
    Vector3D w;

    public Viewport(int imageWidth, int imageHeight, Camera camera) {

        // Dimensions
        this.width = camera.screenWidth();
        this.aspectRatio = (double) imageWidth / imageHeight;
        this.height =  width / aspectRatio;

        // Pixel dimensions
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.rightNormalized = camera.right();
        this.upNormalized = camera.up();

        // This is where we start moving from the screen
        this.screenCenter = camera.position().subtract(camera.behind().scalarMult(camera.focalLength()));  // forward to the center of the screen
        this.lowerLeftVec = this.screenCenter.subtract(camera.right().scalarMult(this.width / 2));  // left to the left bound of the screen
        this.lowerLeftVec = this.lowerLeftVec.subtract(camera.up().scalarMult(this.height / 2));  // down to the lower bound of the screen

        // scale according to the image dimensions:
        this.right = this.rightNormalized.scalarMult(width / imageWidth);
        this.up = this.upNormalized.scalarMult(height / imageHeight);


        camera.setViewport(this);
    }

    /**
     * Takes a pixel value and returns the point relative to the camera on the screen of the pixel
     *
     * @param widthPixel  width pixel
     * @param heightPixel height pixel
     * @return point on the screen corresponding to pixel
     */
    public Vector3D pixelToScreenPoint(int widthPixel, int heightPixel) {
        // u and v already scaled
        return this.lowerLeftVec.add(this.right.scalarMult(widthPixel)).add(this.up.scalarMult(heightPixel));
    }

    public int[] screenPointToPixel(Vector3D screenPoint) {
        screenPoint = screenPoint.subtract(this.lowerLeftVec);
        double projRight = rightNormalized.findProjection(screenPoint).euclideanNorm();
        double projUp = upNormalized.findProjection(screenPoint).euclideanNorm();
        return new int[]{(int)(projRight * imageWidth), (int)(projUp * imageHeight)};
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

    public Vector3D getScreenCenter() {
        return this.screenCenter;
    }

    public double getWidth() {
        return this.width;
    }
}
