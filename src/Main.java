package src;
import java.io.*;
import java.util.*;

public class Main {

    /**
     * Settings
     */
    public static final boolean MULTITHREADED = true;

    /**
     * File Paths
     */
    public static final File file1 = new File("files/input1.txt");
    public static final File file2 = new File("files/input2.txt");
    public static final File file3 = new File("files/input3.txt");

    public static void main(String[] args) {

        try {

            
            System.out.println("--------------------Settings-------------------------");
            System.out.println("Multithreaded: " + MULTITHREADED);

            /**
             * Read the files and analyze the words
             */
            int wcounter = 1;
            System.out.println("--------------------File 1-------------------------");
            Scanner scanner1 = new Scanner(file1);
            char[] w1;
            long startTime = System.currentTimeMillis();
            while (scanner1.hasNextLine()) {
                w1 = scanner1.nextLine().toCharArray();
                Result r1 = analysisMultithreaded(w1);
                System.out.println("Result "+wcounter+" : " + r1);
                wcounter++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("--------------------Time-------------------------");
            System.out.println("Time: " + (((float) endTime - startTime) / 1000) + " s");
            System.out.println("--------------------End Time-------------------------");
            wcounter = 1;

            System.out.println("--------------------File 2-------------------------");
            Scanner scanner2 = new Scanner(file2);
            char[] w2;
            long startTime2 = System.currentTimeMillis();
            while (scanner2.hasNextLine()) {
                w2 = scanner2.nextLine().toCharArray();
                Result r2 = analysisMultithreaded(w2);
                System.out.println("Result "+wcounter+" : " + r2);
                wcounter++;
            }
            long endTime2 = System.currentTimeMillis();
            System.out.println("--------------------Time-------------------------");
            System.out.println("Time: " + (((float) endTime2 - startTime2) / 1000) + " s");
            System.out.println("--------------------End Time-------------------------");
            wcounter = 1;

            System.out.println("--------------------File 3-------------------------");
            Scanner scanner3 = new Scanner(file3);
            char[] w3;
            long startTime3 = System.currentTimeMillis();
            while (scanner3.hasNextLine()) {
                w3 = scanner3.nextLine().toCharArray();
                Result r3 = analysisMultithreaded(w3);
                System.out.println("Result "+wcounter+" : " + r3);
                wcounter++;
            }
            long endTime3 = System.currentTimeMillis();
            System.out.println("--------------------Time-------------------------");
            System.out.println("Time: " + (((float) endTime3 - startTime3) / 1000) + " s");
            System.out.println("--------------------End Time-------------------------");
            wcounter = 1;
            scanner1.close();
            scanner2.close();
            scanner3.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }

    /**
     * Call the analysis method whether it is multithreaded or not
     * @param list
     * @return
     */
    public static Result analysis(char[] list) {
        if (MULTITHREADED)
        {
            return analysisMultithreaded(list);
        }
        else
        {
            return analysis(list, 0, list.length);
        }
    }

    /**
     * Multithreaded Implementation
     * @param list
     * @return
     */
    public static Result analysisMultithreaded(char[] list)
    {
        int numThreads = Runtime.getRuntime().availableProcessors();
        int distance = list.length / numThreads;
        Thread[] threads = new Thread[numThreads];
        Result[] results = new Result[numThreads];
        for (int i = 0; i < numThreads; i++)
        {
            final int start = i * distance;
            final int end = i == numThreads - 1 ? list.length : (i + 1) * distance;
            final int index = i;
            threads[i] = new Thread(() -> results[index] = analysis(list, start, end));
            threads[i].start();
        }
        for (int i = 0; i < numThreads; i++)
        {
            try
            {
                threads[i].join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        Result maxResult = new Result(0, "");
        for (Result result : results)
        {
            if (result.maxLength > maxResult.maxLength)
            {
                maxResult = result;
            }
        }
        return maxResult;
    }

    /**
     * Algorithm to find the longest sequence of characters that repeats 3 times
     * @param list
     * @param start
     * @param end
     * @return
     */
    public static Result analysis(char[] list, int start, int end) {
        String word = "";
        boolean fail = false;

        while (start < end) {
            int distance = (list.length - start)/ 3;
            while (distance > 0) {
                fail = false;
                StringBuilder canditate = new StringBuilder();
                for (int i = start; i < start + distance; i++) {
    
                    if (list[i] == list[i + distance] && list[i] == list[i + (2 * distance)]) {
                        
                        canditate.append( list[i]);
        
                    } else {
                        fail = true;
                        break;
                    }
                }
                if (fail) {
                    distance--;
                } else {
                    if(canditate.length() > word.length()) {
                        word = canditate.toString();
                    }
                    break;
                }
            }
            start++;
        }

        return new Result(word.length(), word);
    }
}