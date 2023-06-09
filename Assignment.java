import java.util.Random;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;


/**
 * @author Nnamdi Kelvin Ikenna-Obi
 * @version Comp 3270 Programming Assignment
 * nzi0007
 * Maximum Sum Contiguous Subvector
 */

public class Assignment {

   // 10 comma-delimited integers
   public static int[] phw = new int[10];

   // An array that stores all four max sums generated by each algorithm
   public static int[] maxSums = new int[4];

   // Creates an array of 19 integer sequences
   public static int[][] arrays = new int[19][];

   // An array that stores all four algorithm runtimes for each array
   public static long[][] runtime = new long[19][4];

    // An array that stores the calculated theoretical time complexity
   public static int[][] timeComplexity = new int[19][4];


   public static int algorithm1(int[] X, int P, int Q) {
      //Initialize maxSoFar to be 0
      int maxSoFar = 0;
      
      //Loop through possible subasrrays
      for (int L = P; L <= Q; L++) {
         for (int U = L; U <= Q; U++) {
            //calculate the sum of the subarray
            int sum = 0;
            for (int I =L; I <= U; I++) {
               sum += X[I];
            }
         
            if (sum > maxSoFar) {
               maxSoFar = sum;
            }
            
         }
      
      }
      // Returns the sum of X[P...Q]
      return maxSoFar;
   
   }

   public static int algorithm2(int [] X, int P, int Q) {
        //Initialize maxSoFar to be 0
      int maxSoFar = 0;
        
        //Loop through possible subasrrays
      for (int L = P; L <= Q; L++) {
         int sum = 0;
         for (int U = L; U <= Q; U++) {
                //calculate the sum of the subarray
            sum += X[U];
             
                // Updates our maxSoFar if the current sum is larger than the previous ones gotten
            if (sum > maxSoFar) {
               maxSoFar = sum;
            }
                
         }
        
      }
        // Returns the sum of X[P...Q]
      return maxSoFar;
     
   }

   public static int algorithm3(int[] X, int L, int U) {
      if (L > U) {
         return 0;
      }
   
      if (L == U) {
         return Math.max(0, X[L]);
      }
   
    // Finds the middle index
      int M = (L + U) / 2;
   
    // Finds the maximum sum of a subvector that crosses 
      int sum = 0;
      int maxToLeft = 0;
      for (int I = M; I >= L; I--) {
         sum += X[I];
         maxToLeft = Math.max(maxToLeft, sum);
      }
   
    //Finds the maximum sum of a subvector that crosses the
    //middle index to the right
      sum = 0;
      int maxToRight = 0;
      for (int I = M + 1; I <= U; I++) {
         sum += X[I];
         maxToRight = Math.max(maxToRight, sum);
      }
   
      int maxCrossing = maxToLeft + maxToRight;
   
    // Recursively finds the maximum sum of a subvector in the left half
      int maxLeft = algorithm3(X, L, M);
   
    // Recursively finds the maximum sum of a subvector in the right half
      int maxRight = algorithm3(X, M + 1, U);
   
    // Returns the maximum of the three
      return Math.max(maxCrossing, Math.max(maxLeft, maxRight));
   }
   public static int algorithm4(int[] X, int P, int Q) {
      int maxSoFar = 0;
      int maxEndingHere = 0;
      for (int I = P; I <= Q; I++) {
         maxEndingHere = Math.max(0, maxEndingHere + X[I]);
         maxSoFar = Math.max(maxSoFar, maxEndingHere);
      }
      return maxSoFar;
   }

/**
 * Reads an input file and converts the comma delimited string to an
 * int[] phw.
 * @param fileIn for the file being read
 * @throws FileNotFoundException if file is not found
 */
public static void runInputFile(String fileIn) throws FileNotFoundException {
    File file = new File(fileIn);
    Scanner scanner = new Scanner(file);
 
    /* Converts the read line, with elements separated by commas,
     * into a String[] inputs. */
    String str = "";
    String[] inputs = new String[phw.length];
    while (scanner.hasNextLine()) {
       str = scanner.nextLine();
       inputs = str.split(",");
    }
    scanner.close();

    /* Converts the String[] inputs into an int[] phw */
    int i = 0;
    for (String s : inputs) {
       s = s.trim();
       phw[i] = Integer.parseInt(s);
       i++;
    }
 }


//Made this to test my algorithms before I started making my other methods
//Main method that tests each algorithm
//  public static void main(String[] args) throws FileNotFoundException {
//         String fileName = "input.txt";
//         BufferedReader br = new BufferedReader(new FileReader(fileName));
//         String line = null;
//         int[] myArray = null;

//         while ((line = br.readLine()) != null) {
//             String[] parts = line.split(",");
//             myArray = new int[parts.length];
//             for (int i = 0; i < parts.length; i++) {
//                 myArray[i] = Integer.parseInt(parts[i]);
//             }
//         }
//         br.close();

//         // Test algorithm 1
//         int result1 = algorithm1(myArray, 0, myArray.length-1);
//         System.out.println("Algorithm 1 result: " + result1);

//         // Test algorithm 2
//         int result2 = algorithm2(myArray, 0, myArray.length-1);
//         System.out.println("Algorithm 2 result: " + result2);

//         // Test algorithm 3
//         int result3 = algorithm3(myArray, 0, myArray.length-1);
//         System.out.println("Algorithm 3 result: " + result3);

//         // Test algorithm 4
//         int result4 = algorithm4(myArray, 0, myArray.length-1);
//         System.out.println("Algorithm 4 result: " + result4);
//     }
 

/*
 * This method writes the results of the algorithms to a CSV file.
 */
 public static void writeCSV() throws FileNotFoundException {
    File output = new File("NnamdiIkenna-Obi_phw_output.csv");
 
    PrintWriter writer = new PrintWriter(output);
    for (int i = 0; i < 19; i++) {
       if (i == 0) {
          writer.write("algorithm1,algorithm2,algorithm3,algorithm4,"
                      + "T1(n),T2(n),T3(n),T4(n)");
       }
       writer.println();
       for (int j = 0; j < 8; j++) {
          if (j <= 3) {
             writer.write((runtime[i][j]) + ",");
          } else {
             if (j != 7) {
                writer.write(timeComplexity[i][j - 4] + ",");
                continue;
             }
             writer.write(timeComplexity[i][j - 4] + "");
          }
       }
    }
    writer.flush();
    writer.close();
 }

 /**

* Calculates the max sum of contiguous subarray (MSCS) of the input array
* uses four different algorithms, and stores the result in an array called maxSum.
* @param arr the input array for which the MSCS is to be calculated
*/
public static void calculateMaxSums(int[] arr, int P, int Q) {
    int[] maxSum = new int[4];

    // Runs Algorithm 1 with input array and P, Q parameters
    maxSum[0] = algorithm1(arr, P, Q);

    // Runs Algorithm 2 with input array and P, Q parameters
    maxSum[1] = algorithm2(arr, P, Q);

    // Runs Algorithm 3 with input array and L, U indices
    maxSum[2] = algorithm3(arr, 0, arr.length-1);

    // Runs Algorithm 4 with input array and P, Q parameters
    maxSum[3] = algorithm4(arr, P, Q);

    // Print the max sums gotten by each algorithm
    System.out.println("Max sums: ");
    for (int i = 0; i < maxSum.length; i++) {
        System.out.println("Algorithm " + (i+1) + ": " + maxSum[i]);
    }
}

/**
 * Fills an array with random values in the range (-50, 49) for algorithms 1, 2, and 4,
 * @param arr
 * @param algorithmNumber
 * @return
 */
public static int[] randomize(int[] arr, int algorithmNumber) {
    Random rand = new Random();
    switch (algorithmNumber) {
        case 1:
        case 2:
        case 4:
            // Algorithm 1, 2, and 4 use random values in the range (-50, 49)
            for (int i = 0; i < arr.length; i++) {
                arr[i] = rand.nextInt(100) - 50;
            }
            break;
        case 3:
            // Algorithm 3 uses random values in the range (-100, 99)
            for (int i = 0; i < arr.length; i++) {
                arr[i] = rand.nextInt(200) - 100;
            }
            break;
        default:
            // Invalid algorithm number
            throw new IllegalArgumentException("Invalid algorithm number: " + algorithmNumber);
    }
    return arr;
}

/**
 * Fills an array with random values in the range (-100, 100) for algorithm 3
 * @param arr
 * @return
 */
public static void createRandomArrays() {
    int n = 10;
    for (int i = 0; i < 19; i++) {
        int[] arr = new int[n];
        for (int j = 0; j < n; j++) {
            arr[j] = (int) (Math.random() * 201) - 100;
        }
        arrays[i] = arr;
        n += 5;
    }
}

/*
 * This method generates the runtime for each algorithm in relation to their respective input sizes.
 * It also generates the theoretical time complexity for each algorithm in relation to their respective input sizes.
 * @param n - number of times the array runs through each algorithm
 */

public static void generateRuntime() {
    for (int i = 0; i < 19; i++) {
        for (int j = 0; j < 4; j++) {
            long t1 = 0;
            long t2 = 0;
            int n = 500;
            // Runs each algorithm 500 times to get the average runtime
            switch(j) {

                // Runs array[i] through Algorithm1 and calculates its theoretical time complexities of each array length.
                case 0:
                    t1 = System.nanoTime();

                    for (int l = 0; l < n; l++) {
                        algorithm1(arrays[i], 0, arrays[i].length - 1);
                    }

                    t2 = System.nanoTime();
                    runtime[i][j] = ((t2 - t1) / n) * 20;
                    timeComplexity[i][j] = calculateTn(arrays[i].length, j);
                    break;

                // Runs array[i] through Algorithm2 and calculates its theoretical time complexities of each array length.
                case 1:
                    t1 = System.nanoTime();

                    for (int l = 0; l < n; l++) {
                        algorithm2(arrays[i], 0, arrays[i].length - 1);
                    }

                    t2 = System.nanoTime();
                    runtime[i][j] = ((t2 - t1) / n) * 20;
                    timeComplexity[i][j] = calculateTn(arrays[i].length, j);
                    break;

                // Runs array[i] through Algorithm3 and calculates its theoretical time complexities of each array length.
                case 2:
                    t1 = System.nanoTime();

                    algorithm3(arrays[i], 0, arrays[i].length - 1);

                    t2 = System.nanoTime();
                    runtime[i][j] = ((t2 - t1) / n) * 20;
                    timeComplexity[i][j] = calculateTn(arrays[i].length, j);
                    break;

            // Runs array[i] through Algorithm4 and calculates its time theoretical complexities of each array length.
            case 3:
            t1 = System.nanoTime();

            for (int l = 0; l < n; l++) {
                algorithm4(arrays[i], 0, arrays[i].length - 1);
            }

            t2 = System.nanoTime();
            runtime[i][j] = ((t2 - t1) / n) * 20;
            timeComplexity[i][j] = calculateTn(arrays[i].length, j);
            break;


                //Default case but should never be reached
                default:
                    break;
            }
        }
    }
}



/**
 * Calculates the theoretical time complexity of each algorithm with the respective input size n.
 * @param n - input size
 * @param algo - numbered algorithm
 * @return T(n) for whichever algorithm is executed
 */
public static int calculateTn(int n, int algo) {
    switch (algo) {
        //Returns the calculated theoretical time complexity of Algorithm1 with an array of size n.
        //T(n) = 7(n^3) + 10(n^2) + 2(n) + 4 */
        case 0:
        return (int) Math.ceil(((7 * (Math.pow(n, 3))) 
        + (10 * (Math.pow(n , 2))) + (2 * n) + 4));

        //Returns the calculated theoretical time complexity of Algorithm2 with an array of size n.
        //T(n) = 14(n^2) + 3(n) + 4
        case 1:
        return (int) Math.ceil(((14 * (Math.pow(n , 2))) + (3 * n) + 4));

        //Returns the calculated theoretical time complexity of Algorithm3 with an array of size n.
        //T(n) = 30(n lg(n)) + 30(n)
        case 2:
    return (int) Math.ceil(((30 * n) * (Math.log(n) / Math.log(2))) + (30 * n));

      //Returns the calculated theoretical time complexity of Algorithm4 with an array of size n.
      //T(n) = 15(n) + 5
        case 3:
        return (int) Math.ceil(((15 * n) + 5));

       default:
       return 0;
    }
}



/**
* Main method used to run the entire program.	
*@param args not used
*@throws FileNotFoundException if the file is not found
*/
    public static void main(String[] args) throws FileNotFoundException {
        // Modify filename to match the path relative to your test file (if not phw_input.txt)
        String filename = "input.txt";
        runInputFile(filename); // Reads the input and creates int array
        int[] maxSum = new int[4];
        maxSum[0] = algorithm1(phw, 0, phw.length - 1);
        maxSum[1] = algorithm2(phw, 0, phw.length - 1);
        maxSum[2] = algorithm3(phw, 0, phw.length - 1);
        maxSum[3] = algorithm4(phw, 0, phw.length - 1);
        System.out.println("algorithm1: " + maxSum[0] + "; algorithm2: "
        + maxSum[1] + "; algorithm3: " + maxSum[2] + "; algorithm4: "
        + maxSum[3]);
        
        // Creates arrays used for matrix generation and generates values used in the matrix.
        createRandomArrays();
        generateRuntime();
        // Writes CSV file with all data.
        writeCSV();
    }

}

