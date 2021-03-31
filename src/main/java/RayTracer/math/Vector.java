package RayTracer.math;

import java.util.ArrayList;

/**
 * A vector of type double
 */
public class Vector implements IVector<Double> {

    protected ArrayList<Double> members;
    private Double norm;

    /**
     * Calculates the euclidean norm of the vector and returns it
     *
     * @return the norm of the vector
     */
    private Double calculateEuclideanNorm() {
        Double norm = 0.0;
        for (Double val : this.members) {
            norm += val * val;
        }
        return Math.sqrt(norm);
    }

    /**
     * Constructor for our vector, members are set as members of the vector
     * Also initializes the euclidean norm
     *
     * @param members
     */
    public Vector(ArrayList<Double> members) {
        this.members = members;
        this.norm = calculateEuclideanNorm();
    }

    /**
     * Returns the dot product of this vector with another vector of the same size
     *
     * @param otherVector second vector
     * @return dot product of this and second vector
     * @throws IllegalArgumentException
     */
    public Double dotProduct(Vector otherVector) throws IllegalArgumentException {
        if (otherVector.dimension() != dimension())
            throw new IllegalArgumentException("Vector dimensions must match");
        Double dotProduct = 0.0;
        // Calculate the dot product
        for (int index = 0; index < dimension(); ++index) {
            dotProduct += this.members.get(index) * otherVector.members.get(index);
        }
        return dotProduct;
    }

    @Override
    public Double euclideanNorm() {
        return this.norm;
    }

    /**
     * Member wise addition of this vector and othervector
     *
     * @param otherVector second vector
     * @return new Vector which is the addition of the two vectors
     * @throws IllegalArgumentException
     */
    public Vector add(Vector otherVector) throws IllegalArgumentException {
        if (otherVector.dimension() != dimension())
            throw new IllegalArgumentException("Vector dimensions must match");
        ArrayList<Double> newMembers = new ArrayList<Double>();
        // Calculate the new vector product
        for (int index = 0; index < dimension(); ++index) {
            newMembers.add(this.members.get(index) + otherVector.members.get(index));
        }
        return new Vector(newMembers);
    }

    /**
     * Member wise subtraction of this vector and othervector
     *
     * @param otherVector second vector
     * @return new Vector which is the subtraction of the other vector from this vector
     * @throws IllegalArgumentException
     */
    public Vector subtract(Vector otherVector) throws IllegalArgumentException {
        if (otherVector.dimension() != dimension())
            throw new IllegalArgumentException("Vector dimensions must match");
        ArrayList<Double> newMembers = new ArrayList<Double>();
        // Calculate the new vector product
        for (int index = 0; index < dimension(); ++index) {
            newMembers.add(this.members.get(index) - otherVector.members.get(index));
        }
        return new Vector(newMembers);
    }

    /**
     * Returns this vector but normalized
     *
     * @return normalized vector
     */
    public Vector normalize() {
        ArrayList<Double> newMembers = new ArrayList<Double>();
        // Calculate the new vector
        for (int index = 0; index < dimension(); ++index) {
            newMembers.add(this.members.get(index) / euclideanNorm());
        }
        return new Vector(newMembers);
    }

    /**
     * Multplies the vector by a scalar
     *
     * @param scalar
     * @return result of scalar multiplication
     */
    public Vector scalarMult(Double scalar) {
        ArrayList<Double> newMembers = new ArrayList<Double>();
        // Calculate the new vector
        for (int index = 0; index < dimension(); ++index) {
            newMembers.add(this.members.get(index) * scalar);
        }
        return new Vector(newMembers);

    }

    public double calculateDistance(Vector other) {
        if (other.dimension() != dimension())
            throw new IllegalArgumentException("Vector dimensions must match");
        double distance = 0;
        for (int index = 0; index < dimension(); ++index) {
            distance += Math.pow(get(index) - other.get(index), 2);
        }
        return Math.sqrt(distance);
    }

    /**
     * Returns the dimension of the vector
     */
    @Override
    public Integer dimension() {
        return this.members.size();
    }

    /**
     * Returns string representation of vector
     */
    public String toString() {
        return this.members.toString();
    }

    @Override
    public Double get(int index) throws IllegalArgumentException {
        if (dimension() <= index)
            throw new IllegalArgumentException("Index out of bounds");
        return this.members.get(index);
    }

}
