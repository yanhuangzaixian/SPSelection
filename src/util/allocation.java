/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import cloudResource.HOST;
import cloudResource.VM;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import orbac.AbstractOrbacPolicy;
import orbac.exception.COrbacException;
import orbac.securityRules.CConcretePermission;
import static util.method.currentHOSTSatisfyCurrentVMForCapacity;
import static util.method.getHOSTByID;
import static util.method.getVMByID;
import static util.method.twoLinkedListShareSameEntity;

/**
 *
 * @author RDSG6431
 */
public class allocation {
    
    
    public static LinkedList <String> getAllPermitHostIDInPolicy (String VM_ID, AbstractOrbacPolicy p) throws COrbacException
    {
    
        LinkedList <String> permitHostIDList=new LinkedList <String> ();
       
        Set concretePermissionList=p.GetConcretePermissions();
     
        Iterator iter3 = concretePermissionList.iterator();
            while (iter3.hasNext())
            {
         
                CConcretePermission Cpermission=(CConcretePermission)iter3.next();
         
                String currentHOSTID=Cpermission.GetSubject();
                String currentVMID=Cpermission.GetObject();
                
                if ( (Cpermission.IsActive()) && ( currentVMID.equals(VM_ID)))
                    {
                        permitHostIDList.add(currentHOSTID);
                    }
            }

        return permitHostIDList; 

    }
    
    
   public static LinkedList <HOST> rankingHostlistFromCheapToHigh (LinkedList <String> HOSTIDList, LinkedList <HOST> HOSTList)

   {
       LinkedList <HOST> rankedList=new LinkedList <HOST> ();
       
       for (String currentHOSTID : HOSTIDList)
            {
                for (HOST currentHOST: HOSTList)
                {
                
                    if(currentHOST.getID().equals(currentHOSTID))
                    
                    {
                        rankedList.add(currentHOST);
                    }
                
                }
            }
       
       
       
       int size=rankedList.size();
       for (int i = 0; i < size - 1; i++) {   
        for (int j = i + 1; j < size; j++) {   
            if (rankedList.get(i).getPrice() > rankedList.get(j).getPrice()) { // 交换两数的位置   
                Collections.swap(rankedList, i, j); 
            }   
        }   
    }   
       
       return rankedList;
       
       
   }

   public static LinkedList <String> rankingHostlistFromCheapToHighAndReturnHOSTIDList (LinkedList <String> HOSTIDList, LinkedList <HOST> HOSTList)

   {
       LinkedList <HOST> rankedList=new LinkedList <HOST> ();
       
       for (String currentHOSTID : HOSTIDList)
            {
                for (HOST currentHOST: HOSTList)
                {
                
                    if(currentHOST.getID().equals(currentHOSTID))
                    
                    {
                        rankedList.add(currentHOST);
                    }
                
                }
            }
       
       
       
       int size=rankedList.size();
       for (int i = 0; i < size - 1; i++) {   
        for (int j = i + 1; j < size; j++) {   
            if (rankedList.get(i).getPrice() > rankedList.get(j).getPrice()) {    
                Collections.swap(rankedList, i, j); 
            }   
        }   
    }   
       
       
       LinkedList <String> returnHOSTIDList=new LinkedList <String> ();
       
       for (HOST currentHOST:rankedList)
       {
         returnHOSTIDList.add(currentHOST.getID());
       }
       
       
       return returnHOSTIDList;
       
       
   }
   
   
   public static HashMap <String,LinkedList <String>> generateFinalDeploySolution
        (AbstractOrbacPolicy p, LinkedList <VM> VMList, LinkedList <HOST> HOSTList, LinkedList <LinkedList <LinkedList <String>>> concreteSeparationPolicy ) throws COrbacException
   {
     
       
    HashMap <String,LinkedList <String> > finalDeploySolution=new HashMap <String,LinkedList <String>>();   
    LinkedList <String> VMAlreadyAllocateList=new LinkedList <String> ();   
    
    
     Set concretePermissionList=p.GetConcretePermissions();
     
     Iterator iter = concretePermissionList.iterator();
     
      while (iter.hasNext()) {
         
         CConcretePermission Cpermission=(CConcretePermission)iter.next();
         String currentVMID=Cpermission.GetObject();
         
         if (Cpermission.IsActive() && !(VMAlreadyAllocateList.contains(currentVMID)))
            {
                
                VM currentVM=getVMByID(currentVMID,VMList);
                
                
                LinkedList <String> HOSTIDListForCurrentVMID=getAllPermitHostIDInPolicy (currentVMID,p);
                HOSTIDListForCurrentVMID=rankingHostlistFromCheapToHighAndReturnHOSTIDList(HOSTIDListForCurrentVMID,HOSTList);
                
                
                
                for (String currentHOSTSolutionID : HOSTIDListForCurrentVMID )  
                    
                        {
                            
                            
                              HOST currentHOST=getHOSTByID(currentHOSTSolutionID,HOSTList);
                              if ( !VMIDInSeparationPolicy (finalDeploySolution,currentHOSTSolutionID,currentVMID,concreteSeparationPolicy) && currentHOSTSatisfyCurrentVMForCapacity(currentHOST,currentVM)  )
                                {
                                   
                                     if (finalDeploySolution.containsKey(currentHOSTSolutionID))
                                     {
                                         
                                        LinkedList <String> currentVMSolution=finalDeploySolution.get(currentHOSTSolutionID);
                                        currentVMSolution.add(currentVMID);        
                                        finalDeploySolution.replace(currentVMID, currentVMSolution);
                                     }
                                     
                                     
                                     else
                                     {
                                        LinkedList <String> currentVMSolution=new LinkedList <String> ();
                                        currentVMSolution.add(currentVMID);
                                        finalDeploySolution.put(currentHOSTSolutionID, currentVMSolution);
                                        
                                     }
                                     
                                     
                                     HOSTList=recalculateSpaceForHOSTList(HOSTList,currentHOSTSolutionID,currentVM);
                                     
                                  }
                              
                
                        }
                
                
                VMAlreadyAllocateList.add(currentVMID);
                
                
             }
         
     }
       
       return finalDeploySolution;
       
   
   }
        
        
        
   public static boolean VMIDInSeparationPolicy (HashMap <String,LinkedList <String> > finalDeploySolution,String currentHOSTSolutionID, String currentVMID, LinkedList <LinkedList <LinkedList <String>>> concreteSeparationPolicy)   
   {
            
               LinkedList <String> VMDeployListForOneHost=finalDeploySolution.get("currentHOSTSolutionID");
               
               for (LinkedList item:concreteSeparationPolicy)
               {
                     LinkedList <String> conflictSet1=(LinkedList <String>) item.get(0);
                     LinkedList <String> conflictSet2=(LinkedList <String>) item.get(1);
                     
                     if (twoLinkedListShareSameEntity(conflictSet1,VMDeployListForOneHost) && conflictSet2.contains(currentVMID))
                         return true;
                     
                     if (twoLinkedListShareSameEntity(conflictSet2,VMDeployListForOneHost) && conflictSet1.contains(currentVMID))
                         return true;
               }
               
               
             return false;  
   
   }
        
        
   
   
   public static LinkedList <HOST> recalculateSpaceForHOSTList(LinkedList <HOST> HOSTList,String currentHOSTSolutionID,VM currentVM)
   {
      HOST currentHOST=getHOSTByID (currentHOSTSolutionID,HOSTList);
      
      float currentHOSTVolume=currentHOST.getVolume();
      String currentHOStVolumeUnit=currentHOST.getVolumeUnit();
              
      float currentVMVolume=currentVM.getVolume();
      float newVolume=currentHOSTVolume-currentVMVolume;
      
      String newItem=Float.toString(newVolume)+currentHOStVolumeUnit;
      
      
      HOSTList.get(HOSTList.indexOf(currentHOST)).getServiceDescription().replace("volume",newItem);
      
      return HOSTList;
      
      
   }
   
   
}