/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package otherTest;



import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import orbac.*;
import orbac.conflict.CAbstractConflict;
import orbac.conflict.CConcreteConflict;
import orbac.exception.COrbacException;
import orbac.securityRules.CConcretePermission;
import orbac.securityRules.CConcreteProhibition;
import orbac.securityRules.CConcreteRule;
import orbac.securityRules.CConcreteRuleContainer;
import orbac.xmlImpl.XmlOrbacPolicy;
import util.method;
import static util.method.printInfo;


/**
 *
 * @author RDSG6431
 */
public class testMotOrBACPrempt1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws COrbacException, IOException {
        // TODO code application logic here
        
        
      System.out.println("Test begins");
          
     COrbacCore core = COrbacCore.GetTheInstance();
    // create new policy using the Jena implementation
    // create new policy using the Jena implementation
    AbstractOrbacPolicy p = core.CreatePolicy ("policy1", XmlOrbacPolicy.class );
   
    // add some abstract entities to the policy
    // add an organization
    p.CreateOrganization ("superCloud");
   
    // add a role in the newly created organization
    p.CreateRoleAndInsertIntoOrg ("H1_Permit", "superCloud");
    p.CreateRoleAndInsertIntoOrg ("H2_Permit", "superCloud");
    p.CreateRoleAndInsertIntoOrg ("H1_Deny", "superCloud");
    p.CreateRoleAndInsertIntoOrg ("H2_Deny", "superCloud");
    // add an activity in the newly created organization
     
    p.CreateActivityAndInsertIntoOrg ("myActivity", "superCloud");
    
    // add a view in the newly created organization
    p.CreateViewAndInsertIntoOrg ("V1_Permit", "superCloud");
    p.CreateViewAndInsertIntoOrg ("V2_Permit", "superCloud");
    p.CreateViewAndInsertIntoOrg ("V1_Deny", "superCloud");
    p.CreateViewAndInsertIntoOrg ("V2_Deny", "superCloud");
    
    
    
     p.CreateRoleAndInsertIntoOrg ("H11_Permit", "superCloud");
     p.CreateViewAndInsertIntoOrg ("V11_Permit", "superCloud");
     p.AbstractPermission ("superCloud","H11_Permit","myActivity","V11_Permit","default_context","R11_Permit");
     p.Empower("superCloud","Host1","H11_Permit");
     p.Use("superCloud","VM1","V11_Permit");

    // create an abstract rule in the newly created organization
    // the default_context context always exists in a XmlOrbacPolicy instance
     
    p.AbstractPermission ("superCloud","H1_Permit","myActivity","V1_Permit","default_context","R1_Permit");
    p.AbstractPermission ("superCloud","H2_Permit","myActivity","V2_Permit","default_context","R2_Permit");
    //p.AbstractProhibition("myOrganization","myRole3","myActivity3","myView3","default_context","R3");
    p.AbstractProhibition ("superCloud","H1_Deny","myActivity","V1_Deny","default_context","R1_Deny");
    p.AbstractProhibition ("superCloud","H2_Deny","myActivity","V2_Deny","default_context","R2_Deny");
    
   // p.AbstractProhibition("myOrganization","myRole","myActivity","myView","default_context","R2");
 
   
    
    
    
    /*Add concrete entities*/
    
    p.AddSubject("Host1");
    p.AddSubject("Host2");
    p.AddSubject("Host3");
    p.AddAction("deploy");
    p.AddObject("VM1");
    p.AddObject("VM2");
    
    p.AddObject("VM4");
     
    
    
    /*Permission rules*/
     p.Consider("superCloud","deploy","myActivity");
    /*Rule permit-1*/
  
   
    p.Empower("superCloud","Host1","H1_Permit"); 
    p.Empower("superCloud","Host2","H1_Permit");
    p.Use("superCloud","VM1","V1_Permit");
     
     /*Test repeated
    p.Empower("superCloud","Host1","H2_Permit"); 
    p.Use("superCloud","VM1","V2_Permit");
    */
    
    p.Empower("superCloud","Host3","H2_Permit"); 
    p.Use("superCloud","VM2","V2_Permit");
    
    p.Use("superCloud","VM4","V2_Permit");
    /*Rule permit-2*/
    
    
    
    
     /*Prohibition rules*/
    
    //p.Consider("superCloud","deploy","myActivity3");
    
    
    p.Empower("superCloud","Host1","H1_Deny");
    p.Use("superCloud","VM1","V1_Deny");
    
    p.Empower("superCloud","Host3","H2_Deny");
    p.Use("superCloud","VM2","V2_Deny");
    
   
    
    
    
    
    boolean r1 = p.IsPermited ("Host1", "deploy", "VM1");
    boolean r2 = p.IsPermited ("Host3", "deploy", "VM2");
    //boolean r3 = p.IsProhibited("Host1", "deploy", "VM1");
    
    
     System.out.println("r1:"+r1+"\n r2:"+r2+"\n");
    
     
    
     
    
   
    method.printInfo("Before resolved conclict");
    
    method.printAllConcretePermission(p);
     
     method.printAllConcreteProhibition(p);
    // method.printAbstractConflict(p);
     method.printConcreteConflict(p);
     
     p.SetRule1AboveRule2("R1_Deny", "R1_Permit","superCloud", "superCloud");
     
      p.WritePolicyFile("policyGenerated"+File.separator+"policy1FirstPreempt.xml",null);
     
     
      
      
      p.SetRule1AboveRule2("R2_Deny", "R2_Permit","superCloud", "superCloud");
      
      p.WritePolicyFile("policyGenerated"+File.separator+"policy2FirstPreempt.xml",null);
     
     //method.resolveConflictSimply(p);
     
     //method.resolveConflictAdvanced(p);
     
     
     
     /*
      LinkedList <CConcreteRuleContainer> containerList=method.resolveConflictAndGenerateRuleContainerList(p);
      
      
      
      
      
      
      
                Set conflict2=p.GetConcreteConflicts();
    //System.out.print(conflict2.size());

                Iterator iter2 = conflict2.iterator();
            while (iter2.hasNext()) {
         
              CConcreteConflict eachConflict2=(CConcreteConflict)iter2.next();
             printInfo("Conflict preempt?");
         
             CConcreteRule permissionInConflict=eachConflict2.GetFirstRule();
            // CConcreteRule prohibitionInConflict=eachConflict2.GetSecondRule();
             boolean pioprity=method.currentConcreteHasLessPriority(containerList, permissionInConflict);
             
             System.out.println(pioprity);
             
      
      
            }
      
      
      */
      
      
      
      
      
      
      
      
      
     
     
      method.printInfo("After resolved conclict");
     
      method.printAllConcretePermission(p);
     
     method.printAllConcreteProhibition(p);
     //method.printAbstractConflict(p);
     method.printConcreteConflict(p);
     
     /*
      boolean r3 = p.IsPermited ("Host1", "deploy", "VM1");
    boolean r4 = p.IsPermited ("Host3", "deploy", "VM2");
    //boolean r3 = p.IsProhibited("Host1", "deploy", "VM1");
    
    
     System.out.println("r3:"+r3+"\n r4:"+r4+"\n");*/
     
    
      System.out.println("Separation constraint\n");
     method.printFromSet(method.getSeparationConflictFromPermissions(p,"VM2","VM4"));

     
     //p.WritePolicyFile("policyTest1.xml",null);
     
     
      System.out.println("Test Ends");
      
      System.exit(0);
    
   } 
}