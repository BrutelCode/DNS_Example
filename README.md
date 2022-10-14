## Simple Example of DNS Server and Client for educational purpose.

The DNS Server has the "hosts.txt" file that will have a 
hostname on each line with its corresponding IP. When the
Server starts, the lines of the "hosts.txt" file will be
read and its recordings will be registered in the Hashtable.
After if receives a request from a client and there is no name
in the Hashtable then it will use the methods of InetAddress
in order to resolve the name to ip, which it will register in
the Hashtable and in addition will add it (append) at the end
of the "hosts.txt" file.

### **1st Option** Run it from **Command Line**:

**1) Compile & Run Server**

-> javac Dns_Server.java

-> java Dns_Server


**2) Compile & Run Client (<ins>in different command line window</ins>)**

-> javac Dns_Client.java

-> java Dns_Client

**After type a domain name to resolve it to IP Address**
   
### **2nd Option** Run it from **IDE!**
