package RayTracer.graphics;

import RayTracer.math.Vector3D;
import com.sun.prism.image.ViewPort;

public class Camera {

    // Camera direction vectors
    private Vector3D v; // up direction
    private Vector3D u; // right direction
    private Vector3D w; // facing the opposite direction of "towards" vector
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
        this.w = position.subtract(lookAtPoint).normalize();
        this.u = this.w.crossProduct(up).normalize();
        this.v = this.u.crossProduct(this.w).normalize();
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

    public Vector3D v() {
        return this.v;
    }

    public Vector3D u() {
        return this.u;
    }

    public Vector3D w() {
        return this.w;
    }

    public Vector3D position() {
        return this.position;
    }

    public double focalLength() {
        return focalLength;
    }

    public double screenWidth() {
        return screenWidth;
    }

    void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}
