package mainapplication;

import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Metodo_Leitura {

    static final int MAX_ROWS = 22700;
    static final int MAX_COLS = 2;
    static final String FILE_IN_CSV = "DAYTON.csv";

    DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.ENGLISH); //Specify your locale

    public static void main(String[] args) throws FileNotFoundException, ParseException {
        //construção de dois arrays para armazenar em memória os dados contido no documento  "DAYTON.csv"
        long[][] array = new long[MAX_ROWS][MAX_COLS];
        int filledRows; //numero de linha dos arrays

        filledRows = readInfoFromFile(array, FILE_IN_CSV);

//        bubbleSortMethod(array, filledRows);
//        insertionSortMethod(array, filledRows);
        System.out.println(array[22679][0]);
    }

    public static int readInfoFromFile(long[][] array, String file) throws FileNotFoundException, ParseException {
        Scanner readFile = new Scanner(new File(file));
        int filledRows = 0;

        readFile.nextLine(); //leitura do ficheiro de texto "DAYTON.csv"
        while (readFile.hasNextLine() && filledRows < MAX_ROWS) { //ler enquanto ficheiro tiver dados e até ao limite definido para os arrays
            String[] splitedArr = readFile.nextLine().split(",");

            for (int idxCl = 0; idxCl < splitedArr.length; idxCl++) {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                Date date = format.parse(splitedArr[0]);
                Timestamp t = new Timestamp(date.getTime());
//                System.out.println("unixTime" + t.getTime());
                array[filledRows][0] = t.getTime();
                array[filledRows][1] = Integer.parseInt(splitedArr[1]);

            }
//            System.out.println("unixTime" + array[filledRows][0]);
//            System.out.println("contagem" + array[filledRows][1]);
            filledRows++;
        }

        return filledRows;

    }

//    public static void insertionSortMethod(long[][] array, int filledRows) {
//        for (int i = 1; i < array.length; i++) {
//
//            long aux = array[i][1];
//            int j = i;
//
//            while ((j > 0) && (array[j - 1][1] > aux)) {
//                array[j][1] = array[j - 1][1];
//                j -= 1;
//            }
//            array[j][1] = aux;
//           System.out.println("test" + array[1][1]);
//        }         
//    }
//    public static void bubbleSortMethod(long array[][], int filledRows) {
//        boolean sorted = false;
//
//
//        while (!sorted) {
//            sorted = true;
//            for (int idx1 = 0; idx1 < filledRows - 1; idx1++) {
//                for (int idx2 = idx1 + 1; idx2 < filledRows; idx2++) {
//                    if (array[idx2][1] > array[idx1][1]) {
//                        long [] temp = array[idx1];
//                        array[idx1] = array[idx2];
//                        array[idx2] = temp;
//                        sorted = false;
//
//                    }
//                }
//            }
//        }
//    }
//}
    public static void mergeSort(long[] array, int left, int right) {
        if (right <= left) {
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(array, left, mid);
        mergeSort(array, mid + 1, right);
        merge(array, left, mid, right);
    }

    public static void merge(long [] array, int left, int mid, int right) {
        // calculating lengths
         int lengthLeft = mid - left + 1;
         int lengthRight = right - mid;

        // creating temporary subarrays
         long[] leftArray = new long[lengthLeft];
         long[] rightArray = new long[lengthRight];

        // copying our sorted subarrays into temporaries
        for (int i = 0; i < lengthLeft; i++) {
            leftArray[i] = array[left + i];
        }
        for (int i = 0; i < lengthRight; i++) {
            rightArray[i] = array[mid + i + 1];
        }

        // iterators containing current index of temp subarrays
        int leftIndex = 0;
        int rightIndex = 0;

        // copying from leftArray and rightArray back into array
        for (int i = left; i < right + 1; i++) {
            // if there are still uncopied elements in R and L, copy minimum of the two
            if (leftIndex < lengthLeft && rightIndex < lengthRight) {
                if (leftArray[leftIndex] < rightArray[rightIndex]) {
                    array[i] = leftArray[leftIndex];
                    leftIndex++;
                } else {
                    array[i] = rightArray[rightIndex];
                    rightIndex++;
                }
            } // if all the elements have been copied from rightArray, copy the rest of leftArray
            else if (leftIndex < lengthLeft) {
                array[i] = leftArray[leftIndex];
                leftIndex++;
            } // if all the elements have been copied from leftArray, copy the rest of rightArray
            else if (rightIndex < lengthRight) {
                array[i] = rightArray[rightIndex];
                rightIndex++;
            }
        }
    }
}
