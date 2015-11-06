/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package orbacapi.test1;

import cloudResource.HOST;
import cloudResource.VM;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import orbac.AbstractOrbacPolicy;
import orbac.exception.COrbacException;
import org.json.simple.parser.ParseException;
import util.method;
import static util.method.filterOrBACPolicy;
import static util.method.printInfo;

/**
 *
 * @author RDSG6431
 */
public class testForOrBACPolicyGeneration1 {
    
     public static void main(String[] args) throws IOException, ParseException, COrbacException
    {
        
        
    System.out.println("**********************begin policyList information****************\n");
   
    LinkedList <HOST> HOSTList=method.readHostFileAndGenerateHOSTList("confSP\\contractFileList.json");     
    LinkedList <ArrayList> HOSTPolicyList=method.readSPFileAndGenerateAbstractPolicy("confSP\\contractFileList.json");
    /*
    for (int i=0;i<HOSTPolicyList.size();i++)
         {
             
             System.out.println("*********policy List********\n");
             method.printArrayList(HOSTPolicyList.get(i));
         
         }*/
    LinkedList <VM> VMList=method.readClientFileAndGenerateVMList("confClient\\test3.json");
    LinkedList <ArrayList> VMPolicyList=method.readClientFileAndGenerateAbstractPolicy("confClient\\test3.json");
    
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
    
    
    
    
    
      /*************************SLA filter***********************************/
    
    p=filterOrBACPolicy(p,VMList,HOSTList);
    
    printInfo("After SLA filter");
    
    
    method.printAllConcretePermission(p);
     
    method.printAllConcreteProhibition(p);
    
    
    
    /*************************Resolve concrete reverse conflict***********************************/
    
      method.printInfo("concreteConflict");
    
      method.printConcreteConflict(p);
   

      method.resolveConflictAdvanced(p);
     
     
      method.printInfo("After resolved conclict");
     
      method.printAllConcretePermission(p);
     
     method.printAllConcreteProhibition(p);
    
    
    /*
    boolean r1 = p.IsPermited ("HOST4", "deploy", "VM1");
    boolean r2 = p.IsPermited ("HOST1", "deploy", "VM1");
    //boolean r3 = p.IsProhibited("Host1", "deploy", "VM1");
    
    
     System.out.println("r1:"+r1+"\n r2:"+r2+"\n");
    */
    
    
    
    System.exit(0);
    
    }
     
     
     
     
    }
    

