package RayTracer.Elements;

import RayTracer.math.Vector3D;

public class Viewport {
    
    // Screen dimensions
    private Double width;
    private Double aspectRatio;
    private Double focalLength; 
    private Double height;

    // Screen Vectors
    private Vector3D lowerLeftVec;

    // Used to get point on screen
    Integer imageWidth;
    Integer imageHeight;

    public Viewport(Double width, Double aspectRatio, Double focalLength, int imageWidth, int imageHeight, Vector3D origin){

        // Dimensions
        this.width = width;
        this.aspectRatio = aspectRatio;
        this.focalLength = focalLength;
        this.height = aspectRatio * width;

        // Pixel dimensions
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        // This is where we start moving from the screen
        lowerLeftVec = new Vector3D(origin.get(0) - (width / 2.0), origin.get(1) - (height / 2.0), focalLength);
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
