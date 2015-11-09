/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import cloudResource.HOST;
import cloudResource.VM;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import orbac.*;
import orbac.conflict.CAbstractConflict;
import orbac.conflict.CConcreteConflict;
import orbac.exception.COrbacException;
import orbac.securityRules.CConcretePermission;
import orbac.securityRules.CConcreteProhibition;
import orbac.securityRules.CConcreteRule;
import orbac.xmlImpl.XmlOrbacPolicy;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author RDSG6431
 */
public class method {
    public static void printAbstractConflict(AbstractOrbacPolicy p) throws COrbacException
            
    {
    
         System.out.println("---------------Abstract conflict ------------------\n");
     
     Set conflict1=p.GetAbstractConflicts();
   

     Iterator iter = conflict1.iterator();
     while (iter.hasNext()) {
         
         CAbstractConflict eachConflict=(CAbstractConflict)iter.next();
         
    System.out.println(eachConflict.GetFirstRule().toString());
    System.out.println(eachConflict.GetSecondRule().toString());
    System.out.println("\n");
     }
    
    
    }
    
    
    public static void printConcreteConflict(AbstractOrbacPolicy p) throws COrbacException
    {
    
        System.out.println("---------------Concrete conflict ------------------\n");
       
     Set conflict2=p.GetConcreteConflicts();
    //System.out.print(conflict2.size());

     Iterator iter2 = conflict2.iterator();
     while (iter2.hasNext()) {
         
         CConcreteConflict eachConflict2=(CConcreteConflict)iter2.next();
         
    System.out.println(eachConflict2.GetFirstRule().toString());
    System.out.println(eachConflict2.GetSecondRule().toString());
    System.out.println("\n");
     }
     
     }
    
    /*public static CConcrteRule getProhibitionRulesFromPolicy(AbstractOrbacPolicy p) throws COrbacException
    {
    
          
    
    }
    */
    
    
    
    public static void printAllConcretePermission(AbstractOrbacPolicy p) throws COrbacException
    {
    
        System.out.println("\n---------------All Concrete Permissions------------------\n");
        
        
        
    Set concretePermissionList=p.GetConcretePermissions();
     
     Iterator iter3 = concretePermissionList.iterator();
     while (iter3.hasNext()) {
         
         CConcretePermission Cpermission=(CConcretePermission)iter3.next();
         
         
         System.out.println(Cpermission.toString());
         System.out.println("Is active ?"+Cpermission.IsActive());
     }
     
    }
    
    
     public static void printAllConcreteProhibition(AbstractOrbacPolicy p) throws COrbacException
     {
      System.out.println("---------------All Concrete Prohibitions------------------\n");
     
     Set concreteProhibitionList=p.GetConcreteProhibitions();
     
     

     
     Iterator iter4 = concreteProhibitionList.iterator();
     while (iter4.hasNext()) {
         
         CConcreteProhibition Cprohibition=(CConcreteProhibition)iter4.next();
         
         System.out.println(Cprohibition.toString());
         
         System.out.println("Is active ?"+Cprohibition.IsActive());
     }
     
     
     
     }
     
     public static AbstractOrbacPolicy resolveConflictSimply(AbstractOrbacPolicy p) throws COrbacException
     {
     
        
        Set conflict2=p.GetConcreteConflicts();
    //System.out.print(conflict2.size());

       Iterator iter2 = conflict2.iterator();
           while (iter2.hasNext()) {
         
         CConcreteConflict eachConflict2=(CConcreteConflict)iter2.next();
         
             eachConflict2.GetFirstRule().SetState(false);
             System.out.println("HAHAHA!");
    
     } 
         
         
        return p;
     
     }
     
     
     public static void printInfo(String info)
     {
          System.out.println("-----------------------------"+info+"-----------------------------"+"\n");   
     }
     
    
     
     
     public static boolean IsProviderHasPrioprity ()
     {
         
          return true;
         
     }
     
     
     
     public static int searchConcreteViewInPermissionList(AbstractOrbacPolicy p, String concreteEntity)
     {
     int count=0;       
     Set concretePermissionList=p.GetConcretePermissions();
     
     Iterator iter3 = concretePermissionList.iterator();
     while (iter3.hasNext()) {
         
         CConcretePermission Cpermission=(CConcretePermission)iter3.next();
         
         if(Cpermission.GetObject().equals(concreteEntity))
         {
           count++;         
         }
         
     }
     
     return count;
     
     }
     
     
     public static CConcretePermission searchConcreteViewInPermissionListAndReturnFirstRule(AbstractOrbacPolicy p, String concreteEntity)
     {
          
     Set concretePermissionList=p.GetConcretePermissions();
     
     Iterator iter3 = concretePermissionList.iterator();
     while (iter3.hasNext()) {
         
         CConcretePermission Cpermission=(CConcretePermission)iter3.next();
         
         if(Cpermission.GetObject().equals(concreteEntity))
         {
           return Cpermission;        
         }
         
     }
     
     return null;
     
     }
     
     
    
     
    public static AbstractOrbacPolicy resolveConflictAdvanced(AbstractOrbacPolicy p) throws COrbacException
     {
     
        
        Set conflict2=p.GetConcreteConflicts();
    //System.out.print(conflict2.size());

       Iterator iter2 = conflict2.iterator();
           while (iter2.hasNext()) {
         
         CConcreteConflict eachConflict2=(CConcreteConflict)iter2.next();
         
         
             CConcreteRule permissionInConflict=eachConflict2.GetFirstRule();
             CConcreteRule prohibitionInConflict=eachConflict2.GetSecondRule();
             
             
             if((method.searchConcreteViewInPermissionList(p,permissionInConflict.GetObject())>1) || method.IsProviderHasPrioprity())      
                {permissionInConflict.SetState(false);}
             else if (!(method.IsProviderHasPrioprity()))
                 {prohibitionInConflict.SetState(false);}
                     
             
            
    
     } 
         
         
        return p;
     
     } 
     
  
    
    
    
    
    
    
    
     public static Set getSeparationConflictFromPermissions(AbstractOrbacPolicy p, String object1NeedToSeparated, String object2NeedToSeparated)
     {
         
         Set <CConcretePermission> setForReturn=new HashSet<CConcretePermission>();
        
         
         CConcretePermission conflictPermission1=null;
         CConcretePermission conflictPermission2=null;
         
         if (       ((method.searchConcreteViewInPermissionList(p, object1NeedToSeparated))==1)
                 &&     ((method.searchConcreteViewInPermissionList(p, object2NeedToSeparated))==1)
             )
             
         {
            conflictPermission1=method.searchConcreteViewInPermissionListAndReturnFirstRule(p, object1NeedToSeparated);
            conflictPermission2=method.searchConcreteViewInPermissionListAndReturnFirstRule(p, object2NeedToSeparated);
            if(conflictPermission1.GetSubject().equals(conflictPermission2.GetSubject()))
            {
               setForReturn.add(conflictPermission1);
               setForReturn.add(conflictPermission2);
               
            }
             
         }
         
         return setForReturn;
         
     }
     
     
/*     
     public static AbstractOrbacPolicy resolveSeparationConflict(AbstractOrbacPolicy p,) throws COrbacException
             
     {
        
     
     }
     
   */
     
     
     public static void printFromSet (Set <CConcretePermission> setRule)
     {
     
     Iterator iter3 = setRule.iterator();
     while (iter3.hasNext()) {
         
         CConcretePermission Cpermission=(CConcretePermission)iter3.next();
         
         
         System.out.println(Cpermission.toString());
         //System.out.println("Is active ?"+Cpermission.IsActive());
     }
     
     }
     
     
     public static void printHashMap (HashMap map)
        
        {
                Iterator iter = map.entrySet().iterator();
                    while (iter.hasNext()) 
                            {
                                HashMap.Entry entry = (HashMap.Entry) iter.next();
                                Object key = entry.getKey();
                                Object value = entry.getValue();
                                System.out.println(key);
                                System.out.println(value);
                            }
        }
     
     
     public static void printArrayList (ArrayList list )
             
     {
        
         for(int i = 0; i < list.size(); i++) 
         {   
            System.out.print(list.get(i));
         }  
     }
     
       public static void printLinkedList (LinkedList list )
             
     {
        
         for(int i = 0; i < list.size(); i++) 
         {   
            System.out.print(list.get(i));
         }  
     }
     
     
     
     public static LinkedList <VM> readClientFileAndGenerateVMList(String fileName) throws IOException, ParseException
     {
          JSONParser parser = new JSONParser();
          LinkedList <VM> VMList=new LinkedList <VM>();
          
	
                
                /*Read file and store in file related format*/
		Object obj = parser.parse(new FileReader(fileName));

		JSONObject jsonObject = (JSONObject) obj;
              
                HashMap serviceRequirement=(HashMap) jsonObject.get("serviceRequirement");
                
                HashMap serviceDescription=(HashMap) jsonObject.get("serviceDescription");
                 
                //method.printHashMap(serviceDescription);
                
                
                HashMap gauranteeTerm=(HashMap) jsonObject.get("gauranteeTerm");
                 
               // method.printHashMap(gauranteeTerm);
           
                
                //ArrayList creationConstraint=(ArrayList) jsonObject.get("creationConstraint");
                
                //method.printArrayList(creationConstraint);
		

                /*Extract information and put it into VMlist*/
                
                 /*0. Servcie Requirement*/
                
                
                Iterator iter0 = serviceRequirement.entrySet().iterator();
                
                while (iter0.hasNext()) 
                            {
                                
                                
                                HashMap.Entry entry = (HashMap.Entry) iter0.next();
                                Object key = entry.getKey();
                                
                              
                                String value= (String) entry.getValue();
                                String [] keys=getSplitResult ((String)key);
                                String ID=keys[0];
                                String term=keys[1];
                                
                                if (VMList.size()==0)
                                {
                                     
                                    VM newVM=new VM(ID,new HashMap(),new HashMap(),new HashMap());
                                    newVM.addServiceRequirement(term, value);
                                    VMList.add(newVM);
                                    
                                }
                                
                                boolean find=false;
                                for (int i=0;i<VMList.size();i++)
                                     {
                                         VM currentVM=VMList.get(i);
                                          
                                          
                                         if (currentVM.getID().equals(ID))
                                          
                                         {
                                         currentVM.addServiceRequirement(term,value);
                                         
                                         find=true;
                                         
                                         
                                         }
                                   
                                     }
                                
                                if (find==false)
                                {
                                     
                                    
                                    VM newVM=new VM(ID,new HashMap(),new HashMap(),new HashMap());
                                    newVM.addServiceRequirement(term,value);
                                    VMList.add(newVM);
                                }
                            
                            
                            }   
                
                
                
                
                
                
                
                
                /*1. Servcie Description*/
                
                Iterator iter1 = serviceDescription.entrySet().iterator();
                    while (iter1.hasNext()) 
                            {
                                
                                
                                HashMap.Entry entry = (HashMap.Entry) iter1.next();
                                Object key = entry.getKey();
                                
                                
                                
                                
                                
                            try{
                                float value = Float.parseFloat((String)entry.getValue());
                                
                                
                                String [] keys=getSplitResult ((String)key);
                                String ID=keys[0];
                                String term=keys[1];
                                
                                if (VMList.size()==0)
                                {
                                     
                                    VM newVM=new VM(ID,new HashMap(),new HashMap(),new HashMap());
                                    newVM.addServiceDescription(term, value);
                                    VMList.add(newVM);
                                    
                                }
                                
                                boolean find=false;
                                for (int i=0;i<VMList.size();i++)
                                     {
                                         VM currentVM=VMList.get(i);
                                          
                                          
                                         if (currentVM.getID().equals(ID))
                                          
                                         {
                                         currentVM.addServiceDescription(term,value);
                                         
                                         find=true;
                                         
                                         
                                         }
                                   
                                     }
                                
                                if (find==false)
                                {
                                     
                                    
                                    VM newVM=new VM(ID,new HashMap(),new HashMap(),new HashMap());
                                    newVM.addServiceDescription(term,value);
                                    VMList.add(newVM);
                                }
                                
                            }
                            
                            catch (NumberFormatException e) 
                            {
                            
                            
                                String value= (String) entry.getValue();
                                String [] keys=getSplitResult ((String)key);
                                String ID=keys[0];
                                String term=keys[1];
                                
                                if (VMList.size()==0)
                                {
                                     
                                    VM newVM=new VM(ID,new HashMap(),new HashMap(),new HashMap());
                                    newVM.addServiceDescription(term, value);
                                    VMList.add(newVM);
                                    
                                }
                                
                                boolean find=false;
                                for (int i=0;i<VMList.size();i++)
                                     {
                                         VM currentVM=VMList.get(i);
                                          
                                          
                                         if (currentVM.getID().equals(ID))
                                          
                                         {
                                         currentVM.addServiceDescription(term,value);
                                         
                                         find=true;
                                         
                                         
                                         }
                                   
                                     }
                                
                                if (find==false)
                                {
                                     
                                    
                                    VM newVM=new VM(ID,new HashMap(),new HashMap(),new HashMap());
                                    newVM.addServiceDescription(term,value);
                                    VMList.add(newVM);
                                }
                            
                            }
                            }     
                                
                             /*2 Gaurantee term*/
                                
                            Iterator iter2 = gauranteeTerm.entrySet().iterator();
                                 
                            while (iter2.hasNext()) 
                            {
                                HashMap.Entry entry2 = (HashMap.Entry) iter2.next();
                                Object key2 = entry2.getKey();
                                Object value2 = entry2.getValue();
                                
                                
                                String [] keys2=getSplitResult((String)key2);
                                String ID2=keys2[0];
                                String term2=keys2[1];
                                
                                if (VMList.size()==0)
                                {
                                    VM newVM=new VM(ID2,new HashMap(),new HashMap(),new HashMap());
                                    newVM.addGauranteeTerm(term2,(String)value2);
                                    
                                }
                                
                                boolean find2=false;
                                for (int i=0;i<VMList.size();i++)
                                     {
                                         VM currentVM=VMList.get(i);
                                        
                                         if (currentVM.getID().equals(ID2))
                                            
                                         currentVM.addGauranteeTerm(term2, (String)value2);
                                         
                                         find2=true;
                                   
                                     }
                                
                                if (find2==false)
                                {
                                    VM newVM=new VM(ID2,new HashMap(),new HashMap(),new HashMap());
                                    newVM.addGauranteeTerm(term2,(String)value2);
                                    
                                }
                            }
                
                
               
                             
                
	
        
       
         return VMList;
     }
     
     
     
       public static LinkedList <ArrayList> readClientFileAndGenerateAbstractPolicy(String fileName) throws IOException, ParseException
       
       {
          JSONParser parser = new JSONParser();
          LinkedList <ArrayList> policyList=new LinkedList <ArrayList > ();
          
	
                
                /*Read file and store in file related format*/
		Object obj = parser.parse(new FileReader(fileName));

		JSONObject jsonObject = (JSONObject) obj;

                ArrayList <ArrayList> creationConstraint=(ArrayList) jsonObject.get("creationConstraint");
 
                for (int i=0;i<creationConstraint.size();i++)
                {
                    
                    ArrayList  currentSLA=creationConstraint.get(i);
                    
                    
                    /*System.out.println("---------------erreur info--------------");
                    System.out.println(currentSLA.get(2));*/
                    
                    
                    if (currentSLA.get(2).equals("true"))
                    {
                    
                        
                        
                        ArrayList <ArrayList> currentRuleList=(ArrayList <ArrayList>) currentSLA.get(3);
                        for (int j=0;j<currentRuleList.size(); j++)
                        {
                         
                             policyList.add(currentRuleList.get(j));
                        }
                    }       
}
                
       
        
        return policyList;
        
       }
       
       
       
       
        public static LinkedList <ArrayList> readSPFileAndGenerateAbstractPolicy(String fileName) throws IOException, ParseException
       
       {
          JSONParser parser = new JSONParser();
          
          LinkedList <ArrayList> policyList=new LinkedList <ArrayList > ();
          
	  ArrayList fileNameList=readContractListFromFile(fileName);
          
          for (int k=0;k<fileNameList.size();k++)
          
            
          {
               
                               
                /*Read file and store in file related format*/
       
		Object obj = parser.parse(new FileReader((String) fileNameList.get(k)));

		JSONObject jsonObject = (JSONObject) obj;

                ArrayList <ArrayList> creationConstraint=(ArrayList) jsonObject.get("creationConstraint");
 
                for (int i=0;i<creationConstraint.size();i++)
                {
                    
                    ArrayList  currentSLA=creationConstraint.get(i);
                    
                    
                    /*System.out.println("---------------erreur info--------------");
                    System.out.println(currentSLA.get(2));*/
                    
                   if(currentSLA.size()!=0)
                   {   
                    if (currentSLA.get(2).equals("true"))
                        {
                    
                        
                        
                        ArrayList <ArrayList> currentRuleList=(ArrayList <ArrayList>) currentSLA.get(3);
                        for (int j=0;j<currentRuleList.size(); j++)
                            {
                         
                             policyList.add(currentRuleList.get(j));
                            }
                        } 
                   }
                }
                
       
       }
        return policyList;
        
       }
     
     
     
     
     
     
     
     
     public static LinkedList <HOST> readHostFileAndGenerateHOSTList(String fileNames) throws IOException, ParseException
     {
         ArrayList fileNameList=readContractListFromFile(fileNames);
          
         JSONParser parser = new JSONParser();
          
         LinkedList <HOST> HOSTList=new LinkedList <HOST>();
          
         
         
          for (int k=0;k<fileNameList.size();k++)
          {
              
              String fileName=(String) fileNameList.get(k);
               /*Read file and store in file related format*/
		Object obj = parser.parse(new FileReader(fileName));

		JSONObject jsonObject = (JSONObject) obj;
    
                
                String ID=(String) jsonObject.get("name");
                
                HashMap <String,Integer> serviceDescription=(HashMap) jsonObject.get("serviceDescription");
                 
                //method.printHashMap(serviceDescription);
                
                
                HashMap gauranteeTerm=(HashMap) jsonObject.get("gauranteeTerm");
                 
               // method.printHashMap(gauranteeTerm);
           
                
                //ArrayList creationConstraint=(ArrayList) jsonObject.get("creationConstraint");
                
                //method.printArrayList(creationConstraint);
		

                /*Extract information and put it into VMlist*/
                /*1. Servcie Description*/
                
                Iterator iter1 = serviceDescription.entrySet().iterator();
                    while (iter1.hasNext()) 
                            {
                                
                                
                                HashMap.Entry entry = (HashMap.Entry) iter1.next();
                                Object key = entry.getKey();
                                
                                
                                try{
                                    float value = Float.parseFloat((String) entry.getValue());
                                     if (HOSTList.size()==0)
                                        {
                                     
                                            HOST newHOST=new HOST(ID,new HashMap(),new HashMap());
                                            newHOST.addServiceDescription((String) key,value);
                                            HOSTList.add(newHOST);
                                    
                                        }
                                
                                         boolean find=false;
                                        for (int i=0;i<HOSTList.size();i++)
                                            {
                                                HOST currentHOST=HOSTList.get(i);
                                          
                                          
                                                if (currentHOST.getID().equals(ID))
                                          
                                                {
                                                    currentHOST.addServiceDescription((String) key, value);
                                         
                                                    find=true;

                                                }
                                   
                                                }
                                
                                        if (find==false)
                                        {
                                     
                                    
                                            HOST newHOST=new HOST(ID,new HashMap(),new HashMap());
                                            newHOST.addServiceDescription((String) key, value);
                                            HOSTList.add(newHOST);
                                         }
                                
                                        // is an integer!
                              } 
                                    catch (NumberFormatException e) 
                                    {
                                       // not an integer!
                                        String value=  (String) entry.getValue();
                                        if (HOSTList.size()==0)
                                    {
                                     
                                        HOST newHOST=new HOST(ID,new HashMap(),new HashMap());
                                        newHOST.addServiceDescription((String) key,value);
                                        HOSTList.add(newHOST);
                                    
                                    }
                                
                                         boolean find=false;
                                        for (int i=0;i<HOSTList.size();i++)
                                        {
                                            HOST currentHOST=HOSTList.get(i);
                                          
                                          
                                            if (currentHOST.getID().equals(ID))
                                          
                                            {
                                                currentHOST.addServiceDescription((String) key, value);
                                         
                                                find=true;
                                         
                                         
                                            }
                                   
                                        }
                                
                                         if (find==false)
                                        {
                                     
                                   
                                        HOST newHOST=new HOST(ID,new HashMap(),new HashMap());
                                        newHOST.addServiceDescription((String) key, value);
                                        HOSTList.add(newHOST);
                                        }
                                
                                 }
                                
                                
                                
                                
                                
                                
                               
                                
                                
                                
                             /*2 Gaurantee term*/
                                
                            Iterator iter2 = gauranteeTerm.entrySet().iterator();
                                 
                            while (iter2.hasNext()) 
                            {
                                HashMap.Entry entry2 = (HashMap.Entry) iter2.next();
                                Object key2 = entry2.getKey();
                                Object value2 = entry2.getValue();
                                
                                
                               
                                
                                if (HOSTList.size()==0)
                                {
                                    HOST newHOST=new HOST(ID,new HashMap(),new HashMap());
                                    newHOST.addGauranteeTerm((String) key2,(String)value2);
                                    
                                }
                                
                                boolean find2=false;
                                for (int i=0;i<HOSTList.size();i++)
                                     {
                                         HOST currentHOST=HOSTList.get(i);
                                        
                                         if (currentHOST.getID().equals(ID))
                                            
                                         currentHOST.addGauranteeTerm((String) key2, (String)value2);
                                         
                                         find2=true;
                                   
                                     }
                                
                                if (find2==false)
                                {
                                    HOST newHOST=new HOST(ID,new HashMap(),new HashMap());
                                    newHOST.addGauranteeTerm((String) key2,(String)value2);
                                    
                                }
                            }
                
                
               
                            }   
              
              
          }
          
          
          return HOSTList;
          
          
     
     }
     
     
     
     
     
   
     
     
     
     public static String [] getSplitResult (String input)
             
     {
            return input.split("_");
     }
     
     
     
     
     public static boolean isIDInVMList (String ID, LinkedList <VM> VMList)
     
     {
        if (VMList==null)
            return false;
        
        for (int i=0;i<VMList.size();i++)
        {
          if (VMList.get(i).getID().equals(ID))
              return true;
        }
        
        return false;
     }
     
     
  
     
         public static ArrayList readContractListFromFile(String fileName) throws IOException, ParseException
    {
    
            
              JSONParser parser = new JSONParser();

	

		Object obj2 = parser.parse(new FileReader("confSP\\contractFileList.json"));

		JSONObject jsonObject = (JSONObject) obj2;
    
                
                
                
                ArrayList fileList=(ArrayList) jsonObject.get("contract_list_of_SP");
                
               
                return fileList;

	
        
       
     
        
    }
         
     
         
         
         public static AbstractOrbacPolicy generateOrBACPolicyFromVMandHostPolicy(LinkedList VMPolicyList, LinkedList <VM> VMList, LinkedList HOSTPolicyList,  LinkedList <HOST> HOSTList) throws COrbacException
         {
             
             
             
         
               COrbacCore core = COrbacCore.GetTheInstance();
                    // create new policy using the Jena implementation
              AbstractOrbacPolicy p = core.CreatePolicy ("policy1", XmlOrbacPolicy.class );
              
                 // add some abstract entities to the policy
                 // add an organization
                 p.CreateOrganization ("superCloud");
                 String currentOrganization="superCloud";
                 String currentActivity="myActivity";
                 String currentContext="default_context";
                 
                 p.CreateActivityAndInsertIntoOrg (currentActivity, "superCloud");
                 
                 
                 p.Consider("superCloud","deploy",currentActivity);
                 p.AddAction("deploy");
              
                
                 
                LinkedList <String> VMIDMemoryList = new LinkedList <String>();
                LinkedList <String> HOSTIDMemoryList = new LinkedList <String>();
                
                
                
                /*Generate OrBAC policy from VM List */
                
                for (int i=0;i<VMPolicyList.size();i++)
                {
                    String currentRole="Role_Permission_"+i;
                    String currentView="View_Permission_"+i;
                    String currentRuleID="Permission_"+i;
                    
                    
                    p.CreateRoleAndInsertIntoOrg (currentRole, currentOrganization);
                    p.CreateViewAndInsertIntoOrg (currentView, currentOrganization);
                    //System.out.println("---------------currentRuleID"+currentRuleID);
                    
                    p.AbstractPermission ("superCloud",currentRole,currentActivity,currentView,currentContext,currentRuleID);
                    ArrayList currentVMRule=(ArrayList) VMPolicyList.get(i);
                    
                    
                    
                    
                    String policyType=(String) currentVMRule.get(0);
                    HashMap HOSTProperty=(HashMap) currentVMRule.get(1);
                    HashMap VMProperty=(HashMap) currentVMRule.get(2);
                    
                   if (policyType.equals ("permission"))
                   {
                        LinkedList <String> VMIDList=findRelatedVMID(VMProperty,VMList);
                    
                        LinkedList <String> HOSTIDList=findRelatedHOSTID(HOSTProperty,HOSTList);
                        
                        
                        if ((VMIDList.size()==0) || (HOSTIDList.size()==0))
                            return null;
                        
                        //p.AbstractPermission ("superCloud",currentRole,currentActivity,currentView,currentContext,currentRuleID);
                        for (int j=0;j<VMIDList.size();j++)
                        {
                            
                             //System.out.print("----------- hahaha This is J"+j);
                             //System.out.print("----------- HOSTLIST size"+HOSTList.size());
                            for (int k=0;k<HOSTIDList.size();k++)
                            {
                             
                             // System.out.print("----------- hahaha This is K"+k);
                              String currentVMID=VMIDList.get(j);
                              String currentHOSTID=HOSTIDList.get(k);
                              //System.out.println("---------------currentHOSTID"+currentHOSTID);
                              //System.out.println("\n---------------currentVMID"+currentVMID);
                              
                              if (!HOSTIDMemoryList.contains(currentHOSTID))
                              {
                                  p.AddSubject(currentHOSTID);
                                  HOSTIDMemoryList.add(currentHOSTID);
                              
                              }
                              
                              
                               if (!VMIDMemoryList.contains(currentVMID))
                              {
                                  p.AddSubject(currentVMID);
                                  VMIDMemoryList.add(currentVMID);
                              
                              }
                              
                              
                              p.Empower(currentOrganization,currentHOSTID,currentRole); 
                              p.Use(currentOrganization,currentVMID,currentView);
                            }
                        }
                        
                      
                        
                   
                 }
                }
                 /*Generate OrBAC policy from HOST List */
                
                for (int j1=0;j1<HOSTPolicyList.size();j1++)
                {
                    
                    String currentRole="Role_Prohibition_"+j1;
                    String currentView="View_Prohibition_"+j1;
                    String currentRuleID="Prohibition_"+j1;
                    
                    
                    p.CreateRoleAndInsertIntoOrg (currentRole, currentOrganization);
                    p.CreateViewAndInsertIntoOrg (currentView, currentOrganization);
                    
                    
                    p.AbstractProhibition (currentOrganization,currentRole,currentActivity,currentView,currentContext,currentRuleID);
                    ArrayList currentHOSTRule=(ArrayList) HOSTPolicyList.get(j1);
                    
                    String policyType=(String) currentHOSTRule.get(0);
                    HashMap HOSTProperty=(HashMap) currentHOSTRule.get(1);
                    HashMap VMProperty=(HashMap) currentHOSTRule.get(2);
                    
                    if (policyType.equals ("prohibition"))
                   {
                        LinkedList <String> VMIDList=findRelatedVMID(VMProperty,VMList);
                    
                        LinkedList <String> HOSTIDList=findRelatedHOSTID(HOSTProperty,HOSTList);
                        
                       // p.AbstractProhibition ("superCloud",currentRole,currentActivity,currentView,currentContext,currentRuleID);
                        for (int j2=0;j2<VMIDList.size();j2++)
                            for (int k2=0;k2<HOSTIDList.size();k2++)
                            {
                              String currentHOSTID=HOSTIDList.get(k2);
                              String currentVMID=VMIDList.get(j2);
                              
                              
                                  if (!HOSTIDMemoryList.contains(currentHOSTID))
                              {
                                  p.AddSubject(currentHOSTID);
                                  HOSTIDMemoryList.add(currentHOSTID);
                              
                              }
                              
                              
                               if (!VMIDMemoryList.contains(currentVMID))
                              {
                                  p.AddSubject(currentVMID);
                                  VMIDMemoryList.add(currentVMID);
                              
                              }
                              
                              p.Empower(currentOrganization,currentHOSTID,currentRole); 
                              p.Use(currentOrganization,currentVMID,currentView);
                            }

                    }
  
                }
  
            return p;    
         }
         
         
         
         public static LinkedList <LinkedList <LinkedList <String>>>  generateConcreteSeparationPolicyFromVMList(LinkedList <ArrayList> VMPolicyList, LinkedList <VM> VMList) throws COrbacException
         {
 
             LinkedList totalConcreteSeparationPolicyList=new LinkedList ();            
             
                
                for (int i=0;i<VMPolicyList.size();i++)
                {

                    ArrayList currentVMRule=(ArrayList) VMPolicyList.get(i);

                    String policyType=(String) currentVMRule.get(0);
                    HashMap VM1Property=(HashMap) currentVMRule.get(1);
                    HashMap VM2Property=(HashMap) currentVMRule.get(2);
                    

                   if (policyType.equals ("separation"))
                   {
                        LinkedList <String> VMID1List=findRelatedVMID(VM1Property,VMList);
                    
                        LinkedList <String> VMID2List=findRelatedVMID(VM2Property,VMList);
                        
                        
                        if ((VMID1List.size()==0) || (VMID2List.size()==0))
                            return null;
                        
                        LinkedList seperationConflictPairList=new LinkedList <LinkedList <String>> ();
                        seperationConflictPairList.add(VMID1List);
                        seperationConflictPairList.add(VMID2List);
                        
                        totalConcreteSeparationPolicyList.add(seperationConflictPairList);
            
                    }
                }
                
                 return totalConcreteSeparationPolicyList;
                
                
         }
        
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
          public static AbstractOrbacPolicy filterOrBACPolicy(AbstractOrbacPolicy p, LinkedList <VM> VMList, LinkedList <HOST> HOSTList) throws COrbacException
         {
         
             
             Set concretePermissionList=p.GetConcretePermissions();
     
                Iterator iter = concretePermissionList.iterator();
                while (iter.hasNext()) 
                
                {
         
                 CConcretePermission Cpermission=(CConcretePermission)iter.next();
                 
                
                
                    String currentHOSTID=Cpermission.GetSubject();
                    String currentVMID=Cpermission.GetObject();
                    
                    HOST currentHOST=findHOSTByIDFromHOSTList(HOSTList,currentHOSTID);
                    VM currentVM=findVMByIDFromVMList(VMList,currentVMID);
                    
                    if ( (!(currentHOSTSatisfyCurrentVMForCapacity(currentHOST,currentVM))) ||   (!(currentHOSTSatisfyCurrentVMForPerformance(currentHOST,currentVM)))     )
                            {
                                Cpermission.SetState(false);
                            }
                    
                   /*
                    System.out.println(Cpermission.toString());
                    System.out.println("Is active ?"+Cpermission.IsActive());
                   */
                
                
                
                }
     
             
             return p;
         }
         
         
         
         
         
         
        public static boolean currentHOSTSatisfyCurrentVMForCapacity(HOST currentHOST,VM currentVM)
        {
        
        
            
                /*1. Filter service requirement */
            
               HashMap <String,String> VMServiceRequirement=currentVM.getServiceRequirement();
               HashMap <String,String> HOSTServiceRequirement=currentHOST.getServiceDescription();
               
               
               
               Iterator iter = VMServiceRequirement.entrySet().iterator();
                    while (iter.hasNext()) 
                            {
                                HashMap.Entry entry = (HashMap.Entry) iter.next();
                                String key_VM_serviceRequirement = (String) entry.getKey();
                                String value_unite_VM_serviceRequirement = (String) entry.getValue();
                                
                                //System.out.println("_____________________ServiceRequirement: "+value_unite_VM_serviceRequirement);
                                
                                
                               String value_unite_HOST_serviceDescription= (String) HOSTServiceRequirement.get(key_VM_serviceRequirement);
                               if (value_unite_HOST_serviceDescription==null)
                               { 
                                    {
                                        return false;
                                    }
                               }
                               
                               else    
                                {
                                    String [] value_unite_VM_serviceRequirement_List=getSplitResult(value_unite_VM_serviceRequirement);
                                    float value_VM_serviceRequirement=Float.parseFloat(value_unite_VM_serviceRequirement_List[0]);
                                    String unite_VM_servcieRequirement=value_unite_VM_serviceRequirement_List[1];
                                    
                                    
                                    String [] value_unite_HOST_serviceDescription_List=getSplitResult(value_unite_HOST_serviceDescription);
                                    
                                    //System.out.println("_____________________ServiceDescription: "+value_unite_HOST_serviceDescription);
                                    //System.out.println("_____________________Input: "+value_unite_HOST_serviceDescription_List[0]);
                                    
                                    float value_HOST_serviceDescription=Float.parseFloat(value_unite_HOST_serviceDescription_List[0]);
                                    String unite_HOST_servcieRequirement=value_unite_HOST_serviceDescription_List[1];
                                    
                                    
                                    if ((!(value_VM_serviceRequirement<=value_HOST_serviceDescription)) || (!(unite_VM_servcieRequirement.equals(unite_HOST_servcieRequirement))))
                                    {
                                        return false;
                                    }
                                    
                                    
                                }
                                 
                                
                                
                                
                                
                            }
               
               
               
            
                return true;
        
        
        }
         
         
         
         
        public static boolean currentHOSTSatisfyCurrentVMForPerformance(HOST currentHOST,VM currentVM)
        {
        
        
            
                /*1. Filter service requirement */
            
               HashMap <String,String> VMServiceGurantee=currentVM.getGauranteeTerm();
               HashMap <String,String> HOSTServiceGurantee=currentHOST.getGauranteeTerm();
               
               
               
               Iterator iter = VMServiceGurantee.entrySet().iterator();
                    while (iter.hasNext()) 
                            {
                                HashMap.Entry entry = (HashMap.Entry) iter.next();
                                String key_VM_serviceGurantee = (String) entry.getKey();
                                String value_unite_VM_serviceGurantee = (String) entry.getValue();
                                
                                
                               String value_unite_HOST_serviceGurantee= (String) HOSTServiceGurantee.get(key_VM_serviceGurantee);
                               if (value_unite_HOST_serviceGurantee==null)
                               { 
                                    {
                                        return false;
                                    }
                               }
                               
                               else    
                                {
                                    String [] value_unite_VM_serviceGurantee_List=getSplitResult(value_unite_VM_serviceGurantee);
                                    
                                    String operator_VM_serviceGuarantee=value_unite_VM_serviceGurantee_List[0];
                                    float value_VM_serviceGurantee=Float.parseFloat(value_unite_VM_serviceGurantee_List[1]);
                                    String unite_VM_serviceGurantee=value_unite_VM_serviceGurantee_List[2];
                                    
                                    
                                    String [] value_unite_HOST_serviceGurantee_List=getSplitResult(value_unite_HOST_serviceGurantee);
                                    
                                    
                                    String operator_HOST_serviceGuarantee=value_unite_HOST_serviceGurantee_List[0];
                                    float value_HOST_serviceGurantee=Float.parseFloat(value_unite_HOST_serviceGurantee_List[1]);
                                    String unite_HOST_serviceGurantee=value_unite_HOST_serviceGurantee_List[2];
                                    
                                    
                                     if (!operator_VM_serviceGuarantee.equals(operator_HOST_serviceGuarantee))
                                     {
                                       return false;
                                     }
                                    
                                    
                                    if ((operator_VM_serviceGuarantee.equals("more")) && (operator_HOST_serviceGuarantee.equals("more")))
                                     {
                                        if ((!(value_VM_serviceGurantee<=value_HOST_serviceGurantee)) || (!(unite_VM_serviceGurantee.equals(unite_HOST_serviceGurantee))))
                                            {
                                                return false;
                                            }
                                    }
                                    
                                    
                                    
                                    if ((operator_VM_serviceGuarantee.equals("less")) && (operator_HOST_serviceGuarantee.equals("less")))
                                     {
                                        if ((!(value_VM_serviceGurantee>=value_HOST_serviceGurantee)) || (!(unite_VM_serviceGurantee.equals(unite_HOST_serviceGurantee))))
                                            {
                                                return false;
                                            }
                                    }
                                    
                                    
                                }
                                 
                                
                                
                                
                                
                            }
               
               
               
            
                return true;
        
        
        }
        
        
        
        
        
        
        
        
        
        
        
        
         
         
         
         public static LinkedList <String> findRelatedVMID (HashMap VMProperty, LinkedList <VM> VMList)
         {
             
             LinkedList <String> VMIDListFound=new LinkedList <String> ();
         
             if(VMProperty.containsKey("ID"))   
                 VMIDListFound.add((String) VMProperty.get("ID"));
             
             else
             {
                  Iterator iter = VMProperty.entrySet().iterator();
                    while (iter.hasNext()) 
                            {
                                HashMap.Entry entry = (HashMap.Entry) iter.next();
                                String keyInRule = (String) entry.getKey();
                                String valueInRule = (String) entry.getValue();
                                
                      
                                     for (int i=0;i<VMList.size();i++)
                                     {
                                        VM currentVM=VMList.get(i);
                                        String currentVMID=currentVM.getID();
                                        HashMap currentServiceDescription=currentVM.getServiceDescription();
                                        
                                        if (currentServiceDescription.containsKey(keyInRule))
                                        {
                                             if (currentServiceDescription.get(keyInRule).equals(valueInRule))
                                             {
                                                addUnRepeatedValueToList (currentVMID,VMIDListFound);
                                             }
                                        }
                                        
                                      }
                                
                                
                            }
             }
         
             
             return VMIDListFound;
             
         }
         
         
         public static LinkedList <String> findRelatedHOSTID (HashMap HOSTProperty, LinkedList <HOST> HOSTList)
         {
             
             LinkedList <String> HOSTIDListFound=new LinkedList <String> ();
         
             if(HOSTProperty.containsKey("ID"))   
                 HOSTIDListFound.add((String) HOSTProperty.get("ID"));
             
             else
             {
                  Iterator iter = HOSTProperty.entrySet().iterator();
                    while (iter.hasNext()) 
                            {
                                HashMap.Entry entry = (HashMap.Entry) iter.next();
                                String keyInRule = (String) entry.getKey();
                                String valueInRule = (String) entry.getValue();
                                
                      
                                     for (int i=0;i<HOSTList.size();i++)
                                     {
                                        HOST currentHOST=HOSTList.get(i);
                                        String currentHOSTID=currentHOST.getID();
                                        HashMap currentServiceDescription=currentHOST.getServiceDescription();
                                        
                                        if (currentServiceDescription.containsKey(keyInRule))
                                        {
                                             if (currentServiceDescription.get(keyInRule).equals(valueInRule))
                                             {
                                                addUnRepeatedValueToList (currentHOSTID,HOSTIDListFound);
                                             }
                                        }
                                        
                                      }
                                
                                
                            }
             }
         
             
             return HOSTIDListFound;
             
         }
         
         
         
         
         public static VM findVMByIDFromVMList (LinkedList <VM> VMList, String VMID)
         {
         
             for (int i=0;i<VMList.size();i++)
             {
                VM currentVM=VMList.get(i);
                if (currentVM.getID().equals(VMID))
                    return  currentVM;
             }
             
             
             return null;
             
         }
         
         
         
            public static HOST findHOSTByIDFromHOSTList (LinkedList <HOST> HOSTList, String HOSTID)
         {
         
             for (int i=0;i<HOSTList.size();i++)
             {
                HOST currentHOST=HOSTList.get(i);
                if (currentHOST.getID().equals(HOSTID))
                    return  currentHOST;
             }
             
             
             return null;
             
         }
         
         
         
         public static LinkedList <String> addUnRepeatedValueToList (String valueToAdd, LinkedList <String> listToAdd )
         {
             if (!listToAdd.contains(valueToAdd))
                 listToAdd.add(valueToAdd);
             
             return listToAdd;
     
         }
         
         
         
         public static VM getVMByID (String VMID, LinkedList <VM> VMList )
         {
              for (VM currentVM:VMList)
              {
                  if(currentVM.getID().equals(VMID))
                      return currentVM;
              }
            
              return null;
         
         }
         
         
         
            public static HOST getHOSTByID (String HOSTID, LinkedList <HOST> HOSTList )
         {
              for (HOST currentHOST:HOSTList)
              {
                  if(currentHOST.getID().equals(HOSTID))
                      return currentHOST;
              }
            
              return null;
         
         }
         
         
         
         public static boolean twoLinkedListShareSameEntity (LinkedList <String> list1, LinkedList <String> list2)
         {
             for (String entity1: list1)
             {
                 for (String entity2: list2)
                 {
                    if (entity1.equals(entity2))
                        return true;
                 }
             }
             
             return false;
                     
         }
         
         
    }
    
    
    
    
    
    
    

