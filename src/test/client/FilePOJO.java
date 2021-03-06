package test.client;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
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
	
	public String getName() {
		return username;
	}
	public void setName(String username) {
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
