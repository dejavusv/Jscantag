/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jscantag;

import com.des.generate.GenerateFile;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Surachai
 */
public class Jscantag {

    GenerateFile g = new GenerateFile();
    private static final String[] ACCESS_MODIFIERS = {"public", "private", "protected"};
    private static final String[] JAVA_KEYWORD = {"abstract", "continue", "for", "new", "switch", "assert", "default", "goto", "package", "synchronized", "boolean", "do", "if", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while"
    };

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Jscantag h = new Jscantag();
        h.ScanMethodFromFile("C:\\Users\\Surachai\\Documents\\NetBeansProjects\\GenerateFile\\src\\com\\des\\generate\\GenerateFile.java");

    }

    public List<String> ScanMethodFromFile(String pathFile) {
        List<String> data = g.readFile(pathFile);
        List<String> MethodList = new LinkedList<String>();
        for (int i = 0; i < data.size(); i++) {
           // System.out.println("data file : " + data.get(i));
            String temp = data.get(i);
            if (temp.trim().length() != 0) {
                
                String[] path = temp.trim().split(" ");
                
                if ((path[0].equalsIgnoreCase(ACCESS_MODIFIERS[0])) || (path[0].equalsIgnoreCase(ACCESS_MODIFIERS[1])) || (path[0].equalsIgnoreCase(ACCESS_MODIFIERS[2]))) {
                    for (int j = 0; j < path.length; j++) {
                        //check class
                        if (path[j].indexOf("class") != -1) {
                            break;
                        } else if (path[j].indexOf(";") != -1) { //check valiable
                            break;
                        } else {           
                            if(path[j].indexOf("(") != -1){
                                if(path[j].indexOf("(") != 0){
                                    System.out.println("methodname :"+path[j].substring(0, path[j].indexOf("(")));
                                    MethodList.add(path[j].substring(0, path[j].indexOf("(")));
                                }else{
                                    System.out.println("methodname :"+path[j-1]);
                                    MethodList.add(path[j-1]);
                                }
                            }
                        }
                                
                    }
                }
            }
        }
        return null;
    }

    public List<String> ScanValiableFromFile(String pathFile) {
        List<String> data = g.readFile(pathFile);
        for (int i = 0; i < data.size(); i++) {

        }
        return null;
    }

}
