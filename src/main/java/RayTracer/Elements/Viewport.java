package RayTracer.Elements;

import RayTracer.math.Vector3D;

public class Viewport {
    
    // Screen dimensions
    private Double width;
    private Double aspectRatio;
    private Double focalLength; //TODO I think it is redundant
    private Double height;

    // Screen Vectors
    private Vector3D lowerLeftVec;

    // Used to get point on screen
    Integer imageWidth;
    Integer imageHeight;

    public Viewport(Double width, Double focalLength, int imageWidth, int imageHeight, Camera camera){

        // Dimensions
        this.width = width;
        this.aspectRatio = (double)imageWidth / imageHeight;
        this.focalLength = focalLength;
        this.height = aspectRatio * width;

        // Pixel dimensions
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        // This is where we start moving from the screen
        lowerLeftVec = camera.position().subtract(camera.w().scalarMult(focalLength));  // forward to the center of the screen
        lowerLeftVec = lowerLeftVec.subtract(camera.u().scalarMult(width/2));  // left to the left bound of the screen
        lowerLeftVec = lowerLeftVec.subtract(camera.v().scalarMult(height / 2));  // down to the lower bound of the screen

    }

    /**
     * Takes a pixel value and returns the point relative to the camera on the screen of the pixel
     * @param widthPixel width pixel
     * @param heightPixel height pixel
     * @return point on the screen corresponding to pixel
     */
    public Vector3D pixelToScreenPoint(Integer widthPixel, Integer heightPixel){
        Double widthRatio = widthPixel.doubleValue() / imageWidth;
        Double heightRatio = heightPixel.doubleValue() / imageWidth;
        return new Vector3D(lowerLeftVec.get(0) + widthRatio * width, lowerLeftVec.get(1) + heightRatio * height, lowerLeftVec.get(2));
    }
}
