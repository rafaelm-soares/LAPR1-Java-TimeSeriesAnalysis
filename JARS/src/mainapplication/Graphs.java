/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainapplication;

import com.panayotis.gnuplot.GNUPlotParameters;
import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import static mainapplication.Functions.*;

/**
 *
 * @author jpjes
 */
public class Graphs {
    static Scanner sc = new Scanner(System.in);
    
    public static void CreateGraph(long[][] arrayOriginal,String formatDate,boolean interativo,String name) throws FileNotFoundException
    {
        String escolha = "";
        long firstDate = arrayOriginal[0][0] / 1000;
        PrintStream arquivo = new PrintStream("dados_filtrados.csv"); 
        
        
        for(int i = 0; i < arrayOriginal.length; i++ ) {
            if(arrayOriginal[i][0] > 0) {
                arquivo.print(arrayOriginal[i][0] / 1000);
                arquivo.print(" ");
                arquivo.print(arrayOriginal[i][1]);
                arquivo.println();
            }
        }
        arquivo.close();
        JavaPlot p = new JavaPlot();
          
        PlotStyle myPlotStyle = new PlotStyle();
        //JavaPlot.getDebugger().setLevel(Debug.VERBOSE);
        myPlotStyle.setStyle(Style.LINESPOINTS);
        myPlotStyle.setLineWidth(1);
        myPlotStyle.setLineType(NamedPlotColor.BLUE);
        myPlotStyle.setPointType(7);
        myPlotStyle.setPointSize(1);
       
        p.setTitle("Analise temporal");
        p.getAxis("x").setLabel("Data", "Arial", 10);
        p.getAxis("y").setLabel("MW", "Arial", 10);
        p.setKey(JavaPlot.Key.TOP_RIGHT);
        
        GNUPlotParameters params = p.getParameters();
        params.set( "grid" );
        
        if(interativo) {
            printLn("Deseja criar um ficheiro png? (sim/s ou nao/n)");
            do {
                escolha = sc.nextLine();
            } while (!(escolha.equalsIgnoreCase("sim") || escolha.equalsIgnoreCase("s") || escolha.equalsIgnoreCase("nao") || escolha.equalsIgnoreCase("n") ) );
        } else {
            escolha = "sim";
        }
        
        if(escolha.equalsIgnoreCase("sim") || escolha.equalsIgnoreCase("s")) {
            params.set( "term", "png" );
            params.set( "output", "\"dados_filtrados_"+name+".png\"" );
        }
        params.set("xtic rotate by -90 scale 1");
        params.set( "xdata", "time" );
        params.set( "timefmt", "\"%s\"" );
        params.set( "format x", formatDate );
        p.setParameters(params);
        p.addPlot( "\"dados_filtrados.csv\" using ($1+(1*3600)):2 with linespoints" );
        p.plot();
            
    }
    
    public static void CreateHistogram(int[] timeInterval,double interval1,double interval2,boolean interativo) throws FileNotFoundException {
        String escolha = "";
        PrintStream arquivo = new PrintStream("dados_histograma.csv"); 
        
        
        arquivo.print("]-INFINiTO," + interval1 + "] " + timeInterval[0]);
        arquivo.println();
        arquivo.print("]"+interval1+"," + interval2 + "] " + timeInterval[1]);
        arquivo.println();
        arquivo.print("]"+interval2+",+INFINiTO[ " + timeInterval[2]);
        arquivo.println();
        arquivo.close();
        JavaPlot p = new JavaPlot();
        GNUPlotParameters params = p.getParameters();
        
        
        
        if(interativo) {
            printLn("Deseja criar um ficheiro png? (sim/s ou nao/n)");
            do {
                escolha = sc.nextLine();
            } while (!(escolha.equalsIgnoreCase("sim") || escolha.equalsIgnoreCase("s") || escolha.equalsIgnoreCase("nao") || escolha.equalsIgnoreCase("n") ) );
        } else {
            escolha = "sim";
        }
        
        if(escolha.equalsIgnoreCase("sim") || escolha.equalsIgnoreCase("s")) {
            params.set( "term", "png" );
            params.set( "output", "\"graph_histogram.png\"" );
        }
        
        
        params.set("style data","histograms");
        p.setParameters(params);
        p.addPlot( "\"dados_histograma.csv\" using 2:xtic(1)  " );
        p.plot();
    }
    
    public static void CreateHistogramOrder(long[][] arrayOriginal,String name,boolean interativo) throws FileNotFoundException {
        String escolha = "";
        JavaPlot p = new JavaPlot();
        GNUPlotParameters params = p.getParameters();
        
        
        
        if(interativo) {
            printLn("Deseja criar um ficheiro png? (sim/s ou nao/n)");
            do {
                escolha = sc.nextLine();
            } while (!(escolha.equalsIgnoreCase("sim") || escolha.equalsIgnoreCase("s") || escolha.equalsIgnoreCase("nao") || escolha.equalsIgnoreCase("n") ) );
        } else {
            escolha = "sim";
        }
        if(escolha.equalsIgnoreCase("sim") || escolha.equalsIgnoreCase("s")) {
            params.set( "term", "png" );
            params.set( "output", "\""+name+".png\"" );
        }
        params.set("xtic rotate by -90 scale 1");
        params.set("style histogram ","clustered");
        params.set("style data","histograms");
        
        p.setParameters(params);
        p.addPlot( "\""+name+"\" using 2:xtic(1) " );
        p.plot();
    }
    
}
