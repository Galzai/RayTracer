package RayTracer.graphics;

import RayTracer.math.Vector3D;

public class Camera {

    // Camera direction vectors
    private Vector3D v; // up direction
    private Vector3D u; // right direction
    private Vector3D w; // facing the opposite direction of "towards" vector
    private Vector3D position;
    private double focalLength;

    // Other members
    Boolean fisheye;

    /**
     * Constructs a new camera object with normalized axis vectors
     *
     * @param position    camera position
     * @param lookAtPoint camera look at poing
     * @param up          camera up vector
     * @param fisheye     true if camera uses fisheye lens
     */
    public Camera(Vector3D position, Vector3D lookAtPoint, Vector3D up, double focalLength, Boolean fisheye) {
        this.position = position;
        this.w = position.subtract(lookAtPoint).normalize();
        this.u = this.w.crossProduct(up).normalize();
        this.v = this.u.crossProduct(this.w).normalize();
        this.focalLength = focalLength;
        this.fisheye = fisheye;
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
}
