package mainapplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;
import java.util.Scanner;
import static mainapplication.Menu.*;
import static mainapplication.Functions.*;
import static mainapplication.Graphs.*;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class MainApplication {

    static Scanner sc = new Scanner(System.in);

    static final int MAX_ROWS = 22700;
    static final long HOUR = 3600000;
    static final int MAX_HOUR = 24;
    static final int MAX_YEAR = 12;
    static final int MAX_COLS = 2;
    static String FILE_IN_CSV = "DAYTON.csv";
    static final String FILE_IN_CSV_REBUILDED = "dados_filtrados.csv";
    static final String FILE_OUT_0 = "UnixTimeStampOriginal.csv";
    static final String FILE_OUT_1 = "MergeSortedAsc.csv";
    static final String FILE_OUT_2 = "MergeSortedDesc.csv";

    public static void main(String[] args) throws FileNotFoundException, ParseException, InterruptedException, UnsupportedEncodingException {

        //construção de dois arrays para armazenar em memória os dados contido no documento  "DAYTON.csv"
        long[][] data = new long[MAX_ROWS][MAX_COLS];
        long[][] arrayForFilter = new long[MAX_ROWS][MAX_COLS];
        long[] dataFilter = new long[2]; // 0 - Min ; 1 - Max
        long[][] filteredArr = new long[MAX_ROWS][MAX_COLS];
        //int[] powerConsumed = new int[MAX_ROWS];
        int filledRows = 0; //numero de linha do array data
        int filteredfilledRows = 0;
        int left = 0;
        int right = 0;
        int totalRows = 0;
        double globalAverage = 0.0;
        String escolha = "";

        String nameOfFile = "";
        String time = "";
        String model = "";
        String orderType = "";
        String nAlpha = "";
        String previewMoment = "";
        String formatDate = "";
        long datetime = 0;

        boolean interativo = false;
        boolean goToFirstChoice = true;
        
        int numeroDeParametros = args.length;
        boolean errorOnParameters = true;
        String errorMessage = "";
        
        if(numeroDeParametros < 2) {
            errorMessage = "Deve introduzir o nome do ficheiro csv que pretende importar ( -nome ts_nome_da_serie_temporal.csv )";
        } else if (numeroDeParametros == 2) {
            interativo = true;
            if(args[0].equalsIgnoreCase("-nome")){
                nameOfFile = args[1];
                File file = new File(nameOfFile);
                if (!file.exists())
                    errorMessage = "Deverá inserir um ficheiro válido";
                else 
                    errorOnParameters = false;
            } else {
                errorMessage = "O primeiro argumento deve ser ( -nome )";
            }
        } else if (numeroDeParametros == 12) {
            if(args[0].equalsIgnoreCase("-nome")){
                nameOfFile = args[1];
                File file = new File(nameOfFile);
                if (!file.exists()) 
                    errorMessage = "Deverá inserir um ficheiro válido";
                else 
                    errorOnParameters = false;
            } else {
                errorMessage = "O primeiro argumento deve ser ( -nome )";
            }
            if(args[2].equalsIgnoreCase("-resolucao")){
                time = args[3];
                if(!(time.equalsIgnoreCase("11") || time.equalsIgnoreCase("12") || time.equalsIgnoreCase("13") || time.equalsIgnoreCase("14") || time.equalsIgnoreCase("2") || time.equalsIgnoreCase("3") || time.equalsIgnoreCase("4"))) {
                    errorMessage += "/nDeverá inserir uma resolucão válida";
                    errorOnParameters = true;
                }
            }else {
                errorMessage += "/nDeverá inserir uma resolucão válida";
                errorOnParameters = true;
            }
            
            if(args[4].equalsIgnoreCase("-modelo")){
                model = args[5];
                if(!(model.equalsIgnoreCase("1") || model.equalsIgnoreCase("2"))) {
                    errorMessage += "/nDeverá inserir um modelo válido";
                    errorOnParameters = true;
                }
            }else {
                errorMessage += "/nDeverá inserir um modelo válido";
                errorOnParameters = true;
            }
            
            if(args[6].equalsIgnoreCase("-tipoOrdenacao")){
                orderType = args[7];
                if(!(orderType.equalsIgnoreCase("1") || orderType.equalsIgnoreCase("2")) ) {
                    errorMessage += "/nDeverá inserir um tipo de ordenaçao válido";
                    errorOnParameters = true;
                }
            }else {
                errorMessage += "/nDeverá inserir um tipo de ordenaçao válido";
                errorOnParameters = true;
            }
            
            if(args[8].equalsIgnoreCase("-parModelo")){
                nAlpha = args[9];
            }else {
                errorMessage += "/nDeverá inserir um tipo de par modelo válido";
                errorOnParameters = true;
            }
            
            if(args[8].equalsIgnoreCase("-parModelo")){
                previewMoment = args[11];
                if(!isValidDate(previewMoment,time)){
                    errorMessage += "/nDeverá inserir um tipo de momento de previsão válido";
                    errorOnParameters = true;
                }  
            }else {
                errorMessage += "/nDeverá inserir um tipo de momento de previsão válido";
                errorOnParameters = true;
            }
        } else {
            errorMessage += "/nNúmero de parametros insuficientes";
            errorOnParameters = true;
        }
        
        if(errorOnParameters){
            printLn(errorMessage);
        } else {
            if (interativo) {
                do {
                    if(goToFirstChoice) {
                        escolha = "1";
                    } else {
                        escolha = openMenu();
                    }
                    switch (escolha) {
                        case "1": // Analisar séries temporais
                            printLn("Escolheu a opção 1. Analisar séries temporais");
                            printLn(".....................................................");
                            boolean valid = true;
                            filledRows = readInfoFromFile(data, FILE_IN_CSV);
                            time = openPreMenu();
                            getDateTime(time, dataFilter);
                            switch (time) {
                                case "11":
                                case "12":
                                case "13":
                                case "14":
                                    arrayForFilter = getArrayWithBoundaries(data, dataFilter);
                                    formatDate = "\"%H:%M\"";
                                    break;
                                case "2":
                                    arrayForFilter = getArrayWithBoundaries(data, dataFilter);
                                    formatDate = "\"%H:%M\"";
                                    break;
                                case "3":
                                    arrayForFilter = calculateArrayAverageByDay(data, dataFilter);
                                    formatDate = "\"%d/%m/%y\"";
                                    break;
                                case "4":
                                    arrayForFilter = calculateArrayAverageByMonth(data, dataFilter);
                                    formatDate = "\"%d/%m\"";
                                    break;
                                default:
                                    goToFirstChoice = true;
                                    valid = false;
                                    break;
                            }

                            if(valid) {
                                CreateGraph(arrayForFilter,formatDate,interativo,"date");
                                goToFirstChoice = false;
                            }
                            break;

                        case "2": // Calcular o número de observações que ocorrem num conjunto de intervalos
                            printLn("Escolheu a opção 2. Calcular o número de observações que ocorrem num conjunto de intervalos");
                            printLn(".....................................................");
                            totalRows = getTotalRows(data, dataFilter);
                            globalAverage = calculateGlobalAverage(data, dataFilter, totalRows);

                            calculateNumberOfObservations(data, dataFilter, globalAverage,interativo);
                            break;

                        case "3": // Ordenar os valores da série temporal
                            printLn("Escolheu a opção 3. Ordenar os valores da série temporal");
                            printLn(".....................................................");
                             {
                                filteredfilledRows = readFilteredArr(filteredArr, FILE_IN_CSV_REBUILDED);
                                readFilteredArr(filteredArr, FILE_IN_CSV_REBUILDED);
                                chooseSortDirection(filteredArr, filteredfilledRows, escolha,time,interativo);
                            }

                            break;

                        case "4": // Implementar a Média Móvel Simples e Média Móvel Exponencialmente Pesada
                            printLn("Escolheu a opção 4. Implementar método de filtragem / suavização");
                            printLn(".....................................................");
                            average(arrayForFilter, dataFilter, time,model,nAlpha,interativo);
                            // OBSERVAÇÕES: É necessário perguntar ao utilizador qual técnica de filtragem pretendida
                            // OBSERVAÇÕES: Perguntar ao utilizador para introduzir o parametro (n se MMS ou a para MMEP)
                            // OBSERVAÇÕES: Deverá dar origem a um gráfico
                            // OBSERVAÇÔES: Deveremos pedir ao utilizador se quer gravar o grafico em PNG ou gravar a serie em ficheiro CSV                
                            //MODULO: xxxxxxxxxxxxxxxxxxxxxxxxxx      
                            break;

                        case "5": // Calcular Erro Médio Absoluto
                            printLn("Escolheu a opção 5. Calcular Erro Médio Absoluto");
                            printLn(".....................................................");
                            // OBSERVAÇÕES: Calcular o N (numero de observações da serie )
                            // DUVIDA: ver se N é o numero de observações da series escolhida ou se é numero da serie total?
                            //MODULO: xxxxxxxxxxxxxxxxxxxxxxxxxx   
                            MAE(arrayForFilter, time,model,nAlpha,interativo);
                            break;

                        case "6": // Prever um valor futuro
                            printLn("Escolheu a opção 6. Prever um valor futuro");
                            printLn(".....................................................");
                            // OBSERVAÇÕES: O utilizador vai ter que introduzir uma nova data
                            //(ter em atenção! o periodo escolhido inicialmente deve permanecer o mesmo - exemplo: inicilamente escolher o periodo da manha do dia 1-01-2010. A pergunta que lhe será feita é: introduza uma data para previsão (O periodo da manha será implicito - uma vez que este nao pderá mudar!)
                            // OBSERVAÇÔES: Deveremos pedir ao utilizador se quer gravar o grafico em PNG ou gravar a serie em ficheiro CSV                      
                            //MODULO: xxxxxxxxxxxxxxxxxxxxxxxxxx   
                            prevision(arrayForFilter, time,model,nAlpha,interativo);
                            break;

                        case "7": //  Carregar nova série temporal
                            printLn("Escolheu a opção 7. Carregar nova série temporal");
                            printLn(".....................................................");
                            FILE_IN_CSV = sc.next();
                            data = new long[MAX_ROWS][MAX_COLS];
                            filledRows = readInfoFromFile(data, FILE_IN_CSV);
                            goToFirstChoice = true;
                            break;

                        case "0":
                            break;

                        default:
                            printLn("Opção inválida!");

                    }
                } while (!escolha.equalsIgnoreCase("0"));
            } else {
                openNonInteractive();
                for(int i = 1; i <= 7; i++) {
                    switch (i) {
                        case 1: // Analisar séries temporais
                            printLn(".....................................................");
                            printLn("Opção 1. Analisar séries temporais");
                            printLn(".....................................................");
                            boolean valid = true;
                            filledRows = readInfoFromFile(data, nameOfFile);
                            getDateTimeForNonInteractive(previewMoment,time, dataFilter);
                            switch (time) {
                                case "11":
                                case "12":
                                case "13":
                                case "14":
                                    arrayForFilter = getArrayWithBoundaries(data, dataFilter);
                                    formatDate = "\"%H:%M\"";
                                    break;
                                case "2":
                                    arrayForFilter = getArrayWithBoundaries(data, dataFilter);
                                    formatDate = "\"%H:%M\"";
                                    break;
                                case "3":
                                    arrayForFilter = calculateArrayAverageByDay(data, dataFilter);
                                    formatDate = "\"%d/%m/%y\"";
                                    break;
                                case "4":
                                    arrayForFilter = calculateArrayAverageByMonth(data, dataFilter);
                                    formatDate = "\"%d/%m\"";
                                    break;
                                default:
                                    goToFirstChoice = true;
                                    valid = false;
                                    break;
                            }

                            if(valid) {
                                CreateGraph(arrayForFilter,formatDate,interativo,"date");
                                goToFirstChoice = false;
                            }
                            break;

                        case 2: // Calcular o número de observações que ocorrem num conjunto de intervalos
                            printLn(".....................................................");
                            printLn("Opção 2. Calcular o número de observações que ocorrem num conjunto de intervalos");
                            printLn(".....................................................");
                            totalRows = getTotalRows(data, dataFilter);
                            globalAverage = calculateGlobalAverage(data, dataFilter, totalRows);

                            calculateNumberOfObservations(data, dataFilter, globalAverage,interativo);
                            break;

                        case 3: // Ordenar os valores da série temporal
                            printLn(".....................................................");
                            printLn("Escolheu a opção 3. Ordenar os valores da série temporal");
                            printLn(".....................................................");
                            filteredfilledRows = readFilteredArr(filteredArr, FILE_IN_CSV_REBUILDED);
                            readFilteredArr(filteredArr, FILE_IN_CSV_REBUILDED);
                            chooseSortDirection(filteredArr, filteredfilledRows, orderType,time,interativo);
                            break;

                        case 4: // Implementar a Média Móvel Simples e Média Móvel Exponencialmente Pesada
                            printLn(".....................................................");
                            printLn("Escolheu a opção 4. Implementar método de filtragem / suavização");
                            printLn(".....................................................");
                            average(arrayForFilter, dataFilter, time,model,nAlpha,interativo);
                            // OBSERVAÇÕES: É necessário perguntar ao utilizador qual técnica de filtragem pretendida
                            // OBSERVAÇÕES: Perguntar ao utilizador para introduzir o parametro (n se MMS ou a para MMEP)
                            // OBSERVAÇÕES: Deverá dar origem a um gráfico
                            // OBSERVAÇÔES: Deveremos pedir ao utilizador se quer gravar o grafico em PNG ou gravar a serie em ficheiro CSV                
                            //MODULO: xxxxxxxxxxxxxxxxxxxxxxxxxx      
                            break;

                        case 5: // Calcular Erro Médio Absoluto
                            printLn(".....................................................");
                            printLn("Escolheu a opção 5. Calcular Erro Médio Absoluto");
                            printLn(".....................................................");
                            // OBSERVAÇÕES: Calcular o N (numero de observações da serie )
                            // DUVIDA: ver se N é o numero de observações da series escolhida ou se é numero da serie total?
                            //MODULO: xxxxxxxxxxxxxxxxxxxxxxxxxx     
                            MAE(arrayForFilter, time,model,nAlpha,interativo);
                            break;

                        case 6: // Prever um valor futuro
                            printLn(".....................................................");
                            printLn("Escolheu a opção 6. Prever um valor futuro");
                            printLn(".....................................................");
                            // OBSERVAÇÕES: O utilizador vai ter que introduzir uma nova data
                            //(ter em atenção! o periodo escolhido inicialmente deve permanecer o mesmo - exemplo: inicilamente escolher o periodo da manha do dia 1-01-2010. A pergunta que lhe será feita é: introduza uma data para previsão (O periodo da manha será implicito - uma vez que este nao pderá mudar!)
                            // OBSERVAÇÔES: Deveremos pedir ao utilizador se quer gravar o grafico em PNG ou gravar a serie em ficheiro CSV                      
                            //MODULO: xxxxxxxxxxxxxxxxxxxxxxxxxx   
                            prevision(arrayForFilter, time,model,nAlpha,interativo);
                            break;
                    }
                }
            }
        }
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
//           System.out.println("test" + array[j][1]);
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
    /*
    public static void merge(long[][] array, long[][] l, long[][] r, int left, int right) throws FileNotFoundException, ParseException {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i][1] <= r[j][1]) {
                array[k][0] = l[i][0];
                array[k++][1] = l[i++][1];
            } else {
                array[k][0] = r[j][0];
                array[k++][1] = r[j++][1];
            }
        }
        while (i < left) {
            array[k][0] = l[i][0];
            array[k++][1] = l[i++][1];
        }
        while (j < right) {
            array[k][0] = r[j][0];
            array[k++][1] = r[j++][1];
        }

    }
     */
    public static int readInfoFromFile(long[][] array, String FILE_IN_CSV) throws FileNotFoundException, ParseException {
        Scanner readFile = new Scanner(new File(FILE_IN_CSV));

        int filledRows = 0;

        readFile.nextLine(); //leitura do ficheiro de texto "DAYTON.csv"

        while (readFile.hasNextLine() && filledRows < MAX_ROWS) { //ler enquanto ficheiro tiver dados e até ao limite definido para os arrays
            String[] splitedArr = readFile.nextLine().split(",");

            for (int idxCl = 0; idxCl < splitedArr.length; idxCl++) {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = format.parse(splitedArr[0]);
                Timestamp t = new Timestamp(date.getTime());

                array[filledRows][0] = t.getTime();
                array[filledRows][1] = Integer.parseInt(splitedArr[1]);

            }
            filledRows++;
        }

        return filledRows;

    }

    private static void getDateTime(String time, long[] dataFilter) throws ParseException {
        int year, month, day;
        String minDate = "", maxDate = "";
        switch (time) {
            case "11":
            case "12":
            case "13":
            case "14":
            case "2":
                year = getInt("o ano: (YYYY) ");
                month = getInt("o mes: (MM) ");
                day = getInt("o dia: (DD) ");
                minDate = "" + year + "-" + month + "-" + day + " ";
                maxDate = minDate;
                break;
            case "3":
                year = getInt("o ano: (YYYY) ");
                month = getInt("o mes: (MM) ");
                YearMonth yearMonthObject = YearMonth.of(year, month);
                day = yearMonthObject.lengthOfMonth();
                minDate = "" + year + "-" + month + "-1 ";
                maxDate = "" + year + "-" + month + "-" + day + " ";
                break;
            case "4":
                year = getInt("o ano: (YYYY) ");
                minDate = "" + year + "-1-1 ";
                maxDate = "" + year + "-12-31 ";
                break;
        }

        switch (time) {
            case "11":
                minDate += "06:00:00";
                maxDate += "11:59:00";
                break;
            case "12":
                minDate += "12:00:00";
                maxDate += "17:59:00";
                break;
            case "13":
                minDate += "18:00:00";
                maxDate += "23:59:00";
                break;
            case "14":
                minDate += "00:00:00";
                maxDate += "05:59:00";
                break;
            case "2":
            case "3":
            case "4":
                minDate += "00:00:00";
                maxDate += "23:59:00";
                break;
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp minTimestamp = new Timestamp(format.parse(minDate).getTime());
        Timestamp maxTimestamp = new Timestamp(format.parse(maxDate).getTime());

        dataFilter[0] = minTimestamp.getTime();
        dataFilter[1] = maxTimestamp.getTime();
    }
    
    private static void getDateTimeForNonInteractive(String date,String time, long[] dataFilter) throws ParseException {
        int year, month, day;
        String minDate = "", maxDate = "";
        String[] dia = date.split("-");
        switch (time) {
            case "11":
            case "12":
            case "13":
            case "14":
            case "2":
                year = Integer.parseInt(dia[0]);
                month = Integer.parseInt(dia[1]);
                day = Integer.parseInt(dia[2]);
                minDate = "" + year + "-" + month + "-" + day + " ";
                maxDate = minDate;
                break;
            case "3":
                year = Integer.parseInt(dia[0]);
                month = Integer.parseInt(dia[1]);
                YearMonth yearMonthObject = YearMonth.of(year, month);
                day = yearMonthObject.lengthOfMonth();
                minDate = "" + year + "-" + month + "-1 ";
                maxDate = "" + year + "-" + month + "-" + day + " ";
                break;
            case "4":
                year = Integer.parseInt(dia[0]);
                minDate = "" + year + "-1-1 ";
                maxDate = "" + year + "-12-31 ";
                break;
        }

        switch (time) {
            case "11":
                minDate += "06:00:00";
                maxDate += "11:59:00";
                break;
            case "12":
                minDate += "12:00:00";
                maxDate += "17:59:00";
                break;
            case "13":
                minDate += "18:00:00";
                maxDate += "23:59:00";
                break;
            case "14":
                minDate += "00:00:00";
                maxDate += "05:59:00";
                break;
            case "2":
            case "3":
            case "4":
                minDate += "00:00:00";
                maxDate += "23:59:00";
                break;
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp minTimestamp = new Timestamp(format.parse(minDate).getTime());
        Timestamp maxTimestamp = new Timestamp(format.parse(maxDate).getTime());

        dataFilter[0] = minTimestamp.getTime();
        dataFilter[1] = maxTimestamp.getTime();
    }

    public static int getInt(String string) {
        int value = -1;

        do {
            printLn("Indique " + string);
            try {
                value = sc.nextInt();
            } catch (Exception e) {
                value = -1;
            }
        } while (value == -1);

        return value;
    }

    public static long[][] getArrayWithBoundaries(long[][] array, long[] limites) {
        long[][] arrayWithBoundaries = new long[array.length][MAX_COLS];
        int n = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i][0] >= limites[0] && array[i][0] <= limites[1]) {
                arrayWithBoundaries[n] = array[i];
                n++;
            }
        }
        return arrayWithBoundaries;
    }
    
    public static long[][] getArrayWithBoundariesWithout(long[][] array, long[] limites) {
        long[][] arrayWithBoundaries = new long[array.length][MAX_COLS];
        int n = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i][0] >= limites[0] && array[i][0] < limites[1]) {
                arrayWithBoundaries[n] = array[i];
                n++;
            }
        }
        return arrayWithBoundaries;
    }

    public static long[][] calculateArrayAverageByDay(long[][] array, long[] limites) {
        long[][] arrayWithBoundaries = getArrayWithBoundaries(array, limites);

        Date day = new Date(arrayWithBoundaries[0][0]);
        YearMonth yearMonthObject = YearMonth.of(day.getYear(), day.getMonth() + 1);
        long[][] arrayAverage = new long[yearMonthObject.lengthOfMonth()][MAX_COLS];

        int totalRegisto = 0;
        long media = 0;
        int registos = 0;
        for (int i = 0; i < arrayWithBoundaries.length && registos < yearMonthObject.lengthOfMonth(); i++) {
            if (arrayWithBoundaries[i][0] > 0) {
                Date date = new Date(arrayWithBoundaries[i][0]);
                if (date.getDay() != day.getDay()) {
                    arrayAverage[registos][0] = day.getTime();
                    arrayAverage[registos][1] = (media / totalRegisto);
                    totalRegisto = 0;
                    media = 0;
                    day = date;
                    registos++;
                }
                media = media + arrayWithBoundaries[i][1];
                totalRegisto++;
            }
        }
        arrayAverage[registos][0] = day.getTime();
        arrayAverage[registos][1] = (media / totalRegisto);

        return arrayAverage;
    }

    public static long[][] calculateArrayAverageByMonth(long[][] array, long[] limites) {
        long[][] arrayWithBoundaries = getArrayWithBoundaries(array, limites);

        Date day = new Date(arrayWithBoundaries[0][0]);

        long[][] arrayAverage = new long[MAX_YEAR][MAX_COLS];

        int totalRegisto = 0;
        long media = 0;
        int registos = 0;
        for (int i = 0; i < arrayWithBoundaries.length && registos < MAX_YEAR; i++) {
            if (arrayWithBoundaries[i][0] > 0) {
                Date date = new Date(arrayWithBoundaries[i][0]);
                if (date.getMonth() != day.getMonth()) {
                    arrayAverage[registos][0] = day.getTime();
                    arrayAverage[registos][1] = (media / totalRegisto);
                    totalRegisto = 0;
                    media = 0;
                    day = date;
                    registos++;
                }
                media = media + arrayWithBoundaries[i][1];
                totalRegisto++;
            }
        }
        arrayAverage[registos][0] = day.getTime();
        arrayAverage[registos][1] = (media / totalRegisto);

        return arrayAverage;
    }

    public static int getTotalRows(long[][] array, long[] limites) {
        int total = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i][0] > 0 && array[i][0] >= limites[0] && array[i][0] <= limites[1]) {
                total++;
            }
        }

        return total;
    }

    public static double calculateGlobalAverage(long[][] array, long[] limites, int totalRows) {
        double average = 0.0;

        for (int i = 0; i < array.length; i++) {
            if (array[i][0] > 0 && array[i][0] >= limites[0] && array[i][0] <= limites[1]) {
                average = average + array[i][1];
            }
        }

        average = (double) Math.round((average / totalRows) * 10) / 10;

        return average;
    }

    public static void calculateNumberOfObservations(long[][] array, long[] limites, double average,boolean interativo) throws FileNotFoundException, UnsupportedEncodingException {
        int[] timeInterval = new int[3];
        double interval1 = (double) average - (0.2 * average);
        double interval2 = (double) average + (0.2 * average);

        for (int i = 0; i < array.length; i++) {
            if (array[i][0] > 0 && array[i][0] >= limites[0] && array[i][0] <= limites[1]) {
                long value = array[i][1];
                for (int t = 0; t < timeInterval.length; t++) {
                    if (t == 0) {
                        if (value < interval1) {
                            timeInterval[t] = timeInterval[t] + 1;
                        }
                    } else if (t == 1) {
                        if (value >= interval1 && value < interval2) {
                            timeInterval[t] = timeInterval[t] + 1;
                        }
                    } else {
                        if (value >= interval2) {
                            timeInterval[t] = timeInterval[t] + 1;
                        }
                    }
                }
            }
        }

        CreateHistogram(timeInterval, Math.round(interval1 * 10) / 10, Math.round(interval2 * 10) / 10,interativo);
    }

    public static int readFilteredArr(long[][] filteredArr, String FILE_IN_CSV_REBUILDED) throws FileNotFoundException, ParseException {
        Scanner readFile = new Scanner(new File(FILE_IN_CSV_REBUILDED));

        int filteredFilledRows = 0;

//        readFile.nextLine(); //leitura do ficheiro de texto "DAYTON.csv"
        while (readFile.hasNextLine() && filteredFilledRows < MAX_ROWS) { //ler enquanto ficheiro tiver dados e até ao limite definido para os arrays
            String[] splitedArr = readFile.nextLine().split(" ");

            for (int idxLn = 0; idxLn < splitedArr.length; idxLn++) {
                for (int idxCl = 0; idxCl < MAX_COLS; idxCl++) {

                    filteredArr[filteredFilledRows][0] = Long.parseLong(splitedArr[0]);
                    filteredArr[filteredFilledRows][1] = Integer.parseInt(splitedArr[1]);

                }
            }
            filteredFilledRows++;
        }

        return filteredFilledRows;

    }

    public static int chooseSortDirection(long[][] filteredArr, int filteredFilledRows, String escolha,String time,boolean interativo) throws FileNotFoundException, ParseException {
        //leitura do ficheiro de texto "dados_filtrados.csv"

        printLn("");
        printLn("Escolha a forma de ordenação dos dados");
        printLn("1.Ordenação Crescente");
        printLn("2.Ordenação Decrescente");
        printLn("3.Voltar ao menu anterior");
        if(interativo)
            escolha = sc.next();

        switch (escolha) {
            case "1":

                printLn("Escolheu a opção 1. Dados ordenados de forma ascendente ");
                mergeSortMethodAsc(filteredArr, filteredFilledRows);
                UnixTimeOutputAsc(filteredArr, filteredFilledRows,time,interativo);
                break;
            case "2":

                printLn("Escolheu a opção 2. Dados ordenados de forma descendente ");
                mergeSortMethodDesc(filteredArr, filteredFilledRows);
                UnixTimeOutputDesc(filteredArr, filteredFilledRows,time,interativo);
                break;

            case "3":
                printLn("Escolheu a opção 3. Voltar ao Menu Principal ");
                escolha = openMenu();
                break;

            default:
                System.out.println("Número inválido. Introduza valores 1 ou 2");
                break;

                     
            }
        return escolha.indexOf(escolha);
    }

    public static void mergeSortMethodAsc(long[][] filteredArr, int filteredfilledRows) throws FileNotFoundException, ParseException {

        if (filteredfilledRows < 2) {
            return;
        }
        int mid = filteredfilledRows / 2;
        long[][] left = new long[mid][2];
        long[][] right = new long[filteredfilledRows - mid][2];

        for (int i = 0; i < mid; i++) {
            left[i][0] = filteredArr[i][0];
            left[i][1] = filteredArr[i][1];
        }

        for (int i = mid; i < filteredfilledRows; i++) {
            right[i - mid][0] = filteredArr[i][0];
            right[i - mid][1] = filteredArr[i][1];
        }

        mergeSortMethodAsc(left, mid);
        mergeSortMethodAsc(right, filteredfilledRows - mid);

        mergeAsc(filteredArr, left, right, mid, filteredfilledRows - mid);

    }

    public static void mergeSortMethodDesc(long[][] filteredArr, int filteredfilledRows) throws FileNotFoundException, ParseException {

        if (filteredfilledRows < 2) {
            return;
        }
        int mid = filteredfilledRows / 2;
        long[][] left = new long[mid][2];
        long[][] right = new long[filteredfilledRows - mid][2];

        for (int i = 0; i < mid; i++) {
            left[i][0] = filteredArr[i][0];
            left[i][1] = filteredArr[i][1];
        }

        for (int i = mid; i < filteredfilledRows; i++) {
            right[i - mid][0] = filteredArr[i][0];
            right[i - mid][1] = filteredArr[i][1];
        }

        mergeSortMethodDesc(left, mid);
        mergeSortMethodDesc(right, filteredfilledRows - mid);

        mergeDesc(filteredArr, left, right, mid, filteredfilledRows - mid);
    }

    public static void mergeDesc(long[][] filteredArr, long[][] l, long[][] r, int left, int right) throws FileNotFoundException, ParseException {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i][1] >= r[j][1]) {
                filteredArr[k][0] = l[i][0];
                filteredArr[k++][1] = l[i++][1];
            } else {
                filteredArr[k][0] = r[j][0];
                filteredArr[k++][1] = r[j++][1];
            }
        }
        while (i < left) {
            filteredArr[k][0] = l[i][0];
            filteredArr[k++][1] = l[i++][1];
        }
        while (j < right) {
            filteredArr[k][0] = r[j][0];
            filteredArr[k++][1] = r[j++][1];
        }

    }

    public static void mergeAsc(long[][] filteredArr, long[][] l, long[][] r, int left, int right) throws FileNotFoundException, ParseException {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i][1] <= r[j][1]) {
                filteredArr[k][0] = l[i][0];
                filteredArr[k++][1] = l[i++][1];
            } else {
                filteredArr[k][0] = r[j][0];
                filteredArr[k++][1] = r[j++][1];
            }
        }
        while (i < left) {
            filteredArr[k][0] = l[i][0];
            filteredArr[k++][1] = l[i++][1];
        }
        while (j < right) {
            filteredArr[k][0] = r[j][0];
            filteredArr[k++][1] = r[j++][1];
        }

    }

    public static void UnixTimeOutputAsc(long[][] filteredArr, int filteredfilledRows, String time,boolean interativo) throws FileNotFoundException { //passagem de metodos por parametros
        String formatDate = "";
        PrintWriter printer = new PrintWriter(FILE_OUT_1);
        for (int idxLn = 0; idxLn < filteredfilledRows; idxLn++) {
            switch (time) {
                case "11":
                case "12":
                case "13":
                case "14":
                case "2":
                    formatDate = "HH:mm\"";
                    break;
                case "3":
                    formatDate = "yyyy-MM-dd";
                    break;
                case "4":
                    formatDate = "MM-dd";
                    break;
            }
                        
            String s = new SimpleDateFormat(formatDate).format(filteredArr[idxLn][0] * 1000);
            printer.printf("%s %d\n", s, filteredArr[idxLn][1]);

        }
        printer.close();
        CreateHistogramOrder(filteredArr,FILE_OUT_1,interativo);
    }
    
    public static void UnixTimeOutputDesc(long[][] filteredArr, int filteredfilledRows,String time,boolean interativo) throws FileNotFoundException { //passagem de metodos por parametros
        String formatDate = "";
        PrintWriter printer = new PrintWriter(FILE_OUT_2);
        for (int idxLn = 0; idxLn < filteredfilledRows; idxLn++) {
            switch (time) {
                case "11":
                case "12":
                case "13":
                case "14":
                case "2":
                    formatDate = "HH:mm";
                    break;
                case "3":
                    formatDate = "yyyy-MM-dd";
                    break;
                case "4":
                    formatDate = "MM-dd";
                    break;
            }
                        
            String s = new SimpleDateFormat(formatDate).format(filteredArr[idxLn][0] * 1000);
            printer.printf("%s %d\n", s, filteredArr[idxLn][1]);

        }
        printer.close();
        CreateHistogramOrder(filteredArr,FILE_OUT_2,interativo);
    }

    public static long[][] calculateArrayByDay(long[][] array, long[] limites) {
        long[][] arrayWithBoundaries = getArrayWithBoundariesWithout(array, limites);

        Date day = new Date(arrayWithBoundaries[0][0]);
        YearMonth yearMonthObject = YearMonth.of(day.getYear(), day.getMonth() + 1);
        long[][] arrayAverage = new long[yearMonthObject.lengthOfMonth()][MAX_COLS];

        int totalRegisto = 0;
        long soma = 0;
        int registos = 0;
        for (int i = 0; i < arrayWithBoundaries.length && registos < yearMonthObject.lengthOfMonth(); i++) {
            if (arrayWithBoundaries[i][0] > 0) {
                Date date = new Date(arrayWithBoundaries[i][0]);
                if (date.getDay() != day.getDay()) {
                    arrayAverage[registos][0] = day.getTime();
                    arrayAverage[registos][1] = (soma / totalRegisto );
                    totalRegisto = 0;
                    soma = 0;
                    day = date;
                    registos++;
                }
                soma = soma + arrayWithBoundaries[i][1];
                totalRegisto++;
            }
        }
        arrayAverage[registos][0] = day.getTime();
        arrayAverage[registos][1] = (soma / totalRegisto );

        return arrayAverage;
    }

    public static long[][] calculateArrayByMonth(long[][] array, long[] limites) {
        long[][] arrayWithBoundaries = getArrayWithBoundaries(array, limites);

        Date day = new Date(arrayWithBoundaries[0][0]);

        long[][] arrayAverage = new long[MAX_YEAR][MAX_COLS];

        int totalRegisto = 0;
        long soma = 0;
        int registos = 0;
        for (int i = 0; i < arrayWithBoundaries.length && registos < MAX_YEAR; i++) {
            if (arrayWithBoundaries[i][0] > 0) {
                Date date = new Date(arrayWithBoundaries[i][0]);
                if (date.getMonth() != day.getMonth()) {
                    arrayAverage[registos][0] = day.getTime();
                    arrayAverage[registos][1] = soma;
                    totalRegisto = 0;
                    soma = 0;
                    day = date;
                    registos++;
                }
                soma = soma + arrayWithBoundaries[i][1];
                totalRegisto++;
            }
        }
        arrayAverage[registos][0] = day.getTime();
        arrayAverage[registos][1] = soma;

        return arrayAverage;
    }

    public static long[][] calculateArrayByYear(long[][] array, long[] limites) {
        long[][] arrayWithBoundaries = getArrayWithBoundaries(array, limites);

        Date year = new Date(arrayWithBoundaries[0][0]);

        long[][] arrayAverage = new long[MAX_YEAR][MAX_COLS];

        int totalRegisto = 0;
        long soma = 0;
        int registos = 0;
        for (int i = 0; i < arrayWithBoundaries.length && registos < MAX_YEAR; i++) {
            if (arrayWithBoundaries[i][0] > 0) {
                Date date = new Date(arrayWithBoundaries[i][0]);
                if (date.getYear() != year.getYear()) {
                    arrayAverage[registos][0] = year.getTime();
                    arrayAverage[registos][1] = soma;
                    totalRegisto = 0;
                    soma = 0;
                    year = date;
                    registos++;
                }
                soma = soma + arrayWithBoundaries[i][1];
                totalRegisto++;
            }
        }
        arrayAverage[registos][0] = year.getTime();
        arrayAverage[registos][1] = soma;

        return arrayAverage;
    }

    public static void average(long[][] array, long[] limites, String time,String model,String nAlpha,boolean interativo) throws FileNotFoundException {

        long[][] newArray = new long[MAX_ROWS][MAX_COLS];
        long[][] arrayFilter = new long[MAX_ROWS][MAX_COLS];
        System.out.println("Escolha o metodo de calculo:");
        System.out.println("");
        System.out.println("1. Media Movel Simples (MMS)");
        System.out.println("2. Media Movel Exponencialmente Pesada (MMEP)");
        System.out.println("");
        String formatDate = "";
        String opcao = "";
        if(interativo)
            opcao = sc.next();
        else
            opcao = model;
        double media;
        switch (opcao) {
            case "1":

                System.out.println("Insira o valor n (ordem da média móvel):");
                int n = 0;
                if(interativo)
                    n = sc.nextInt();
                else
                    n = Integer.parseInt(nAlpha);
                
                System.out.println("1: MMS");
                System.out.println(".......................................................");

                //newArray = calculateArray(array, limites, time);

                arrayFilter = MMS(array, n);
                
                
                break;
            case "2":
                
                double alpha = 0;
                if(interativo){
                    do {
                        System.out.println("Insira o valor alpha:");
                        alpha = sc.nextDouble();
                    } while (alpha <= 0 && alpha > 1);
                }
                else
                    alpha = Double.parseDouble(nAlpha);
                

                System.out.println("2: MMEP");
                System.out.println(".......................................................");

                //newArray = calculateArray(array, limites, time);

                arrayFilter = MMEP(array, alpha);

                break;
            default:
                break;
        }
        
        switch (time) {
            case "11":
            case "12":
            case "13":
            case "14":
                formatDate = "\"%H:%M\"";
                break;
            case "2":
                formatDate = "\"%H:%M\"";
                break;
            case "3":
                formatDate = "\"%d/%m/%y\"";
                break;
            case "4":
                formatDate = "\"%d/%m\"";
                break;
        }
        CreateGraph(arrayFilter, formatDate,interativo,"suavizacao");
    }

    public static long[][] calculateArray(long[][] data, long[] dataFilter, String time) {

        long[][] newArray = new long[MAX_ROWS][MAX_COLS];
        long[] limites = new long[2];

        limites[1] = dataFilter[0];
        limites[0] = data[0][0];
        switch (time) {
            case "11":
            case "12":
            case "13":
            case "14":
                newArray = calculateArrayByDay(data, limites);
                break;
            case "2":
                newArray = calculateArrayByDay(data, limites);
                break;
            case "3":
                newArray = calculateArrayByMonth(data, limites);
                break;
            case "4":
                newArray = calculateArrayByYear(data, limites);
                break;
            default:
                break;
        }
        return newArray;
    }

    public static void MAE(long[][] array,  String time,String model,String nAlpha,boolean interativo) throws FileNotFoundException {        // Erro Medio Absoluto
        
        long[][] arrayFilter = new long[MAX_ROWS][MAX_COLS];
        
        System.out.println("Escolha o metodo de calculo:");
        System.out.println("");
        System.out.println("1. Media Movel Simples (MMS)");
        System.out.println("2. Media Movel Exponencialmente Pesada (MMEP)");
        System.out.println("");
        String opcao;
        if(interativo)
            opcao = sc.next();
        else 
            opcao = model;
        switch (opcao) {
            case "1":

                System.out.println("Insira o valor n (ordem da média móvel):");
                int n = 0;
                if(interativo)
                    n = sc.nextInt();
                else
                    n = Integer.parseInt(nAlpha);

                System.out.println("1: MMS");
                System.out.println(".......................................................");


                arrayFilter = MMS(array, n);
                break;
            case "2":

                double alpha = 0;
                if(interativo){
                    do {
                        System.out.println("Insira o valor alpha:");
                        alpha = sc.nextDouble();
                    } while (alpha <= 0 && alpha > 1);
                }
                else
                    alpha = Double.parseDouble(nAlpha);

                System.out.println("2: MMEP");
                System.out.println(".......................................................");


                arrayFilter = MMEP(array, alpha);

                break;
            default:
                break;
        }
        double media = 0.0;
        for (int i = 1; i < array.length - 1; i++) {
            if(array[i][0] > 0 && arrayFilter[i][1] > 0) {
                media += Math.abs( array[i][1] - arrayFilter[i][1] );
            }
        }
        media = media / array.length;
        
        if(interativo)
            printLn( " O erro médio absoluto é: " + media);
        else {
            PrintStream arquivo = new PrintStream("mae.txt"); 
        
            arquivo.print(" O erro médio absoluto é: " + media);
            arquivo.println();
            arquivo.close();
        }
    }

    public static long[][] MMS(long[][] array, int n) {        // Media Movel Simples

        long sumArray = 0;

        long[][] arrayMMS = new long[array.length][MAX_COLS];

        for (int i = array.length - 1; i > 0  ; i--) {
            if(array[i][0] > 0) {
                if(i >= n - 1) {
                    for(int r = i; r > i - n; r--) 
                        sumArray += array[r][1];
                    
                    long media = sumArray / n;
                    arrayMMS[i][0] = array[i][0];
                    arrayMMS[i][1] = media;
                    sumArray = 0;
                }
            }
        }
        
        return arrayMMS;
    }

    public static long[][] MMEP(long[][] array, double alpha) throws FileNotFoundException {      // Media Movel Exponencialmente Pesada

        long[][] arrayMMEP = new long[array.length][MAX_COLS];

        double lastValue = array[0][1];
        arrayMMEP[0][1] = (long) Math.round(lastValue);
        arrayMMEP[0][0] = array[0][0];
        double average = 0.0;

        for (int i = 1; i < array.length; i++) {
            if(array[i][0] > 0) {
                average = (alpha * array[i][1] + (1 - alpha) * lastValue);
                arrayMMEP[i][1] = (long) Math.round(average);
                arrayMMEP[i][0] = array[i][0];
                lastValue = average;
            }
        }

        return arrayMMEP;
    }

    public static void prevision(long[][] array, String time,String model,String nAlpha,boolean interativo) throws ParseException, FileNotFoundException {
        long[][] newArray = new long[MAX_ROWS][MAX_COLS];
        long[][] arrayPrevision = new long[MAX_ROWS][MAX_COLS];
        long nextTimestamp = 0;
        String dateToConvert = "";
        long lastTimestamp = getLastTimestamp(array);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date day = new Date(lastTimestamp );
        
        String formatDate = "";
        System.out.println("Escolha o metodo de calculo:");
        System.out.println("");
        System.out.println("1. Media Movel Simples (MMS)");
        System.out.println("2. Media Movel Exponencialmente Pesada (MMEP)");
        System.out.println("");

        String opcao;
        if(interativo)
            opcao = sc.next();
        else 
            opcao = model;
        
        Timestamp minTimestamp;
        int year, month;
        
        switch (time) {
            case "11":
            case "12":
            case "13":
            case "14":
            case "2":
                nextTimestamp = lastTimestamp + HOUR ;
                break;
            case "3":
                dateToConvert = "" + ( day.getYear() + 1900 ) + "-" + ( day.getMonth() + 2 ) + "-1 00:00:00";
                minTimestamp = new Timestamp(format.parse(dateToConvert).getTime());
                nextTimestamp = minTimestamp.getTime();
                break;
            case "4":
                if((day.getMonth() + 2) > 12)
                    month = 1;
                else
                    month = day.getMonth() + 2;
                YearMonth yearMonthObject = YearMonth.of(day.getYear() + 1900, month);
                int days = yearMonthObject.lengthOfMonth();
                dateToConvert = "" + ( day.getYear() + 1900 )  + "-" + ( day.getMonth() + 2 ) + "-"+ days +" 00:00:00";
                minTimestamp = new Timestamp(format.parse(dateToConvert).getTime());
                nextTimestamp = minTimestamp.getTime();
                break;
        }
        
        switch (opcao) {
            case "1":

                System.out.println("Insira o valor n (ordem da média móvel):");
                int n = 0;
                if(interativo)
                    n = sc.nextInt();
                else
                    n = Integer.parseInt(nAlpha);

                System.out.println("1: MMS");
                System.out.println(".......................................................");


                arrayPrevision = MMSPrevision(array, n, nextTimestamp);
               
                
                break;
            case "2":

                double alpha = 0;
                if(interativo){
                    do {
                        System.out.println("Insira o valor alpha:");
                        alpha = sc.nextDouble();
                    } while (alpha <= 0 && alpha > 1);
                }
                else
                    alpha = Double.parseDouble(nAlpha);

                System.out.println("2: MMEP");
                System.out.println(".......................................................");

                arrayPrevision = MMEPPrevisiion(array, alpha, nextTimestamp);

                break;
            default:
                break;
        }
        
         
        switch (time) {
            case "11":
            case "12":
            case "13":
            case "14":
                formatDate = "\"%H:%M\"";
                break;
            case "2":
                formatDate = "\"%H:%M\"";
                break;
            case "3":
                formatDate = "\"%d/%m/%y\"";
                break;
            case "4":
                formatDate = "\"%d/%m\"";
                break;
        }
        CreateGraph(arrayPrevision, formatDate,interativo,"previsao");
    }
    
    public static long[][] MMSPrevision(long[][] array, int n,long nextTimestamp ) {        // Media Movel Simples

        long sumArray = 0;
        boolean valid = true;
        long[][] arrayMMS = new long[array.length + 1][MAX_COLS];

        for (int i = 0 ; i < arrayMMS.length && valid; i++) {
            if(i < array.length && array[i][0] > 0) {
                arrayMMS[i][0] = array[i][0];
                arrayMMS[i][1] = array[i][1];
            } else {
                valid = false;
                for(int r = i - 1; r > (i -1) - n; r--) 
                    sumArray += array[r][1];

                long media = sumArray / n;
                arrayMMS[i][0] = nextTimestamp;
                arrayMMS[i][1] = media;
            }
        }
        return arrayMMS;
    }
    
    public static long[][] MMEPPrevisiion(long[][] array, double alpha,long nextTimestamp) throws FileNotFoundException {      // Media Movel Exponencialmente Pesada

        long[][] arrayMMEP = new long[array.length + 1][MAX_COLS];
        boolean valid = true;
        
        double average = 0.0;
        double lastValue = array[0][1];
        for (int i = 0; i < arrayMMEP.length && valid; i++) {
            if(i < array.length && array[i][0] > 0) {
                average = (alpha * array[i][1] + (1 - alpha) * lastValue);
                arrayMMEP[i][1] = (long) Math.round(average);
                arrayMMEP[i][0] = array[i][0];
                lastValue = average;
            } else {
                valid = false;
                average = (alpha * lastValue + (1 - alpha) * lastValue);
                arrayMMEP[i][1] = (long) Math.round(average);
                arrayMMEP[i][0] = nextTimestamp;
            }
        }
        
        return arrayMMEP;
    }
    
    
    public static long getLastTimestamp(long[][] array) {
        long lastTimestamp = 0;
        for (int i = 0 ; i < array.length  ; i++) {
            if(array[i][0] > 0) {
                lastTimestamp =  array[i][0];
            }
        }
        return lastTimestamp;
    }
    
   
}


