/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package orbacapi.test1;

import java.util.HashMap;

/**
 *
 * @author RDSG6431
 */
public class VM {
    
    String ID;
    HashMap serviceDescription ;
    HashMap requirement ;
    HashMap permitForHost;
    
    
    public void printInfo()
    
    {
        System.out.println("-----------------VM information-----------\n");
        System.out.println("ID: "+ID);
        System.out.println("serviceDescription:");
        method.printHashMap(serviceDescription);
        System.out.println("requirement:");
        method.printHashMap(requirement);
    }
    
    
    
    

    public VM(String ID, HashMap serviceDescription, HashMap requirement, HashMap permitForHost) {
        this.ID = ID;
        this.serviceDescription = serviceDescription;
        this.requirement = requirement;
        this.permitForHost = permitForHost;
    }
    

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setServiceDescription(HashMap serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public void setRequirement(HashMap requirement) {
        this.requirement = requirement;
    }

    public void setPermitForHost(HashMap permitForHost) {
        this.permitForHost = permitForHost;
    }

    public String getID() {
        return ID;
    }

    public HashMap getServiceDescription() {
        return serviceDescription;
    }

    public HashMap getRequirement() {
        return requirement;
    }

    public HashMap getPermitForHost() {
        return permitForHost;
    }
    
    
    
    public void addServiceDescription(String key, int value)
    {
        this.serviceDescription.put(key,value);
    
    }
    
    
    
     public void addRequirement(String key, String value)
    {
        this.requirement.put(key,value);
    
    }
    
    
    
    
}
