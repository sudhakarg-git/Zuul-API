package com.belk.employee.model;

public class Employee {
	
	private String empId;
	private String empName;
	private String designation;
	private String deptId;
	
	public Employee() {}
	
	public Employee(String empId, String empName, String designation, String deptId) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.designation = designation;
		this.deptId = deptId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	
	

}
