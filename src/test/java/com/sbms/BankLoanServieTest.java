package com.sbms;

import com.sbms.servicesImpl.BankLoanService;
import org.junit.jupiter.api.*;

import java.time.Duration;
@DisplayName("Testing of BankLoanService")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class BankLoanServieTest {

    private static BankLoanService service;
    @BeforeAll
    public static void setUpOnce(){
        System.out.println("BankLoanServiceTest.setUpOnce()");
        service = new BankLoanService();
    }

    @Test
    @DisplayName("Testing with calcSimpleIntrestAmountWithBigNumbers")
    @Order(1)
    public void testcalcSimpleIntrestAmountWithBigNumbers(){
        System.out.println("BankLoanServieTest.testcalcSimpleIntrestAmountWithBigNumbers");
        double actual = service.calcsimpleIntrestAmount(1000000, 2, 12);
        double expected = 240000.0f;
        Assertions.assertEquals(expected,actual,"may be result not matching");
    }

    @Test
    @DisplayName("Testing with calcSimpleIntrestAmountWithSmallNumbers")
    @Order(2)
    public void testcalcSimpleIntrestAmountWithSmallNumbers(){
        System.out.println("BankLoanServieTest.testcalcSimpleIntrestAmountWithSmallNumbers");
        Double actual = service.calcsimpleIntrestAmount(10000, 2, 12);
        double expected = 2400.0f;
        Assertions.assertEquals(expected,actual,"may be result not matching");
    }

    @Test
    @DisplayName("Testing with calcSimpleIntrestAmountWithInvalidInputs")
    @Order(3)
    public void testcalcSimpleIntrestAmountWithInvalidInputs(){
        System.out.println("BankLoanServieTest.testcalcSimpleIntrestAmountWithInvalidInputs");
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.calcsimpleIntrestAmount(0,0,0));
    }

    @Test
    @DisplayName("Testing with calcSimpleIntrestAmountForNoException")
    @Order(4)
    public void testcalcSimpleIntrestAmountForNoException(){
        System.out.println("BankLoanServieTest.testcalcSimpleIntrestAmountForNoException");
        Assertions.assertDoesNotThrow(()->service.calcsimpleIntrestAmount(10000,2,12));
    }

    @Test
    @DisplayName("Testing with calcSimpleIntrestAmountWithTimer")
//    @Disabled
    @Order(5)
    public void testcalcSimpleIntrestAmountWithTimer(){
        System.out.println("BankLoanServieTest.testcalcSimpleIntrestAmountWithTimer");
        Assertions.assertTimeout(Duration.ofMillis(2000),()-> service.calcsimpleIntrestAmount(100000, 2, 12));
    }

    public static  void  clearOnce(){
        System.out.println("BankLoanServiceTest.clearOnce()");
        service= null;
    }



}
