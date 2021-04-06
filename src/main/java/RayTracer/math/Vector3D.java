package RayTracer.math;

public class Vector3D {
    private double first;
    private double second;
    private double third;
    double norm;

    /**
     * construct a new 3D vector
     *
     * @param first  first member
     * @param second second member
     * @param third  third member
     */
    public Vector3D(double first, double second, double third) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.norm = calculateEuclideanNorm();
    }

    /**
     * construct a new 3D vector
     *
     * @param first  first member
     * @param second second member
     * @param third  third member
     */
    public Vector3D(int first, int second, int third) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.norm = calculateEuclideanNorm();
    }

    /**
     * Private constructor used for down-casting
     *
     * @param otherVec other vector to create 3d vector from
     * @throws IllegalArgumentException
     */
    private Vector3D(Vector3D otherVec) {
        this.first = otherVec.first;
        this.second = otherVec.second;
        this.third = otherVec.third;
        this.norm = otherVec.norm;
    }

    /**
     * Returns the cross product of this vector and other vector
     *
     * @param otherVector vector to create cross product with
     * @return cross product of vector and otherVector
     */
    public Vector3D crossProduct(Vector3D otherVector) {
        // a_2 * b_3 - a_3 * b_2
        double x = this.second * otherVector.third - this.third * otherVector.second;
        // a_3 * b_1 - a_1 * b_3)
        double y = this.third * otherVector.first - this.first * otherVector.third;
        // a_1 * b_2 - a_2 * b_1
        double z = this.first * otherVector.second - this.second * otherVector.first;
        return new Vector3D(x, y, z);
    }

    /**
     * Returns component of othervector which is parallel to this
     *
     * @param otherVector
     * @return component of othervector which is parallel to this
     */
    public Vector3D findProjection(Vector3D otherVector) {
        // Find projection of b onto a
        double norm = euclideanNorm();
        return scalarMult(dotProduct(otherVector) / norm * norm);
    }

    public double getFirst() {
        return first;
    }

    public double getSecond() {
        return second;
    }

    public double getThird() {
        return third;
    }

    /**
     * Returns component of othervector which is perpendicular to this
     *
     * @param otherVector
     * @return component of othervector which is perpendicular to this
     */
    public Vector3D findPerpendicular(Vector3D otherVector) {
        // Find projection of b onto a
        Vector3D projOther = scalarMult(dotProduct(otherVector) / (this.norm * this.norm));
        // This is the component of b perpendicular to a
        return otherVector.subtract(projOther);
    }

    /**
     * find the angle from firstVector to other vector
     * 
     * @param firstVector
     * @param otherVector
     * @return angle from first vector to otherVector
     */
    public static double findAngle(Vector3D firstVector, Vector3D otherVector) {
        return Math.atan(firstVector.dotProduct(otherVector) / (firstVector.euclideanNorm() * otherVector.euclideanNorm()));
    }

    public double euclideanNorm() {
        return this.norm;
    }

    public Vector3D add(Vector3D otherVector) {
        return new Vector3D(this.first + otherVector.first, this.second + otherVector.second, this.third + otherVector.third);
    }

    public Vector3D componentMult(Vector3D otherVector) {
        return new Vector3D(this.first * otherVector.first, this.second * otherVector.second, this.third * otherVector.third);
    }

    public Vector3D subtract(Vector3D otherVector) {
        return new Vector3D(this.first - otherVector.first, this.second - otherVector.second, this.third - otherVector.third);
    }

    public Vector3D normalize() {
        return new Vector3D(first / norm, second / norm, third / norm);
    }

    public Vector3D scalarMult(double scalar) {
        return new Vector3D(this.first * scalar, this.second * scalar, this.third * scalar);
    }


    /**
     * Returns the dot product of this vector with another vector
     *
     * @param otherVector second vector
     * @return dot product of this and second vector
     * @throws IllegalArgumentException
     */
    public Double dotProduct(Vector3D otherVector) {
        return this.first * otherVector.first + this.second * otherVector.second + this.third * otherVector.third;
    }

    /**
     * @param other
     * @return euclidean distance between this and other
     */

    public double calculateDistance(Vector3D other) {
        double distance = Math.pow(this.first - other.first, 2) + Math.pow(this.second - other.second, 2) + Math.pow(this.third - other.third, 2);
        return Math.sqrt(distance);
    }

    public double get(int index) {
        if (index < 0 || index > 2) {
            throw new IllegalArgumentException("index should be between 0 to 2!");
        }
        if (index == 0) {
            return this.first;
        } else if (index == 1) {
            return this.second;
        }
        return this.third;
    }


    /**
     * Calculates the euclidean norm of the vector and returns it
     *
     * @return the norm of the vector
     */
    private double calculateEuclideanNorm() {
        double norm = first * first + second * second + third * third;
        return Math.sqrt(norm);
    }

}
    
