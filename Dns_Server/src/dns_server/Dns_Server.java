/**
 *
 * @author Christos H.
 */
import java.net.*;
import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

public class Dns_Server {
    public static void main(String[] args) throws Exception
    {
        try{
            int PortNumber = 8000;
            String Line;
            Hashtable<String,String> Hash = new Hashtable<String,String>();
                
            System.out.println("Default port is 8000");
           
            // Open a file for reading
            BufferedReader br = new BufferedReader(new FileReader("hosts.txt"));
            
            // Reading the file          
                while((Line = br.readLine()) != null)
                {
                    String[] split = Line.split(" ", 2); // Separate 2 words through the space(" ")
                   /* System.out.println(split[0]);
                      System.out.println(split[1]);
				   */
                    
                    Hash.put(split[0], split[1]); // Put the items in the HashTable
                  
                }
                br.close(); // Close the file
               
              
            // Run the Server
            System.out.println("The DNS Server is running....");
            DatagramSocket ServerSoc = new DatagramSocket(PortNumber); // Create a Socket
            byte[] recvData = new byte[256]; // Data we will receive from client
            byte[] sendData = new byte[256]; // Data we will send to client
            
            
            // Run an infinite loop
            while(true)
            {   
               DatagramPacket recPacket = new DatagramPacket(recvData,recvData.length);// Data object we receive
               ServerSoc.receive(recPacket); // We accept the package from the client
                
               String DnsCon = new String(recPacket.getData(),0,recPacket.getLength()); // Create a new String from the client message
                  
               String IP_Add_1;
               InetAddress IP_Add_2;
               
              if(Hash.containsKey(DnsCon)) // If there is a HostName in the HashTable
              {
                 IP_Add_1 = Hash.get(DnsCon); // We get the IP from the HashTable
              }
              
              else
              {  
                try{
                    IP_Add_2 = InetAddress.getByName(DnsCon); // Get the IP address
                    IP_Add_1 = IP_Add_2.getHostAddress(); // Assign the IP to String
                    Hash.put(DnsCon, IP_Add_1); // Put it on the HashTable
                
                    FileWriter fw = new FileWriter("hosts.txt",true); // File for registration
                    fw.write(System.getProperty("line.separator")); // Change the line
                    fw.write(DnsCon+ " "+IP_Add_1);	// Write the HostName & IP Address
                    fw.close(); // Close the file
                   }
                catch(UnknownHostException x) // Unknown IP Address
                    {
                    //System.out.println("Error -1,IP does not exist!");
                    IP_Add_1 = "-1"; // Send -1 to the client
                    }        
              }
            
            
              
             
               InetAddress IPAddress = recPacket.getAddress(); // Get the client IP address through a package
               int port = recPacket.getPort(); // Get the port
               
               sendData = IP_Add_1.getBytes(); // Assign the IP we will send
               
               DatagramPacket sendPacket = new DatagramPacket // Create the package for the client
               (sendData, sendData.length, IPAddress, port);
               
               ServerSoc.send(sendPacket); // Send the package to the client
                           
            }
           }
 
        catch(FileNotFoundException x) // The file not found
        {
            System.out.println("File Error!");
        }
        catch(IOException x) // Problem with the file
        {
            System.out.println("Problem with file!");
        }
    }           
}
