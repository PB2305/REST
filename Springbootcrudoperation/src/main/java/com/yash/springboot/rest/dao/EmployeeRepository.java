package com.yash.springboot.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yash.springboot.rest.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}