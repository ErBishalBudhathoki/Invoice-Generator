package com.bishal.invoice.Model;

import javafx.scene.control.Label;

import java.time.LocalDate;

public class CompanyOwner extends Label {
    public String companyName, jobTitle;
    public Long abn;
    public LocalDate periodStartingDate;
    public LocalDate periodEndingDate;
    public int id;

    public CompanyOwner() {
//        try {
//            this.abn = Long.parseLong("");
//            this.companyName = "";
//            this.periodStartingDate = LocalDate.parse("");
//            this.periodEndingDate = LocalDate.parse("");
//        } catch(Exception ne){
//            System.out.println(ne);
//        }


    }

    public CompanyOwner(long abn, String companyName, LocalDate periodStartingDate, LocalDate periodEndingDate, String jobTitle) {
        this.abn = abn;
        this.companyName = companyName;
        this.periodStartingDate = periodStartingDate;
        this.periodEndingDate = periodEndingDate;
        this.jobTitle = jobTitle;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getID() {
        return  id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getAbn() {
        return abn;
    }

    public void setAbn(Long abn) {
        this.abn = abn;
    }

    public LocalDate getPeriodStartingDate() {
        return periodStartingDate;
    }

    public LocalDate setPeriodStartingDate(LocalDate periodStartingDate) {
        this.periodStartingDate = periodStartingDate;
        return periodStartingDate;
    }

    public LocalDate getPeriodEndingDate() {
        return periodEndingDate;
    }

    public LocalDate setPeriodEndingDate(LocalDate periodEndingDate) {
        this.periodEndingDate = periodEndingDate;
        return periodEndingDate;
    }
}
