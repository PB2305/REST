package com.yash.springboot.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.yash.springboot.rest.exception.EmployeeNotFoundException;
import com.yash.springboot.rest.model.Employee;
import com.yash.springboot.rest.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController extends ResourceSupport {

	@Autowired
	EmployeeService employeeService;

	@RequestMapping(method = RequestMethod.GET, produces = { "application/xml", "application/json" })
	public List<Employee> getEmployees() {
		return employeeService.getEmployees();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/xml", "application/json" })
	public Resource<Employee> getEmployeeById(@PathVariable int id) {
		Optional<Employee> emp = employeeService.getEmployeeById(id);
		Resource<Employee> resource;
		if (emp.isPresent()) {
			resource = new Resource<Employee>(emp.get());

			Link selflink = ControllerLinkBuilder
					.linkTo(ControllerLinkBuilder.methodOn(EmployeeController.class).getEmployees()).withSelfRel();
			resource.add(selflink);
		} else
			throw new EmployeeNotFoundException("Could not find employee with id- " + id);
		return resource;

	}

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/xml", "application/json" }, produces = {
			"application/xml", "application/json" })
	public Employee createEmployee(@RequestBody Employee newemp) {
		System.out.println(this.getClass().getSimpleName() + " - Create new employee method is invoked.");
		return employeeService.addNewEmployee(newemp);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/xml",
			"application/json" }, produces = { "application/xml", "application/json" })
	public Employee updateEmployee(@RequestBody Employee updemp, @PathVariable int id) {
		Optional<Employee> emp = employeeService.getEmployeeById(id);
		if (!emp.isPresent())
			throw new EmployeeNotFoundException("Could not find employee with id - " + id);

		if (updemp.getName() == null || updemp.getName().isEmpty())
			updemp.setName(emp.get().getName());
		if (updemp.getDepartment() == null || updemp.getDepartment().isEmpty())
			updemp.setDepartment(emp.get().getDepartment());
		if (updemp.getSalary() == 0)
			updemp.setSalary(emp.get().getSalary());

		updemp.setId(id);
		return employeeService.updateEmployee(updemp);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteEmployeeById(@PathVariable int id) {
		Optional<Employee> emp = employeeService.getEmployeeById(id);
		if (!emp.isPresent())
			throw new EmployeeNotFoundException("Could not find employee with id - " + id);

		employeeService.deleteEmployeeById(id);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteAll() {
		employeeService.deleteAllEmployees();
	}
}
