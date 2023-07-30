package com.sbms.servicesImpl;

public class BankLoanService {

    public double calcsimpleIntrestAmount(double pAmt, double rate, double time){
        System.out.println("BankLoanService.calcsimpleIntrestAmount(-,-,-)");
        if (pAmt <= 0 || rate <= 0 || time <= 0){
            throw new IllegalArgumentException("Invalid Inputs");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return pAmt*rate*time/100;
    }
}
