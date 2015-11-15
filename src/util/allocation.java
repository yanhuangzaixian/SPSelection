/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import cloudResource.HOST;
import cloudResource.VM;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import orbac.AbstractOrbacPolicy;
import orbac.exception.COrbacException;
import orbac.securityRules.CConcretePermission;
import static util.method.currentHOSTSatisfyCurrentVMForCapacity;
import static util.method.getHOSTByID;
import static util.method.getVMByID;
import static util.method.printInfo;
import static util.method.printLinkedList;
import static util.method.twoLinkedListShareSameEntity;
import com.jcraft.jsch.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import org.apache.commons.io.IOUtils;
import util.allocation;
import util.method;
import static util.method.printInfo;

/**
 *
 * @author RDSG6431
 */
public class allocation {
    
    
    public static LinkedList <String> getAllPermitHostIDInPolicy (String VM_ID, AbstractOrbacPolicy p) throws COrbacException
    {
    
        LinkedList <String> permitHostIDList=new LinkedList <String> ();
       
        Set concretePermissionList=p.GetConcretePermissions();
     
        Iterator iter3 = concretePermissionList.iterator();
            while (iter3.hasNext())
            {
         
                CConcretePermission Cpermission=(CConcretePermission)iter3.next();
         
                String currentHOSTID=Cpermission.GetSubject();
                String currentVMID=Cpermission.GetObject();
                
                if ( (Cpermission.IsActive()) && ( currentVMID.equals(VM_ID)))
                    {
                        permitHostIDList.add(currentHOSTID);
                    }
            }

        return permitHostIDList; 

    }
    
    
   public static LinkedList <HOST> rankingHostlistFromCheapToHigh (LinkedList <String> HOSTIDList, LinkedList <HOST> HOSTList)

   {
       LinkedList <HOST> rankedList=new LinkedList <HOST> ();
       
       for (String currentHOSTID : HOSTIDList)
            {
                for (HOST currentHOST: HOSTList)
                {
                
                    if(currentHOST.getID().equals(currentHOSTID))
                    
                    {
                        rankedList.add(currentHOST);
                    }
                
                }
            }
       
       
       
       int size=rankedList.size();
       for (int i = 0; i < size - 1; i++) {   
        for (int j = i + 1; j < size; j++) {   
            if (rankedList.get(i).getPrice() > rankedList.get(j).getPrice()) { // 交换两数的位置   
                Collections.swap(rankedList, i, j); 
            }   
        }   
    }   
       
       return rankedList;
       
       
   }

   public static LinkedList <String> rankingHostlistFromCheapToHighAndReturnHOSTIDList (LinkedList <String> HOSTIDList, LinkedList <HOST> HOSTList)

   {
       LinkedList <HOST> rankedList=new LinkedList <HOST> ();
       
       for (String currentHOSTID : HOSTIDList)
            {
                for (HOST currentHOST: HOSTList)
                {
                
                    if(currentHOST.getID().equals(currentHOSTID))
                    
                    {
                        rankedList.add(currentHOST);
                    }
                
                }
            }
       
       
       
       int size=rankedList.size();
       for (int i = 0; i < size - 1; i++) {   
        for (int j = i + 1; j < size; j++) {   
            if (rankedList.get(i).getPrice() > rankedList.get(j).getPrice()) {    
                Collections.swap(rankedList, i, j); 
            }   
        }   
    }   
       
       
       LinkedList <String> returnHOSTIDList=new LinkedList <String> ();
       
       for (HOST currentHOST:rankedList)
       {
         returnHOSTIDList.add(currentHOST.getID());
       }
       
       
       return returnHOSTIDList;
       
       
   }
   
   
   public static HashMap <String,LinkedList <String>> generateFinalDeploySolution
        (AbstractOrbacPolicy p, LinkedList <VM> VMList, LinkedList <HOST> HOSTList, LinkedList <LinkedList <LinkedList <String>>> concreteSeparationPolicy ) throws COrbacException
   {
     
       
    HashMap <String,LinkedList <String> > finalDeploySolution=new HashMap <String,LinkedList <String>>();   
     LinkedList <String>  VMAlreadyAllocateList=new LinkedList <String> ();   
    
    
     Set concretePermissionList=p.GetConcretePermissions();
     
     Iterator iter = concretePermissionList.iterator();
     
      while (iter.hasNext()) {
         
         CConcretePermission Cpermission=(CConcretePermission)iter.next();
         String currentVMID=Cpermission.GetObject();
         
         if (Cpermission.IsActive() && !(VMAlreadyAllocateList.contains(currentVMID)))
            {
                
                VM currentVM=getVMByID(currentVMID,VMList);
                
                
                LinkedList <String> HOSTIDListForCurrentVMID=getAllPermitHostIDInPolicy (currentVMID,p);
                
                
               /* printInfo("HOSTIDListForCurrentVMID");
                printLinkedList(HOSTIDListForCurrentVMID);*/
                
                HOSTIDListForCurrentVMID=rankingHostlistFromCheapToHighAndReturnHOSTIDList(HOSTIDListForCurrentVMID,HOSTList);
                
                
                
                for (String currentHOSTSolutionID : HOSTIDListForCurrentVMID )  
                    
                        {
                            
                            
                              HOST currentHOST=getHOSTByID(currentHOSTSolutionID,HOSTList);
                              if ( !VMIDInSeparationPolicy (finalDeploySolution,currentHOSTSolutionID,currentVMID,concreteSeparationPolicy) && currentHOSTSatisfyCurrentVMForCapacity(currentHOST,currentVM) && !(VMAlreadyAllocateList.contains(currentVMID)) )
                                {
                                   
                                     if (finalDeploySolution.containsKey(currentHOSTSolutionID))
                                     {
                                         
                                        LinkedList <String> currentVMSolution=finalDeploySolution.get(currentHOSTSolutionID);
                                        currentVMSolution.add(currentVMID);        
                                        finalDeploySolution.replace(currentVMID, currentVMSolution);
                                     }
                                     
                                     
                                     else
                                     {
                                        LinkedList <String> currentVMSolution=new LinkedList <String> ();
                                        currentVMSolution.add(currentVMID);
                                        finalDeploySolution.put(currentHOSTSolutionID, currentVMSolution);
                                        
                                     }
                                     
                                     
                                     HOSTList=recalculateSpaceForHOSTList(HOSTList,currentHOSTSolutionID,currentVM);
                                     VMAlreadyAllocateList.add(currentVMID);
                                  }
                              
                                 
                        }
                
                
                
                
                
             }
         
     }
       
      /*
      printInfo("seperation policy");
      printLinkedList(concreteSeparationPolicy);
      
      
      printInfo("Host list after allocation");
      for (HOST eachHOST: HOSTList)
      {
         eachHOST.printInfo();
      }
      */
      
       return finalDeploySolution;
       
       
       
       
   
   }
        
        
        
   public static boolean VMIDInSeparationPolicy (HashMap <String,LinkedList <String> > finalDeploySolution,String currentHOSTSolutionID, String currentVMID, LinkedList <LinkedList <LinkedList <String>>> concreteSeparationPolicy)   
   {
            
               LinkedList <String> VMDeployListForOneHost=finalDeploySolution.get(currentHOSTSolutionID);
               
               if (VMDeployListForOneHost!=null)
               {   
               
                for (LinkedList item:concreteSeparationPolicy)
                    {
                     LinkedList <String> conflictSet1=(LinkedList <String>) item.get(0);
                     LinkedList <String> conflictSet2=(LinkedList <String>) item.get(1);
                     
                     
                     /*
                     printInfo("print Conflict set");
                     
                     printLinkedList(conflictSet1);
                     printInfo("\n");
                     printLinkedList(conflictSet2);
                     printInfo("\n");
                    printLinkedList(VMDeployListForOneHost);
                     */
                     
                     if (twoLinkedListShareSameEntity(conflictSet1,VMDeployListForOneHost) && conflictSet2.contains(currentVMID))
                         return true;
                     
                     if (twoLinkedListShareSameEntity(conflictSet2,VMDeployListForOneHost) && conflictSet1.contains(currentVMID))
                         return true;
                    }
               
                    return false;
               
               }
               
               else
               {
                    return false;  
               }
   
   }
        
        
   
   
   public static LinkedList <HOST> recalculateSpaceForHOSTList(LinkedList <HOST> HOSTList,String currentHOSTSolutionID,VM currentVM)
   {
      HOST currentHOST=getHOSTByID (currentHOSTSolutionID,HOSTList);
      
      float currentHOSTVolume=currentHOST.getVolume();
      String currentHOStVolumeUnit=currentHOST.getVolumeUnit();
              
      float currentVMVolume=currentVM.getVolume();
      float newVolume=currentHOSTVolume-currentVMVolume;
      
      String newItem=Float.toString(newVolume)+"_"+currentHOStVolumeUnit;
      
      
      HOSTList.get(HOSTList.indexOf(currentHOST)).getServiceDescription().replace("volume",newItem);
      
      return HOSTList;
      
      
   }
   
   
   
   public static String getVolumeIdFromString(String returnInfo)
    {
        
       int begin=returnInfo.lastIndexOf("encrypted");
       int end=returnInfo.lastIndexOf("links");
       
       begin=begin+312;
       end=end-232;
       
       //System.out.println(begin);
       //System.out.println(end);
       
       String ID=returnInfo.substring(begin, end);
       
       return ID;
   
    }
   
   
   public static String createVMAndVolumeOnSP(String VMID,int volume, String user, String passWord, String host, int port) throws JSchException, IOException
   {
     JSch jsch=new JSch();  

      
  

      Session session=jsch.getSession(user, host, port);
      
     
      // username and password will be given via UserInfo interface.
      UserInfo ui=new MyUserInfo(passWord);
      session.setUserInfo(ui);
      
      session.connect();

      
      String command0="pwd";
      String command1="cd devstack";
      String command2="source openrc admin admin";
      String command3="nova boot --flavor 1  --image 443880f8-84bc-4077-86ca-d8dd8ae69289"+" "+VMID;
      String command4="nova volume-create"+" "+volume+" "+"--display_name outVol1";
     
      //String command5="cd devstack && source openrc admin admin && nova boot --flavor 1  --image 443880f8-84bc-4077-86ca-d8dd8ae69289  outVM1 && nova volume-create 1 --display_name outVol1 ";
            
      String command5=command1+" && "+command2+" && "+command3+" && "+command4;

      Channel channel=session.openChannel("exec");
      
      
      
      ((ChannelExec)channel).setCommand(command5);
      
      
      
      
      
      
     
      
      channel.setInputStream(null);


      ((ChannelExec)channel).setErrStream(System.err);

      InputStream in=channel.getInputStream();

      channel.connect();

      
     
      
      
        StringWriter writer = new StringWriter();
        IOUtils.copy(in, writer, "UTF-8");
        String returnInfo = writer.toString();
        
        System.out.println(returnInfo);
        
          ((ChannelExec)channel).setErrStream(System.err);
          
        printInfo("flag0");
         
        channel.disconnect();
        session.disconnect();
        
        return returnInfo;
   
   }
   
   
   
   
    public static void attachVolumeToVM(String VMID,String volumeID, String user,String passWord, String host, int port) throws JSchException, IOException
   {
     JSch jsch=new JSch();  

      
  

      Session session=jsch.getSession(user, host, port);
      
     
      // username and password will be given via UserInfo interface.
      UserInfo ui=new MyUserInfo(passWord);
      session.setUserInfo(ui);
      
      session.connect();

      
      String command0="pwd";
      String command1="cd devstack";
      String command2="source openrc admin admin";
     
      String command6="nova volume-attach"+" "+VMID+" "+volumeID+" /dev/vdb";
        
        
     
       //String command7="pwd";
      String command7=command1+" && "+command2+" && "+command6;

       printInfo(command7);
      
      Channel channel=session.openChannel("exec");
      
      
      
      ((ChannelExec)channel).setCommand(command7);
      
      
      
      
      
      
     
      
      channel.setInputStream(null);


      ((ChannelExec)channel).setErrStream(System.err);

      InputStream in=channel.getInputStream();

      channel.connect();

      
     
      
      
        StringWriter writer = new StringWriter();
        IOUtils.copy(in, writer, "UTF-8");
        String returnInfo = writer.toString();
        
        System.out.println(returnInfo);
        
          ((ChannelExec)channel).setErrStream(System.err);
          
        printInfo("flag0");
         
        channel.disconnect();
        session.disconnect();
        
        
   
   }
   
   
   
   
   
   public static class MyUserInfo implements UserInfo, UIKeyboardInteractive{
    
 
      
      
      
    String passwd;
    JTextField passwordField=(JTextField)new JPasswordField(20);  
      
      
         
    MyUserInfo(String password)
    {
      this.passwd=password;
    }
    
    
    public String getPassword(){ return passwd; }
    
    
    public boolean promptYesNo(String str){
        
      /*  
      Object[] options={ "yes", "no" };
      int foo=JOptionPane.showOptionDialog(null, 
             str,
             "Warning", 
             JOptionPane.DEFAULT_OPTION, 
             JOptionPane.WARNING_MESSAGE,
             null, options, options[0]);
       return foo==0;
       */
        
        
       return true;
       
    }
  
    

    public String getPassphrase(){ return null; }
    
    public boolean promptPassphrase(String message){ return true; }
    
    public boolean promptPassword(String message){
        /*
      Object[] ob={passwordField}; 
      int result=
        JOptionPane.showConfirmDialog(null, ob, message,
                                      JOptionPane.OK_CANCEL_OPTION);
      if(result==JOptionPane.OK_OPTION){
        passwd=passwordField.getText();
        return true;
      }
      else{ 
        return false; 
      }*/
        
        
        return true;
    }
    
    
    public void showMessage(String message){
      JOptionPane.showMessageDialog(null, message);
    }
    final GridBagConstraints gbc = 
      new GridBagConstraints(0,0,1,1,1,1,
                             GridBagConstraints.NORTHWEST,
                             GridBagConstraints.NONE,
                             new Insets(0,0,0,0),0,0);
    private Container panel;
    public String[] promptKeyboardInteractive(String destination,
                                              String name,
                                              String instruction,
                                              String[] prompt,
                                              boolean[] echo){
      panel = new JPanel();
      panel.setLayout(new GridBagLayout());

      gbc.weightx = 1.0;
      gbc.gridwidth = GridBagConstraints.REMAINDER;
      gbc.gridx = 0;
      panel.add(new JLabel(instruction), gbc);
      gbc.gridy++;

      gbc.gridwidth = GridBagConstraints.RELATIVE;

      JTextField[] texts=new JTextField[prompt.length];
      for(int i=0; i<prompt.length; i++){
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.weightx = 1;
        panel.add(new JLabel(prompt[i]),gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;
        if(echo[i]){
          texts[i]=new JTextField(20);
        }
        else{
          texts[i]=new JPasswordField(20);
        }
        panel.add(texts[i], gbc);
        gbc.gridy++;
      }

      if(JOptionPane.showConfirmDialog(null, panel, 
                                       destination+": "+name,
                                       JOptionPane.OK_CANCEL_OPTION,
                                       JOptionPane.QUESTION_MESSAGE)
         ==JOptionPane.OK_OPTION){
        String[] response=new String[prompt.length];
        for(int i=0; i<prompt.length; i++){
          response[i]=texts[i].getText();
        }
	return response;
      }
      else{
        return null;  // cancel
      }
    }
  }
   
   
}