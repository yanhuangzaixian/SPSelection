/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package otherTest;

import java.io.IOException;
import util.method;

/**
 *
 * @author RDSG6431
 */
public class testGetIDFromFile {
    
    
    public static void main(String[] arg) throws IOException
    {
    
       String returnInfo=method.fromFileToString("returnInfo.txt");
       
       int begin=returnInfo.lastIndexOf("encrypted");
       int end=returnInfo.lastIndexOf("links");
       
       begin=begin+313;
       end=end-233;
       
       System.out.println(begin);
       System.out.println(end);
       
       String ID=returnInfo.substring(begin, end);
       System.out.println(ID);
       
       
    
    
    }
    
    
}
