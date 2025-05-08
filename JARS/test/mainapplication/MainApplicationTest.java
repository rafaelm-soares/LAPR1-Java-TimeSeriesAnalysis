/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainapplication;

import java.io.File;
import static mainapplication.MainApplication.FILE_IN_CSV;
import static mainapplication.MainApplication.FILE_IN_CSV_REBUILDED;
import static mainapplication.MainApplication.FILE_OUT_1;
import static mainapplication.MainApplication.FILE_OUT_2;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author jpjes
 */
public class MainApplicationTest {
    
    public MainApplicationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test of main method, of class MainApplication.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        MainApplication.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of readInfoFromFile method, of class MainApplication.
     */
    @Test
    public void testReadInfoFromFile() throws Exception {
//        System.out.println("readInfoFromFile");
//        long[][] array = null;
//        String file = "";
//        String expResult = "*.csv";
//        int result = MainApplication.readInfoFromFile(array, file);
//        assertEquals(expResult, result);
        File file1 = new File(FILE_IN_CSV);
        assertTrue(file1.exists());
    }

    /**
     * Test of getArrayWithBoundaries method, of class MainApplication.
     */
    @Test
    public void testGetArrayWithBoundaries() {
        System.out.println("getArrayWithBoundaries");
        long[][] array = {{1493593200,1807},
                            {1493679600,1790},
                            {1493766000,1820},
                            {1493852400,1864},
                            {1493938800,1870},
                            {1494025200,1603},
                            {1494111600,1526}};
        long[] limites = {1493766000,1493938800};
        long[][] expResult = {{1493766000,1820},
                            {1493852400,1864},
                            {1493938800,1870},{0,0},{0,0},{0,0},{0,0}};
        long[][] result = MainApplication.getArrayWithBoundaries(array, limites);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of calculateArrayAverageByDay method, of class MainApplication.
     */
    @Test
    public void testCalculateArrayAverageByDay() {
        System.out.println("calculateArrayAverageByDay");
        long[][] array = {{1493593200,1807},
                            {1493679600,1790},
                            {1493766000,1820},
                            {1493852400,1864},
                            {1493938800,1870},
                            {1494025200,1603},
                            {1494111600,1526}};
        long[] limites = {1493593200,1494111600};
        long[][] expResult = {{1493593200,1807},
                            {1493679600,1790},
                            {1493766000,1820},
                            {1493852400,1864},
                            {1493938800,1870},
                            {1494025200,1603},
                            {1494111600,1526},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};
        long[][] result = MainApplication.calculateArrayAverageByDay(array, limites);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateArrayAverageByMonth method, of class MainApplication.
     */
    @Test
    public void testCalculateArrayAverageByMonth() {
        System.out.println("calculateArrayAverageByMonth");
        long[][] array = null;
        long[] limites = null;
        long[][] expResult = null;
        long[][] result = MainApplication.calculateArrayAverageByMonth(array, limites);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of getTotalRows method, of class MainApplication.
     */
    @Test
    public void testGetTotalRows() {
        System.out.println("getTotalRows");
        long[][] array = {{1493593200,1807},
                            {1493679600,1790},
                            {1493766000,1820},
                            {1493852400,1864},
                            {1493938800,1870},
                            {1494025200,1603},
                            {1494111600,1526}};
        long[] limites = null;
        int expResult = 7;
        int result = MainApplication.getTotalRows(array, limites);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of calculateGlobalAverage method, of class MainApplication.
     */
    @Test
    public void testCalculateGlobalAverage() {
        System.out.println("calculateGlobalAverage");
        long[][] array = {{1493593200,1807},
                            {1493679600,1790},
                            {1493766000,1820},
                            {1493852400,1864},
                            {1493938800,1870},
                            {1494025200,1603},
                            {1494111600,1526}};
        int totalRows = 6;
        double expResult = 1745.5;
        long[] limites = {1493679600,1494111600};
        double result = MainApplication.calculateGlobalAverage(array,limites, totalRows);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of mergeDesc method, of class MainApplication.
     */
    @Test
    public void testMergeDesc() throws Exception {
        System.out.println("mergeDesc");
        long[][] filteredArr = {{1493593200,1807},
                                {1493679600,1790}};
                                
                                
        long[][] l = {{1493679600,1790}};
        long[][] r = {{1493593200,1807}};
        int left = 1;
        int right = 1;
        long[][] filteredArrNew = {{1493593200,1807},
                                   {1493679600,1790}};
        MainApplication.mergeDesc(filteredArr, l, r, left, right);
       assertArrayEquals(filteredArrNew, filteredArr);
    }

    /**
     * Test of mergeAsc method, of class MainApplication.
     */
    @Test
    public void testMergeAsc() throws Exception {
        System.out.println("mergeAsc");
        long[][] filteredArr = {{1493593200,1807},
                                {1493679600,1790}};
                                
                                
        long[][] l = {{1493679600,1790}};
        long[][] r = {{1493593200,1807}};
        int left = 1;
        int right = 1;
        long[][] filteredArrNew = {{1493679600,1790},
                                   {1493593200,1807}};
        MainApplication.mergeDesc(filteredArr, l, r, left, right);
       assertArrayEquals(filteredArrNew, filteredArr);
    }

    /**
     * Test of calculateNumberOfObservations method, of class MainApplication.
     */
    @Test
    public void testCalculateNumberOfObservations() throws Exception {
        System.out.println("calculateNumberOfObservations");
        long[][] array = null;
        long[] limites = null;
        double average = 0.0;
        boolean interativo = true;
        MainApplication.calculateNumberOfObservations(array, limites, average,interativo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mergeSortMethodAsc method, of class MainApplication.
     */
    @Test
    public void testMergeSortMethodAsc() throws Exception {
        System.out.println("mergeSortMethodAsc");
        long [][] actual = {{1483228800,2089},
                            {1485907200,1965},
                            {1488326400,1945},
                            {1491001200,1730},
                            {1493593200,1810},
                            {1496271600,2074},
                            {1498863600,2168},
                            {1501542000,2120},
                            {1504220400,1905},
                            {1506812400,1800},
                            {1509494400,1929},
                            {1512086400,2144}};
        long [][] expected = {{1491001200,1730},
                            {1506812400,1800},
                            {1493593200,1810},
                            {1504220400,1905},
                            {1509494400,1929},
                            {1488326400,1945},
                            {1485907200,1965},
                            {1496271600,2074},
                            {1483228800,2089},
                            {1501542000,2120},
                            {1512086400,2144},
                            {1498863600,2168}};
        MainApplication.mergeSortMethodAsc(actual, actual.length);
        assertArrayEquals(actual, expected);
    }

    /**
     * Test of mergeSortMethodDesc method, of class MainApplication.
     */
    @Test
    public void testMergeSortMethodDesc() throws Exception {
        System.out.println("mergeSortMethodDesc");
        long [][] actual = {{1483228800,2089},
                            {1485907200,1965},
                            {1488326400,1945},
                            {1491001200,1730},
                            {1493593200,1810},
                            {1496271600,2074},
                            {1498863600,2168},
                            {1501542000,2120},
                            {1504220400,1905},
                            {1506812400,1800},
                            {1509494400,1929},
                            {1512086400,2144}};
        long [][] expected = {{1498863600,2168},
                            {1512086400,2144},
                            {1501542000,2120},
                            {1483228800,2089},
                            {1496271600,2074},
                            {1485907200,1965},
                            {1488326400,1945},
                            {1509494400,1929},
                            {1504220400,1905},
                            {1493593200,1810},
                            {1506812400,1800},
                            {1491001200,1730}};
        MainApplication.mergeSortMethodDesc(actual, actual.length);
        assertArrayEquals(actual, expected);
    }

    /**
     * Test of chooseSortDirection method, of class MainApplication.
     */
    @Test
    public void testChooseSortDirection() throws Exception {
        Menu menu = new Menu();
        
//        System.out.println("chooseSortDirection");
//        long[][] array = null;
//        int filledRows = 0;
//        String escolha = "1.Ordenação Crescente";
//        String expResult = "Escolheu a opção 1. Dados ordenados de forma ascendente";
//        MainApplication.chooseSortDirection(array, filledRows,escolha);
//        assertEquals(expResult, escolha);
            
    }

    /**
     * Test of UnixTimeOutputAsc method, of class MainApplication.
     */
    @Test
    public void testUnixTimeOutputAsc() throws Exception {
        System.out.println("UnixTimeOutputAsc");
        File file = new File(FILE_OUT_1);
        assertTrue(file.exists());
    }

    /**
     * Test of UnixTimeOutputDesc method, of class MainApplication.
     */
    @Test
    public void testUnixTimeOutputDesc() throws Exception {
        System.out.println("UnixTimeOutputDesc");
        File file = new File(FILE_OUT_2);
        assertTrue(file.exists());
    }

    /**
     * Test of readFilteredArr method, of class MainApplication.
     */
    @Test
    public void testReadFilteredArr() throws Exception {
        System.out.println("readFilteredArr");
        File file = new File(FILE_IN_CSV_REBUILDED);
        assertTrue(file.exists());
    }

    /**
     * Test of getArrayWithBoundariesWithout method, of class MainApplication.
     */
    @Test
    public void testGetArrayWithBoundariesWithout() {
        System.out.println("getArrayWithBoundariesWithout");
        long[][] array = null;
        long[] limites = null;
        long[][] expResult = null;
        long[][] result = MainApplication.getArrayWithBoundariesWithout(array, limites);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateArrayByDay method, of class MainApplication.
     */
    @Test
    public void testCalculateArrayByDay() {
        System.out.println("calculateArrayByDay");
        long[][] array = null;
        long[] limites = null;
        long[][] expResult = null;
        long[][] result = MainApplication.calculateArrayByDay(array, limites);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateArrayByMonth method, of class MainApplication.
     */
    @Test
    public void testCalculateArrayByMonth() {
        System.out.println("calculateArrayByMonth");
        long[][] array = null;
        long[] limites = null;
        long[][] expResult = null;
        long[][] result = MainApplication.calculateArrayByMonth(array, limites);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateArrayByYear method, of class MainApplication.
     */
    @Test
    public void testCalculateArrayByYear() {
        System.out.println("calculateArrayByYear");
        long[][] array = null;
        long[] limites = null;
        long[][] expResult = null;
        long[][] result = MainApplication.calculateArrayByYear(array, limites);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of average method, of class MainApplication.
     */
    @Test
    public void testAverage() throws Exception {
        System.out.println("average");
        long[][] array = null;
        long[] limites = null;
        String time = "";
        String model = "";
        String nAlpha = "";
        boolean interativo = true;
        MainApplication.average(array, limites, time,model,nAlpha,interativo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateArray method, of class MainApplication.
     */
    @Test
    public void testCalculateArray() {
        System.out.println("calculateArray");
        long[][] data = null;
        long[] dataFilter = null;
        String time = "";
        long[][] expResult = null;
        long[][] result = MainApplication.calculateArray(data, dataFilter, time);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of MAE method, of class MainApplication.
     */
    @Test
    public void testMAE() throws Exception {
        System.out.println("MAE");
        long[][] array = null;
        String time = "";
        String model = "";
        String nAlpha = "";
        boolean interativo = true;
        MainApplication.MAE(array, time,model,nAlpha,interativo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of MMS method, of class MainApplication.
     */
    @Test
    public void testMMS() {
        System.out.println("MMS");
        long[][] array = null;
        int n = 0;
        long[][] expResult = null;
        long[][] result = MainApplication.MMS(array, n);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of MMEP method, of class MainApplication.
     */
    @Test
    public void testMMEP() throws Exception {
        System.out.println("MMEP");
        long[][] array = null;
        double alpha = 0.0;
        long[][] expResult = null;
        long[][] result = MainApplication.MMEP(array, alpha);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of prevision method, of class MainApplication.
     */
    @Test
    public void testPrevision() throws Exception {
        System.out.println("prevision");
        long[][] array = null;
        String time = "";
        String model = "";
        String nAlpha = "";
        boolean interativo = true;
        MainApplication.prevision(array, time,model,nAlpha,interativo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of MMSPrevision method, of class MainApplication.
     */
    @Test
    public void testMMSPrevision() {
        System.out.println("MMSPrevision");
        long[][] array = null;
        int n = 0;
        long nextTimestamp = 0L;
        long[][] expResult = null;
        long[][] result = MainApplication.MMSPrevision(array, n, nextTimestamp);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of MMEPPrevisiion method, of class MainApplication.
     */
    @Test
    public void testMMEPPrevisiion() throws Exception {
        System.out.println("MMEPPrevisiion");
        long[][] array = null;
        double alpha = 0.0;
        long nextTimestamp = 0L;
        long[][] expResult = null;
        long[][] result = MainApplication.MMEPPrevisiion(array, alpha, nextTimestamp);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastTimestamp method, of class MainApplication.
     */
    @Test
    public void testGetLastTimestamp() {
        System.out.println("getLastTimestamp");
        long[][] array = null;
        long expResult = 0L;
        long result = MainApplication.getLastTimestamp(array);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
    
}
