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
/**
 *
 * @author RDSG6431
 */
public class contractMultiClients {
    private static String s;
    //private static String s;
    public static void main(String[] args)
    {
        
        int totalNumber=500;
        
    
        JSONObject obj = new JSONObject();
	obj.put("name", "clientTemplate");
        obj.put("context", "VM-deployment");
	//obj.put("Context", new Integer);

        HashMap serviceRequirement=new HashMap();
        
        
        HashMap serviceDescription=new HashMap();
        serviceRequirement.put("VM1_volume","18_GB");
        serviceDescription.put("VM1_purpose","dev");
        serviceDescription.put("VM1_data","private");
        serviceDescription.put("VM1_application","internal");
        
        
        
        serviceRequirement.put("VM2_volume","20_GB");
        serviceDescription.put("VM2_purpose","prod");
        serviceDescription.put("VM2_data","public");
        serviceDescription.put("VM2_application","business");
        
        
        serviceRequirement.put("VM3_volume","30_GB");
        serviceDescription.put("VM3_purpose","test");
        serviceDescription.put("VM3_data","public");
        serviceDescription.put("VM3_application","business");
        
        
        serviceRequirement.put("VM4_volume","20_GB");
        serviceDescription.put("VM4_purpose","prod");
        serviceDescription.put("VM4_data","public");
        serviceDescription.put("VM4_application","business");
        
        
        for (int i=5;i<totalNumber;i++)
            {
                serviceRequirement.put("VM"+i+"_volume","20_GB");
                serviceDescription.put("VM"+i+"_purpose","prod");
                serviceDescription.put("VM"+i+"_data","public");
                serviceDescription.put("VM"+i+"_application","business");
            }
        
        
        
        
        obj.put("serviceRequirement", serviceRequirement);
        obj.put("serviceDescription", serviceDescription);
        
        HashMap gauranteeTerm=new HashMap();
        gauranteeTerm.put("VM1_availability","more_97_percentage");
        gauranteeTerm.put("VM2_availability","more_99_percentage");
        gauranteeTerm.put("VM3_availability","more_95_percentage");
        gauranteeTerm.put("VM4_availability","more_99_percentage");
        obj.put("gauranteeTerm", gauranteeTerm);
        
        //Constraint1
        
        HashMap host_rule1=new HashMap ();
        HashMap VM_rule1=new HashMap ();
        host_rule1.put("certificate", "true");
        VM_rule1.put("purpose","dev");
        
        ArrayList rule1=new ArrayList();
        rule1.add("permission");
        rule1.add(host_rule1);
        rule1.add(VM_rule1);
        
        
        HashMap host_rule1_2=new HashMap ();
        HashMap VM_rule1_2=new HashMap ();
        host_rule1_2.put("certificate", "true");
        VM_rule1_2.put("purpose","prod");
        
        ArrayList rule1_2=new ArrayList();
        rule1_2.add("permission");
        rule1_2.add(host_rule1_2);
        rule1_2.add(VM_rule1_2);
        
        
        
        
        HashMap host_rule1_3=new HashMap ();
        HashMap VM_rule1_3=new HashMap ();
        host_rule1_3.put("certificate", "true");
        VM_rule1_3.put("purpose","test");
        
        ArrayList rule1_3=new ArrayList();
        rule1_3.add("permission");
        rule1_3.add(host_rule1_3);
        rule1_3.add(VM_rule1_3);
        
        
        
        
        
        
        
        
        HashMap host_rule2=new HashMap ();
        HashMap VM_rule2=new HashMap ();
        host_rule2.put("location", "France");
        VM_rule2.put("ID","VM2");
        
        ArrayList rule2=new ArrayList();
        rule2.add("permission");
        rule2.add(host_rule2);
        rule2.add(VM_rule2);
        
        HashMap host_rule2_1=new HashMap ();
        HashMap VM_rule2_1=new HashMap ();
        host_rule2_1.put("location", "UK");
        VM_rule2_1.put("ID","VM2");
        
        ArrayList rule2_1=new ArrayList();
        rule2_1.add("permission");
        rule2_1.add(host_rule2_1);
        rule2_1.add(VM_rule2_1);
        
        
        
        
        
        HashMap host_rule3=new HashMap ();
        HashMap VM_rule3=new HashMap ();
        host_rule3.put("location", "France");
        VM_rule3.put("application","business");
        
        ArrayList rule3=new ArrayList();
        rule3.add("permission");
        rule3.add(host_rule3);
        rule3.add(VM_rule3);
        
        
        HashMap host_rule3_1=new HashMap ();
        HashMap VM_rule3_1=new HashMap ();
        host_rule3_1.put("location", "UK");
        VM_rule3_1.put("application","business");
        
        ArrayList rule3_1=new ArrayList();
        rule3_1.add("permission");
        rule3_1.add(host_rule3_1);
        rule3_1.add(VM_rule3_1);
        
        
        
        HashMap VMSeperation_rule_1_1=new HashMap();
        HashMap VMSeperation_rule_1_2=new HashMap();
        
        VMSeperation_rule_1_1.put("ID","VM1");
        VMSeperation_rule_1_2.put("ID","VM3");
        
         ArrayList rule4=new ArrayList();
         rule4.add("separation");
         rule4.add(VMSeperation_rule_1_1);
         rule4.add(VMSeperation_rule_1_2);
        
        
         
        
        ArrayList policyInConstraint1=new ArrayList();
        policyInConstraint1.add(rule1);
        policyInConstraint1.add(rule1_2);
        policyInConstraint1.add(rule1_3);
        
        
        
        policyInConstraint1.add(rule2);
         policyInConstraint1.add(rule2_1);
         
          policyInConstraint1.add(rule3);
         policyInConstraint1.add(rule3_1);
         
         
         policyInConstraint1.add(rule4);
         
         
        
        ArrayList creationConstraint1=new ArrayList();
        creationConstraint1.add("RP4");
        creationConstraint1.add("true");
        creationConstraint1.add("true");
        creationConstraint1.add(policyInConstraint1);
        
        
        ArrayList totalConstraint=new ArrayList();
        totalConstraint.add(creationConstraint1);
        
        
        obj.put("creationConstraint",totalConstraint);
        
        
        
        
        
        
        

        
	try {

		FileWriter file = new FileWriter("confClient"+File.separator+"test3.json");
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