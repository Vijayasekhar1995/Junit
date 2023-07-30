package com.sbms.controllersTest;

import com.sbms.controllers.EmployeeController;
import com.sbms.entitys.Employee;
import com.sbms.servicesImpl.EmployeeServicesImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Testing of EmployeeController")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class EmployeeControllerTest {

//  If we didn't use public  EmployeeControllerTest(){ MockitoAnnotations.openMocks(this); } then @InjectMocks annotation will not work.

    @InjectMocks
    EmployeeController employeeController;

    @Mock
    EmployeeServicesImpl employeeServicesImpl;

    public  EmployeeControllerTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    @DisplayName("Testing of save")
    public void testsave(){
        Employee employee = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        when(employeeServicesImpl.save(employee)).thenReturn(employee);
        ResponseEntity<Employee> employeeResponseEntity = employeeController.save(employee);
        assertEquals(HttpStatus.CREATED,employeeResponseEntity.getStatusCode());
        assertEquals(employee,employeeResponseEntity.getBody());
    }

    @Test
    @Order(2)
    @DisplayName("Testing of saveAll")
    public void testsaveAll(){
        Employee employee1 = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        Employee employee2 = new Employee(1,"Kalavakuri",7000.00, "Kaligiri");
        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);
        when(employeeServicesImpl.saveAll(list)).thenReturn(list);
        ResponseEntity<List<Employee>> employeeListResponseEntity = employeeController.saveAll(list);
        assertEquals(HttpStatus.CREATED,employeeListResponseEntity.getStatusCode());
        assertEquals(list,employeeListResponseEntity.getBody());
    }

    @Test
    @Order(3)
    @DisplayName("Testing of findById If Employee Is Found ")
    public void testfindByIdIfEmployeeIsFound(){
        Employee employee = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        when(employeeServicesImpl.findById(1)).thenReturn(employee);
        ResponseEntity responseEntity = employeeController.findById(1);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(employee,responseEntity.getBody());
    }

    @Test
    @Order(4)
    @DisplayName("Testing of findById If Employee Is Not Found ")
    public void testfindByIdIfEmployeeIsNotFound(){
        when(employeeServicesImpl.findById(1000)).thenReturn("Employee was not found with an Id :: "+1000);
        ResponseEntity responseEntity = employeeController.findById(1000);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Employee was not found with an Id :: "+1000,responseEntity.getBody());
    }

    @Test
    @Order(5)
    @DisplayName("Testing of findAll If Employees Is Found")
    public void testfindAllIfEmployeeIsFound(){
        Employee employee1 = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        Employee employee2 = new Employee(1,"Kalavakuri",7000.00, "Kaligiri");
        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);
        when(employeeServicesImpl.findAll()).thenReturn(list);
        ResponseEntity responseEntity = employeeController.findAll();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(list,responseEntity.getBody());
    }

    @Test
    @Order(6)
    @DisplayName("Testing of findAll If Employees Is Not Found")
    public void testfindAllIfEmployeeIsNotFound(){
        List<Employee> list = new ArrayList<>();
        when(employeeServicesImpl.findAll()).thenReturn(list);
        ResponseEntity responseEntity = employeeController.findAll();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("No Employees are there",responseEntity.getBody());
    }

    @Test
    @Order(7)
    @DisplayName("Testing of findAllById If Employees are Found ")
    public void findAllByIdIfEmployeesAreFound() {
        Employee employee1 = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        Employee employee2 = new Employee(2,"Kalavakuri",7000.00, "Kaligiri");
        Employee employee3 = new Employee(3,"VJ",7000.00, "Kaligiri");
        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);
        list.add(employee3);
        when(employeeServicesImpl.findAllById(List.of(1,2,3))).thenReturn(list);
        ResponseEntity responseEntity = employeeController.findAllById(1, 2, 3);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(list,responseEntity.getBody());
    }

    @Test
    @Order(8)
    @DisplayName("Testing of findAllById If Employees are Not Found ")
    public void findAllByIdIfEmployeesAreNotFound() {
        List<Employee> list = new ArrayList<>();
        when(employeeServicesImpl.findAllById(List.of(1,2,3))).thenReturn(list);
        ResponseEntity responseEntity = employeeController.findAllById(1, 2, 3);
        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
        assertEquals("No Employee Records are found whatever the Ids you are supplied",responseEntity.getBody());
    }

    @Test
    @Order(9)
    @DisplayName("Testing of existById If Employee Is Exist")
    public void testexistByIdIfEmployeeIsExist(){
        when(employeeServicesImpl.existById(1)).thenReturn(true);
        ResponseEntity stringResponseEntity = employeeController.existById(1);
        assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());
        assertEquals("Employee record is existed with an id :: " + 1, stringResponseEntity.getBody());
    }

    @Test
    @Order(10)
    @DisplayName("Testing of existById If Employee Is Not Exist")
    public void testexistByIdIfEmployeeIsNotExist(){
        when(employeeServicesImpl.existById(1000)).thenReturn(false);
        ResponseEntity<String> stringResponseEntity = employeeController.existById(1000);
        assertEquals(HttpStatus.BAD_REQUEST, stringResponseEntity.getStatusCode());
        assertEquals("Employee Record is not existed with an id :: " + 1000, stringResponseEntity.getBody());
    }

    @Test
    @Order(11)
    @DisplayName("Testing of count If Employee Records Are Exist")
    public void testcountIfEmployeeRecordsAreExist(){
        when(employeeServicesImpl.count()).thenReturn(31l);
        ResponseEntity<String> stringResponseEntity = employeeController.count();
        assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());
        assertEquals("The No.of Employee records are found is :: " + 31, stringResponseEntity.getBody());
    }

    @Test
    @Order(12)
    @DisplayName("Testing of count If Employee Records Are Not Exist")
    public void testcountIfEmployeeRecordsAreNotExist(){
        when(employeeServicesImpl.count()).thenReturn(0l);
        ResponseEntity<String> stringResponseEntity = employeeController.count();
        assertEquals(HttpStatus.BAD_REQUEST, stringResponseEntity.getStatusCode());
        assertEquals("The No.of Employee records are found is :: " + 0, stringResponseEntity.getBody());
    }

    @Test
    @Order(13)
    @DisplayName("Testing of update")
    public void testupdate(){
        Employee employee = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        when(employeeServicesImpl.update(employee)).thenReturn(employee);
        ResponseEntity<Employee> employeeResponseEntity = employeeController.update(employee);
        assertEquals(HttpStatus.CREATED,employeeResponseEntity.getStatusCode());
        assertEquals(employee, employeeResponseEntity.getBody());
    }

    @Test
    @Order(14)
    @DisplayName("Testing of updateAll")
    public void testupdateAll(){
        Employee employee1 = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        Employee employee2 = new Employee(1,"Kalavakuri",7000.00, "Kaligiri");
        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);
        when(employeeServicesImpl.updateAll(list)).thenReturn(list);
        ResponseEntity<Employee> employeeResponseEntity = employeeController.updateAll(list);
        assertEquals(HttpStatus.CREATED,employeeResponseEntity.getStatusCode());
        assertEquals(list, employeeResponseEntity.getBody());
    }

    @Test
    @Order(15)
    @DisplayName("Testing of deleteById")
    public void testdeleteById(){
        employeeController.deleteById(1);
        verify(employeeServicesImpl, times(1)).deleteById(1);
    }

    @Test
    @Order(16)
    @DisplayName("Testing of deleteAllById")
    public void testdeleteAllById(){
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        employeeController.deleteAllById(list);
        verify(employeeServicesImpl, times(1)).deleteAllById(list);
    }

    @Test
    @Order(17)
    @DisplayName("Testing of deleteByEntity")
    public void testdeleteByEntity(){
        Employee employee = new Employee(1,"Vijayasekhar",7000.00,"Kaligiri");
        employeeController.deleteByEntity(employee);
        verify(employeeServicesImpl, times(1)).deleteByEntity(employee);
    }

    @Test
    @Order(18)
    @DisplayName("Testing of deleteAllByEntity")
    public void testdeleteAllByEntity(){
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1,"Vijayasekhar",7000.00,"Kaligiri"));
        list.add(new Employee(2, "Kalavakuri", 8000.00,"Kondapuram"));
        list.add(new Employee(3,"VJ",9000.00,"Settipalem"));

        employeeController.deleteAllByEntity(list);
        verify(employeeServicesImpl, times(1)).deleteAllByEntity(list);
    }

    @Test
    @Order(19)
    @DisplayName("Testing of deleteAll")
    public void testdeleteAll(){
        employeeController.deleteAll();
        verify(employeeServicesImpl, times(1)).deleteAll();
    }

}
