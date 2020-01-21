package com.belk.employee.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.belk.employee.model.Employee;

@RestController
public class EmployeeController {
	
private final static Logger LOG = LoggerFactory.getLogger(EmployeeController.class);
	
	@Value("${app.environment}")
	private String environment;
	
	@GetMapping("/employees")
	public List<Employee> getEmployeeDetails(){
		
		LOG.info("Current Running Environment {}",environment);
		
		Employee employee = new Employee();
		employee.setEmpId("1000");
		employee.setEmpName("Sam");
		employee.setDesignation("Lead Developer");
		employee.setDeptId("100");
		
		Employee employee1 = new Employee();
		employee1.setEmpId("1001");
		employee1.setEmpName("Victor");
		employee1.setDesignation("PCF Developer");
		employee1.setDeptId("101");
		
		Employee employee2 = new Employee();
		employee2.setEmpId("1002");
		employee2.setEmpName("David");
		employee2.setDesignation("Test Engineer");
		employee2.setDeptId("102");
		
		List<Employee> empList = new ArrayList<Employee>();
		empList.add(employee);
		empList.add(employee1);
		empList.add(employee2);
		
		return empList;
		
	}

}
