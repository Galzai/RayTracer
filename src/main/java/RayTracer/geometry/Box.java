package RayTracer.geometry;

import RayTracer.graphics.Intersection;
import RayTracer.graphics.Material;
import RayTracer.graphics.Ray;
import RayTracer.math.MathUtils;
import RayTracer.math.Matrix3D;
import RayTracer.math.Vector3D;

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


        this.transformationMatrix = Matrix3D.createTransformationMatrix(this.uAxis, this.vAxis, this.wAxis);
        this.inverseTransformationMatrix = Matrix3D.transposeMatrix(this.transformationMatrix);

        Vector3D transformedScales = transformationMatrix.vecMult(scales);
        Vector3D transformedCenter = transformationMatrix.vecMult(center);

        this.minBounds = transformedCenter.subtract(transformedScales.scalarMult(0.5));
        this.maxBounds = transformedCenter.add(transformedScales.scalarMult(0.5));
    }

    @Override
    public Intersection findIntersection(Ray ray) {
        Vector3D transformedOrigin = this.transformationMatrix.vecMult(ray.origin());
        Vector3D transformedDirection = this.transformationMatrix.vecMult((ray.direction()));
        Ray transformedRay = new Ray(transformedOrigin, transformedDirection);

        double tUmin = (this.minBounds.get(0) - transformedRay.origin().get(0)) / transformedRay.direction().get(0);
        double tUMax = (maxBounds.get(0) - transformedRay.origin().get(0)) / transformedRay.direction().get(0);
        double tMin = Math.min(tUmin, tUMax);
        double tMax = Math.max(tUmin, tUMax);

        double tVMin = (minBounds.get(1) - transformedRay.origin().get(1)) / transformedRay.direction().get(1);
        double tVMax = (maxBounds.get(1) - transformedRay.origin().get(1)) / transformedRay.direction().get(1);
        double tVEnter = Math.min(tVMin, tVMax);
        double tVExit = Math.max(tVMin, tVMax);

        if ((tMin > tVExit) || (tVEnter > tMax)) {
            return null;
        }
        tMin = Math.max(tMin, tVEnter);
        tMax = Math.min(tMax, tVExit);

        double tWMin = (minBounds.get(2) - transformedRay.origin().get(2)) / transformedRay.direction().get(2);
        double tWMax = (maxBounds.get(2) - transformedRay.origin().get(2)) / transformedRay.direction().get(2);
        double tWEnter = Math.min(tWMin, tWMax);
        double tWExit = Math.max(tWMin, tWMax);

        if ((tMin > tWMax) || (tWMin > tMax)) {
            return null;
        }

        tMin = Math.max(tMin, tWEnter);
        tMax = Math.min(tMax, tWExit);

        if (tMin < 0) {
            // opposite direction
            return null;
        }

        Vector3D TransformedIntersectionPoint = transformedRay.at(tMin);

        Vector3D transformedNormal = null;

//        if (Math.abs(TransformedIntersectionPoint.get(0) - minBounds.get(0)) <= MathUtils.EPSILON)
//            transformedNormal = this.uAxis.scalarMult(-1.0);
//        else if (Math.abs(TransformedIntersectionPoint.get(0) - maxBounds.get(0)) <= MathUtils.EPSILON)
//            transformedNormal = this.uAxis.scalarMult(1.0);
//        else if (Math.abs(TransformedIntersectionPoint.get(1) - minBounds.get(1)) <= MathUtils.EPSILON)
//            transformedNormal = this.vAxis.scalarMult(-1.0);
//        else if (Math.abs(TransformedIntersectionPoint.get(1) - maxBounds.get(1)) <= MathUtils.EPSILON)
//            transformedNormal = this.vAxis.scalarMult(1.0);
//        else if (Math.abs(TransformedIntersectionPoint.get(2) - minBounds.get(2)) <= MathUtils.EPSILON)
//            transformedNormal = this.wAxis.scalarMult(-1.0);
//        else if (Math.abs(TransformedIntersectionPoint.get(2) - maxBounds.get(2)) <= MathUtils.EPSILON)
//            transformedNormal = this.wAxis.scalarMult(1.0);

        if (Math.abs(TransformedIntersectionPoint.get(0) - this.inverseTransformationMatrix.vecMult(this.minBounds).get(0)) <= MathUtils.EPSILON)
            transformedNormal = this.uAxis.scalarMult(-1.0);
        else if (Math.abs(TransformedIntersectionPoint.get(0) - this.inverseTransformationMatrix.vecMult(this.maxBounds).get(0)) <= MathUtils.EPSILON)
            transformedNormal = this.uAxis.scalarMult(1.0);
        else if (Math.abs(TransformedIntersectionPoint.get(1) - this.inverseTransformationMatrix.vecMult(this.minBounds).get(1)) <= MathUtils.EPSILON)
            transformedNormal = this.vAxis.scalarMult(-1.0);
        else if (Math.abs(TransformedIntersectionPoint.get(1) - this.inverseTransformationMatrix.vecMult(this.maxBounds).get(1)) <= MathUtils.EPSILON)
            transformedNormal = this.vAxis.scalarMult(1.0);
        else if (Math.abs(TransformedIntersectionPoint.get(2) - this.inverseTransformationMatrix.vecMult(this.minBounds).get(2)) <= MathUtils.EPSILON)
            transformedNormal = this.wAxis.scalarMult(-1.0);
        else if (Math.abs(TransformedIntersectionPoint.get(2) - this.inverseTransformationMatrix.vecMult(this.maxBounds).get(2)) <= MathUtils.EPSILON)
            transformedNormal = this.wAxis.scalarMult(1.0);

        Vector3D normal = this.inverseTransformationMatrix.vecMult(transformedNormal);
        Vector3D intersectionPoint = this.inverseTransformationMatrix.vecMult(TransformedIntersectionPoint);
        return new Intersection(intersectionPoint, normal, this, tMin); // same t value

    }

    @Override
    public Material getMaterial() {
        return material;
    }
}
