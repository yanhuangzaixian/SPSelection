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

   
   
   
   public static HashMap <String,LinkedList <String>> generateFinalDeploySolution
        (AbstractOrbacPolicy p, LinkedList <VM> VMList, LinkedList <HOST> HOSTList, LinkedList <LinkedList <LinkedList <String>>> concreteSeparationPolicy ) throws COrbacException
   {
     
       
    HashMap <String,LinkedList <String> > FinalDeploySolution=new HashMap <String,LinkedList <String>>();   
       
     Set concretePermissionList=p.GetConcretePermissions();
     
     Iterator iter = concretePermissionList.iterator();
     
      while (iter.hasNext()) {
         
         CConcretePermission Cpermission=(CConcretePermission)iter.next();
         
         if (Cpermission.IsActive())
            {
                String currentVMID=Cpermission.GetObject();
                VM currentVM=getVMByID(currentVMID,VMList);
                
                
                LinkedList <String> HOSTIDListForCurrentVMID=getAllPermitHostIDInPolicy (currentVMID,p);
                
                for (String currentHOSTSolutionID : HOSTIDListForCurrentVMID )  
                    
                        {
                            
                            
                              HOST currentHOST=getHOSTByID(currentHOSTSolutionID,HOSTList);
                              if (  !VMIDInSeperationPolicy (currentVMID,concreteSeparationPolicy)   || currentHOSTSatisfyCurrentVMForCapacity(currentHOST,currentVM)  )
                              {
                                 
                              
                              }
                
                        }
                
                
                
                
                
             }
         
     }
       
       return FinalDeploySolution;
       
   
   }
   
   
   
   
   
}