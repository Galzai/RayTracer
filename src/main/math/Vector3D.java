package main.math;

import java.util.ArrayList;

public class Vector3D extends Vector {

    static final int DIMENSION = 3;
    /**
     * 
     * @param members
     * @throws IllegalArgumentException
     */
    Vector3D(ArrayList<Double> members) throws IllegalArgumentException{
        super(members);
        // because of java langauge specs, we can perform the check after constructor call only
        if(members.size() != DIMENSION)
        {
            throw new IllegalArgumentException("Initializer array must contain 3 values");
        }
    }
    
    /**
     * Returns the cross product of this vector and other vector
     * @param otherVector vector to create cross product with
     * @return cross product of vector and otherVector
     */
    public Vector3D crossProduct(Vector3D otherVector){
        ArrayList<Double> newMembers = new ArrayList<Double>();
        // (a_2 * b_3 - a_3 * b_2)
        newMembers.add((m_members.get(1) * otherVector.m_members.get(2)) - (m_members.get(2) * otherVector.m_members.get(1)));
        // -(a_1 * b_3 - a_3 * b_1)
        newMembers.add(-1 * ((m_members.get(0) * otherVector.m_members.get(2)) - (m_members.get(2) * otherVector.m_members.get(0))));
        // (a_1 * b_2 - a_2 * b_1)
        newMembers.add((m_members.get(0) * otherVector.m_members.get(1)) - (m_members.get(1) * otherVector.m_members.get(0)));
        return new Vector3D(newMembers);
    }
}
