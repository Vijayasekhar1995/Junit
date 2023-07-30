package com.sbms.RealDB;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbms.entitys.Employee;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
//    @MockBean
//    EmployeeServicesImpl employeeServicesImpl;

    @Autowired
    ObjectMapper objectMapper;

//    @Test
//    @Order(1)
//    @DisplayName("Testing of save By Sdet")
//    public void testsaveBySdet() throws Exception {
//        Employee employee = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
//        String string = objectMapper.writeValueAsString(employee);
//        when(employeeServicesImpl.save(employee)).thenReturn(employee);
//        this.mockMvc.perform(post("/save").content(string).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andDo(print());
//    }

//    @Test
//    @Order(2)
//    @DisplayName("Testing of save By AIT")
//    public void testsaveByAit() throws Exception {
//        Employee employee = new Employee(1,"Vijayasekhar",7000.00, "Kaligiri");
//        String string = objectMapper.writeValueAsString(employee);
//        when(employeeServicesImpl.save(ArgumentMatchers.any())).thenReturn(employee);
//        MvcResult mvcResult = this.mockMvc.perform(post("/save").content(string).contentType(MediaType.APPLICATION_JSON)).andReturn();
//        MockHttpServletResponse response = mvcResult.getResponse();
//        Integer status = response.getStatus();
//        assertEquals(201,status);
//    }

    
    @Test
    @Order(3)
    @DisplayName("Testing of save")
    public void testsave() throws Exception {
//      when we insert this record, JPA is generating eid as 2000 for this record. I already setted JPA in this way to generate eid for this record as 2000 by creating sequence genertor with a name employee_sequence (create sequence employee_sequence START with 2000 INCREMENT by 1;) for to generate primary key values for Employee Entity .
        Employee employeeRequest = new Employee("Vijayasekhar",7000.00, "Kaligiri");
        String employeeJsonRequest = objectMapper.writeValueAsString(employeeRequest);

        MvcResult mvcResult =  this.mockMvc.perform(post("/save").content(employeeJsonRequest).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Employee employeeRespose = new Employee(2000,"Vijayasekhar",7000.00, "Kaligiri");
        String employeeJsonResponse = objectMapper.writeValueAsString(employeeRespose);
        assertEquals(201,response.getStatus());
        assertEquals(employeeJsonResponse, response.getContentAsString());
    }

    @Test
    @Order(4)
    @DisplayName("Testing of saveAll")
    public void testsaveAll() throws Exception {

//      when we insert this record JPA is generating eid as 2001 for this record. I already setted JPA in this way to generate eid for this record as 2001.
        Employee employee1Request  = new Employee("Kalavakuri",7000.00, "Kaligiri");
//      when we insert this record JPA is generating eid as 2002 for this record. I already setted JPA in this way to generate eid for this record as 2001.
        Employee employee2Request  = new Employee("VJ",7000.00, "Kaligiri");

        List<Employee> employeesListRequest = new ArrayList<>();
        employeesListRequest.add(employee1Request);
        employeesListRequest.add(employee2Request);
        String employeesJsonListRequest = objectMapper.writeValueAsString(employeesListRequest);

        MockHttpServletResponse response = this.mockMvc.perform(post("/saveAll").content(employeesJsonListRequest).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();
        Employee employee1Response = new Employee(2001,"Kalavakuri",7000.00, "Kaligiri");
        Employee employee2Response = new Employee(2002,"VJ",7000.00, "Kaligiri");

        List<Employee> employeesListResponse = new ArrayList<>();
        employeesListResponse.add(employee1Response);
        employeesListResponse.add(employee2Response);

        String employeesJsonListResponse = objectMapper.writeValueAsString(employeesListResponse);
        assertEquals(201,response.getStatus());
        assertEquals(employeesJsonListResponse, response.getContentAsString());
    }

    @Test
    @Order(5)
    @DisplayName("Testing of findById If Employee Is Found ")
    public void testfindByIdIfEmployeeIsFound() throws Exception {
        Employee employeeResponse = new Employee(2000,"Vijayasekhar",7000.00, "Kaligiri");
        String employeeJsonResponse = objectMapper.writeValueAsString(employeeResponse);
        MockHttpServletResponse response = mockMvc.perform(get("/findById/2000")).andDo(print()).andReturn().getResponse();
        assertEquals(200,response.getStatus());
        assertEquals(employeeJsonResponse,response.getContentAsString());
    }

    @Test
    @Order(6)
    @DisplayName("Testing of findById If Employee Is Not Found ")
    public void testfindByIdIfEmployeeIsNotFound() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/findById/1000")).andDo(print()).andReturn().getResponse();
        assertEquals(400,response.getStatus());
        assertEquals("Employee was not found with an Id :: "+1000,response.getContentAsString());
    }

    @Test
    @Order(7)
    @DisplayName("Testing of findAll If Employees Is Found")
    public void testfindAllIfEmployeesIsFound() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/findAll")).andDo(print()).andReturn().getResponse();
        Employee employee1Response = new Employee(2000,"Vijayasekhar",7000.00, "Kaligiri");
        Employee employee2Response = new Employee(2001,"Kalavakuri",7000.00, "Kaligiri");
        Employee employee3Response = new Employee(2002,"VJ",7000.00, "Kaligiri");

        List<Employee> employeesListResponse = new ArrayList<>();
        employeesListResponse.add(employee1Response);
        employeesListResponse.add(employee2Response);
        employeesListResponse.add(employee3Response);
        List<Employee> collect = employeesListResponse.stream().sorted().collect(Collectors.toList());

        String employeesJsonListResponse = objectMapper.writeValueAsString(collect);
        assertEquals(200, response.getStatus());
        assertEquals(employeesJsonListResponse,response.getContentAsString());
    }

    @Test
    @Order(8)
    @DisplayName("Testing of findAll If Employees Is Not Found")
    public void testfindAllIfEmployeesIsNotFound() throws Exception {
//      Just Before call this method, I deleted all the records which are in the employee table. After Testing this method, I added the same records manually through SQL developer which are in the Employee table before calling this test method.
        MockHttpServletResponse response = mockMvc.perform(get("/findAll")).andDo(print()).andReturn().getResponse();
        assertEquals(400, response.getStatus());
        assertEquals("No Employees are there",response.getContentAsString());
    }

    @Test
    @Order(9)
    @DisplayName("Testing of findAllById If Employees are Found ")
    public void findAllByIdIfEmployeesAreFound() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/findAllById?id1=2000&id2=2001&id3=2002")).andDo(print()).andReturn().getResponse();

        Employee employee1Response = new Employee(2000,"Vijayasekhar",7000.00, "Kaligiri");
        Employee employee2Response = new Employee(2001,"Kalavakuri",7000.00, "Kaligiri");
        Employee employee3Response = new Employee(2002,"VJ",7000.00, "Kaligiri");

        List<Employee> employeesListResponse = new ArrayList<>();
        employeesListResponse.add(employee1Response);
        employeesListResponse.add(employee2Response);
        employeesListResponse.add(employee3Response);
        List<Employee> collect = employeesListResponse.stream().sorted().collect(Collectors.toList());

        String employeesJsonListResponse = objectMapper.writeValueAsString(collect);

        assertEquals(200,response.getStatus());
        assertEquals(employeesJsonListResponse,response.getContentAsString());
    }

    @Test
    @Order(10)
    @DisplayName("Testing of findAllById If Employees are Not Found ")
    public void findAllByIdIfEmployeesAreNotFound() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/findAllById?id1=1000&id2=1001&id3=1002")).andDo(print()).andReturn().getResponse();
        assertEquals(400,response.getStatus());
        assertEquals("No Employee Records are found whatever the Ids you are supplied",response.getContentAsString());
    }

    @Test
    @Order(11)
    @DisplayName("Testing of existById If Employee Is Exist")
    public void testexistByIdIfEmployeeIsExist() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/existById/2000")).andDo(print()).andReturn().getResponse();
        assertEquals(200, response.getStatus());
        assertEquals("Employee record is existed with an id :: " + 2000, response.getContentAsString());
    }

    @Test
    @Order(12)
    @DisplayName("Testing of existById If Employee Is Not Exist")
    public void testexistByIdIfEmployeeIsNotExist() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/existById/1000")).andDo(print()).andReturn().getResponse();
        assertEquals(400, response.getStatus());
        assertEquals("Employee Record is not existed with an id :: " + 1000, response.getContentAsString());
    }

    @Test
    @Order(13)
    @DisplayName("Testing of count If Employee Records Are Exist")
    public void testcountIfEmployeeRecordsAreExist() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/count")).andDo(print()).andReturn().getResponse();
        assertEquals(200, response.getStatus());
        assertEquals("The No.of Employee records are found is :: " + 3, response.getContentAsString());
    }

    @Test
    @Order(14)
    @DisplayName("Testing of count If Employee Records Are Not Exist")
    public void testcountIfEmployeeRecordsAreNotExist() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/count")).andDo(print()).andReturn().getResponse();
        assertEquals(400, response.getStatus());
        assertEquals("The No.of Employee records are found is :: " + 0, response.getContentAsString());
    }

    @Test
    @Order(15)
    @DisplayName("Testing of update")
    public void testupdate() throws Exception {
        Employee employee = new Employee(2000,"Vijayasekhar",7000.00, "Kaligiri");
        String string = objectMapper.writeValueAsString(employee);
        MockHttpServletResponse response = mockMvc.perform(put("/update").content(string).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();
        assertEquals(201,response.getStatus());
        assertEquals(string, response.getContentAsString());
    }

    @Test
    @Order(16)
    @DisplayName("Testing of updateAll")
    public void testupdateAll() throws Exception {
        Employee employee1 = new Employee(2000,"Vijayasekhar",7000.00, "Kaligiri");
        Employee employee2 = new Employee(2001,"Kalavakuri",7000.00, "Kaligiri");
        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);

        String string = objectMapper.writeValueAsString(list);
        MockHttpServletResponse response = mockMvc.perform(put("/updateAll").content(string).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();
        assertEquals(201,response.getStatus());
        assertEquals(string, response.getContentAsString());
    }

    @Test
    @Order(17)
    @DisplayName("Testing of deleteById")
    public void testdeleteById() throws Exception {
//      For Testing purpose I deleted the record which is having an id 2000 in the Employee table. After Testing this method, I added the same record manually through SQL developer which is having an id 2000 in the Employee table before calling this test method.
        MockHttpServletResponse response = mockMvc.perform(delete("/deleteById/2000")).andDo(print()).andReturn().getResponse();
        assertEquals(200,response.getStatus());
        assertEquals("Employee record deleted successfully with an id :: "+2000, response.getContentAsString());
    }

    @Test
    @Order(18)
    @DisplayName("Testing of deleteById With Exception")
    public void testdeleteByIdWithException() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(delete("/deleteById/1000")).andDo(print()).andReturn().getResponse();
        assertEquals(400,response.getStatus());
        assertEquals("There is no Employee Record with id :: "+1000, response.getContentAsString());
    }
    @Test
    @Order(19)
    @DisplayName("Testing of deleteAllById")
    public void testdeleteAllById() throws Exception {
//      For Testing purpose I deleted the records which is having an ids 2000 and 2001 in the Employee table. After Testing this method, I added the same records manually through SQL developer which is having an ids 2000 and 2001 in the Employee table before calling this test method.
        List<Integer> list = new ArrayList<>(Arrays.asList(2000, 2001));
        String integerList = objectMapper.writeValueAsString(list);
        MockHttpServletResponse response = mockMvc.perform(delete("/deleteAllById").content(integerList).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();
        assertEquals(200,response.getStatus());
        assertEquals("Employees deleted successfully for whatever the ids you supplied", response.getContentAsString());
    }

    @Test
    @Order(20)
    @DisplayName("Testing of deleteByEntity")
    public void testdeleteByEntity() throws Exception {
//      For Testing purpose I deleted the record which is having an id 2000 in the Employee table. After Testing this method, I added the same record manually through SQL developer which is having an id 2000 in the Employee table before calling this test method.
        Employee employee = new Employee(2000,"Vijayasekhar",7000.00,"Kaligiri");
        String employeeJson = objectMapper.writeValueAsString(employee);
        MockHttpServletResponse response = mockMvc.perform(delete("/deleteByEntity").content(employeeJson).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();
        assertEquals(200,response.getStatus());
        assertEquals("Employee deleted Successfully", response.getContentAsString());
    }

    @Test
    @Order(21)
    @DisplayName("Testing of deleteAllByEntity")
    public void testdeleteAllByEntity() throws Exception {
//      For Testing purpose I deleted the records which is having an ids 2000, 2001 and 2001 in the Employee table. After Testing this method, I added the same records manually through SQL developer which is having an ids 2000, 2001 and 2002 in the Employee table before calling this test method.
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(2000,"Vijayasekhar",7000.00,"Kaligiri"));
        list.add(new Employee(2001, "Kalavakuri", 8000.00,"Kondapuram"));
        list.add(new Employee(2002,"VJ",9000.00,"Settipalem"));
        String employeesJsonList = objectMapper.writeValueAsString(list);
        MockHttpServletResponse response = mockMvc.perform(delete("/deleteAllByEntity").content(employeesJsonList).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andReturn().getResponse();
        assertEquals(200,response.getStatus());
        assertEquals("Employees are deleted Successfully", response.getContentAsString());
    }

    @Test
    @Order(22)
    @DisplayName("Testing of deleteAll")
    public void testdeleteAll() throws Exception {
//      For Testing purpose I deleted the all records which are in the Employee table. After Testing this method, I didn't added the same records manually through SQL developer which are in the Employee table before calling this test method. because, After testing this test method no more methods are not there to test in this class.
        MockHttpServletResponse response = mockMvc.perform(delete("/deleteAll")).andDo(print()).andReturn().getResponse();
        assertEquals(200,response.getStatus());
        assertEquals("All Employee Records are deleted Successfully", response.getContentAsString());
    }
    
}
