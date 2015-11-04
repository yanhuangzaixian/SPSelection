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
public class VM {

   
    String ID;
    HashMap serviceRequirement;
    HashMap serviceDescription ;
    HashMap requirement ;
    
    
    public void printInfo()
    
    {
        System.out.println("-----------------VM information-----------\n");
        System.out.println("ID: "+ID);
        System.out.println("serviceRequirement:");
        method.printHashMap(serviceRequirement);
        System.out.println("serviceDescription:");
        method.printHashMap(serviceDescription);
        System.out.println("requirement:");
        method.printHashMap(requirement);
    }
    
    
    
    

    public VM(String ID, HashMap serviceRequirement,HashMap serviceDescription,HashMap requirement) {
        this.ID = ID;
        this.serviceRequirement=serviceRequirement;
        this.serviceDescription = serviceDescription;
        this.requirement=requirement;
        
    }
    

    
    
    
    
    public void setID(String ID) {
        this.ID = ID;
    }

    public void setServiceDescription(HashMap serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    

    public String getID() {
        return ID;
    }

    public HashMap getServiceDescription() {
        return serviceDescription;
    }

   
    
     public HashMap getServiceRequirement() {
        return serviceRequirement;
    }

    public void setServiceRequirement(HashMap serviceRequirement) {
        this.serviceRequirement = serviceRequirement;
    }
    
    
    
    
    public void addServiceDescription(String key, Object value)
    {
        this.serviceDescription.put(key,value);
    
    }
    
    
    
     public void addRequirement(String key, String value)
    {
        this.requirement.put(key,value);
    
    }
    
     
     public void addServiceRequirement(String key, Object value)
    {
        this.serviceRequirement.put(key,value);
    
    }
    
    
    
}
