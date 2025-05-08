package mainapplication;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static mainapplication.Functions.*;

public class Menu {

    static Scanner ler = new Scanner(System.in);
    
    public static String openPreMenu() {
        printLn("");
        printLn(".........................................................................");
        printLn("                    Escolha da Resolução Temporal");
        printLn(".........................................................................");
        printLn("");
        printLn(" 1. Periodo do dia"); 
        printLn(" 2. Diario");    
        printLn(" 3. Mensal");  
        printLn(" 4. Anual");  
        printLn("");

        printLn(" Insira o espaço temporal pretendido:");       
        String opcaoEspaTemp = ler.next();
        printLn("");  
         switch (opcaoEspaTemp) {
            case "1": //Periodo do dia
                 String[] options = {"11","12","13","14"};
                do {
                    printLn("Ecollha um dos seguintes periodos");
                    printLn("11. manhã (6:00 às 11h59)");
                    printLn("12. tarde (12:00 às 17h59)");   
                    printLn("13. noite (18:00 às 23h59)");
                    printLn("14. madrugada (0:00 às 5h59)");   
                    printLn(".....................................................");
                    printLn("Escolha uma das opções:   ");
                    printLn(".....................................................");
                    opcaoEspaTemp = ler.next();
                } while (!isValidString(opcaoEspaTemp,options));
                break;
            case "11":
            case "12":
            case "13":
            case "14":
            case "2":
            case "3":
            case "4":
                    // Não faz nada
                break;
            default:
                printLn(".....................................................");
                printLn("Escolha uma opção valida  ");
                printLn(".....................................................");
                opcaoEspaTemp = openPreMenu();
                break;
         }
         
         return opcaoEspaTemp;
    }

    public static String openMenu() {

        printLn("");
        printLn(".........................................................................");
        printLn("                        Análise de Séries Temporais");
        printLn(".........................................................................");
        printLn("|");
        printLn("|  1. Analisar séries temporais");
        printLn("|  2. Calcular o número de observações que ocorrem num conjunto de intervalos");
        printLn("|  3. Ordenar os valores da série temporal");
        printLn("|  4. Implementar método de filtragem / suavização");
        printLn("|  5. Calcular Erro Médio Absoluto");
        printLn("|  6. Prever um valor futuro");
        printLn("|  7. Carregar nova série temporal");

        printLn("|  0: Sair");
        printLn(".........................................................................");
        printLn("");
        
        printLn("Escolha uma das opções:   ");
        String opcaoMenu = ler.next();
        printLn("");

        return opcaoMenu;
    }
    
    public static void openNonInteractive() {

        printLn("");
        printLn(".........................................................................");
        printLn("                        Análise de Séries Temporais - não interativo");
        printLn(".........................................................................");
        printLn("|");
        printLn("|  1. Analisar séries temporais");
        printLn("|  2. Calcular o número de observações que ocorrem num conjunto de intervalos");
        printLn("|  3. Ordenar os valores da série temporal");
        printLn("|  4. Implementar método de filtragem / suavização");
        printLn("|  5. Calcular Erro Médio Absoluto");
        printLn("|  6. Prever um valor futuro");
    }
    
}
