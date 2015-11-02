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
public class testJson1 {
    private static String s;
    //private static String s;
    public static void main(String[] args)
    {
        
        
        
    
        JSONObject obj = new JSONObject();
	obj.put("name", "clientTemplate");
        obj.put("context", "VM-deployment");
	//obj.put("Context", new Integer);

        HashMap serviceDescription=new HashMap();
        serviceDescription.put("VM1_volume","10");
        serviceDescription.put("VM2_volume","20");
        serviceDescription.put("VM3_volume","30");
        obj.put("serviceDescription", serviceDescription);
        
        
        HashMap gauranteeTerm=new HashMap();
        gauranteeTerm.put("VM1_performance","more_10_GB");
        gauranteeTerm.put("VM2_performance","more_20_GB");
        gauranteeTerm.put("VM3_performance","more_30_GB");
        obj.put("gauranteeTerm", gauranteeTerm);
        
        //Constraint1
        
        HashMap host_rule1=new HashMap ();
        HashMap VM_rule1=new HashMap ();
        host_rule1.put("location", "France");
        VM_rule1.put("ID","VM1");
        
        ArrayList rule1=new ArrayList();
        rule1.add("permission");
        rule1.add(host_rule1);
        rule1.add(VM_rule1);
        
        
        
        HashMap host_rule2=new HashMap ();
        HashMap VM_rule2=new HashMap ();
        host_rule2.put("Location", "France");
        VM_rule2.put("ID","VM2");
        
        ArrayList rule2=new ArrayList();
        rule2.add("permission");
        rule2.add(host_rule1);
        rule2.add(VM_rule1);
        
        
         
        
        ArrayList policyInConstraint1=new ArrayList();
        policyInConstraint1.add(rule1);
        policyInConstraint1.add(rule2);
        
        ArrayList creationConstraint1=new ArrayList();
        creationConstraint1.add("RP4");
        creationConstraint1.add("true");
        creationConstraint1.add("true");
        creationConstraint1.add(policyInConstraint1);
        
        
        ArrayList totalConstraint=new ArrayList();
        totalConstraint.add(creationConstraint1);
        
        
        obj.put("creationConstraint",totalConstraint);
        
        
        
        
        
        
        

        
	try {

		FileWriter file = new FileWriter("confClient\\test3.json");
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

		Object obj2 = parser.parse(new FileReader("test2.json"));

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

    

