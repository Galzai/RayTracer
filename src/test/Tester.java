package test;

import main.java.math.Vector3D;

public class Tester {
    public static void main(String[] args){

        Vector3D testVec1 = new Vector3D(0.0, 1.1, 2.2);
        Vector3D testVec2 = new Vector3D(1.1, 2.5, 3.9);
        Vector3D resultVec = testVec1.add(testVec2);
        System.out.println(resultVec.get(1));
        System.out.println(testVec1.dotProduct(testVec2));
    }

}
