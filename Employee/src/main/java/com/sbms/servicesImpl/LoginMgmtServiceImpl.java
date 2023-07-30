package com.sbms.servicesImpl;

import com.sbms.repositorys.LoginRepository;
import com.sbms.servicesI.LoginMgmtServiceI;

public class LoginMgmtServiceImpl implements LoginMgmtServiceI {

    private LoginRepository loginRepository;

    public LoginRepository getLoginRepository() {
        return loginRepository;
    }

    public void setLoginRepository(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LoginMgmtServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public boolean login(String username, String password) {

        if (username.equals("") || password.equals("")){
            throw new IllegalArgumentException("Empty Credentials");
        }
        Integer count = loginRepository.Authenticate(username, password);
        return count == 0 ? false : true;
    }


}
