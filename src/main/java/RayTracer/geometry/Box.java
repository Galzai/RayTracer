package RayTracer.geometry;

import RayTracer.graphics.Intersection;
import RayTracer.graphics.Material;
import RayTracer.graphics.Ray;
import RayTracer.math.MathUtils;
import RayTracer.math.Matrix3D;
import RayTracer.math.Vector3D;

import java.util.Random;

public class Box implements Surface{
    private Vector3D uAxis;
    private Vector3D vAxis;
    private Vector3D wAxis;
    //TODO remove redundant members:
    private Vector3D center;
    private Vector3D scales;
    private Vector3D rotations;
    private Vector3D minBounds;
    private Vector3D maxBounds;
    private Matrix3D transformationMatrix;
    private Matrix3D inverseTransformationMatrix;
    private Material material;

    private Vector3D tMinBounds;
    private Vector3D tMaxBounds;


    public Box(Vector3D center, Vector3D scales, Vector3D rotations, Material material) {
        this.center = center;
        this.scales = scales;
        this.rotations = rotations;
        this.material = material;
        Matrix3D xRotation = Matrix3D.createXRotationMatrix(rotations.get(0));
        Matrix3D yRotation = Matrix3D.createYRotationMatrix(rotations.get(1));
        Matrix3D zRotation = Matrix3D.createZRotationMatrix(rotations.get(2));

        this.uAxis = zRotation.vecMult(yRotation.vecMult(Matrix3D.xAxis));
        this.vAxis = zRotation.vecMult(yRotation.vecMult(xRotation.vecMult(Matrix3D.yAxis)));
        this.wAxis = zRotation.vecMult(yRotation.vecMult(xRotation.vecMult(Matrix3D.zAxis)));


//        this.transformationMatrix = Matrix3D.createTransformationMatrix(this.uAxis, this.vAxis, this.wAxis);
//        this.inverseTransformationMatrix = Matrix3D.transposeMatrix(this.transformationMatrix);

//        Vector3D transformedScales = transformationMatrix.vecMult(scales);
//        Vector3D transformedCenter = transformationMatrix.vecMult(center);
//
//        this.minBounds = center.subtract(transformedScales.scalarMult(0.5));
//        this.maxBounds = center.add(transformedScales.scalarMult(0.5));
//
//        this.tMinBounds = this.inverseTransformationMatrix.vecMult(minBounds);
//        this.tMaxBounds = this.inverseTransformationMatrix.vecMult(maxBounds);
    }

    @Override
    public Intersection findIntersection(Ray ray) {
        double tNear = Double.NEGATIVE_INFINITY;
        double tFar = Double.POSITIVE_INFINITY;
        Vector3D CP = center.subtract(ray.origin());

        double r = uAxis.dotProduct(CP);
        double s = uAxis.dotProduct(ray.direction());

        double t0 = (r + scales.get(0) / 2) / s;
        double t1 = (r - scales.get(0) / 2) / s;

        if (t0 > t1) {
            double tmp = t0;
            t0 = t1;
            t1 = tmp;
        }
        tNear = Math.max(tNear,t0);
        tFar = Math.min(tFar, t1);

        if (tNear > tFar || tFar < 0) {
            return null;
        }

        r = vAxis.dotProduct(CP);
        s = vAxis.dotProduct(ray.direction());
        t0 = (r + scales.get(1) / 2) / s;
        t1 = (r - scales.get(1) / 2) / s;

        if (t0 > t1) {
            double tmp = t0;
            t0 = t1;
            t1 = tmp;
        }
        tNear = Math.max(tNear,t0);
        tFar = Math.min(tFar, t1);

        if (tNear > tFar || tFar < 0) {
            return null;
        }

        r = wAxis.dotProduct(CP);
        s = wAxis.dotProduct(ray.direction());
        t0 = (r + scales.get(2) / 2) / s;
        t1 = (r - scales.get(2) / 2) / s;

        if (t0 > t1) {
            double tmp = t0;
            t0 = t1;
            t1 = tmp;
        }
        tNear = Math.max(tNear,t0);
        tFar = Math.min(tFar, t1);

        if (tNear > tFar || tFar < 0) {
            return null;
        }

        if (tNear < 0) {
            return null; // opposite direction
        }

        Vector3D intersectionPoint = ray.at(tNear);
        Vector3D normal = new Vector3D(1,2,2);
        return new Intersection(intersectionPoint, normal, this, tNear);




//        Vector3D transformedOrigin = this.transformationMatrix.vecMult(ray.origin());
//        Vector3D transformedDirection = this.transformationMatrix.vecMult((ray.direction()));
//        Ray transformedRay = new Ray(transformedOrigin, transformedDirection);
//
//        double tUmin = (this.tMinBounds.get(0) - transformedRay.origin().get(0)) / transformedRay.direction().get(0);
//        double tUMax = (tMaxBounds.get(0) - transformedRay.origin().get(0)) / transformedRay.direction().get(0);
//        double tMin = Math.min(tUmin, tUMax);
//        double tMax = Math.max(tUmin, tUMax);
//
//        double tVMin = (tMinBounds.get(1) - transformedRay.origin().get(1)) / transformedRay.direction().get(1);
//        double tVMax = (tMaxBounds.get(1) - transformedRay.origin().get(1)) / transformedRay.direction().get(1);
//        double tVEnter = Math.min(tVMin, tVMax);
//        double tVExit = Math.max(tVMin, tVMax);
//
//        if ((tMin > tVExit) || (tVEnter > tMax)) {
//            return null;
//        }
//        tMin = Math.max(tMin, tVEnter);
//        tMax = Math.min(tMax, tVExit);
//
//        double tWMin = (tMinBounds.get(2) - transformedRay.origin().get(2)) / transformedRay.direction().get(2);
//        double tWMax = (tMaxBounds.get(2) - transformedRay.origin().get(2)) / transformedRay.direction().get(2);
//        double tWEnter = Math.min(tWMin, tWMax);
//        double tWExit = Math.max(tWMin, tWMax);
//
//        if ((tMin > tWMax) || (tWMin > tMax)) {
//            return null;
//        }
//
//        tMin = Math.max(tMin, tWEnter);
//        tMax = Math.min(tMax, tWExit);
//
//        if (tMin < 0) {
//            // opposite direction
//            return null;
//        }

//        Vector3D TransformedIntersectionPoint = transformedRay.at(tMin);
//        Vector3D normal = null;
//
//        if (Math.abs(TransformedIntersectionPoint.get(0) - this.tMinBounds.get(0)) <= MathUtils.EPSILON)
//            normal = this.uAxis.scalarMult(-1.0);
//        else if (Math.abs(TransformedIntersectionPoint.get(0) - this.tMaxBounds.get(0)) <= MathUtils.EPSILON)
//            normal = this.uAxis.scalarMult(1.0);
//        else if (Math.abs(TransformedIntersectionPoint.get(1) - this.tMinBounds.get(1)) <= MathUtils.EPSILON)
//            normal = this.vAxis.scalarMult(-1.0);
//        else if (Math.abs(TransformedIntersectionPoint.get(1) - this.tMaxBounds.get(1)) <= MathUtils.EPSILON)
//            normal = this.vAxis.scalarMult(1.0);
//        else if (Math.abs(TransformedIntersectionPoint.get(2) - this.tMinBounds.get(2)) <= MathUtils.EPSILON)
//            normal = this.wAxis.scalarMult(-1.0);
//        else if (Math.abs(TransformedIntersectionPoint.get(2) - this.tMaxBounds.get(2)) <= MathUtils.EPSILON)
//            normal = this.wAxis.scalarMult(1.0);
//
//        Vector3D intersectionPoint = this.inverseTransformationMatrix.vecMult(TransformedIntersectionPoint);
//        Vector3D intersectionPoint2 = ray.at(tMin);
//        return new Intersection(intersectionPoint, normal, this, tMin); // same t value

    }

    @Override
    public Material getMaterial() {
        return material;
    }
}



//package RayTracer.geometry;
//
//import RayTracer.graphics.Intersection;
//import RayTracer.graphics.Material;
//import RayTracer.graphics.Ray;
//import RayTracer.math.MathUtils;
//import RayTracer.math.Matrix3D;
//import RayTracer.math.Vector3D;
//
//public class Box implements Surface{
//    private Vector3D uAxis;
//    private Vector3D vAxis;
//    private Vector3D wAxis;
//    //TODO remove redundant members:
//    private Vector3D center;
//    private Vector3D scales;
//    private Vector3D rotations;
//    private Vector3D minBounds;
//    private Vector3D maxBounds;
//    private Matrix3D transformationMatrix;
//    private Matrix3D inverseTransformationMatrix;
//    private Material material;
//
//    private Vector3D tMinBounds;
//    private Vector3D tMaxBounds;
//
//
//    public Box(Vector3D center, Vector3D scales, Vector3D rotations, Material material) {
//        this.center = center;
//        this.scales = scales;
//        this.rotations = rotations;
//        this.material = material;
//        Matrix3D xRotation = Matrix3D.createXRotationMatrix(rotations.get(0));
//        Matrix3D yRotation = Matrix3D.createYRotationMatrix(rotations.get(1));
//        Matrix3D zRotation = Matrix3D.createZRotationMatrix(rotations.get(2));
//
//        this.uAxis = zRotation.vecMult(yRotation.vecMult(Matrix3D.xAxis));
//        this.vAxis = zRotation.vecMult(yRotation.vecMult(xRotation.vecMult(Matrix3D.yAxis)));
//        this.wAxis = zRotation.vecMult(yRotation.vecMult(xRotation.vecMult(Matrix3D.zAxis)));
//
//
//        this.transformationMatrix = Matrix3D.createTransformationMatrix(this.uAxis, this.vAxis, this.wAxis);
//        this.inverseTransformationMatrix = Matrix3D.transposeMatrix(this.transformationMatrix);
//
//        Vector3D transformedScales = transformationMatrix.vecMult(scales);
//        Vector3D transformedCenter = transformationMatrix.vecMult(center);
//
//        this.minBounds = center.subtract(transformedScales.scalarMult(0.5));
//        this.maxBounds = center.add(transformedScales.scalarMult(0.5));
//
//        this.tMinBounds = this.inverseTransformationMatrix.vecMult(minBounds);
//        this.tMaxBounds = this.inverseTransformationMatrix.vecMult(maxBounds);
//    }
//
//    @Override
//    public Intersection findIntersection(Ray ray) {
//        Vector3D transformedOrigin = this.transformationMatrix.vecMult(ray.origin());
//        Vector3D transformedDirection = this.transformationMatrix.vecMult((ray.direction()));
//        Ray transformedRay = new Ray(transformedOrigin, transformedDirection);
//
//        double tUmin = (this.tMinBounds.get(0) - transformedRay.origin().get(0)) / transformedRay.direction().get(0);
//        double tUMax = (tMaxBounds.get(0) - transformedRay.origin().get(0)) / transformedRay.direction().get(0);
//        double tMin = Math.min(tUmin, tUMax);
//        double tMax = Math.max(tUmin, tUMax);
//
//        double tVMin = (tMinBounds.get(1) - transformedRay.origin().get(1)) / transformedRay.direction().get(1);
//        double tVMax = (tMaxBounds.get(1) - transformedRay.origin().get(1)) / transformedRay.direction().get(1);
//        double tVEnter = Math.min(tVMin, tVMax);
//        double tVExit = Math.max(tVMin, tVMax);
//
//        if ((tMin > tVExit) || (tVEnter > tMax)) {
//            return null;
//        }
//        tMin = Math.max(tMin, tVEnter);
//        tMax = Math.min(tMax, tVExit);
//
//        double tWMin = (tMinBounds.get(2) - transformedRay.origin().get(2)) / transformedRay.direction().get(2);
//        double tWMax = (tMaxBounds.get(2) - transformedRay.origin().get(2)) / transformedRay.direction().get(2);
//        double tWEnter = Math.min(tWMin, tWMax);
//        double tWExit = Math.max(tWMin, tWMax);
//
//        if ((tMin > tWMax) || (tWMin > tMax)) {
//            return null;
//        }
//
//        tMin = Math.max(tMin, tWEnter);
//        tMax = Math.min(tMax, tWExit);
//
//        if (tMin < 0) {
//            // opposite direction
//            return null;
//        }
//
//        Vector3D TransformedIntersectionPoint = transformedRay.at(tMin);
//        Vector3D normal = null;
//
//        if (Math.abs(TransformedIntersectionPoint.get(0) - this.tMinBounds.get(0)) <= MathUtils.EPSILON)
//            normal = this.uAxis.scalarMult(-1.0);
//        else if (Math.abs(TransformedIntersectionPoint.get(0) - this.tMaxBounds.get(0)) <= MathUtils.EPSILON)
//            normal = this.uAxis.scalarMult(1.0);
//        else if (Math.abs(TransformedIntersectionPoint.get(1) - this.tMinBounds.get(1)) <= MathUtils.EPSILON)
//            normal = this.vAxis.scalarMult(-1.0);
//        else if (Math.abs(TransformedIntersectionPoint.get(1) - this.tMaxBounds.get(1)) <= MathUtils.EPSILON)
//            normal = this.vAxis.scalarMult(1.0);
//        else if (Math.abs(TransformedIntersectionPoint.get(2) - this.tMinBounds.get(2)) <= MathUtils.EPSILON)
//            normal = this.wAxis.scalarMult(-1.0);
//        else if (Math.abs(TransformedIntersectionPoint.get(2) - this.tMaxBounds.get(2)) <= MathUtils.EPSILON)
//            normal = this.wAxis.scalarMult(1.0);
//
//        Vector3D intersectionPoint = this.inverseTransformationMatrix.vecMult(TransformedIntersectionPoint);
//        Vector3D intersectionPoint2 = ray.at(tMin);
//        return new Intersection(intersectionPoint, normal, this, tMin); // same t value
//
//    }
//
//    @Override
//    public Material getMaterial() {
//        return material;
//    }
//}
