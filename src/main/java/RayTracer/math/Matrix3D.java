package RayTracer.math;

public class Matrix3D {
    
    public static final int DIMENSION = 3;
    private Double[][] matrixInner;

    /**
     * Construct a new 3D matrix
     * @param matrix
     * @throws IllegalArgumentException
     */
    public Matrix3D(Double[][] matrix) throws IllegalArgumentException{
        if(matrix.length != 3 || matrix[0].length != 3)
            throw new IllegalArgumentException("Invalid matrix dimensions");
        this.matrixInner = matrix;
    }

    /**
     * Multiplies matrix by vector
     * @param vector to multiply with
     * @return result of multplication
     */
    public Vector3D vecMult(Vector3D vector){
        Double first = 
            matrixInner[0][0] * vector.get(0) + matrixInner[0][1] * vector.get(1) + matrixInner[0][2] * vector.get(2);
        Double second = 
            matrixInner[1][0] * vector.get(0) + matrixInner[1][1] * vector.get(1) + matrixInner[1][2] * vector.get(2);
        Double third = 
        matrixInner[2][0] * vector.get(0) + matrixInner[2][1] * vector.get(1) + matrixInner[2][2] * vector.get(2);

        return new Vector3D(first, second, third);
    }

    /**
     * Creates a transformation matrix  from standard base to right, up ,towards
     * @param right 
     * @param up
     * @param towards
     * @return transformation matrix 
     */
    public static Matrix3D createTransformationMatrix(Vector3D right, Vector3D up, Vector3D towards){
        Double[][] matrix = new Double[3][3];

        matrix[0][0] = right.get(0);
        matrix[0][1] = right.get(1);
        matrix[0][2] = right.get(2);

        matrix[1][0] = up.get(0);
        matrix[1][1] = up.get(1);
        matrix[1][2] = up.get(2);

        matrix[2][0] = towards.get(0);
        matrix[2][1] = towards.get(1);
        matrix[2][2] = towards.get(2);

        return new Matrix3D(matrix);
    }
}
