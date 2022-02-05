package com.pushpraj.springboot.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.pushpraj.springboot.exception.ResourceNotFoundException;
import com.pushpraj.springboot.model.Employee;
import com.pushpraj.springboot.repository.EmployeeRepository;

@Service
public class EmployeeService {
	

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	public Employee createEmployee(Employee employee) {
		return employeeRepository.save(employee);

	}
	
	public ResponseEntity<Employee> getEmployeeById(Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		return ResponseEntity.ok(employee);
	}
	
	public ResponseEntity<Employee> updateEmployee(Long id,Employee employeeDetails){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(Long id){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
