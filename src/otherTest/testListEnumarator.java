/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package otherTest;

import java.util.LinkedList;
import util.method;

/**
 *
 * @author RDSG6431
 */
public class testListEnumarator {
    
    
    public static void main(String[] arg)
            
    {
          LinkedList <Long> list1=new LinkedList <Long>();
          long value1=200;
          long value2=400;
          list1.add(value1);
          list1.add(value2);
          
          for (int i=0;i<list1.size();i++)
          {
              list1.set(i,list1.get(i)/2);
          }
          
          method.printLinkedList(list1);
    }
    
}
