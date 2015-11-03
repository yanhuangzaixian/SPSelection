/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package otherTest;

import java.util.HashMap;
import util.method;

/**
 *
 * @author RDSG6431
 */
public class testHashMap {
    
    
     public static void main(String[] args) 
             
    {
            HashMap map=new HashMap ();
            map.put("Key1","Value1");
            map.put("Key1","Value2");
            map.put("Key1","Value1");
            method.printHashMap(map);
            
    }
    
    
    
}
