package test.server;

import javax.xml.bind.annotation.XmlRootElement;

/*------------------------------------------------------------------------------*/
//Server needs FilePOJO to unmarshal the XML									//
//We could have used an XSD but since we have the file better to copy it over	//
/*------------------------------------------------------------------------------*/

public class FilePOJO {
	FilePOJO(){}
	FilePOJO(String username, String filename, String date, byte[]contents){
		this.username = username;
		this.filename = filename;
		this.date = date;
		this.contents = contents;
	}
	
	private String username;
	private String filename;
	private String date; //yyyy-MM-dd
	private byte[] contents;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public byte[] getContents() {
		return contents;
	}
	public void setContents(byte[] contents) {
		this.contents = contents;
	}
}
