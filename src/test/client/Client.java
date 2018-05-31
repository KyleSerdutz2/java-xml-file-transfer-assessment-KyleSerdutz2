package test.client;
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
The application will first create a directory for the username
if it does not already exist. The application will then create a subdirectory
for the date if it does not already exist.

Finally, the application will recreate the file in that directory
by decoding the Base64 encoded contents
and writing the decoded contents to the file.

*/
public class Client {

	public static void main(String[] args) {
		
	}

}
