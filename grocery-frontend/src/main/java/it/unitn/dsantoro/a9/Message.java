package it.unitn.dsantoro.a9;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6210633770635712660L;
	
	private String text;
	private String severity;
	private Date date;
	public static String INFO = "info";
	public static String WARNING = "warning";
	public static String ERROR = "error";
	
	
	public Message(String severity, String text){
		this.severity = severity;
		this.text = text;
		this.date = new Date();
	}
	
	@Override
	public String toString() {
		return String.format("<div class=\"%s\"><i>%s</i>: %s</div>", severity, date, text);
	}
}
