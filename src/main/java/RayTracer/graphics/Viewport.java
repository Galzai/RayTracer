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

    Camera camera; //TODO temporary solution

    public Viewport(Double width, int imageWidth, int imageHeight, Camera camera) {

        // Dimensions
        this.width = width;
        this.aspectRatio = (double) imageWidth / imageHeight;
        this.height =  width / aspectRatio;

        // Pixel dimensions
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        // This is where we start moving from the screen
        lowerLeftVec = camera.position().subtract(camera.w().scalarMult(camera.focalLength()));  // forward to the center of the screen
        lowerLeftVec = lowerLeftVec.subtract(camera.u().scalarMult(width / 2));  // left to the left bound of the screen
        lowerLeftVec = lowerLeftVec.subtract(camera.v().scalarMult(height / 2));  // down to the lower bound of the screen

        this.camera = camera;

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
        return lowerLeftVec.add(camera.u().scalarMult(widthRatio * width)).add(camera.v().scalarMult(heightRatio * height));
//        TODO delete: return new Vector3D(lowerLeftVec.get(0) + widthRatio * width, lowerLeftVec.get(1) + heightRatio * height, lowerLeftVec.get(2));
    }

}
