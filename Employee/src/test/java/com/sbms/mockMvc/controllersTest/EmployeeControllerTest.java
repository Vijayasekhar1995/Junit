package com.sbms.mockMvc.controllersTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbms.entitys.Employee;
import com.sbms.servicesImpl.EmployeeServicesImpl;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.sbms")
@AutoConfigureMockMvc
@ContextConfiguration
@DisplayName("Testing of Employee Controller by MockMvc")
@SpringBootTest
public class EmployeeControllerTest {

//  To work Spring Annotations properly (like @Autowired Annotation etc., ) we must keep @SpringBootTest Annotation above Test Class.

    @Autowired
    MockMvc mockMvc;

//  At the time of usage of mockMvc if we use @Mock and @InjectMocks to mock the data , data will not Mock. When we call the particular method  that method directly hit the DB if we use @Mock and @InjectMocks.to overcome this we can use @MockBean
    @MockBean
    EmployeeServicesImpl employeeServicesImpl;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    @DisplayName("Testing of save By Sdet")
    public void testsaveBySdet() throws Exception {
        Employee employee = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        String string = objectMapper.writeValueAsString(employee);
        when(employeeServicesImpl.save(employee)).thenReturn(employee);
        this.mockMvc.perform(post("/save").content(string).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andDo(print());
    }

    @Test
    @Order(2)
    @DisplayName("Testing of save By AIT")
    public void testsaveByAit() throws Exception {
        Employee employee = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        String string = objectMapper.writeValueAsString(employee);
        when(employeeServicesImpl.save(ArgumentMatchers.any())).thenReturn(employee);
        MvcResult mvcResult = this.mockMvc.perform(post("/save").content(string).contentType(MediaType.APPLICATION_JSON)).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Integer status = response.getStatus();
        assertEquals(201,status);
    }

    @Test
    @Order(3)
    @DisplayName("Testing of save")
    public void testsave() throws Exception {
        Employee employee = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        String employeeJson = objectMapper.writeValueAsString(employee);
        when(employeeServicesImpl.save(employee)).thenReturn(employee);
        MvcResult mvcResult =  this.mockMvc.perform(post("/save").content(employeeJson).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(201,response.getStatus());
        assertEquals(employeeJson, response.getContentAsString());
    }

    @Test
    @Order(4)
    @DisplayName("Testing of saveAll")
    public void testsaveAll() throws Exception {
        Employee employee1 = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        Employee employee2 = new Employee(1,"Kalavakuri",7000.00, "Kaligiri");
        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);
        String employeesJsonList = objectMapper.writeValueAsString(list);
        when(employeeServicesImpl.saveAll(list)).thenReturn(list);
        MockHttpServletResponse response = this.mockMvc.perform(post("/saveAll").content(employeesJsonList).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();
        assertEquals(201,response.getStatus());
        assertEquals(employeesJsonList, response.getContentAsString());
    }

    @Test
    @Order(5)
    @DisplayName("Testing of findById If Employee Is Found ")
    public void testfindByIdIfEmployeeIsFound() throws Exception {
        Employee employee = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        when(employeeServicesImpl.findById(1)).thenReturn(employee);
        String employeeJson = objectMapper.writeValueAsString(employee);
        MockHttpServletResponse response = mockMvc.perform(get("/findById/1")).andDo(print()).andReturn().getResponse();
        assertEquals(200,response.getStatus());
        assertEquals(employeeJson,response.getContentAsString());
    }

    @Test
    @Order(6)
    @DisplayName("Testing of findById If Employee Is Not Found ")
    public void testfindByIdIfEmployeeIsNotFound() throws Exception {
        when(employeeServicesImpl.findById(1000)).thenReturn("Employee was not found with an Id :: "+1000);
        MockHttpServletResponse response = mockMvc.perform(get("/findById/1000")).andDo(print()).andReturn().getResponse();
        assertEquals(400,response.getStatus());
        assertEquals("Employee was not found with an Id :: "+1000,response.getContentAsString());
    }

    @Test
    @Order(7)
    @DisplayName("Testing of findAll If Employees Is Found")
    public void testfindAllIfEmployeesIsFound() throws Exception {
        Employee employee1 = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        Employee employee2 = new Employee(1,"Kalavakuri",7000.00, "Kaligiri");
        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);
        when(employeeServicesImpl.findAll()).thenReturn(list);
        String employeesJsonList = objectMapper.writeValueAsString(list);
        MockHttpServletResponse response = mockMvc.perform(get("/findAll")).andDo(print()).andReturn().getResponse();
        assertEquals(200, response.getStatus());
        assertEquals(employeesJsonList,response.getContentAsString());
    }

    @Test
    @Order(8)
    @DisplayName("Testing of findAll If Employees Is Not Found")
    public void testfindAllIfEmployeesIsNotFound() throws Exception {
        List<Employee> list = new ArrayList<>();
        when(employeeServicesImpl.findAll()).thenReturn(list);
        MockHttpServletResponse response = mockMvc.perform(get("/findAll")).andDo(print()).andReturn().getResponse();
        assertEquals(400, response.getStatus());
        assertEquals("No Employees are there",response.getContentAsString());
    }

    @Test
    @Order(9)
    @DisplayName("Testing of findAllById If Employees are Found ")
    public void findAllByIdIfEmployeesAreFound() throws Exception {
        Employee employee1 = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        Employee employee2 = new Employee(2,"Kalavakuri",7000.00, "Kaligiri");
        Employee employee3 = new Employee(3,"VJ",7000.00, "Kaligiri");
        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);
        list.add(employee3);
        when(employeeServicesImpl.findAllById(List.of(1,2,3))).thenReturn(list);
        String string = objectMapper.writeValueAsString(list);
        MockHttpServletResponse response = mockMvc.perform(get("/findAllById?id1=1&id2=2&id3=3")).andDo(print()).andReturn().getResponse();
        assertEquals(200,response.getStatus());
        assertEquals(string,response.getContentAsString());
    }

    @Test
    @Order(10)
    @DisplayName("Testing of findAllById If Employees are Not Found ")
    public void findAllByIdIfEmployeesAreNotFound() throws Exception {
        List<Employee> list = new ArrayList<>();
        when(employeeServicesImpl.findAllById(List.of(1,2,3))).thenReturn(list);
        String string = objectMapper.writeValueAsString(list);
        MockHttpServletResponse response = mockMvc.perform(get("/findAllById?id1=1&id2=2&id3=3")).andDo(print()).andReturn().getResponse();
        assertEquals(400,response.getStatus());
        assertEquals("No Employee Records are found whatever the Ids you are supplied",response.getContentAsString());
    }

    @Test
    @Order(11)
    @DisplayName("Testing of existById If Employee Is Exist")
    public void testexistByIdIfEmployeeIsExist() throws Exception {
        when(employeeServicesImpl.existById(1)).thenReturn(true);
        MockHttpServletResponse response = mockMvc.perform(get("/existById/1")).andDo(print()).andReturn().getResponse();
        assertEquals(200, response.getStatus());
        assertEquals("Employee record is existed with an id :: " + 1, response.getContentAsString());
    }

    @Test
    @Order(12)
    @DisplayName("Testing of existById If Employee Is Not Exist")
    public void testexistByIdIfEmployeeIsNotExist() throws Exception {
        when(employeeServicesImpl.existById(1000)).thenReturn(false);
        MockHttpServletResponse response = mockMvc.perform(get("/existById/1000")).andDo(print()).andReturn().getResponse();
        assertEquals(400, response.getStatus());
        assertEquals("Employee Record is not existed with an id :: " + 1000, response.getContentAsString());
    }

    @Test
    @Order(13)
    @DisplayName("Testing of count If Employee Records Are Exist")
    public void testcountIfEmployeeRecordsAreExist() throws Exception {
        when(employeeServicesImpl.count()).thenReturn(31l);
        MockHttpServletResponse response = mockMvc.perform(get("/count")).andDo(print()).andReturn().getResponse();
        assertEquals(200, response.getStatus());
        assertEquals("The No.of Employee records are found is :: " + 31, response.getContentAsString());
    }

    @Test
    @Order(14)
    @DisplayName("Testing of count If Employee Records Are Not Exist")
    public void testcountIfEmployeeRecordsAreNotExist() throws Exception {
        when(employeeServicesImpl.count()).thenReturn(0l);
        MockHttpServletResponse response = mockMvc.perform(get("/count")).andDo(print()).andReturn().getResponse();
        assertEquals(400, response.getStatus());
        assertEquals("The No.of Employee records are found is :: " + 0, response.getContentAsString());
    }

    @Test
    @Order(15)
    @DisplayName("Testing of update")
    public void testupdate() throws Exception {
        Employee employee = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        when(employeeServicesImpl.update(employee)).thenReturn(employee);
        String string = objectMapper.writeValueAsString(employee);
        MockHttpServletResponse response = mockMvc.perform(put("/update").content(string).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();
        assertEquals(201,response.getStatus());
        assertEquals(string, response.getContentAsString());
    }

    @Test
    @Order(16)
    @DisplayName("Testing of updateAll")
    public void testupdateAll() throws Exception {
        Employee employee1 = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
        Employee employee2 = new Employee(1,"Kalavakuri",7000.00, "Kaligiri");
        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);
        when(employeeServicesImpl.updateAll(list)).thenReturn(list);
        String string = objectMapper.writeValueAsString(list);
        MockHttpServletResponse response = mockMvc.perform(put("/updateAll").content(string).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();
        assertEquals(201,response.getStatus());
        assertEquals(string, response.getContentAsString());
    }

    @Test
    @Order(17)
    @DisplayName("Testing of deleteById")
    public void testdeleteById() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(delete("/deleteById/1")).andDo(print()).andReturn().getResponse();
        assertEquals(200,response.getStatus());
        assertEquals("Employee record deleted successfully with an id :: "+1, response.getContentAsString());
        verify(employeeServicesImpl, times(1)).deleteById(1);
    }

    @Test
    @Order(18)
    @DisplayName("Testing of deleteAllById")
    public void testdeleteAllById() throws Exception {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        String integerList = objectMapper.writeValueAsString(list);
        MockHttpServletResponse response = mockMvc.perform(delete("/deleteAllById").content(integerList).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();
        assertEquals(200,response.getStatus());
        assertEquals("Employees deleted successfully for whatever the ids you supplied", response.getContentAsString());
        verify(employeeServicesImpl, times(1)).deleteAllById(list);
    }

    @Test
    @Order(19)
    @DisplayName("Testing of deleteByEntity")
    public void testdeleteByEntity() throws Exception {
        Employee employee = new Employee(1,"Vijayasekhar",7000.00,"Kaligiri");
        String employeeJson = objectMapper.writeValueAsString(employee);
        MockHttpServletResponse response = mockMvc.perform(delete("/deleteByEntity").content(employeeJson).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();
        assertEquals(200,response.getStatus());
        assertEquals("Employee deleted Successfully", response.getContentAsString());
        verify(employeeServicesImpl, times(1)).deleteByEntity(employee);
    }

    @Test
    @Order(20)
    @DisplayName("Testing of deleteAllByEntity")
    public void testdeleteAllByEntity() throws Exception {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1,"Vijayasekhar",7000.00,"Kaligiri"));
        list.add(new Employee(2, "Kalavakuri", 8000.00,"Kondapuram"));
        list.add(new Employee(3,"VJ",9000.00,"Settipalem"));
        String employeesJsonList = objectMapper.writeValueAsString(list);
        MockHttpServletResponse response = mockMvc.perform(delete("/deleteAllByEntity").content(employeesJsonList).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();
        assertEquals(200,response.getStatus());
        assertEquals("Employees are deleted Successfully", response.getContentAsString());
        verify(employeeServicesImpl, times(1)).deleteAllByEntity(list);
    }

    @Test
    @Order(21)
    @DisplayName("Testing of deleteAll")
    public void testdeleteAll() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(delete("/deleteAll")).andDo(print()).andReturn().getResponse();
        assertEquals(200,response.getStatus());
        assertEquals("All Employee Records are deleted Successfully", response.getContentAsString());
        verify(employeeServicesImpl, times(1)).deleteAll();
    }
}
