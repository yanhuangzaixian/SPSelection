/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package componentTest;

import cloudResource.HOST;
import util.method;
import cloudResource.VM;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import org.json.simple.parser.ParseException;

/**
 *
 * @author RDSG6431
 */
public class testForSP1 {
    
    
    
    
    public static void main(String[] args) throws IOException, ParseException
    {
        
        
         System.out.println("**********************begin VM information****************\n");
         
          /*Test1  read Client File which contains only one VM*/
        
         LinkedList <HOST> HOSTList=method.readHostFileAndGenerateHOSTList("confSP\\contractFileList.json");
         
         
         
         
         for (int i=0;i<HOSTList.size();i++)
         {
             
             HOSTList.get(i).printInfo();
         
         }
         
         
         System.out.println("**********************begin policyList information****************\n");
         
    LinkedList <ArrayList> policyList=method.readSPFileAndAbstractAbstractPolicy("confSP\\contractFileList.json");
    
    for (int i=0;i<policyList.size();i++)
         {
             
             System.out.println("*********policy List********\n");
             method.printArrayList(policyList.get(i));
         
         }
    
    }
}
