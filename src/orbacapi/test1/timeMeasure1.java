/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package orbacapi.test1;

import cloudResource.HOST;
import cloudResource.VM;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import orbac.AbstractOrbacPolicy;
import orbac.exception.COrbacException;
import orbac.securityRules.CConcreteRuleContainer;
import org.json.simple.parser.ParseException;
import static util.allocation.generateFinalDeploySolution;
import util.method;
import static util.method.filterOrBACPolicy;
import static util.method.generateConcreteSeparationPolicyFromVMList;
import static util.method.printInfo;

/**
 *
 * @author RDSG6431
 */
public class timeMeasure1 {
    
     public static void main(String[] args) throws IOException, ParseException, COrbacException
    {
        
    long Time1 = System. currentTimeMillis();  
    
    System.out.println("**********************begin policyList information****************\n");
   
    LinkedList <HOST> HOSTList=method.readHostFileAndGenerateHOSTList("confSP"+File.separator+"contractFileList.json");     
    LinkedList <ArrayList> HOSTPolicyList=method.readSPFileAndGenerateAbstractPolicy("confSP"+File.separator+"contractFileList.json");
    /*
    for (int i=0;i<HOSTPolicyList.size();i++)
         {
             
             System.out.println("*********policy List********\n");
             method.printArrayList(HOSTPolicyList.get(i));
         
         }*/
    LinkedList <VM> VMList=method.readClientFileAndGenerateVMList("confClient"+File.separator+"test3.json");
    LinkedList <ArrayList> VMPolicyList=method.readClientFileAndGenerateAbstractPolicy("confClient"+File.separator+"test3.json");
    
    AbstractOrbacPolicy p=method.generateOrBACPolicyFromVMandHostPolicy(VMPolicyList, VMList, HOSTPolicyList, HOSTList);
    
    
    if (p==null)
    {
     System.out.println("\n ------------------null policy generated------------------");
    }
        
   else
    {
     method.printAllConcretePermission(p);
     
     method.printAllConcreteProhibition(p);
    }

    
   
    
    
    long Time2 = System. currentTimeMillis(); 
    long duration1=Time2-Time1;
   System.out.println("*****************Initial Security Policy generation: "+duration1+"ms");
    
    p.WritePolicyFile("policyGenerated"+File.separator+"policy_1_SecurityRequirement.xml",null);
    
    
    
    
    
    
    
    long Time3 = System. currentTimeMillis(); 
    
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
     HashMap <String,LinkedList <String>> finalDeploySolution= generateFinalDeploySolution(p, VMList, HOSTList, concreteSeparationPolicy) ;
     
     
     
     
     method.printInfo("final deploy solution");
     method.printHashMap(finalDeploySolution);
     
     
     
     
     
    /*
    boolean r1 = p.IsPermited ("HOST4", "deploy", "VM1");
    boolean r2 = p.IsPermited ("HOST1", "deploy", "VM1");
    //boolean r3 = p.IsProhibited("Host1", "deploy", "VM1");
    
    
     System.out.println("r1:"+r1+"\n r2:"+r2+"\n");
    */
    long Time4 = System. currentTimeMillis(); 
    
    
    
   long duration2=Time4-Time3;
   System.out.println("*****************final deployment solution generation: "+duration2+"ms");
    
    
    
    System.exit(0);
    
    }
     
     
     
     
    }