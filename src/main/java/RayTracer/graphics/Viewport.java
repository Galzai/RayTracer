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

    Vector3D right;
    Vector3D up;
    Vector3D w;

    public Viewport(int imageWidth, int imageHeight, Camera camera) {

        // Dimensions
        this.width = camera.screenWidth();
        this.aspectRatio = (double) imageWidth / imageHeight;
        this.height =  width / aspectRatio;

        // Pixel dimensions
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.right = camera.right();
        this.up = camera.up();

        // This is where we start moving from the screen
        this.lowerLeftVec = camera.position().subtract(camera.behind().scalarMult(camera.focalLength()));  // forward to the center of the screen
        this.lowerLeftVec = this.lowerLeftVec.subtract(camera.right().scalarMult(this.width / 2));  // left to the left bound of the screen
        this.lowerLeftVec = this.lowerLeftVec.subtract(camera.up().scalarMult(this.height / 2));  // down to the lower bound of the screen

        // scale according to the image dimensions:
        this.right = this.right.scalarMult(width / imageWidth);
        this.up = this.up.scalarMult(height / imageHeight);


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
