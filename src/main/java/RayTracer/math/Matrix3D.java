package RayTracer.math;

import java.awt.*;

public class Matrix3D {
    public static final int DIMENSION = 3;
    public static final Vector3D xAxis = new Vector3D(1, 0, 0);
    public static final Vector3D yAxis = new Vector3D(0, 1, 0);
    public static final Vector3D zAxis = new Vector3D(0, 0, 1);
    private double[][] matrixInner;

    /**
     * Construct a new 3D matrix
     *
     * @param matrix
     * @throws IllegalArgumentException
     */
    public Matrix3D(double[][] matrix) throws IllegalArgumentException {
        if (matrix.length != DIMENSION || matrix[0].length != DIMENSION)
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
        double first =
                matrixInner[0][0] * vector.getFirst() + matrixInner[0][1] * vector.getSecond() + matrixInner[0][2] * vector.getThird();
        double second =
                matrixInner[1][0] * vector.getFirst() + matrixInner[1][1] * vector.getSecond() + matrixInner[1][2] * vector.getThird();
        double third =
                matrixInner[2][0] * vector.getFirst() + matrixInner[2][1] * vector.getSecond() + matrixInner[2][2] * vector.getThird();

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
        double[][] matrix = new double[DIMENSION][DIMENSION];
        matrix[0][0] = u.getFirst();
        matrix[0][1] = v.getFirst();
        matrix[0][2] = w.getFirst();

        matrix[1][0] = u.getSecond();
        matrix[1][1] = v.getSecond();
        matrix[1][2] = w.getSecond();

        matrix[2][0] = u.getThird();
        matrix[2][1] = v.getThird();
        matrix[2][2] = w.getThird();

        return new Matrix3D(matrix);
    }

    /**
     * Creates the transposed matrix
     *
     * @param matrix
     * @return new transposed matrix
     */
    public static Matrix3D transposeMatrix(Matrix3D matrix) {
        double[][] transpose = new double[DIMENSION][DIMENSION];
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
        double[][] matrix = new double[DIMENSION][DIMENSION];
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
        double[][] matrix = new double[DIMENSION][DIMENSION];
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
        double[][] matrix = new double[DIMENSION][DIMENSION];
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