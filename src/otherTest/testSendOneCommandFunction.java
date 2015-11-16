/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package otherTest;

import com.jcraft.jsch.JSchException;
import java.io.IOException;
import util.allocation;

/**
 *
 * @author RDSG6431
 */
public class testSendOneCommandFunction {
    
    
    public static void main(String[] arg) throws IOException, JSchException{
    
        String returnInfo=allocation.createVMAndVolumeOnSP("outVM", 1, "volumeName", "yli03", "VJWWFDRA", "127.0.0.1", 2222);
        
        String volumeId=allocation.getVolumeIdFromString(returnInfo);
        
        allocation.attachVolumeToVM("outVM", volumeId, "yli03", "VJWWFDRA", "127.0.0.1", 2222);
        
        
    }
    
    
}
