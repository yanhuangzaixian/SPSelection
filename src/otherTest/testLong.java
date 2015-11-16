/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package otherTest;

import cloudResource.VM;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.toIntExact;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import org.json.simple.parser.ParseException;
import static util.allocation.deployVMandAttachVolume;
import static util.allocation.getHostConnectionInfoFromFile;
import util.method;

/**
 *
 * @author RDSG6431
 */
public class testLong {
    
    
    public static void main(String[] arg) throws IOException, ParseException{
    
                         HashMap  hostConfInfo =getHostConnectionInfoFromFile("confConnection"+File.separator+"portInfo.json");
      
              
       
                         //int currentPort=toIntExact((long)hostConfInfo.get("HOST1"));
                         int currentPort=(int)hostConfInfo.get("HOST1");
                              
                              
                           
              
                    
    }
    
}
