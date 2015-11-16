/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloudResource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import util.method;
/**
 *
 * @author RDSG6431
 */
public class VM {

   
    String ID;
    HashMap serviceRequirement;
    HashMap serviceDescription ;
    HashMap gauranteeTerm ;
    //LinkedList <String> HOSTMapList;
    
    public void printInfo()
    
    {
        System.out.println("-----------------VM information-----------\n");
        System.out.println("ID: "+ID);
        System.out.println("serviceRequirement:");
        method.printHashMap(serviceRequirement);
        System.out.println("serviceDescription:");
        method.printHashMap(serviceDescription);
        System.out.println("gauranteeTerm:");
        method.printHashMap(gauranteeTerm);
    }
    
 
    
    

    public VM(String ID, HashMap serviceRequirement,HashMap serviceDescription,HashMap gauranteeTerm) {
        this.ID = ID;
        this.serviceRequirement=serviceRequirement;
        this.serviceDescription = serviceDescription;
        this.gauranteeTerm=gauranteeTerm;
        //this.HOSTMapList=new LinkedList <String> ();
        
    }

    
    /*
    public void addHOSTMapList_NoRepeated (String HOSTAdded)
    {
       if (!(HOSTMapList.contains (HOSTAdded)))
       {
         this.HOSTMapList.add (HOSTAdded);
       }
    }
    
    
    public void deleteHOSTMapList(String HOSTDeleted)
    {
         HOSTMapList.remove(HOSTDeleted);
    }
    
    
    
    public void setHOSTMapList(LinkedList<String> HOSTMapList) {
        this.HOSTMapList = HOSTMapList;
    }

    public LinkedList<String> getHOSTMapList() {
        return HOSTMapList;
    }
    */
    
    
    public void setGauranteeTerm(HashMap gauranteeTerm) {
        this.gauranteeTerm = gauranteeTerm;
    }

    public HashMap getGauranteeTerm() {
        return gauranteeTerm;
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
    
    
    
     public void addGauranteeTerm(String key, String value)
    {
        this.gauranteeTerm.put(key,value);
    
    }
    
     
     public void addServiceRequirement(String key, Object value)
    {
        this.serviceRequirement.put(key,value);
    
    }
    
    
      public float getVolume ()
             
     {
        HashMap serviceServiceRequirement=getServiceRequirement();
        
        
        
        Iterator iter = serviceServiceRequirement.entrySet().iterator();
                    while (iter.hasNext()) 
                            {
                                HashMap.Entry entry = (HashMap.Entry) iter.next();
                                String volumeItem = (String) entry.getValue();
                                
                                
                                String volume_unit[]=volumeItem.split("_");
        
                                float volume=Float.parseFloat(volume_unit[0]);
                                return volume;
                                
                            }
        
        
        
         return 0;
     }
     
      
       public int getVolumeAndTransferToInt ()
             
     {
        HashMap serviceServiceRequirement=getServiceRequirement();
        
        
        
        Iterator iter = serviceServiceRequirement.entrySet().iterator();
                    while (iter.hasNext()) 
                            {
                                HashMap.Entry entry = (HashMap.Entry) iter.next();
                                String volumeItem = (String) entry.getValue();
                                
                                
                                String volume_unit[]=volumeItem.split("_");
        
                                float volume=Float.parseFloat(volume_unit[0]);
                                
                                int volumeTransforme=Math.round(volume);
                                
                                return volumeTransforme;
                                
                            }
        
        
        
         return 0;
     }
     
    
}
