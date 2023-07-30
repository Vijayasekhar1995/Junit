package com.sbms.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbms.entitys.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
