package com.practice.vaadin.data;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.practice.vaadin.domain.Customer;
import com.practice.vaadin.persistent.mongo.repository.CustomerRepository;

@Component
public class ExcelFileLoader {
	
	@Autowired
	CustomerRepository customerRepository;
	
	private void processSheet(XSSFSheet sheet) throws Exception{
		List<Method> setterMethods = new ArrayList<Method>();
		XSSFRow fieldRow = sheet.getRow(0);
		
		int columnCount = fieldRow.getPhysicalNumberOfCells();
		for(int i=0; i < columnCount; i++){
			XSSFCell cell = fieldRow.getCell(i);
			String value =null;
			if(!StringUtils.isEmpty(value = cell.getStringCellValue())){
				String setterMethodName = "set" + value.substring(0, 1).toUpperCase() + value.substring(1);
				setterMethods.add(Customer.class.getDeclaredMethod(setterMethodName, String.class));
			}
		}
			
		for(int i= 1; i<sheet.getPhysicalNumberOfRows() -1; i++){
			processRow(sheet, setterMethods, i);
		}
	}
	
	private void processRow(XSSFSheet sheet, List<Method> setterMethods, int i) {
		Customer customer = new Customer();
		
		customerRepository.save(customer);
	}



	public void processFile(File file) throws Exception {
		XSSFWorkbook wb = null;
		wb = new XSSFWorkbook(file);

		try {
			processSheet(wb.getSheetAt(0));
		} finally {
			if (wb != null) {
				wb.close();
			}
		}
	}
}
