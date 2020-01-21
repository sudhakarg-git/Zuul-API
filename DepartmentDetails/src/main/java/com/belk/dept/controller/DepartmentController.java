package com.belk.dept.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.belk.dept.model.Department;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class DepartmentController {
	
	private final static Logger LOG = LoggerFactory.getLogger(DepartmentController.class);
	
	@Value("${app.environment}")
	private String environment;
	
	
	@GetMapping("/departments")
	@HystrixCommand(fallbackMethod = "getDepts")
	public List<Department> getDepartments() throws Exception{
		
		LOG.info("Current Running Environment {}",environment);
		
		Department department1 = new Department();
		department1.setDeptId("100");
		department1.setDeptName("XStore");
		
		Department department2 = new Department();
		department2.setDeptId("101");
		department2.setDeptName("Ecomm");
		
		Department department3 = new Department();
		department3.setDeptId("102");
		department3.setDeptName("Test");
		
		List<Department> departments = new ArrayList<Department>();
		departments.add(department1);
		departments.add(department2);
		departments.add(department3);
		
		if(departments.size()>0)
		throw new Exception("error");
		
		return departments;
		
		
	}
	
	public List<Department> getDepts(){
		Department department1 = new Department();
		department1.setDeptId("100");
		department1.setDeptName("XStore");
		
		Department department2 = new Department();
		department2.setDeptId("101");
		department2.setDeptName("Ecomm");
		
		List<Department> departments = new ArrayList<Department>();
		departments.add(department1);
		departments.add(department2);
		
		return departments;
	}
	
	
	

}
