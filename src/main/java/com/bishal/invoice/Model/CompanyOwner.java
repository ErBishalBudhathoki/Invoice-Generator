package com.bishal.invoice.Model;

import javafx.scene.control.Label;

import java.time.LocalDate;

public class CompanyOwner extends Label {
    public String companyName;
    public Long abn;
    public LocalDate periodStartingDate;
    public LocalDate periodEndingDate;
    public int id;

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

    public String jobTitle;

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
