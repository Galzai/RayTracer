package RayTracer.math;

//TODO delete class!


/**
 * Represents a mathematical vector with T dimensions
 */
public interface IVector<T extends Number> {
    

    /**
     * Returns the dimension of the vector
     * @return dimension of the vector
     */
    public Integer dimension();

    /**
     * Returns the euclideanNorm of the vector
     */
    public T euclideanNorm();

    /**
     * Return the member of the vector at index
     * @param index
     * @return the member of the vector at index
     */
    public T get(int index) throws IllegalArgumentException;
}
