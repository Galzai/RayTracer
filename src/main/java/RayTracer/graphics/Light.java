package RayTracer.graphics;

import RayTracer.math.Vector3D;

public class Light {
    private Vector3D position;
    private ComputationalColor color;
    private double specularIntensity;
    private double shadowIntensity;
    private double radius;

    public Light(Vector3D position, ComputationalColor color, double specularIntensity, double shadowIntensity, double radius) {
        this.position = position;
        this.color = color;
        this.specularIntensity = specularIntensity;
        this.shadowIntensity = shadowIntensity;
        this.radius = radius;
    }

    public Vector3D getPosition() {
        return position;
    }

    public ComputationalColor getColor() {
        return color;
    }

    public double getSpecularIntensity() {
        return specularIntensity;
    }

    public double getShadowIntensity() {
        return shadowIntensity;
    }

    public double getRadius() {
        return radius;
    }
}
