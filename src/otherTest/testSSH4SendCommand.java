/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otherTest;

/**
 *
 * @author RDSG6431
 */
import com.jcraft.jsch.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import org.apache.commons.io.IOUtils;

public class testSSH4SendCommand{
    
    
    
    
  public static void main(String[] arg) throws IOException, JSchException{
    
      JSch jsch=new JSch();  

      
      
      
      /*
      if(arg.length>0){
        host=arg[0];
      }
      else{
        host=JOptionPane.showInputDialog("Enter username@hostname",
                                         System.getProperty("user.name")+
                                         "@localhost"); 
      }*/
      
      
      
      String user="yli03";
      String host="127.0.0.1";
      int port=2222;
      String passWord="VJWWFDRA";

      Session session=jsch.getSession(user, host, port);
      
     
      // username and password will be given via UserInfo interface.
      UserInfo ui=new MyUserInfo(passWord);
      session.setUserInfo(ui);
      
      session.connect();

      String command1="cd devstack";
      String command2="source openrc admin admin";
      String command3="nova boot --flavor 1  --image 443880f8-84bc-4077-86ca-d8dd8ae69289  outVM1";
      
      String command4="cd devstack && source openrc admin admin && nova boot --flavor 1  --image 443880f8-84bc-4077-86ca-d8dd8ae69289  outVM1 && nova volume-create 1 --display_name outVol1 ";
              

      Channel channel=session.openChannel("exec");
      ((ChannelExec)channel).setCommand(command4);
      //((ChannelExec)channel).setCommand(command2);
      //((ChannelExec)channel).setCommand(command3);
      
       //session.connect(30000); 
      
       //Channel channel=session.openChannel("shell");

      // Enable agent-forwarding.
      //((ChannelShell)channel).setAgentForwarding(true);
     /*
     InputStream in1 = IOUtils.toInputStream(command1, "UTF-8");  
     InputStream in2 = IOUtils.toInputStream(command2, "UTF-8");  
     InputStream in3 = IOUtils.toInputStream(command3, "UTF-8");  
       
      channel.setInputStream(in1);
      channel.setInputStream(in2);
      channel.setInputStream(in3);
      */
      
      
      
      
      
      

      // X Forwarding
      // channel.setXForwarding(true);

      //channel.setInputStream(System.in);
      
      
      channel.setInputStream(null);

      //channel.setOutputStream(System.out);

      //FileOutputStream fos=new FileOutputStream("/tmp/stderr");
      //((ChannelExec)channel).setErrStream(fos);
      ((ChannelExec)channel).setErrStream(System.err);

      InputStream in=channel.getInputStream();

      channel.connect();

      
      /*
      byte[] tmp=new byte[1024];
      while(true){
        while(in.available()>0){
          int i=in.read(tmp, 0, 1024);
          if(i<0)break;
          System.out.print(new String(tmp, 0, i));
        }
       */
      
      
        StringWriter writer = new StringWriter();
        IOUtils.copy(in, writer, "UTF-8");
        String theString = writer.toString();
        
        System.out.println("hahaha\n"+theString);
        
        
        
        /*
        if(channel.isClosed()){
          if(in.available()>0) continue; 
          System.out.println("exit-status: "+channel.getExitStatus());
          break;
        }
        try{Thread.sleep(1000);}catch(Exception ee){}*/
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
