package language_proyect;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Preprocesador {

    public static void main(String[] args) throws IOException {
        String inputtxt = ""; //input text
        String outputtxt = ""; //Output text 
        String temptxt = "";
        List<String> lines;
        int size;

        try {

            FileReader fr = new FileReader("rawText.txt");
            BufferedReader br = new BufferedReader(fr);

            String st;

            while ((st = br.readLine()) != null) {
                inputtxt = inputtxt + st + '\n';
            }

            fr.close();

        } catch (IOException e) {

            e.printStackTrace();

        }
        //System.out.println(inputtxt);

        String[] linesArray = inputtxt.split("[\n;]"); //divide el imput en lineas
        lines = new LinkedList<String>(Arrays.asList(linesArray));;  //pasa las lineas a una lista
        /*
        for(int i = 0; i<linesArray.length;i++){
            System.out.println(linesArray[i] + " " + i );
        } 
         */
        size = lines.size();

        for(int i = 0; i < lines.size();i++){
            temptxt = lines.get(i);
            temptxt = temptxt.replaceAll("\t"," ");
            temptxt = temptxt.trim();
            lines.set(i, temptxt);
        }
        
        int count = 0;
        
        
        while (count != lines.size()) {
            if (lines.get(count).equals("")) {
                lines.remove(count);
                count = 0;
            } else {
                count++;
            }
        }
        
        size = lines.size();
        for (int i = 0; i < size; i++) {
            temptxt = lines.get(i);

            for (int j = 0; j < temptxt.length(); j++) {
                if ((temptxt.charAt(j) == ' ' && temptxt.charAt(j + 1) == ' ')) {
                    temptxt = temptxt.substring(0, j) + temptxt.substring(j + 1);
                    j = 0;
                }
            }

            if (!(temptxt.equals(""))) {
                outputtxt = outputtxt + temptxt + "\n";
            }
        }
        
        System.out.println(outputtxt);
        
        File file = new File("preText.txt"); 
          
        if(file.delete()) { 
            System.out.println("File deleted successfully"); 
        } else { 
            System.out.println("Failed to delete the file"); 
        } 
        
        BufferedWriter out = new BufferedWriter(new FileWriter("preText.txt"));
        
        try {
            out.write(outputtxt); 
        } catch (IOException e) {
            System.out.println("Exception ");

        } finally {
            out.close();
        };
    }

}
