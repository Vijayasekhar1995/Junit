package com.sbms.servicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sbms.entitys.Employee;
import com.sbms.repositorys.EmployeeRepository;
import com.sbms.servicesI.EmployeeServicesI;

@Service
public class EmployeeServicesImpl implements EmployeeServicesI {
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public Employee save(Employee employee) {
		Employee save = employeeRepository.save(employee);
		return save;
	}

	@Override
	public List<Employee> saveAll(List<Employee> employeelist) {
		List<Employee> saveAll = employeeRepository.saveAll(employeelist);
		return saveAll;
	}

	@Override
	@Cacheable(cacheNames = "employees", key="#id")
	public Object findById(Integer id) {
		
		Optional<Employee> findById = employeeRepository.findById(id);
		Employee employee;
		if (findById.isPresent()) {
			employee=findById.get();
			return employee;
		} else {
			return "Employee was not found with an Id :: " + id;
		}
	}

	@Override
	public List<Employee> findAll() {
		List<Employee> findAll = employeeRepository.findAll();
		return findAll.stream().sorted().collect(Collectors.toList());
	}

	@Override
	public List<Employee> findAllById(List<Integer> integerList) {
		List findAllById = employeeRepository.findAllById(integerList);
		System.out.println(findAllById);
		return (List<Employee>) findAllById.stream().sorted().collect(Collectors.toList());
	}

	@Override
	public boolean existById(Integer integer) {
		boolean existsById = employeeRepository.existsById(integer);
		return existsById;
	}

	@Override
	public Long count() {
		Long count = employeeRepository.count();
		return count;
	}

	@Override
	@CachePut(cacheNames = "employees", key="#id")
	public Employee update(Employee employee) {
		Employee save = employeeRepository.save(employee);
		return save;
	}

	@Override
	public List<Employee> updateAll(List<Employee> employeeList) {

		List<Employee> saveAll = employeeRepository.saveAll(employeeList);
		return saveAll;
	}

	@Override
	@CacheEvict(cacheNames = "employees", key="#id")
	public void deleteById(Integer id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public void deleteAllById(List<Integer> integerList) {
		employeeRepository.deleteAllById(integerList);
	}

	@Override
	public void deleteByEntity(Employee employee) {
		employeeRepository.delete(employee);
	}

	@Override
	public void deleteAllByEntity(List<Employee> employeeList) {
		employeeRepository.deleteAll(employeeList);
	}

	@Override
	public void deleteAll() {
		employeeRepository.deleteAll();
	}

}
