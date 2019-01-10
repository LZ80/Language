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
        preprocessor(text);
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
}
