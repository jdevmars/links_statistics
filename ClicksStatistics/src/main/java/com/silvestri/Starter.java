package com.silvestri;

import java.awt.HeadlessException;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

// a classe XSSFWorkbook não funciona, múltiplos erros de dependência de versões erradas e difícil de achar a correta
// toda a parte do Excel foi comentada temporariamente
public class Starter {
	
	private static String startDate;
	private static String endDate;
	private static String api_key;
	private static String server_number;
	private static List<String> ids;
	private static List<String> message_names;
	private static List<String> message_types;
	private static List<String> sent_dates;
	
	public static void main(String[] args) {
		
		try {
			setServer_number(JOptionPane.showInputDialog("What's the server number?"));
			setApi_key(JOptionPane.showInputDialog("What's the API Key?"));
			setStartDate(JOptionPane.showInputDialog("What's the initial date (yyyy-MM-dd)?"));
			endDate = JOptionPane.showInputDialog("What's the end date (yyyy-MM-dd)?");
		} catch (HeadlessException e) {
			e.printStackTrace();
		} 
		
		MessageList messageList = new MessageList();
		messageList.fetch_message_list();
		messageList.generate_txt_from_xml();
		
		LinksStatistics linksStatistics = new LinksStatistics();
		
		try {
			linksStatistics.fetch_links_statistics(ids);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		linksStatistics.generate_txt_from_xml(ids);
		
		/* desabilitado temporariamente
		 * 
		 * 
		Excel excel = new Excel(ids, message_names, message_types, sent_dates);
		excel.generate_message_list_ex();
		excel.generate_links_statistics_ex();
		*/

	}

	public static String getStartDate() {
		return startDate;
	}

	public static void setStartDate(String startDate) {
		Starter.startDate = startDate;
	}
	
	public static String getEndDate() {
		return endDate;
	}

	public static void setEndDate(String endDate) {
		Starter.endDate = endDate;
	}

	public static String getApi_key() {
		return api_key;
	}

	public static void setApi_key(String api_key) {
		Starter.api_key = api_key;
	}

	public static String getServer_number() {
		return server_number;
	}

	public static void setServer_number(String server_number) {
		Starter.server_number = server_number;
	}

	public static List<String> getIds() {
		return ids;
	}

	public static void setIds(List<String> ids) {
		Starter.ids = ids;
	}

	public static List<String> getMessage_names() {
		return message_names;
	}

	public static void setMessage_names(List<String> message_names) {
		Starter.message_names = message_names;
	}

	public static List<String> getMessage_types() {
		return message_types;
	}

	public static void setMessage_types(List<String> message_types) {
		Starter.message_types = message_types;
	}

	public static List<String> getSent_date() {
		return sent_dates;
	}

	public static void setSent_date(List<String> sent_date) {
		Starter.sent_dates = sent_date;
	}

}
