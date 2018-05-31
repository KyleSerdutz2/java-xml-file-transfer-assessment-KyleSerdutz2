package test.client;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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
public class Client {
	public static String marshalFile(String userName, String fileName, String date, byte[] fileBuffer) throws JAXBException{
		/*FilePOJO
		Username
		Filename
		Date
		(File) Contents
		*/
		
		/*===============================*/
		boolean bDebugConsolePrint = false;
		/*===============================*/
		
		FilePOJO filePOJO = new FilePOJO(userName, fileName, date, fileBuffer);
		String XMLString = "";
		
		//*Marshaling*
		JAXBContext context = JAXBContext.newInstance(FilePOJO.class);
		Marshaller marshaller = context.createMarshaller();
		
		if(bDebugConsolePrint) {
			//*Print to Console*
			
			//For the ease of Formatting
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			marshaller.marshal(filePOJO, System.out);
			/*NOTE:JAXB orders alphabetically IE
			 * 1st contents
			 * 2nd date
			 * 3rd filename
			 * 4th username
			 */
		}else {
			//*Return XMLString*
			try ( StringWriter sw = new StringWriter(); ){
				marshaller.marshal(filePOJO, sw);
			    XMLString = sw.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return XMLString;
	}
	
	//==========
	
	static Socket client = null;
	
	static FileInputStream sFileInputStream; //InputStream which can detect files
	
	static DataOutputStream sDataOutputStream; //Can print to console or send to Server
	static DataInputStream sDataInputStream; //Can take in anything incoming from Server
	
	static BufferedInputStream sBufferedInputStream; //Faster version of DataInputStream
	
	public static void main(String[] args) {
		System.out.println("Client Running...");

	    final String pathSep = File.separator;
		final String pathToWDir = System.getProperty("user.dir");
	    final String pathToClientFiles = "client files";
	    final String pathServerFileFolder = "server files";

	    final String pFileName = "a.PDF";
	    final String[] pFileNames = {
	    		"a.PDF",
	    		"a2.PDF",
	    		"a3.PDF",
	    };
	    String pathToFile = pathToWDir+pathSep+pathToClientFiles+pathSep+pFileName;
	    String[] pathToFiles = {
	    		pathToWDir+pathSep+pathToClientFiles+pathSep+pFileNames[0],
	    		pathToWDir+pathSep+pathToClientFiles+pathSep+pFileNames[1],
	    		pathToWDir+pathSep+pathToClientFiles+pathSep+pFileNames[2],
	    };
	    
	    String cHostName = "127.0.0.1";
	    String cPortNumber = "10777";
	    
		
        try (Scanner input = new Scanner(System.in)){
            /*ServerIP and PortNumber*/
            boolean bServerPortDone = false;
            while(!bServerPortDone) {
                System.out.println("\nCurrent ServerIP is: \t"+ cHostName);
                System.out.println("Current PortNumber is: \t"+ cPortNumber);
               
                System.out.println("\nWould you like to make any alterations?");
                System.out.print("[y/n]: ");
           
                char yn = input.nextLine().charAt(0);
                if(yn=='y') {
                    System.out.print("\nServerIP: ");
                    cHostName = input.nextLine(); //Input is a "\n"
                    System.out.print("PortNumber: ");
                    cPortNumber = input.nextLine();
                }else if(yn=='n') {
                    bServerPortDone = true;
                }else {
                    System.out.println("\nInvalid Input");
                }
            }
        }
        
        
        try { //We can guarantee safety later after fully functioning
        	System.out.println("In..");
	        //*Search for Files:Send to XML*
	        String XMLString = "";
	        for(int i = 0; i < pFileNames.length; i++) {
	        	//---
	            //*Files*
	            File file = new File(pathToFile);

		        //TODO search the directory and detect files on its own
		        //TODO search the directory and detect files on its own
		        //TODO search the directory and detect files on its own
	            //your directory
//            	String[]fileNameFL  = pFileNames[i].split(".");
//	            File dir = new File(pathToFiles[i]);
//	            File[] matchingFiles = dir.listFiles(new FilenameFilter() {
//	                public boolean accept(File dir, String name) {
//	                    return name.startsWith(fileNameFL[0]) && name.endsWith(fileNameFL[1]);
//	                }
//	            });
//	            File[] files = new File[3];
//	        	for(int i = 0; i < files.length; i++) {
//	        		files[i] = new File(pathToFile);
//	        	}
		        
		        //---
		        //Date
		        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		        LocalDateTime now = LocalDateTime.now();
		        String[] YearMonthDay = dtf.format(now).split(" "); 

		        //Reading-In/Formatting File
		        byte[] fileBuffer = new byte[(int) file.length()];
		        
		        sFileInputStream = new FileInputStream(pathToFiles[i]);
		        
		        sBufferedInputStream = new BufferedInputStream(sFileInputStream);
		        sBufferedInputStream.read(fileBuffer, 0, fileBuffer.length);

		        System.out.println(i+1);
		        //XML Marshaling
	        	if(i==0)
	        		XMLString += marshalFile("Kyle", pFileNames[i], YearMonthDay[0], fileBuffer);
	        	//else
	        		//XMLString += "\n"+marshalFile("Kyle", pFileNames[i], YearMonthDay[0], fileBuffer);
	        }

	        System.out.println("---");
	        
	        //---

        	//*Client*
        	client = new Socket(cHostName, Integer.parseInt(cPortNumber));
            sDataOutputStream = new DataOutputStream(client.getOutputStream()); //sDataOutputStream = new DataOutputStream(System.out);
            sDataInputStream = new DataInputStream(client.getInputStream());
            
            //--
	        
        	//*File Sending*
	        System.out.println("\n\tAttempting Sending...");
			//Sending File (To previously specified DataOutputStream)
	        sDataOutputStream.write(XMLString.getBytes(), 0, XMLString.getBytes().length);
	        sDataOutputStream.flush();
	        
	        System.out.println("\n\nSending Completed...");
        } catch (Exception ex){
        	ex.printStackTrace();
        } finally {
        	try {
	            client.close();
	        	sFileInputStream.close();
	        	sDataOutputStream.close();
	        	sBufferedInputStream.close();
        	} catch (Exception e) {
            	e.printStackTrace();
        	}
        }
	}
}
