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
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class LinksStatistics {
	
	public void fetch_links_statistics(List<String> ids) throws IOException {
		final HttpClient httpClient = HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_2)
				.build();
		
		HttpResponse<String> response = null;
		
		Path xml_file = Paths.get("C:\\ApiResults\\links_statistics.xml");
		Files.deleteIfExists(xml_file);
		
		FileWriter file = new FileWriter("C:\\ApiResults\\links_statistics.xml",true);
		BufferedWriter output = new BufferedWriter(file);
		PrintWriter out = new PrintWriter(output);
		out.println("<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>");
		out.println("<ApiResponses>");

		int index = 0;
		do {
			
		HttpRequest request = HttpRequest.newBuilder()
				.GET()
				//.uri(URI.create("https://api3.esv2.com/v2/Api/LinkStatistics/" + ids.get(index) + "?apiKey=1l52wORZAYlpRzWxll8D"))
				//.uri(URI.create("https://api4.esv2.com/v2/Api/LinkStatistics/" + ids.get(index) + "?apiKey=2QZyexYB6TOKl2XAo4ez"))
				//.uri(URI.create("https://api4.esv2.com/v2/Api/LinkStatistics/" + ids.get(index) + "?apiKey=2QZyexYB6TOKl2XAo4ez&startDate=" + startDate + "&" + "endDate=" + endDate))
				//.uri(URI.create("https://api7.esv2.com/v2/Api/LinkStatistics/" + ids.get(index) + "?apiKey=GyvdqBcZ0jlAeQpSVDf0&startDate=" + startDate + "&" + "endDate=" + endDate))
				//.uri(URI.create("https://api4.esv2.com/v2/Api/LinkStatistics/" + ids.get(index) + "?apiKey=2QZyexYB6TOKl2XAo4ez&startDate=2021-09-01&endDate=2021-09-30"))
				.uri(URI.create("https://api" + Starter.getServer_number() + ".esv2.com/v2/Api/LinkStatistics/" + ids.get(index) + "?apiKey=" + Starter.getApi_key() + "&startDate=" + Starter.getStartDate() + "&" + "endDate=" + Starter.getEndDate()))
				.setHeader("User-Agent", "Java 16 HttpClient Bot")
				.build();
		
		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		
		System.out.println("\nReturn code: " + response.statusCode());
		System.out.println("Messagem ID: " + ids.get(index).toString());
		System.out.println("\n" + response.body());
		out.println(response.body());
		
		index++;
		} while (index < ids.size());
		

		
		out.println("</ApiResponses>");
		out.close();

	}
	
	public void generate_txt_from_xml(List<String> ids) {
		try {
			FileReader fr = new FileReader("C:\\ApiResults\\links_statistics.xml");
			SAXBuilder saxBuilder = new SAXBuilder();
	        Document document = saxBuilder.build(fr); 
	        Element classElement = document.getRootElement(); // captura <ApiResponses>
	        
	        Path txt_file = Paths.get("C:\\ApiResults\\links_statistics.txt");
			Files.deleteIfExists(txt_file);
	        
	        FileWriter file = new FileWriter("C:\\ApiResults\\links_statistics.txt",true);
			BufferedWriter output = new BufferedWriter(file);
			PrintWriter out = new PrintWriter(output);
			
			System.out.println("Relatório de cliques de " + Starter.getStartDate() + " a " + Starter.getEndDate() + ":");
			System.out.println("================================================");
			out.println("Relatório de cliques de " + Starter.getStartDate() + " a " + Starter.getEndDate() + ":");
			out.println("==================================================");
			
			List<Element> apiResponse = classElement.getChildren("ApiResponse"); // cria lista de <ApiResponse>
	        for (int quanto_api_response = 0; quanto_api_response < apiResponse.size(); quanto_api_response++) {
	        	Element apiResponseElement = apiResponse.get(quanto_api_response); // verifica total de <ApiResponse>
	        	List<Element> data = apiResponseElement.getChildren("Data"); // cria lista de <Data>
	        	
	        	System.out.println();
	        	out.println();
	        	System.out.println("--------------------------------------");
	        	out.println("--------------------------------------");
	        	System.out.println("ID da mensagem: " + ids.get(quanto_api_response));
	        	out.println("ID da mensagem: " + ids.get(quanto_api_response));
	        	System.out.println("Nome da mensagem: " + Starter.getMessage_names().get(quanto_api_response));
	        	out.println("Nome da mensagem: " + Starter.getMessage_names().get(quanto_api_response));
	        	System.out.println("Tipo da mensagem: " + Starter.getMessage_types().get(quanto_api_response));
	        	out.println("Tipo da mensagem: " + Starter.getMessage_types().get(quanto_api_response));
	        	System.out.println("Data/hora do disparo: " + Starter.getSent_date().get(quanto_api_response));
	        	out.println("Data/hora do disparo: " + Starter.getSent_date().get(quanto_api_response));
	        	System.out.println("--------------------------------------");
	        	out.println("--------------------------------------");
	        	
	        	for (int quanto_data = 0; quanto_data < data.size(); quanto_data++) {
	        		Element dataElement = data.get(quanto_data); // verifica total de <Data>
	        		List<Element> linkStatistics = dataElement.getChildren("LinkStatistics"); // cria lista de <LinkStatistics>
	        		for (int quanto_link_statistics = 0; quanto_link_statistics < linkStatistics.size(); quanto_link_statistics++) {
	        			Element linkStatisticsElement = linkStatistics.get(quanto_link_statistics); // verifica total de <LinkStatistics>
	        			List<Element> linkStatistic = linkStatisticsElement.getChildren("LinkStatistic"); //cria lista de <LinkStatistic>
	        			for (int quanto_link_statistic = 0; quanto_link_statistic < linkStatistic.size(); quanto_link_statistic++) {
	        				Element linkStatisticElement = linkStatistic.get(quanto_link_statistic);
	        				
	        				System.out.println(); 
	        				out.println();
	        				
	        				/*System.out.println("ID da mensagem: " + ids.get(index_ids));
	        				out.println("ID da mensagem: " + ids.get(index_ids));
	        				System.out.println("Assunto: " + this.names.get(index_ids));
	        				out.println("Assunto: " + this.names.get(index_ids));
	        				System.out.println("Tipo: " + this.types.get(index_ids));
	        				out.println("Tipo: " + this.types.get(index_ids));*/
	        				
		 	                System.out.print("URL: ");
		 	                out.print("URL: ");
		 	                System.out.println(linkStatisticElement.getChildText("Url"));
		 	                out.println(linkStatisticElement.getChildText("Url"));
		 	                System.out.print("Cliques Totais: ");
		 	                out.print("Cliques Totais: ");
		 	                System.out.println(linkStatisticElement.getChildText("Clicks"));
		 	                out.println(linkStatisticElement.getChildText("Clicks"));
		 	               
		 	                // ids.add(count, messageElement.getChildText("Id"));
		 	               
		 	                System.out.print("Cliques únicos: ");
		 	                out.print("Cliques únicos: ");
		 	                System.out.println(linkStatisticElement.getChildText("UniqueClicks"));
		 	                out.println(linkStatisticElement.getChildText("UniqueClicks"));
	        			}
	        		   
	        		    
	        			
	        		}
	        	}
	        	
	        }
	        
	        out.close();
	        
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (JDOMException jdome) {
			jdome.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
			
	}

}
