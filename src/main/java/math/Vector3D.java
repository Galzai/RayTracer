package main.java.math;

import java.util.ArrayList;
import java.util.Arrays;

public class Vector3D extends Vector {

    static final int DIMENSION = 3;

    /**
     * construct a new 3D vector
     * @param first first member
     * @param second second member
     * @param third third member
     */
    public Vector3D(Double first, Double second, Double third)
    {
        super(new ArrayList<Double>(Arrays.asList(first, second , third)));
    }
    
    /**
     * Private constructor used for downcasting
     * @param otherVec other vector to create 3d vector from
     * @throws IllegalArgumentException
     */
    private Vector3D(Vector otherVec) throws IllegalArgumentException{
        super(otherVec.m_members);
        if(otherVec.dimension() > 3)
            throw new IllegalArgumentException("Vector must have 3 members");
    }
    /**
     * Returns the cross product of this vector and other vector
     * @param otherVector vector to create cross product with
     * @return cross product of vector and otherVector
     */
    public Vector3D crossProduct(Vector3D otherVector){
        // (a_2 * b_3 - a_3 * b_2)
        Double x = ((m_members.get(1) * otherVector.m_members.get(2)) - (m_members.get(2) * otherVector.m_members.get(1)));
        // -(a_1 * b_3 - a_3 * b_1)
        Double y = (-1 * ((m_members.get(0) * otherVector.m_members.get(2)) - (m_members.get(2) * otherVector.m_members.get(0))));
        // (a_1 * b_2 - a_2 * b_1)
        Double z = ((m_members.get(0) * otherVector.m_members.get(1)) - (m_members.get(1) * otherVector.m_members.get(0)));
        return new Vector3D(x, y, z);
    }

    @Override
    public Vector3D add(Vector otherVector) throws IllegalArgumentException{
        Vector result = super.add(otherVector);
        return new Vector3D(result);
    }
}
    
