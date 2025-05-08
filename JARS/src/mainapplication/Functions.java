/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainapplication;

import static mainapplication.MainApplication.calculateArrayAverageByDay;
import static mainapplication.MainApplication.calculateArrayAverageByMonth;
import static mainapplication.MainApplication.getArrayWithBoundaries;

/**
 *
 * @author jpjes
 */
public class Functions {
    
    public static void printLn(String string) {
        System.out.println(string);
    }
    
    public static void print(String string) {
        System.out.print(string);
    }
    
     public static boolean isValidString(String opcaoEspaTemp,String[] options) {
        boolean valid = false;
        int i = 0;
        while (!valid && i < options.length) {
            if(opcaoEspaTemp.equalsIgnoreCase(options[i]))
                valid = true;
            i++;
        }
        return valid;
    }
     
     public static boolean isValidDate(String date,String time) {
        boolean valid = false;
        
        String[] dia = date.split("-");
        
        switch (time) {
            case "11":
            case "12":
            case "13":
            case "14":
            case "2":
                if(dia.length == 3)
                    valid = true;
                break;
            case "3":
                if(dia.length == 2)
                    valid = true;
                break;
            case "4":
                if(dia.length == 1)
                    valid = true;
                break;
        }
        
        
        return valid;
    }
    
}
