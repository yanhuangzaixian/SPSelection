/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package otherTest;


import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import net.schmizz.sshj.*;
import org.bouncycastle.*;

/** This examples demonstrates how a remote command can be executed. */
public class testSSH1 {

    public static void main(String[] args)
            throws IOException {
        final SSHClient ssh = new SSHClient();
        ssh.loadKnownHosts();

        ssh.connect("192.168.27.134");
        try {
            //ssh.authPublickey("VJWWFDRA");
            ssh.authPassword("yli03", "VJWWFDRA");
            
            final Session session = ssh.startSession();
            try {
                final Command cmd = session.exec("ping -c 1 google.com");
                System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
                cmd.join(5, TimeUnit.SECONDS);
                System.out.println("\n** exit status: " + cmd.getExitStatus());
            } finally {
                session.close();
            }
        } finally {
            ssh.disconnect();
        }
    }
}