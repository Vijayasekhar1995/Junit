package com.sbms;

import com.sbms.repositorys.LoginRepository;
import com.sbms.servicesI.LoginMgmtServiceI;
import com.sbms.servicesImpl.LoginMgmtServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

@DisplayName("Testing of LoginMgmtService")
public class LoginMgmtServiceTest {

    private static LoginRepository loginRepository;
    private static LoginMgmtServiceI loginMgmtServiceI;

    @BeforeAll
    public static void setupOnce(){
        loginRepository = Mockito.mock(LoginRepository.class);
        loginMgmtServiceI = new LoginMgmtServiceImpl(loginRepository);
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
