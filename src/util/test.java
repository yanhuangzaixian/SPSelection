/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import cloudResource.HOST;
import cloudResource.VM;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import orbac.AbstractOrbacPolicy;
import orbac.exception.COrbacException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import static util.allocation.generateFinalDeploySolution;
import static util.method.generateConcreteSeparationPolicyFromVMList;
import static util.method.printInfo;

/**
 *
 * @author RDSG6431
 */
public class test {
    
    
    public static long[] timeMeasure (int VMNumber,int HOSTNumber) throws IOException, ParseException, COrbacException

            {
            
                    //System.out.println("**********************begin policyList information****************\n");
   
                    LinkedList <HOST> HOSTList=method.readHostFileAndGenerateHOSTList("confSP"+File.separator+"contractFileList.json");     
                     LinkedList <ArrayList> HOSTPolicyList=method.readSPFileAndGenerateAbstractPolicy("confSP"+File.separator+"contractFileList.json");
   
                    LinkedList <VM> VMList=method.readClientFileAndGenerateVMList("confClient"+File.separator+"test3.json");
                    LinkedList <ArrayList> VMPolicyList=method.readClientFileAndGenerateAbstractPolicy("confClient"+File.separator+"test3.json");
    
    
    
                        long Time1 = System. currentTimeMillis();  
                     AbstractOrbacPolicy p=method.generateOrBACPolicyFromVMandHostPolicy(VMPolicyList, VMList, HOSTPolicyList, HOSTList,VMNumber,HOSTNumber);
                    long Time2 = System. currentTimeMillis(); 
                        long duration1=Time2-Time1;
     
    
    
                    if (p==null)
                    {
                    System.out.println("\n ------------------null policy generated------------------");
                     }
        
                   else
                    {
                    method.printAllConcretePermission(p);
     
                    method.printAllConcreteProhibition(p);
                    }

    
   
    
    
    
    
                        p.WritePolicyFile("policyGenerated"+File.separator+"policy_1_SecurityRequirement.xml",null);
    
    
    
    
    
    
    
   
    
                         /*************************SLA filter***********************************/
    
                         //LinkedList <CConcreteRuleContainer> containerList=method.filterOrBACPolicyAndReturnContainerList(p,VMList,HOSTList);
                         p=method.filterOrBACPolicyAndGeneratePriorityAbstractPolicy(p, VMList, HOSTList);
                        printInfo("After SLA filter");

                        //method.printRuleContainerlist(containerList);



                        p.WritePolicyFile("policyGenerated"+File.separator+"policy_2_AfterSLA.xml",null);

                        /*
                        method.printAllConcretePermission(p);

                        method.printAllConcreteProhibition(p);
    
                        */

                        /*************************Resolve concrete reverse conflict***********************************/

                          method.printInfo("concreteConflict");

                          method.printConcreteConflict(p);


                         p=method.resolveConflictAdvancedByAddAbstractRuleAndSetPrioprity(p);

                         //containerList=method.resolveConflictAndAddRuleContainerList(p,containerList);




                          p.WritePolicyFile("policyGenerated"+File.separator+"policy_3_AfterConflictResolution.xml",null);



                          method.printInfo("After resolved conclict");

                          //method.printRuleContainerlist(containerList);

                          /*
                          method.printAllConcretePermission(p);

                          method.printAllConcreteProhibition(p);
                        */





                         LinkedList <LinkedList <LinkedList <String>>> concreteSeparationPolicy =generateConcreteSeparationPolicyFromVMList(VMPolicyList, VMList);



                         long returnValue[];
                         returnValue=new long[2];

                          long Time3 = System. currentTimeMillis(); 
                         HashMap <String,LinkedList <String>> finalDeploySolution= generateFinalDeploySolution(p, VMList, HOSTList, concreteSeparationPolicy) ;
                          long Time4 = System. currentTimeMillis(); 
                           long duration2=Time4-Time3;


                           returnValue[0]=duration1;
                           returnValue[1]=duration2;




                          // System.out.println("*****************Initial Security Policy generation: "+duration1+"ms");
                          //System.out.println("*****************final deployment solution generation: "+duration2+"ms");



                         //method.printInfo("final deploy solution");
                         //method.printHashMap(finalDeploySolution);





                        /*
                        boolean r1 = p.IsPermited ("HOST4", "deploy", "VM1");
                        boolean r2 = p.IsPermited ("HOST1", "deploy", "VM1");
                        //boolean r3 = p.IsProhibited("Host1", "deploy", "VM1");


                         System.out.println("r1:"+r1+"\n r2:"+r2+"\n");
                        */





                        return returnValue;


                        //System.exit(0);
            
            
            
            }
    
    
    
    
    public static void VMAndHostGeneration (int totalClientNumber, int totalHOSTNumber)
            
    {
    
       
        
        
        JSONObject obj = new JSONObject();
     
        ArrayList fileList=new ArrayList();
        
        
        fileList.add("confSP"+File.separator+"HOST1.json");
        fileList.add("confSP"+File.separator+"HOST2.json");
        fileList.add("confSP"+File.separator+"HOST3.json");
        fileList.add("confSP"+File.separator+"HOST4.json");
        
        for(int i=5;i<totalHOSTNumber;i++)
        {
        fileList.add("confSP"+File.separator+"HOST"+i+".json");
        }
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

        
        
        for(int j=5;j<totalHOSTNumber;j++)
            
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
    
        
        
        
        
        
        /**Client parts**/
        
        
         
        
    
        obj = new JSONObject();
	obj.put("name", "clientTemplate");
        obj.put("context", "VM-deployment");
	//obj.put("Context", new Integer);

        HashMap serviceRequirement=new HashMap();
        
        
        serviceDescription=new HashMap();
        serviceRequirement.put("VM1_volume","1_GB");
        serviceDescription.put("VM1_purpose","dev");
        serviceDescription.put("VM1_data","private");
        serviceDescription.put("VM1_application","internal");
        
        
        
        serviceRequirement.put("VM2_volume","2_GB");
        serviceDescription.put("VM2_purpose","prod");
        serviceDescription.put("VM2_data","public");
        serviceDescription.put("VM2_application","business");
        
        
        serviceRequirement.put("VM3_volume","1_GB");
        serviceDescription.put("VM3_purpose","test");
        serviceDescription.put("VM3_data","public");
        serviceDescription.put("VM3_application","business");
        
        
        serviceRequirement.put("VM4_volume","12_GB");
        serviceDescription.put("VM4_purpose","prod");
        serviceDescription.put("VM4_data","public");
        serviceDescription.put("VM4_application","business");
        
        
        for (int i=5;i<totalClientNumber;i++)
            {
                serviceRequirement.put("VM"+i+"_volume","20_GB");
                serviceDescription.put("VM"+i+"_purpose","prod");
                serviceDescription.put("VM"+i+"_data","public");
                serviceDescription.put("VM"+i+"_application","business");
            }
        
        
        
        
        obj.put("serviceRequirement", serviceRequirement);
        obj.put("serviceDescription", serviceDescription);
        
        gauranteeTerm=new HashMap();
        gauranteeTerm.put("VM1_availability","more_97_percentage");
        gauranteeTerm.put("VM2_availability","more_99_percentage");
        gauranteeTerm.put("VM3_availability","more_95_percentage");
        gauranteeTerm.put("VM4_availability","more_99_percentage");
        obj.put("gauranteeTerm", gauranteeTerm);
        
        //Constraint1
        
        host_rule1=new HashMap ();
        VM_rule1=new HashMap ();
        host_rule1.put("certificate", "true");
        VM_rule1.put("purpose","dev");
        
        rule1=new ArrayList();
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
        
        
         
        
       policyInConstraint1=new ArrayList();
        policyInConstraint1.add(rule1);
        policyInConstraint1.add(rule1_2);
        policyInConstraint1.add(rule1_3);
        
        
        
        policyInConstraint1.add(rule2);
         policyInConstraint1.add(rule2_1);
         
          policyInConstraint1.add(rule3);
         policyInConstraint1.add(rule3_1);
         
         
         policyInConstraint1.add(rule4);
         
         
        
       creationConstraint1=new ArrayList();
        creationConstraint1.add("RP4");
        creationConstraint1.add("true");
        creationConstraint1.add("true");
        creationConstraint1.add(policyInConstraint1);
        
        
       totalConstraint=new ArrayList();
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
        
        
    
    
    }
    
    
}
