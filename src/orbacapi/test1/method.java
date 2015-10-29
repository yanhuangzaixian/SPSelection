/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package orbacapi.test1;

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
     
     
     
     public static void  printFromSet (Set <CConcretePermission> setRule)
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
     
     
     public static LinkedList <VM> readClientFileAndGenerateVMList(String fileName)
     {
          JSONParser parser = new JSONParser();
          LinkedList <VM> VMList=new LinkedList <VM>();
          
	try {
                
                /*Read file and store in file related format*/
		Object obj = parser.parse(new FileReader(fileName));

		JSONObject jsonObject = (JSONObject) obj;
    
                
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
                                int value = Integer.parseInt((String)entry.getValue());
                                
                                
                                String [] keys=getIDandInfo ((String)key);
                                String ID=keys[0];
                                String term=keys[1];
                                
                                if (VMList.size()==0)
                                {
                                     
                                    VM newVM=new VM(ID,new HashMap(),new HashMap(),new HashMap());
                                    newVM.addServiceDescription(term, (int)value);
                                    VMList.add(newVM);
                                    
                                }
                                
                                boolean find=false;
                                for (int i=0;i<VMList.size();i++)
                                     {
                                         VM currentVM=VMList.get(i);
                                          
                                          
                                         if (currentVM.getID().equals(ID))
                                          
                                         {
                                         currentVM.addServiceDescription(term, (int)value);
                                         
                                         find=true;
                                         
                                         
                                         }
                                   
                                     }
                                
                                if (find==false)
                                {
                                     
                                    
                                    VM newVM=new VM(ID,new HashMap(),new HashMap(),new HashMap());
                                    newVM.addServiceDescription(term, (int)value);
                                    VMList.add(newVM);
                                }
                                
                                
                                
                                
                             /*2 Gaurantee term*/
                                
                            Iterator iter2 = gauranteeTerm.entrySet().iterator();
                                 
                            while (iter2.hasNext()) 
                            {
                                HashMap.Entry entry2 = (HashMap.Entry) iter2.next();
                                Object key2 = entry2.getKey();
                                Object value2 = entry2.getValue();
                                
                                
                                String [] keys2=getIDandInfo ((String)key2);
                                String ID2=keys2[0];
                                String term2=keys2[1];
                                
                                if (VMList.size()==0)
                                {
                                    VM newVM=new VM(ID2,new HashMap(),new HashMap(),new HashMap());
                                    newVM.addRequirement(term2,(String)value2);
                                    
                                }
                                
                                boolean find2=false;
                                for (int i=0;i<VMList.size();i++)
                                     {
                                         VM currentVM=VMList.get(i);
                                        
                                         if (currentVM.getID().equals(ID2))
                                            
                                         currentVM.addRequirement(term2, (String)value2);
                                         
                                         find2=true;
                                   
                                     }
                                
                                if (find2==false)
                                {
                                    VM newVM=new VM(ID2,new HashMap(),new HashMap(),new HashMap());
                                    newVM.addRequirement(term2,(String)value2);
                                    
                                }
                            }
                
                
               
                            }   
                
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
        
       
         return VMList;
     }
     
     
     
       public static LinkedList <ArrayList> readClientFileAndAbstractAbstractPolicy(String fileName)
       
       {
          JSONParser parser = new JSONParser();
          LinkedList <ArrayList> policyList=new LinkedList <ArrayList > ();
          
	try {
                
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
                }
        catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
       
        
        return policyList;
        
       }
     
     
     
     
     
     
     /*
       
     public static LinkedList <HOST> readHostFileAndGenerateHOSTList()
     {
     
     
     }
     */
     
     
     
     
   
     
     
     
     public static String [] getIDandInfo (String input)
             
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
     
     
    }
    
    
    
    
    
    
    

