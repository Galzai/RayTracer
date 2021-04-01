package RayTracer.math;

public class Matrix3D {
    public static final int DIMENSION = 3;
    public static final Vector3D xAxis = new Vector3D(1, 0, 0);
    public static final Vector3D yAxis = new Vector3D(0, 1, 0);
    public static final Vector3D zAxis = new Vector3D(0, 0, 1);
    private Double[][] matrixInner;

    /**
     * Construct a new 3D matrix
     *
     * @param matrix
     * @throws IllegalArgumentException
     */
    public Matrix3D(Double[][] matrix) throws IllegalArgumentException {
        if (matrix.length != 3 || matrix[0].length != 3)
            throw new IllegalArgumentException("Invalid matrix dimensions");
        this.matrixInner = matrix;
    }

    /**
     * Multiplies matrix by vector
     *
     * @param vector to multiply with
     * @return result of multplication
     */
    public Vector3D vecMult(Vector3D vector) {
        Double first =
                matrixInner[0][0] * vector.get(0) + matrixInner[0][1] * vector.get(1) + matrixInner[0][2] * vector.get(2);
        Double second =
                matrixInner[1][0] * vector.get(0) + matrixInner[1][1] * vector.get(1) + matrixInner[1][2] * vector.get(2);
        Double third =
                matrixInner[2][0] * vector.get(0) + matrixInner[2][1] * vector.get(1) + matrixInner[2][2] * vector.get(2);

        return new Vector3D(first, second, third);
    }

    /**
     * Creates a transformation matrix from standard base to u, v, w
     *
     * @param u
     * @param v
     * @param w
     * @return transformation matrix
     */
    public static Matrix3D createTransformationMatrix(Vector3D u, Vector3D v, Vector3D w) {
        Double[][] matrix = new Double[3][3];
        matrix[0][0] = u.get(0);
        matrix[0][1] = v.get(0);
        matrix[0][2] = w.get(0);

        matrix[1][0] = u.get(1);
        matrix[1][1] = v.get(1);
        matrix[1][2] = w.get(1);

        matrix[2][0] = u.get(2);
        matrix[2][1] = v.get(2);
        matrix[2][2] = w.get(2);

        return new Matrix3D(matrix);
    }

    /**
     * Creates the transposed matrix
     *
     * @param matrix
     * @return new transposed matrix
     */
    public static Matrix3D transposeMatrix(Matrix3D matrix) {
        Double[][] transpose = new Double[3][3];
        transpose[0][0] = matrix.matrixInner[0][0];
        transpose[0][1] = matrix.matrixInner[1][0];
        transpose[0][2] = matrix.matrixInner[2][0];

        transpose[1][0] = matrix.matrixInner[0][1];
        transpose[1][1] = matrix.matrixInner[1][1];
        transpose[1][2] = matrix.matrixInner[2][1];

        transpose[2][0] = matrix.matrixInner[0][2];
        transpose[2][1] = matrix.matrixInner[1][2];
        transpose[2][2] = matrix.matrixInner[2][2];

        return new Matrix3D(transpose);
    }

    /**
     * Creates a rotation matrix around the x-axis clockwise (in left coordinates system)
     *
     * @param angle Rotation angle in radians
     * @return rotation matrix around the x-axis clockwise (in left coordinates system)
     */
    public static Matrix3D createXRotationMatrix(double angle) {
        Double[][] matrix = new Double[3][3];
        matrix[0][0] = 1.0;
        matrix[0][1] = 0.0;
        matrix[0][2] = 0.0;

        matrix[1][0] = 0.0;
        matrix[1][1] = Math.cos(angle);
        matrix[1][2] = Math.sin(angle);

        matrix[2][0] = 0.0;
        matrix[2][1] = -Math.sin(angle);
        matrix[2][2] = Math.cos(angle);

        return new Matrix3D(matrix);

    }

    /**
     * Creates a rotation matrix around the y-axis clockwise (in left coordinates system)
     *
     * @param angle Rotation angle in radian
     * @return rotation matrix around the y-axis clockwise (in left coordinates system)
     */
    public static Matrix3D createYRotationMatrix(double angle) {
        Double[][] matrix = new Double[3][3];
        matrix[0][0] = Math.cos(angle);
        matrix[0][1] = 0.0;
        matrix[0][2] = -Math.sin(angle);

        matrix[1][0] = 0.0;
        matrix[1][1] = 1.0;
        matrix[1][2] = 0.0;

        matrix[2][0] = Math.sin(angle);
        matrix[2][1] = 0.0;
        matrix[2][2] = Math.cos(angle);

        return new Matrix3D(matrix);

    }

    /**
     * Creates a rotation matrix around the z-axis clockwise (in left coordinates system)
     *
     * @param angle Rotation angle in radians
     * @return rotation matrix around the z-axis clockwise (in left coordinates system)
     */
    public static Matrix3D createZRotationMatrix(double angle) {
        Double[][] matrix = new Double[3][3];
        matrix[0][0] = Math.cos(angle);
        matrix[0][1] = Math.sin(angle);
        matrix[0][2] = 0.0;

        matrix[1][0] = -Math.sin(angle);
        matrix[1][1] = Math.cos(angle);
        matrix[1][2] = 0.0;

        matrix[2][0] = 0.0;
        matrix[2][1] = 0.0;
        matrix[2][2] = 1.0;

        return new Matrix3D(matrix);

    }


}