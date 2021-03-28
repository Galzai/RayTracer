package main.java.math;

import java.util.ArrayList;

/**
 * A vector of type double
 */
public class Vector implements IVector<Double>{

    protected ArrayList<Double> m_members;
    private Double m_norm;

    /**
     * Calculates the eucledian norm of the vector and returns it
     * @return the norm of the vector
     */
    private Double calculateEucledianNorm()
    {
        Double norm = 0.0;
        for(Double val : m_members){
            norm += val * val;
        }
        return norm;
    }

    /**
     * Constructor for our vector, members are set as members of the vector
     * Also initializes the eucledian norm
     * @param members
     */
    public Vector(ArrayList<Double> members)
    {
        m_members = members;
        m_norm = calculateEucledianNorm();
    }

    /**
     * Returns the dot product of this vector with another vector of the same size
     * @param otherVector second vector
     * @return dot product of this and second vector
     * @throws IllegalArgumentException
     */
    public Double dotProduct(Vector otherVector) throws IllegalArgumentException{
        if(otherVector.dimension() != dimension())
            throw new IllegalArgumentException("Vector dimensions must match");
        Double dotProduct = 0.0;
        // Calculate the dot product
        for(int index = 0; index < dimension(); ++index){
            dotProduct += m_members.get(index) * otherVector.m_members.get(index);
        }
        return dotProduct;
    }

    @Override
    public Double eucledianNorm() {
        return m_norm;
    }

    /**
     * Member wise addition of this vector and othervector
     * @param otherVector second vector
     * @return new Vector which is the addition of the two vectors
     * @throws IllegalArgumentException
     */
    public Vector add(Vector otherVector) throws IllegalArgumentException{
        if(otherVector.dimension() != dimension())
            throw new IllegalArgumentException("Vector dimensions must match");
        ArrayList<Double> newMembers = new ArrayList<Double>();
        // Calculate the new vector product
        for(int index = 0; index < dimension(); ++index){
            newMembers.add(m_members.get(index) + otherVector.m_members.get(index));
        }
        return new Vector(newMembers);
    }

    /**
     * Returns the dimension of the vector
     */
    @Override
    public Integer dimension() {
        return m_members.size();
    }

    @Override
    public Double get(int index) throws IllegalArgumentException {
        if(dimension() <= index)
            throw new IllegalArgumentException("Index out of bounds");
        return m_members.get(index);
    }

}
