/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package evaluation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import orbac.exception.COrbacException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import util.method;



/**
 *
 * @author RDSG6431
 */
public class evaluation2OrBACGeneration {
    
    
    public static void main(String[] args) throws IOException, ParseException, COrbacException
    {
    
       
        
        
        for (int i=0;i<5;i++)
       {
          
                    String info=null;
                    
                    LinkedList <Long> policyGenerationTime=new LinkedList <Long>();
                     LinkedList <Long> allocationTime=new LinkedList <Long>();
                    

                  for (int totalClientNumber=10;totalClientNumber<61;totalClientNumber=totalClientNumber+10)
                  {

                      for (int totalHOSTNumber=10;totalHOSTNumber<61;totalHOSTNumber=totalHOSTNumber+10)

                          {
                              
                            int count=0;

                            
                            util.test.VMAndHostGeneration(totalClientNumber, totalHOSTNumber);
                            long returnValue[]=util.test.timeMeasure(totalClientNumber, totalHOSTNumber);

                            info=info+"-----------------------------------\n";
                            info=info+"VM: "+  totalClientNumber+"\n";
                            info=info+"HOST: "+totalHOSTNumber+"\n";
                            info=info+"Policy generation time: "+returnValue[0]+"\n";
                            info=info+"allocation  time: "+returnValue[1]+"\n";

                          
                            if ((totalClientNumber==10) && (totalHOSTNumber==10))
                            {
                               policyGenerationTime.add(returnValue[0]);
                               allocationTime.add(returnValue[1]);
                            }
                            
                            
                             else
                                {
                                       policyGenerationTime.set(count,policyGenerationTime.get(count)+returnValue[0]);
                                      allocationTime.set(count,allocationTime.get(count)+returnValue[1]);
                                 }
                          count++;

                          }


                       method.fromStringToFile(info, "evaluation"+File.separator+"test2and3.txt");
                
                       
                       
                      

                  }
                  
                  
                  
                  
                  
                            
                    
                  
                  
                  
                  
                  
                  
          
        
       }
        
        
        
        
        
        
        
        
    }
    
}
