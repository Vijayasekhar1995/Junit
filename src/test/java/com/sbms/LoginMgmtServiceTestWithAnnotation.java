package com.sbms;

import com.sbms.repositorys.LoginRepository;
import com.sbms.servicesI.LoginMgmtServiceI;
import com.sbms.servicesImpl.LoginMgmtServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@DisplayName("Testing of LoginMgmtService With Annotation")
public class LoginMgmtServiceTestWithAnnotation {
    @Mock
    private static LoginRepository loginRepository;
    @InjectMocks
    private static LoginMgmtServiceImpl loginMgmtServiceI;

    public  LoginMgmtServiceTestWithAnnotation(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testing with Valid Credentials")
    public void testloginWithValidCredentials(){
        Mockito.when(loginRepository.Authenticate("vijay","sekhar")).thenReturn(1);
        Assertions.assertTrue(loginMgmtServiceI.login("vijay","sekhar"));

    }

    @Test
    @DisplayName("Testing with InValid Credentials")
    public void testloginWithInValidCredentials(){
        Mockito.when(loginRepository.Authenticate("vijay","kalavakuri")).thenReturn(0);
        Assertions.assertFalse(loginMgmtServiceI.login("vijay","kalavakuri"));
    }

    @Test
    @DisplayName("Testing with No Credentials")
    public void testloginWithNoCredentials(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {loginMgmtServiceI.login("","");});
    }


}
