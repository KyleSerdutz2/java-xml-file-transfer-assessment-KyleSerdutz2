package test.client;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FilePOJO {
	private String name;
	private String date; //yyyy-MM-dd
	private String filename;
	private Byte[] contents;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Byte[] getContents() {
		return contents;
	}
	public void setContents(Byte[] contents) {
		this.contents = contents;
	}
}
