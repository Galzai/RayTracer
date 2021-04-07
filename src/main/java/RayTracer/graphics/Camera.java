package RayTracer.graphics;

import RayTracer.math.Vector3D;

public class Camera {

    // Camera direction vectors
    private Vector3D up; // up direction
    private Vector3D right; // right direction
    private Vector3D behind; // facing the opposite direction of "towards" vector
    private Vector3D position;
    private double focalLength;
    // Other members
    private boolean fisheye;
    private double fisheyeCoeff;
    private double screenWidth;
    private Viewport viewport;

    //TODO maybe add ViewPort as a member


    /**
     * Constructs a new camera object with normalized axis vectors
     *
     * @param position    camera position
     * @param lookAtPoint camera look at poing
     * @param up          camera up vector
     * @param fisheye     true if camera uses fisheye lens
     */
    public Camera(Vector3D position, Vector3D lookAtPoint, Vector3D up, double focalLength, double screenWidth, boolean fisheye, double fisheyeCoeff) {
        this.position = position;
        this.behind = position.subtract(lookAtPoint).normalize();
        this.right = this.behind.crossProduct(up).normalize();
        this.up = this.right.crossProduct(this.behind).normalize();
        this.focalLength = focalLength;
        this.fisheye = fisheye;
        this.fisheyeCoeff = fisheyeCoeff;
        this.screenWidth = screenWidth;
    }

    public Camera(Vector3D position, Vector3D lookAtPoint, Vector3D up, double screenWidth, double focalLength, boolean fisheye) {
        this(position, lookAtPoint, up, focalLength, screenWidth, fisheye, 0.5);
    }

    public Camera(Vector3D position, Vector3D lookAtPoint, Vector3D up, double focalLength, double screenWidth) {
        this(position, lookAtPoint, up, focalLength, screenWidth, false, 0.5);
    }

    public Vector3D up() {
        return this.up;
    }

    public Vector3D right() {
        return this.right;
    }

    public Vector3D behind() {
        return this.behind;
    }

    public Vector3D position() {
        return this.position;
    }

    public double focalLength() {
        return this.focalLength;
    }

    public double screenWidth() {
        return this.screenWidth;
    }

    public boolean fisheye() {
        return this.fisheye;
    }

    public double fisheyeCoeff() {
        return this.fisheyeCoeff;
    }

    void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}
