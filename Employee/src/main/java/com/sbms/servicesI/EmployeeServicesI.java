package com.sbms.servicesI;

import java.util.List;

import com.sbms.entitys.Employee;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmployeeServicesI {
	
	public Employee save(Employee employee);

	public List<Employee> saveAll(List<Employee> employeelist);
	
	public Object findById(Integer id);

	public List<Employee> findAll();

	public List<Employee> findAllById(List<Integer> integerList);

	public boolean existById(Integer integer);

	public Long count();

	public Employee update(Employee employee);

	public List<Employee> updateAll(List<Employee> employeeList);

	public void deleteById(Integer id);

	public void deleteAllById(@RequestBody List<Integer> integerList);

	public void deleteByEntity(Employee employee);

	public void deleteAllByEntity(List<Employee> employeeList);

	public void deleteAll();
}
