package com.sbms.servicesTest;

import com.sbms.entitys.Employee;
import com.sbms.repositorys.EmployeeRepository;
import com.sbms.servicesImpl.EmployeeServicesImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Testing of EmployeeService")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)

public class EmployeeServiceTest {


    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServicesImpl employeeServicesImpl;

    public  EmployeeServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    @DisplayName("Testing of save")
    public void testsave(){
         Employee employee = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
         when(employeeRepository.save(employee)).thenReturn(employee);
         assertEquals(employee, employeeServicesImpl.save(employee));
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
        when(employeeRepository.saveAll(list)).thenReturn(list);
        assertEquals(list,employeeServicesImpl.saveAll(list));
    }

    @Test
    @Order(3)
    @DisplayName("Testing of findById If Employee Is Found ")
    public void testfindByIdIfEmployeeIsFound(){
        Employee employee1 = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        when(employeeRepository.findById(1)).thenReturn(Optional.ofNullable(employee1));
        assertEquals((Object) employee1,employeeServicesImpl.findById(1));
    }

    @Test
    @Order(4)
    @DisplayName("Testing of findById If Employee Is Not Found ")
    public void testfindByIdIfEmployeeIsNotFound(){
        when(employeeRepository.findById(1000)).thenReturn(Optional.empty());
        String string = "Employee was not found with an Id :: "+1000;
        assertEquals((Object) string,employeeServicesImpl.findById(1000));
    }
    @Test
    @Order(5)
    @DisplayName("Testing of findAll")
    public void testfindAll(){
        Employee employee1 = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        Employee employee2 = new Employee(1,"Kalavakuri",7000.00, "Kaligiri");
        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);
        when(employeeRepository.findAll()).thenReturn(list);
        assertEquals(list,employeeServicesImpl.findAll());
    }

    @Test
    @Order(6)
    @DisplayName("Testing of findAllById")
    public void findAllById() {
        Employee employee1 = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        Employee employee2 = new Employee(2,"Kalavakuri",7000.00, "Kaligiri");
        Employee employee3 = new Employee(3,"VJ",7000.00, "Kaligiri");
        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);
        list.add(employee3);
        when(employeeRepository.findAllById(List.of(1,2,3))).thenReturn(list);
        assertEquals(list,employeeServicesImpl.findAllById(List.of(1,2,3)));
    }

    @Test
    @Order(7)
    @DisplayName("Testing of findAllById")
    public void testfindAllById(){
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1,"Vijayasekhar",7000.00,"Kaligiri"));
        list.add(new Employee(2, "Kalavakuri", 8000.00,"Kondapuram"));
        list.add(new Employee(3,"VJ",9000.00,"Settipalem"));
        when(employeeRepository.findAllById(List.of(1,2,3))).thenReturn(list);
        assertEquals(list, employeeServicesImpl.findAllById(List.of(1,2,3)));
    }

    @Test
    @Order(8)
    @DisplayName("Testing of existById")
    public void testexistById(){
        when(employeeRepository.existsById(3)).thenReturn(true);
        assertEquals(true,employeeServicesImpl.existById(3));
    }

    @Test
    @Order(9)
    @DisplayName("Testing of count")
    public void testcount(){
        when(employeeRepository.count()).thenReturn(3l);
        assertEquals(3,employeeServicesImpl.count());
    }

    @Test
    @Order(10)
    @DisplayName("Testing of update")
    public void testupdate(){
        Employee employee = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        when(employeeRepository.save(employee)).thenReturn(employee);
        assertEquals(employee, employeeServicesImpl.update(employee));
    }
    @Test
    @Order(11)
    @DisplayName("Testing of updateAll")
    public void testupdateAll(){
        Employee employee1 = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        Employee employee2 = new Employee(1,"Kalavakuri",7000.00, "Kaligiri");
        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);
        when(employeeRepository.saveAll(list)).thenReturn(list);
        assertEquals(list,employeeServicesImpl.updateAll(list));
    }

    @Test
    @Order(12)
    @DisplayName("Testing of deleteById")
    public void testdeleteById(){
        employeeServicesImpl.deleteById(4);
        verify(employeeRepository, times(1)).deleteById(4);
    }

    @Test
    @Order(13)
    @DisplayName("Testing of deleteAllById")
    public void testdeleteAllById(){
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        employeeServicesImpl.deleteAllById(list);
        verify(employeeRepository, times(1)).deleteAllById(list);
    }

    @Test
    @Order(14)
    @DisplayName("Testing of deleteByEntity")
    public void testdeleteByEntity(){
        Employee employee = new Employee(1,"Vijayasekhar",7000.00,"Kaligiri");
        employeeServicesImpl.deleteByEntity(employee);
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    @Order(15)
    @DisplayName("Testing of deleteAllByEntity")
    public void testdeleteAllByEntity(){
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1,"Vijayasekhar",7000.00,"Kaligiri"));
        list.add(new Employee(2, "Kalavakuri", 8000.00,"Kondapuram"));
        list.add(new Employee(3,"VJ",9000.00,"Settipalem"));

        employeeServicesImpl.deleteAllByEntity(list);
        verify(employeeRepository, times(1)).deleteAll(list);
    }

    @Test
    @Order(16)
    @DisplayName("Testing of deleteAll")
    public void testdeleteAll(){
        employeeServicesImpl.deleteAll();
        verify(employeeRepository, times(1)).deleteAll();
    }
}
