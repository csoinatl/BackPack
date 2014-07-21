
package Project2.Part2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * CS6423 Algorithmic Processes
 * Summer 2014
 * Project 2: Part 2 - Reads the numbers from the file
 * Daniel Kerr and Charles So
 * Date: 07/06/2014
 * File: FileReader.java
 */
public class FileReader {
    /**
     * Gets a file and reads the list of integers from them
     * @param fileName The path to the file to read
     * @return and Double[] of the values to use
     */
    public static Double[] getArray(String fileName) {
        BufferedReader br = null;
        InputStream is = FileReader.class.getClassLoader().getResourceAsStream(fileName);
        ArrayList<Double> numbersFromFile = new ArrayList<Double>();

        try {
            br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null) {
                try {
                    numbersFromFile.add(new Double(line));
                } catch (Exception ex) {
                    System.out.println("We didn't see a number");
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println("IO Exception Occurred - " + ex.getLocalizedMessage());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Double[] theArray = new Double[numbersFromFile.size()];
        return numbersFromFile.toArray(theArray);
    }
}
