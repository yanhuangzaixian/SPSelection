package contractEditor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



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
/**
 *
 * @author RDSG6431
 */
public class contractHOST4 {
    private static String s;
    //private static String s;
    public static void main(String[] args)
    {
        
        
        
    
        JSONObject obj = new JSONObject();
	obj.put("name", "HOST4");
        obj.put("context", "VM-deployment");
        
	//obj.put("Context", new Integer);

        HashMap serviceDescription=new HashMap();
        serviceDescription.put("location","France");
        serviceDescription.put("certificate","true");
        serviceDescription.put("volume","100_GB");
        serviceDescription.put("price","3_euro");
        
        obj.put("serviceDescription", serviceDescription);
        
        
        HashMap gauranteeTerm=new HashMap();
        gauranteeTerm.put("availability","more_98_percentage");
        obj.put("gauranteeTerm", gauranteeTerm);
        
        //Constraint1
       
        
        ArrayList creationConstraint1=new ArrayList();
        ArrayList totalConstraint=new ArrayList();
        totalConstraint.add(creationConstraint1);
        
        
        obj.put("creationConstraint",totalConstraint);
        
        
        
        
        
        
        

        
	try {

		FileWriter file = new FileWriter("confSP\\Host4.json");
		file.write(obj.toJSONString());
		file.flush();
		file.close();

	} catch (IOException e) {
		e.printStackTrace();
	}

	System.out.print(obj);

        
        
        
        
        
        
        
        /*
        
        JSONParser parser = new JSONParser();

	try {

		Object obj2 = parser.parse(new FileReader("confSP\\confHost1.json"));

		JSONObject jsonObject = (JSONObject) obj2;
    
                HashMap serviceDescription2=(HashMap) jsonObject.get("serviceDescription");
                 
                method.printHashMap(serviceDescription2);
                
                
                HashMap gauranteeTerm2=(HashMap) jsonObject.get("gauranteeTerm");
                 
                method.printHashMap(gauranteeTerm2);
                
                
                
                ArrayList creationConstraint=(ArrayList) jsonObject.get("creationConstraint");
                
                method.printArrayList(creationConstraint);
		

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}

        
       
     
        
        */
        
        
     }
         
                
    }
