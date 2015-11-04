/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloudResource;

import java.util.HashMap;
import util.method;

/**
 *
 * @author RDSG6431
 */
public class HOST {
    
    String ID;
    HashMap serviceDescription;
    HashMap gauranteeTerm;
   

     public void printInfo()
    
    {
        System.out.println("-----------------HOST information-----------\n");
        System.out.println("ID: "+ID);
        System.out.println("serviceDescription:");
        method.printHashMap(serviceDescription);
        System.out.println("gauranteeTerm:");
        method.printHashMap(gauranteeTerm);
    }
    
    
    public HOST(String ID, HashMap serviceDescription, HashMap gauranteeTerm) {
        this.ID = ID;
        this.serviceDescription = serviceDescription;
        this.gauranteeTerm=gauranteeTerm;
        
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public HashMap getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(HashMap serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    
    public HashMap getGauranteeTerm(){
       return gauranteeTerm;
    }
    
    public void setGauranteeTerm (HashMap gauranteeTerm)
    {
      this.gauranteeTerm=gauranteeTerm;
    }
    
    
    public void addServiceDescription(String key, Object value)
    {
        this.serviceDescription.put(key,value);
    
    }
    
    
    
     public void addGauranteeTerm(String key, String value)
    {
        this.gauranteeTerm.put(key,value);
    
    }
    
    
    
}
