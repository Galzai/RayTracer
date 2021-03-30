package RayTracer.Elements;

import RayTracer.math.Vector3D;

public class Camera {

    // Camera direction vectors
     private Vector3D up;
     private Vector3D right;
     private Vector3D towards;
     private Vector3D position;

    // Other members
     Boolean fisheye;

     /**
      * Constructs a new camera object with normalized axis vectors
      * @param position camera position
      * @param lookAtPoint camera look at poing
      * @param up camera up vector
      * @param fisheye true if camera uses fisheye lens
      */
     public Camera(Vector3D position, Vector3D lookAtPoint, Vector3D up, Boolean fisheye){
        this.position = position;
        this.towards = lookAtPoint.subtract(position).normalize();
        this.right = up.crossProduct(this.towards).normalize();
        this.up = this.towards.crossProduct(this.right).normalize();
        this.fisheye = fisheye;
     }

     public Vector3D up(){
        return this.up;
     }

     public Vector3D right(){
        return this.right;
     }

     public Vector3D towards(){
         return this.towards;
     }

     public Vector3D position(){
         return this.position;
     }
}
