package test.server;

import java.io.*;
import java.net.*;
import java.util.*;

/*
The student will create an application that inspects a directory,
reads in the contents of a file,
uses JAXB to create an XML document for that file which contains
	a username(the name of the student),
	the date in yyyy-MM-dd format,
	the filename,
	and the contents of the file itself (stored as a byte array).
The application will then open a Socket,
write that XML document to that Socket,
and then close the Socket.

The application will repeat this process
for each file in the designated directory.

//^Client
//vServer

The student will then create an application
that hosts a ServerSocket that listens for incoming connections.
When a connection is received, it will spawn a client handler thread
to interact with the client and then return to listening for new connections.
The client handler thread will read an XML document from the connection.
The XML document will contain
	a username,
	a date,
	a filename,
	and the contents of the file.
The application will first create a
	directory for the username
if it does not already exist. The application will then
	create a subdirectory for the date
if it does not already exist.

Finally, the application will recreate the file in that directory
by decoding the Base64 encoded contents
and writing the decoded contents to the file.

*/
public class Server {

	static FileInputStream sFileInputStream; //InputStream which can detect files
	
	static DataOutputStream sDataOutputStream; //Can print to console or send to Server
	static DataInputStream sDataInputStream; //Can take in anything incoming from Server
	
	static BufferedInputStream sBufferedInputStream; //Faster version of DataInputStream
	
	public static void main(String[] args) {
		System.out.println("Server Running...");

	    final String pathSep = File.separator;
		final String pathToWDir = System.getProperty("user.dir");
	    final String pathServerFileFolder = "server files";

	    String cPortNumber = "10777";	    
	    
	    ServerSocket server = null;
        try (Scanner input = new Scanner(System.in)){

            //---
        	
            /*ServerIP and PortNumber*/
            boolean bServerPortDone = false;
            while(!bServerPortDone) {
                System.out.println("Current PortNumber is: \t"+ cPortNumber);
               
                System.out.println("\nWould you like to make any alterations?");
                System.out.print("[y/n]: ");
           
                char yn = input.nextLine().charAt(0);
                if(yn=='y') {
                    System.out.print("PortNumber: ");
                    cPortNumber = input.nextLine();
                }else if(yn=='n') {
                    bServerPortDone = true;
                }else {
                    System.out.println("\nInvalid Input");
                }
            }
            
            //---
            
            //*Server*
    	    server = new ServerSocket(Integer.parseInt(cPortNumber));

            //---
    	    
            //*Client Threads*
            Socket client = null;
            Thread newThread = null;
            
            //ArrayList in case we need to rejoin
            ArrayList<Thread> clientHandlers = new ArrayList<Thread>();
            
            //Program ends based on an Exception Thrown
        	System.out.println("\n\tAccepting Clients...\n");
            while(true) {
            	client = server.accept();
            	//If(Program didn't throw TimeOutException yet)
            		System.out.println("Client Joined...");
	            	newThread = new Thread(new ClientHandler(client));
	            	newThread.start();
	            	clientHandlers.add(newThread);
            }

            //---
            
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
            try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
}
