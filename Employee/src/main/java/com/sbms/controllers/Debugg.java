package com.sbms.controllers;

public class Debugg {

    public static void main(String[] args) {
        Debugg debugg = new Debugg();
        String grade = debugg.Grade(99, 76, 77);
        System.out.println("Grade is :: " + grade);
    }

    public String Grade(Integer sub1, Integer sub2, Integer sub3) {
        Double avgMarks = avgMarks(sub1, sub2, sub3);
        return (avgMarks >= 75.00) ? "A Grade" : (avgMarks >= 65.00) ? "B Grade" : (avgMarks >= 50.00) ? "C Grade" : "D grade";
    }

    public Double avgMarks(Integer sub1, Integer sub2, Integer sub3) {
        Double avgMarks;
        Integer totalMarks = this.totalMarks(sub1, sub2, sub3);
        avgMarks = (double) (totalMarks / 3);
        return avgMarks;
    }



    public Integer totalMarks(Integer sub1, Integer sub2, Integer sub3) {
        Integer total;
        total = sub1 + sub2 + sub3;
        return total;
    }

}
