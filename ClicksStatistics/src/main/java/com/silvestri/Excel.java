package com.silvestri;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Excel {
	
	List<String> ids;
	List<String> names;
	List<String> types;
	List<String> sent_dates;

	public Excel(List<String> ids, List<String> names, List<String> types, List<String> sent_dates) {
		this.ids = ids;
		this.names = names;
		this.types = types;
		this.sent_dates = sent_dates;
	}

	public void generate_message_list_ex() {
		
		Workbook workbook = new XSSFWorkbook();
		
		Sheet sheet = workbook.createSheet("Lista de mensagens");
		
		sheet.setColumnWidth(0, 1000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 32000);
		
		Row header = sheet.createRow(0);
		
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.DIAMONDS);
		
		Font font = workbook.createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		headerStyle.setFont(font);
		
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("ID");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(1);
		headerCell.setCellValue("Tipo");
		headerCell.setCellStyle(headerStyle);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Mensagem");
		headerCell.setCellStyle(headerStyle);
		
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(true);
		
		for (int y = 2; y < ids.size(); y++) {
			Row row = sheet.createRow(y);
			Cell id = row.createCell(0);
			id.setCellValue(ids.get(y));
			Cell type = row.createCell(1);
			type.setCellValue(types.get(y));
			Cell message = row.createCell(2);
			message.setCellValue(names.get(y));

		}
		
		try {
			FileOutputStream output = new FileOutputStream("C:\\ApiResults\\lista_mensagens.xlsx");
			workbook.write(output);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void generate_links_statistics_ex() {
		
		Workbook workbook = new XSSFWorkbook();
		
		Sheet sheet = workbook.createSheet("Cliques nos links");
		
		sheet.setColumnWidth(0, 2000); // ID's
		sheet.setColumnWidth(1, 4000); // tipos
		sheet.setColumnWidth(2, 6000); // datas/horas
		sheet.setColumnWidth(3, 6000); // totais
		sheet.setColumnWidth(4, 6000); // únicos
		sheet.setColumnWidth(5, 30000); // mensagens
		sheet.setColumnWidth(6, 65280); // links (valor máximo de 65.280)
		
		Row header = sheet.createRow(0);
		
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GOLD.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		Font font = workbook.createFont();
		font.setFontName("Arial");
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		headerStyle.setFont(font);
		
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("ID");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(1);
		headerCell.setCellValue("Tipo");
		headerCell.setCellStyle(headerStyle);
		
		headerCell = header.createCell(2);
		headerCell.setCellValue("Data/hora");
		headerCell.setCellStyle(headerStyle);
		
		headerCell = header.createCell(3);
		headerCell.setCellValue("Cliques totais");
		headerCell.setCellStyle(headerStyle);		

		headerCell = header.createCell(4);
		headerCell.setCellValue("Cliques únicos");
		headerCell.setCellStyle(headerStyle);
		
		headerCell = header.createCell(5);
		headerCell.setCellValue("Mensagem");
		headerCell.setCellStyle(headerStyle);
		
		headerCell = header.createCell(6);
		headerCell.setCellValue("URL");
		headerCell.setCellStyle(headerStyle);
		
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(true);
		
		try {
			FileReader fr = new FileReader("C:\\ApiResults\\links_statistics.xml");
			SAXBuilder saxBuilder = new SAXBuilder();
	        Document document = saxBuilder.build(fr); 
	        Element classElement = document.getRootElement(); // captura <ApiResponses>
	        
	        List<Element> apiResponse = classElement.getChildren("ApiResponse"); // cria lista de <ApiResponse>
	        for (int quanto_api_response = 0; quanto_api_response < apiResponse.size(); quanto_api_response++) {
	        	Element apiResponseElement = apiResponse.get(quanto_api_response); // verifica total de <ApiResponse>
	        	List<Element> data = apiResponseElement.getChildren("Data"); // cria lista de <Data>
	        	for (int quanto_data = 0; quanto_data < data.size(); quanto_data++) {
	        		Element dataElement = data.get(quanto_data); // verifica total de <Data>
	        		List<Element> linkStatistics = dataElement.getChildren("LinkStatistics"); // cria lista de <LinkStatistics>
	        		for (int quanto_link_statistics = 0; quanto_link_statistics < linkStatistics.size(); quanto_link_statistics++) {
	        			Element linkStatisticsElement = linkStatistics.get(quanto_link_statistics); // verifica total de <LinkStatistics>
	        			List<Element> linkStatistic = linkStatisticsElement.getChildren("LinkStatistic"); //cria lista de <LinkStatistic>
	        			for (int quanto_link_statistic = 0; quanto_link_statistic < linkStatistic.size(); quanto_link_statistic++) {
	        				Element linkStatisticElement = linkStatistic.get(quanto_link_statistic);
	        				// System.out.println(linkStatistic.size());
		 	                // CONTINUAR A INVESTIGAÇÃO DAQUI (TXT COMPLETO, MAS XLSX COM 20 LINHAS. LINKSTATISTIC PEQUENO)
	        				// System.out.println(ids.size());
	        				for (int index = 1; index < linkStatisticElement.getContentSize(); index++) {
		 	      			Row row = sheet.createRow(index);
		 	      			Cell id = row.createCell(0);
		 	      			id.setCellValue(ids.get(quanto_api_response));
		 	      			Cell tipo = row.createCell(1);
		 	      			tipo.setCellValue(types.get(quanto_api_response));
		 	      			Cell data_hora = row.createCell(2);
		 	      			data_hora.setCellValue(sent_dates.get(quanto_api_response));
		 	      			Cell totais = row.createCell(3);
		 	      			totais.setCellValue(linkStatisticElement.getChildText("Clicks"));
		 	      			Cell unicos = row.createCell(4);
		 	      			unicos.setCellValue(linkStatisticElement.getChildText("UniqueClicks"));
		 	      			Cell mensagem = row.createCell(5);
		 	      			mensagem.setCellValue(names.get(quanto_api_response));
		 	      			Cell url = row.createCell(6);
		 	      			url.setCellValue(linkStatisticElement.getChildText("Url"));
		 	               }
	        			}
	        		}
	        	}
	        }
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			FileOutputStream output = new FileOutputStream("C:\\ApiResults\\links_statistics.xlsx");
			workbook.write(output);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
