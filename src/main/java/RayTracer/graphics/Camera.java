package RayTracer.graphics;

import RayTracer.math.Vector3D;

public class Camera {

    // Camera direction vectors
    private Vector3D v;
    private Vector3D u;
    private Vector3D w;
    private Vector3D position;

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
    public Camera(Vector3D position, Vector3D lookAtPoint, Vector3D up, Boolean fisheye) {
        this.position = position;
        this.w = position.subtract(lookAtPoint).normalize();  // facing the opposite direction of "towards" vector
        this.u = up.crossProduct(this.w).normalize();  // right direction
        this.v = this.w.crossProduct(this.u).normalize();  // up direction
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
}
