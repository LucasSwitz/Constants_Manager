package robot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Administrator on 1/3/2016.
 */
public abstract class ConstantsManager {

    private static File file;
    private static HashMap<String,Object> map = new HashMap<>();

    public static void setFile(File file){
        ConstantsManager.file = file;
        readFile();
    }

    public static boolean isFileSet(){return file!=null;}

    private static void readFile()
    {
        char[] chars = new char[2048];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.read(chars);
            processVarData(chars);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void processVarData(char[] chars)
    {
        String currentVarName = "";
        String currentVarValue = "";
        String currentData = "";

        for(int i =0; i < chars.length; i++)
        {
            char c = chars[i];
            switch(c)
            {
                case (char)0:
                    //remove null characters
                break;
                case ' ':
                    break;
                case '=':
                    currentVarName = currentData;
                    currentData= "";
                    break;
                case '\r':
                    break;
                case '\n':
                    currentVarValue = currentData;
                    currentData = "";
                    addData(currentVarName,currentVarValue);
                    break;
                default:
                    currentData+=c;
                    if (i + 1 == chars.length) {
                    currentVarValue = currentData;
                    addData(currentVarName, currentVarValue);
                }
            }
        }
    }

    private static void addData(String name, String value)
    {
        //determine implicit types
        if(value.indexOf('"') != -1)
        {
            value = value.replace("\"","");
            addString(name, value);
        }
        else if(value.indexOf('.') != -1)
        {
            addDouble(name,Double.parseDouble(value));
        }
        else  if (Character.isAlphabetic(value.toCharArray()[0]))
        {
            addBool(name, Boolean.parseBoolean(value));
        }
        else if (Character.isDigit(value.toCharArray()[0]))
        {
            addInt(name,Integer.parseInt(value));
        }
        else
        {
            //Congrats! You invented a new type.
        }
    }
    private static void addBool(String name, Boolean bool)
    {
        map.put(name,bool);
    }
    private static void addString(String name, String string)
    {
        map.put(name,string);
    }
    private static void addInt(String name, Integer integer)
    {
        map.put(name,integer);
    }
    private static void addDouble(String name, Double doub)
    {
        map.put(name,doub);
    }
    private static boolean isDataLegal(String name,Class clazz)
    {
        return (map.get(name)!=null && map.get(name).getClass() == clazz);
    }
    private static Object getData(String name, Class clazz) throws IllegalArgumentException
    {
        if(isDataLegal(name,clazz)) {
            return map.get(name);
        }
        else{
            String message = "Either var: "+name+" is missing or of the wrong type. (implicitly typed to): "+clazz.toString();
            if(!isFileSet())
                message = "File not set!";
            throw new IllegalArgumentException(message);
        }
    }
    public static double getDouble(String name) {
        return  (double)(Double)getData(name, Double.class);
    }
    public static String getString(String name) {
        return  (String)getData(name, String.class);
    }
    public static int getInt(String name) {
        return  (int)(Integer)getData(name, Integer.class);
    }
    public static boolean getBool(String name) {
        return  (boolean)(Boolean)getData(name, Boolean.class);
    }


}