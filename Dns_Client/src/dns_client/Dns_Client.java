/**
 *
 * @author Christos H.
 */
import java.net.*;
import java.util.Scanner;

public class Dns_Client {
    public static void main(String[] args) throws Exception
        {
            String HostName;
            String IPhost = "127.0.0.1";
            int portNumber = 8000;
            Scanner in = new Scanner(System.in);
     
            
            System.out.println("Default Port: 8000");
			System.out.println("");
            System.out.print("Type a HostName:");
            HostName = in.nextLine();
			 if(HostName.isEmpty()) 
				HostName = "localhost";
            
        try{
            DatagramSocket ClientSoc = new DatagramSocket(); // Create a Socket
            ClientSoc.setSoTimeout(10000); // 10000ms max for data delivery
            
            InetAddress IPAddress = InetAddress.getByName(IPhost); // IP communication
            
            byte[] sendData = new byte[256];// Data that we will send
            byte[] recvData = new byte[256];// Data that we will receive
            
            
            sendData = HostName.getBytes(); // The HostName that we will send to the server
            
            DatagramPacket sendPacket = new DatagramPacket // Create the Packet
            (sendData, sendData.length, IPAddress, portNumber);
            
            ClientSoc.send(sendPacket); // Send the packet
            
            DatagramPacket recvPacket = new DatagramPacket (recvData,recvData.length);// Receive the data from the Server
            ClientSoc.receive(recvPacket);// Accept the package
			
            String My_IP = new String(recvPacket.getData(),0,recvPacket.getLength()); // Convert IP to String
            
            
            if(My_IP.equals("-1")) // If unknown IP Address
            {
			System.out.println("");
            System.out.println("* *********************** *");
            System.out.println("* The Dns Server response *");
            System.out.println("* *********************** *");
            System.out.println("\tNOT FOUND!");
            }
            else
            {
	    //Print the HostName and the IP Address
	    System.out.println("");
	    System.out.println("* *********************** *");
            System.out.println("* The Dns Server response *");
            System.out.println("* *********************** *");
            System.out.println("Hostname   : "+ HostName);
            System.out.println("IP Address : "+ My_IP);
            }
            ClientSoc.close(); // Close the connection to the Server
        }
        
        catch(SocketTimeoutException x) // In case the data do not arrive in a predetermined time
        {
            System.out.println("Timeout!");
        }
        catch(SocketException s) // Problem with connection
        {
            System.out.println("Can not create a socket!");
        }
    }
}
