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
public class HOST {
    
    String ID;
    HashMap serviceDescription;
    HashMap denyForVM;

    public HOST(String ID, HashMap serviceDescription, HashMap denyForVM) {
        this.ID = ID;
        this.serviceDescription = serviceDescription;
        this.denyForVM = denyForVM;
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

    public HashMap getDenyForVM() {
        return denyForVM;
    }

    public void setDenyForVM(HashMap denyForVM) {
        this.denyForVM = denyForVM;
    }
    
    
    
    
}
