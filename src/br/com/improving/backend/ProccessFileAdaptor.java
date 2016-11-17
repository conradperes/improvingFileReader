package br.com.improving.backend;

import static br.com.improving.util.PrintUtils.print;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import br.com.improving.backend.entity.Customer;

public class ProccessFileAdaptor {
	public enum Headers {
		ID, Description, Monetary, Type
	}

	private static  Map<Long, Customer> result = new HashMap<Long, Customer>();
	private static  List<Customer> result2 = new ArrayList<Customer>();
	int count = 0;
	// public static String FILE_NAME =
	// "/Users/conradmarquesperes/Documents/workspace/improving/clients.csv";
	

	public static void main(String[] args) throws Exception {
		proccessFile(null, null);
	}

	public static Map<Long, Customer> getResult() {
		return result;
	}

	public static void proccessFile(String fileName, Repository repository) throws Exception{
		Long fileId = repository.generateFileId(fileName);
		ProccessFileAdaptor cvs = new ProccessFileAdaptor();
		Reader reader = null;
		try {
			reader = new FileReader(fileName);
			Iterable<CSVRecord> records = null;
			try {
				records = CSVFormat.TDF.withAllowMissingColumnNames().parse(reader);
			} catch (IOException e) {
				e.printStackTrace();
			}
			long begin = System.currentTimeMillis();
			logHour();
			print("Início do Processamento =" + begin);
			for (CSVRecord record : records) {
				// String id = record.get(Headers.ID);
				writingInList(repository, fileId, cvs, record);
			}
			
			long end = System.currentTimeMillis();
			print("Tempo de processamento em minutos =" + ((end - begin)/60000));
			logHour();
			fillingMap();
			print("Tem o cliente id 40459638?"+lookup(result, 40459638));
			
		
		} catch (FileNotFoundException e) {
			throw new Exception(e);
		}
	}
	private static void logHour(){
		print(new Date());
	}

	private static void fillingMap() {
		for(Customer c:result2){
			result.put((long)c.getId(), c);
		}
		
	}

	private static void writingInList(Repository repository, Long fileId, ProccessFileAdaptor cvs, CSVRecord record) {
		cvs.count++;
		String description = record.get(0);
		String monetary = record.get(1);
		String type = record.get(2);
		Customer customer = Customer.getInstance();
		customer.type(type);
		customer.fileId(fileId);
		customer.description(description);
		Long lineNumber = new Long(cvs.count);
		if (customer.monetary(monetary)) {
			if (repository.getClientId(description) != null)
				repository.save(fileId, lineNumber, lineNumber, new BigDecimal(monetary));
		} else {
			repository.reportInvalidLine(fileId, cvs.count, Field.VALUE, "Dados monetários inválidos");
			if (repository.getClientId(description) == null)
				repository.reportInvalidLine(fileId, cvs.count, Field.VALUE, "Dados monetários inválidos");
		}
		customer.id(cvs.count);
		result2.add(customer);
//		result.put(cvs.count, customer);
	}

	public static boolean lookup(Map<Long, Customer> map, long pk) {
		if (map.containsKey(pk))
			return true;
		return false;
	}

}
