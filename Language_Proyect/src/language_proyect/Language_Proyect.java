/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package language_proyect;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import UI.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
/**
 *
 * @author macas
 */
public class Language_Proyect {

    static LanguageUI gui;
    static String fileName = "";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        gui = new LanguageUI();
        
        
        String text = null;
        
        //Preprocessing
        ArrayList<String> lines = preprocessor(text);
        //Lexical Analysis
        ArrayList<LexLine> lexLines = lexicalAnalyser(lines);
    }
    
    public static void saveFile()
    {
        fileName = pickFileName("./");
        if(fileName == "")
        {
            fileName = pickFileName("./");
        }
        
        String text = gui.getProgram();
        
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(text);
            writer.close();
        }
        catch(Exception e)
        {
            
        }
        
    }
    
    public static void openFile(String base)
    {
        File file = new File(pickFileName(base));
        
        String text = "";
        
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(file);
            scanner.useDelimiter("\\Z");
            text = scanner.next();
        }
        catch(Exception e)
        {
            
        }
        
        gui.setText(text);
    }
    
    public static String pickFileName(String base)
    {   
        fileName = "";
        fileName = gui.filePicker(base);
        System.out.println(fileName);
        
        return fileName;
    }
    
    public static ArrayList<String> preprocessor(String text)
    {
        //START!!!!!
        //eliminating carry returns
        String temp = "";
        for(int i=0; i<text.length(); i++)
        {
            if(text.charAt(i) != (char)13)
            {
                temp += text.charAt(i);
            }
        }
        text = temp;
        
        //print(text);
        
        //Split by lines
        String []l = text.split("\n");
        ArrayList<String> lines = new ArrayList<>();
        
        for(int i=0; i<l.length; i++)
        {
            lines.add(l[i]);
        }
        
        //replace tabs
        for(int i=0; i<lines.size(); i++)
        {
            lines.set(i, lines.get(i).replaceAll("\\s+", " "));
        }
        //print(lines);
        //trim whitespace
        
        for(int i=0; i<lines.size(); i++){
            lines.set(i, lines.get(i).trim());
        }
        
        //eliminates nulls
        for(int i=0; i<lines.size(); i++)
        {
            if(lines.get(i).equals(null) || lines.get(i).equals(""))
            {
                lines.remove(i);
            }
        }
        
        return lines;
    }
    
    static class LexPair{
        public String type;
        public String token;
        
        LexPair(String type, String token)
        {
            this.type = type;
            this.token = token;
        }
        
        @Override
        public String toString()
        {
            return "<" + this.type + "," + this.token + ">";
        }
    }
    
    static class LexLine{
        public ArrayList<LexPair> tokens;
        
        LexLine(ArrayList<LexPair> tokens)
        {
            this.tokens = tokens;
        }
        
        @Override
        public String toString()
        {
            String s = "";
            for(int i=0; i<this.tokens.size(); i++)
            {
                s.concat(this.tokens.get(i).toString());
            }
            return s;
        }
    }
    
    public static ArrayList<LexLine> lexicalAnalyser(ArrayList<String> lines)
    {
        
        ArrayList<LexLine> lexLines = new ArrayList();
        for(int i=0; i<lines.size(); i++)
        {
            String line = lines.get(i);
            int iword=0;
            int fword = 0;
            
            ArrayList<LexPair> lexic = new ArrayList();
            
            while(iword < line.length())
            {
                
                while(iword < line.length())
                {
                    char iw = line.charAt(iword);
                    if(isOperator(iw)){
                        lexic.add(new LexPair("operator", String.valueOf(iw)));
                        iword++;
                    }else if(isSymbol(iw)){
                        lexic.add(new LexPair("symbol", String.valueOf(iw)));
                        iword++;
                    }else if(iw == ' '){
                        iword++;
                    }else{
                        break;
                    }
                }
                if(iword >= line.length())
                {
                    break;
                }
                fword = iword;
                
                while(fword < line.length() && !isOperator(line.charAt(fword)) && !isSymbol(line.charAt(fword)) && line.charAt(fword) != ' ')
                {
                    System.out.println(line.charAt(fword));
                    fword++;
                }
                String word = line.substring(iword, fword);
                String result = wordAnalyzer(word);
                
                if(result.equals("error"))
                {
                    System.out.println("ERROR!!!!!!! " + word);
                }
                else
                {
                    lexic.add(new LexPair(result, word));
                    System.out.println("GOOD");
                }
                iword = fword;
            }
            System.out.println(lexic);
            lexLines.add(new LexLine(lexic));
        }
        
        return lexLines;
    }
    
    public static String wordAnalyzer(String word)
    {
        String[] keywords = new String[]{"int", "while", "if"};
        
        for (int i = 0; i < keywords.length; i++) {
            if(word.equals(keywords[i]))
            {
                return "keyword";
            }
        }
        
        if(word.matches("\\d+"))
        {
            return "number";
        }
        else if(word.matches("\\w+"))
        {
            return "identifier";
        }
        else
        {
            return "error";
        }
    }
    
    public static boolean isOperator(char c)
    {
        int[] operators = new int[]{33,37,38,42,43,45,47,60,61,62,124};
        
        for(int o: operators)
        {
            if((char)o == c)
            {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isSymbol(char c)
    {
        int[] symbols = new int[]{35,40,41,44,46,59,91,93,123,125}; 
        
        for(int o: symbols)
        {
            if((char)o == c)
            {
                return true;
            }
        }
        return false;
    }
}
