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
    
}
