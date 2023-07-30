package com.sbms.controllers;

import com.sbms.entitys.Employee;
import com.sbms.exceptions.EmployeeNotFoundException;
import com.sbms.servicesI.EmployeeServicesI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeServicesI employeeServicesI;

    @PostMapping("/save")
    public ResponseEntity<Employee> save(@RequestBody Employee employee) {

        Employee save = employeeServicesI.save(employee);
        return new ResponseEntity<Employee>(save, HttpStatus.CREATED);

    }

    @PostMapping("/saveAll")
    public ResponseEntity<List<Employee>> saveAll(@RequestBody List<Employee> employeelist) {

        List<Employee> saveAll = employeeServicesI.saveAll(employeelist);
        return new ResponseEntity<List<Employee>>(saveAll, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable Integer id) {

        Object findById = employeeServicesI.findById(id);
        if (findById instanceof Employee) {
            return new ResponseEntity(findById, HttpStatus.OK);
        }
        return new ResponseEntity(findById.toString(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/findAllById", method = RequestMethod.GET)
    public ResponseEntity findAllById(@RequestParam("id1") Integer id1, @RequestParam("id2") Integer id2, @RequestParam("id3") Integer id3) {
        List list = new ArrayList();
        list.add(id1);
        list.add(id2);
        list.add(id3);

        List<Employee> findallById = employeeServicesI.findAllById(list);
        if (findallById.size() > 0) {
            return new ResponseEntity(findallById, HttpStatus.OK);
        }
        return new ResponseEntity("No Employee Records are found whatever the Ids you are supplied", HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity findAll() {
        List<Employee> findAll = employeeServicesI.findAll();
        if (findAll.size() > 0) {
            return new ResponseEntity(findAll, HttpStatus.OK);
        }
        return new ResponseEntity<String>("No Employees are there", HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "existById/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> existById(@PathVariable("id") Integer id) {
        boolean b = employeeServicesI.existById(id);
        if (b == true) {
            return new ResponseEntity("Employee record is existed with an id :: " + id, HttpStatus.OK);
        }
        return new ResponseEntity("Employee Record is not existed with an id :: " + id, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResponseEntity count() {
        Long count = employeeServicesI.count();
        if (count > 0) {
            return new ResponseEntity("The No.of Employee records are found is :: " + count, HttpStatus.OK);
        }
        return new ResponseEntity("The No.of Employee records are found is :: " + count, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity update(@RequestBody Employee employee) {
        Employee update = employeeServicesI.update(employee);
        return  new ResponseEntity(update, HttpStatus.CREATED);
    }

    @PutMapping(value = "/updateAll", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity updateAll(@RequestBody List<Employee> employeeList) {
        List<Employee> updateAll = employeeServicesI.updateAll(employeeList);
        return  new ResponseEntity(updateAll, HttpStatus.CREATED);
    }
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id){
        try {
            employeeServicesI.deleteById(id);
            return new ResponseEntity("Employee record deleted successfully with an id :: "+id, HttpStatus.OK) ;
        }catch (Exception e){
           throw  new EmployeeNotFoundException("There is no Employee Record with id :: "+id);
        }

    }

    @DeleteMapping("deleteAllById")
    public ResponseEntity deleteAllById(@RequestBody List<Integer> integerList){
        employeeServicesI.deleteAllById(integerList);
        return new ResponseEntity("Employees deleted successfully for whatever the ids you supplied", HttpStatus.OK);
    }
    @DeleteMapping(value="/deleteByEntity", consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity deleteByEntity(@RequestBody Employee employee) {
        employeeServicesI.deleteByEntity(employee);
        return new ResponseEntity("Employee deleted Successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "deleteAllByEntity", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity deleteAllByEntity(@RequestBody List<Employee> employeeList){
        employeeServicesI.deleteAllByEntity(employeeList);
        return new ResponseEntity("Employees are deleted Successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteAll")
    public ResponseEntity deleteAll(){
        employeeServicesI.deleteAll();
        return new ResponseEntity("All Employee Records are deleted Successfully", HttpStatus.OK);
    }

}
