package scenario1;

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
public class hostConnect {
    private static String s;
    //private static String s;
    public static void main(String[] args) throws IOException, ParseException
    {
        
        
        
    
        JSONObject obj = new JSONObject();
	
        
	//obj.put("Context", new Integer);

        HashMap <String,Integer> connectPort=new HashMap <String,Integer> ();
        connectPort.put("HOST1",2222);
        connectPort.put("HOST2",2223);
      
        
        obj.put("connectPort", connectPort);
        
 
        
	try {

		FileWriter file = new FileWriter("confConnection"+File.separator+"portInfo.json");
		file.write(obj.toJSONString());
		file.flush();
		file.close();

	} catch (IOException e) {
		e.printStackTrace();
	}

	System.out.print(obj);

        
        
        
        
        
        
        
        /*
       
             JSONParser parser = new JSONParser();

	

		Object obj2 = parser.parse(new FileReader("confConnection\\portInfo.json"));

		JSONObject jsonObject = (JSONObject) obj2;
    
                HashMap <String, Integer> connectPort2=(HashMap<String,Integer>) jsonObject.get("connectPort");
                 
                method.printHashMap(connectPort2);
     */
        
       
     
        
        
        
        
     }
         
                
    }