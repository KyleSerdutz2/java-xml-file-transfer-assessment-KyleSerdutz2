package test.server;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class ClientHandler implements Runnable{
	private Socket client;
	
	ClientHandler(Socket client){
		this.client = client;
	}
	
	static FileOutputStream sFileOutputStream = null; //OutputStream which can write files
	
	static BufferedOutputStream sBufferedOutputStream = null;
	
	static InputStream sInputStream = null; //Can take from Client
	static BufferedInputStream sBufferedInputStream = null; //Faster version of DataInputStream
	
	static StringReader sStringReader = null;
	
	final String pathSep = File.separator;
	final String pathToWDir = System.getProperty("user.dir");
    final String pathServerFileFolder = "server files";
	
    final static int KILO_BYTE = 1000;
    
	@Override
	public void run() {
		//*ClientHandler Thread*
		try {
            //*Receive XMLString*
    		System.out.println("Writing Bytes...");
			sInputStream = client.getInputStream();
			sBufferedOutputStream = new BufferedOutputStream(client.getOutputStream());//
			sBufferedInputStream = new BufferedInputStream(client.getInputStream());
			byte[] bufferXMLBytes = new byte[5000*KILO_BYTE];
			int counter = 0;
			int x = 0;
			while((x = sBufferedInputStream.read(bufferXMLBytes)) != -1) {
	    		System.out.println(bufferXMLBytes[counter]);
	    		sBufferedOutputStream.write(bufferXMLBytes, 0, x);
				counter++;
			};
    		System.out.println("Bytes received...");
    		
    		System.out.println(bufferXMLBytes);
    		
    		//---
    		
    		//Split into many Strings
//    		String[] XMLStrings = XMLString.split("\n");
    		
			//*UnMarshall*
			JAXBContext jaxbContext = JAXBContext.newInstance(FilePOJO.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			
//			FilePOJO[] filePOJOs = FilePOJO[XMLStrings.length];
//			for(int i = 0; i < XMLStrings.length; i++)
//				filePOJOs = (FilePOJO[]) unmarshaller.unmarshal(sStringReader);
			
			sStringReader = new StringReader(bufferXMLBytes.toString());
			FilePOJO filePOJO = (FilePOJO) unmarshaller.unmarshal(sStringReader);
			
			System.out.println(filePOJO.getUsername());
			System.out.println(filePOJO.getDate());
			System.out.println(filePOJO.getFilename());
//			System.out.println(filePOJO.getContents());
			
			//*Make Directory*
			String pathFinalFilePath =
					pathToWDir+pathSep
					+pathServerFileFolder+pathSep
					+filePOJO.getUsername()+pathSep
					+filePOJO.getDate();
			new File(pathFinalFilePath).mkdirs();
			
            //*Write to File*
			
			//Decoding Base64
			//TODO
			
			//Final Steps
			File newFile = new File(pathFinalFilePath+pathSep+"x.PDF");
			sFileOutputStream = new FileOutputStream(newFile);
			sFileOutputStream.write(bufferXMLBytes, 0, bufferXMLBytes.length);
			
			System.out.println(bufferXMLBytes);
			//
    		System.out.println("File written...");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				sStringReader.close();
				sFileOutputStream.close();
				sInputStream.close();
				sBufferedInputStream.close();
				System.out.println("File can be opened...");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
