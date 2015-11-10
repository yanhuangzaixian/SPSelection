package contractEditor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import util.method;
/**
 *
 * @author RDSG6431
 */
public class SPConfFileEditor {
    private static String s;
    //private static String s;
    public static void main(String[] args)
    {
        
        
        
    
        JSONObject obj = new JSONObject();
        ArrayList fileList=new ArrayList();
        
        fileList.add("confSP"+File.separator+"HOST1.json");
        fileList.add("confSP"+File.separator+"HOST2.json");
        fileList.add("confSP"+File.separator+"HOST3.json");
        fileList.add("confSP"+File.separator+"HOST4.json");
	
        obj.put("contract_list_of_SP", fileList);
        
        
	try {

		FileWriter file = new FileWriter("confSP"+File.separator+"contractFileList.json");
		file.write(obj.toJSONString());
		file.flush();
		file.close();

	} catch (IOException e) {
		e.printStackTrace();
	}

	System.out.print(obj);

        
        
        
        
        
        
        
        
        
        
        
        
     }
         
                
    }
