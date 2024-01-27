package com.silvestri;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class MessageList {
	
	public void fetch_message_list() {
		final HttpClient httpClient = HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_2)
				.build();
		
		HttpRequest request = HttpRequest.newBuilder()
	            .GET()
	            .uri(URI.create("https://api" + Starter.getServer_number() + ".esv2.com/v2/Api/Messages?apiKey=" + Starter.getApi_key() + "&startDate=" + Starter.getStartDate() + "&" + "endDate=" + Starter.getEndDate()))
	            .setHeader("User-Agent", "Java 16 HttpClient Bot")
	            .build();
		
		try {
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			System.out.println("\n Return code: " + response.statusCode());
			System.out.println("\n" + response.body()); 
			
			Path xml_file = Paths.get("C:\\ApiResults\\messages_list.xml");
			Files.deleteIfExists(xml_file);
			
			FileWriter file = new FileWriter("C:\\ApiResults\\messages_list.xml",true);
			BufferedWriter output = new BufferedWriter(file);
			PrintWriter out = new PrintWriter(output);
			out.println("<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>");
			out.println(response.body());
			out.println();
			out.close(); 
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void generate_txt_from_xml() {
		
		List<String> ids = new ArrayList<String>();
        List<String> message_names = new ArrayList<String>();
        List<String> message_types = new ArrayList<String>();
        List<String> sent_dates = new ArrayList<String>();
		
		
		try {
			FileReader fr = new FileReader("C:\\ApiResults\\messages_list.xml");
			SAXBuilder saxBuilder = new SAXBuilder();
	        Document document = saxBuilder.build(fr); 
	        Element classElement = document.getRootElement();
	        
	        Path txt_file = Paths.get("C:\\ApiResults\\messages_list.txt");
	    	Files.deleteIfExists(txt_file);
	    	
	    	FileWriter file = new FileWriter("C:\\ApiResults\\messages_list.txt", true);
	    	BufferedWriter output = new BufferedWriter(file);
	    	PrintWriter out = new PrintWriter(output);
	    	
	    	List<Element> data = classElement.getChildren("Data");
	    	
	    	for (int quanto = 0; quanto < data.size(); quanto++) {
	        	Element dataElement = data.get(quanto);
	        	List<Element> messages = dataElement.getChildren("Messages");
	        	
	        	for (int temp = 0; temp < messages.size(); temp++) {    
		            Element messagesElement = messages.get(temp);   
		            
		            List<Element> message = messagesElement.getChildren("Message");
		            System.out.println("\n" + messagesElement.getName() + ": " + message.size());
		            out.println("\n" + messagesElement.getName() + ": " + message.size());
		            System.out.println("----------------------------");
		            out.println("----------------------------");
	        	
		            for (int count = 0; count < message.size(); count++) {
		            	
			               Element messageElement = message.get(count);

			               System.out.println();
			               out.println();
			               System.out.print("Subject: ");
			               out.print("Subject: ");
			               System.out.println(messageElement.getChildText("Subject"));
			               out.println(messageElement.getChildText("Subject"));
			               System.out.print("Id: ");
			               out.print("Id: ");
			               System.out.println(messageElement.getChildText("Id"));
			               out.println(messageElement.getChildText("Id"));
			               System.out.print("Sent Date: ");
			               out.print("Sent Date: ");
			               System.out.println(messageElement.getChildText("SentDate"));
			               out.println(messageElement.getChildText("SentDate"));
			               System.out.print("Type: ");
			               out.print("Type: ");
			               System.out.println(messageElement.getChildText("Type"));
			               out.println(messageElement.getChildText("Type"));
			               System.out.print("FromName: ");
			               out.print("FromName: ");
			               System.out.println(messageElement.getChildText("FromName"));
			               out.println(messageElement.getChildText("FromName"));
			               System.out.print("FromEmail: ");
			               out.print("FromEmail: ");
			               System.out.println(messageElement.getChildText("FromEmail"));
			               out.println(messageElement.getChildText("FromEmail"));
			               
			               ids.add(count, messageElement.getChildText("Id"));
			               message_names.add(count, messageElement.getChildText("Subject"));
			               message_types.add(count, messageElement.getChildText("Type"));
			               sent_dates.add(count, messageElement.getChildText("SentDate"));
			               
		            }
	        	        
	        	}
	        	
	    	}
	    	
	    	Starter.setIds(ids);
            Starter.setMessage_names(message_names);
            Starter.setMessage_types(message_types);
            Starter.setSent_date(sent_dates);
	    	
	    	out.close();
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
	}

}
