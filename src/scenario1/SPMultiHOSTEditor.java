/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scenario1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONObject;

/**
 *
 * @author RDSG6431
 */
public class SPMultiHOSTEditor {
  
    
    public static void main(String[] args)
    {
        
        int totalNumber=4;
    
        JSONObject obj = new JSONObject();
     
        ArrayList fileList=new ArrayList();
        
        
        fileList.add("confSP"+File.separator+"HOST1.json");
        fileList.add("confSP"+File.separator+"HOST2.json");
        /*fileList.add("confSP"+File.separator+"HOST3.json");
        fileList.add("confSP"+File.separator+"HOST4.json");
        
        for(int i=5;i<totalNumber;i++)
        {
        fileList.add("confSP"+File.separator+"HOST"+i+".json");
        }*/
        obj.put("contract_list_of_SP", fileList);
        
        
	try {

		FileWriter file = new FileWriter("confSP"+File.separator+"contractFileList.json");
		file.write(obj.toJSONString());
		file.flush();
		file.close();

	} catch (IOException e) {
		e.printStackTrace();
	}
        
        
        
        obj = new JSONObject();
	obj.put("name", "HOST1");
        obj.put("context", "VM-deployment");
        
	//obj.put("Context", new Integer);

        HashMap serviceDescription=new HashMap();
        serviceDescription.put("location","France");
        serviceDescription.put("certificate","true");
        serviceDescription.put("volume","10_GB");
        serviceDescription.put("price","6_euro");
        
        obj.put("serviceDescription", serviceDescription);
        
        
        HashMap gauranteeTerm=new HashMap();
        gauranteeTerm.put("availability","more_98_percentage");
        obj.put("gauranteeTerm", gauranteeTerm);
        
        //Constraint1
        
        HashMap host_rule1=new HashMap ();
        HashMap VM_rule1=new HashMap ();
        host_rule1.put("ID", "HOST1");
        VM_rule1.put("purpose","test");
        
        ArrayList rule1=new ArrayList();
        rule1.add("prohibition");
        rule1.add(host_rule1);
        rule1.add(VM_rule1);
        
        
        
         
        
        ArrayList policyInConstraint1=new ArrayList();
        policyInConstraint1.add(rule1);
        
        
        ArrayList creationConstraint1=new ArrayList();
        creationConstraint1.add("other");
        creationConstraint1.add("true");
        creationConstraint1.add("true");
        creationConstraint1.add(policyInConstraint1);
        
        
        ArrayList totalConstraint=new ArrayList();
        totalConstraint.add(creationConstraint1);
        
        
        obj.put("creationConstraint",totalConstraint);
        
        
        
        
        
        
        

        
	try {

		FileWriter file = new FileWriter("confSP"+File.separator+"Host1.json");
		file.write(obj.toJSONString());
		file.flush();
		file.close();

	} catch (IOException e) {
		e.printStackTrace();
	}
        
        
        obj = new JSONObject();
	obj.put("name", "HOST2");
        obj.put("context", "VM-deployment");
        
	//obj.put("Context", new Integer);

        serviceDescription=new HashMap();
        serviceDescription.put("location","UK");
        serviceDescription.put("certificate","false");
        serviceDescription.put("volume","10_GB");
        serviceDescription.put("price","5_euro");
        
        obj.put("serviceDescription", serviceDescription);
        
        gauranteeTerm=new HashMap();
        gauranteeTerm.put("availability","more_99_percentage");
        obj.put("gauranteeTerm", gauranteeTerm);
        
        //Constraint1
        
        host_rule1=new HashMap ();
        VM_rule1=new HashMap ();
        host_rule1.put("ID", "HOST2");
        VM_rule1.put("data","private");
        
         rule1=new ArrayList();
        rule1.add("prohibition");
        rule1.add(host_rule1);
        rule1.add(VM_rule1);
        
        
        
         
        
       policyInConstraint1=new ArrayList();
        policyInConstraint1.add(rule1);
        
        
       creationConstraint1=new ArrayList();
        creationConstraint1.add("other");
        creationConstraint1.add("true");
        creationConstraint1.add("true");
        creationConstraint1.add(policyInConstraint1);
        
        
        totalConstraint=new ArrayList();
        totalConstraint.add(creationConstraint1);
        
        
        obj.put("creationConstraint",totalConstraint);
        
        
        
        
        
        
        

        
	try {

		FileWriter file = new FileWriter("confSP"+File.separator+"Host2.json");
		file.write(obj.toJSONString());
		file.flush();
		file.close();

	} catch (IOException e) {
		e.printStackTrace();
	}
        
        
        
        
        obj = new JSONObject();
	obj.put("name", "HOST3");
        obj.put("context", "VM-deployment");
        
	//obj.put("Context", new Integer);

        serviceDescription=new HashMap();
        serviceDescription.put("location","China");
        serviceDescription.put("certificate","false");
        serviceDescription.put("volume","10_GB");
        serviceDescription.put("price","4_euro");
        
        obj.put("serviceDescription", serviceDescription);
        
        
        gauranteeTerm=new HashMap();
        gauranteeTerm.put("availability","more_97_percentage");
        obj.put("gauranteeTerm", gauranteeTerm);
        
        //Constraint1
        
       host_rule1=new HashMap ();
        VM_rule1=new HashMap ();
        host_rule1.put("ID", "HOST3");
        VM_rule1.put("application","internal");
        
        rule1=new ArrayList();
        rule1.add("prohibition");
        rule1.add(host_rule1);
        rule1.add(VM_rule1);
        
        
        
         
        
        policyInConstraint1=new ArrayList();
        policyInConstraint1.add(rule1);
        
        
        creationConstraint1=new ArrayList();
        creationConstraint1.add("other");
        creationConstraint1.add("true");
        creationConstraint1.add("true");
        creationConstraint1.add(policyInConstraint1);
        
        
        totalConstraint=new ArrayList();
        totalConstraint.add(creationConstraint1);
        
        
        obj.put("creationConstraint",totalConstraint);
        
        
        
        
        
        
        

        
	try {

		FileWriter file = new FileWriter("confSP"+File.separator+"Host3.json");
		file.write(obj.toJSONString());
		file.flush();
		file.close();

	} catch (IOException e) {
		e.printStackTrace();
	}
        
        
        
        obj = new JSONObject();
	obj.put("name", "HOST4");
        obj.put("context", "VM-deployment");
        
	//obj.put("Context", new Integer);

        serviceDescription=new HashMap();
        serviceDescription.put("location","France");
        serviceDescription.put("certificate","true");
        serviceDescription.put("volume","10_GB");
        serviceDescription.put("price","3_euro");
        
        obj.put("serviceDescription", serviceDescription);
        
        
        gauranteeTerm=new HashMap();
        gauranteeTerm.put("availability","more_98_percentage");
        obj.put("gauranteeTerm", gauranteeTerm);
        
        //Constraint1
       
        
        creationConstraint1=new ArrayList();
        totalConstraint=new ArrayList();
        totalConstraint.add(creationConstraint1);
        
        
        obj.put("creationConstraint",totalConstraint);
        
        
        
        
        
        
        

        
	try {

		FileWriter file = new FileWriter("confSP"+File.separator+"Host4.json");
		file.write(obj.toJSONString());
		file.flush();
		file.close();

	} catch (IOException e) {
		e.printStackTrace();
	}

        
        
        for(int j=5;j<totalNumber;j++)
            
        {
        obj = new JSONObject();
	obj.put("name", "HOST"+j);
        obj.put("context", "VM-deployment");
        
	//obj.put("Context", new Integer);

        serviceDescription=new HashMap();
        serviceDescription.put("location","France");
        serviceDescription.put("certificate","true");
        serviceDescription.put("volume","10_GB");
        serviceDescription.put("price","3_euro");
        
        obj.put("serviceDescription", serviceDescription);
        
        
        gauranteeTerm=new HashMap();
        gauranteeTerm.put("availability","more_95_percentage");
        obj.put("gauranteeTerm", gauranteeTerm);
        
        //Constraint1
       
        
        creationConstraint1=new ArrayList();
        totalConstraint=new ArrayList();
        totalConstraint.add(creationConstraint1);
        
        
        obj.put("creationConstraint",totalConstraint);
        
        
        
        
        
        
        

        
	try {

		FileWriter file = new FileWriter("confSP"+File.separator+"Host"+j+".json");
		file.write(obj.toJSONString());
		file.flush();
		file.close();

	} catch (IOException e) {
		e.printStackTrace();
	}
        
        
        }
        
    }
    
    
}
