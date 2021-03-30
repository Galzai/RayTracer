package RayTracer.math;

import java.util.ArrayList;
import java.util.Arrays;

public class Vector3D extends Vector {

    static final int DIMENSION = 3;

    /**
     * construct a new 3D vector
     *
     * @param first  first member
     * @param second second member
     * @param third  third member
     */
    public Vector3D(Double first, Double second, Double third) {
        super(new ArrayList<Double>(Arrays.asList(first, second, third)));
    }

    /**
     * Private constructor used for downcasting
     *
     * @param otherVec other vector to create 3d vector from
     * @throws IllegalArgumentException
     */
    private Vector3D(Vector otherVec) throws IllegalArgumentException {
        super(otherVec.members);
        if (otherVec.dimension() > 3)
            throw new IllegalArgumentException("Vector must have 3 members");
    }

    /**
     * Returns the cross product of this vector and other vector
     *
     * @param otherVector vector to create cross product with
     * @return cross product of vector and otherVector
     */
    public Vector3D crossProduct(Vector3D otherVector) {
        // (a_2 * b_3 - a_3 * b_2)
        Double x = ((this.members.get(1) * otherVector.members.get(2)) - (this.members.get(2) * otherVector.members.get(1)));
        // -(a_1 * b_3 - a_3 * b_1)
        Double y = (-1 * ((this.members.get(0) * otherVector.members.get(2)) - (this.members.get(2) * otherVector.members.get(0))));
        // (a_1 * b_2 - a_2 * b_1)
        Double z = ((this.members.get(0) * otherVector.members.get(1)) - (this.members.get(1) * otherVector.members.get(0)));
        return new Vector3D(x, y, z);
    }

    /**
     * Returns component of othervector which is perpendicular to this
     *
     * @param otherVector
     * @return component of othervector which is perpendicular to this
     */
    public Vector3D findPerpendicular(Vector3D otherVector) {
        // Find projection of b onto a
        double norm = euclideanNorm();
        Vector3D projOther = scalarMult(dotProduct(otherVector) / norm * norm);
        // This is the component of b perpendicular to a
        return otherVector.subtract(projOther);
    }

    @Override
    public Vector3D add(Vector otherVector) throws IllegalArgumentException {
        Vector result = super.add(otherVector);
        return new Vector3D(result);
    }

    @Override
    public Vector3D subtract(Vector otherVector) throws IllegalArgumentException {
        Vector result = super.subtract(otherVector);
        return new Vector3D(result);
    }

    @Override
    public Vector3D normalize() {
        return new Vector3D(super.normalize());
    }

    @Override
    public Vector3D scalarMult(Double scalar) {
        return new Vector3D(super.scalarMult(scalar));
    }
}
    
