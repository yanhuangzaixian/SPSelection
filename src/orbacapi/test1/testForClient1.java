/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package orbacapi.test1;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author RDSG6431
 */
public class testForClient1 {
    
    
    
    
    public static void main(String[] args)
    {
         System.out.println("**********************begin VM information****************\n");
         
          /*Test1  read Client File which contains only one VM*/
         LinkedList <VM> VMList=method.readClientFileAndGenerateVMList("confClient\\test3.json");
         
         
         
         
         for (int i=0;i<VMList.size();i++)
         {
             
             VMList.get(i).printInfo();
         
         }
         
         
         System.out.println("**********************begin policyList information****************\n");
         
    LinkedList <ArrayList> policyList=method.readClientFileAndAbstractAbstractPolicy("confClient\\test3.json");
    
    for (int i=0;i<policyList.size();i++)
         {
             
             System.out.println("*********policy List********\n");
             method.printArrayList(policyList.get(i));
         
         }
    
    }
}
