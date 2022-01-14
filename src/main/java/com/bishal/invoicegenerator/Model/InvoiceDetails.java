package com.bishal.invoicegenerator.Model;

import java.time.LocalDate;
import java.util.Date;

public class InvoiceDetails {
    public String invoiceComponents;
    public LocalDate workedDate;
    public String startTime;
    public String endTime;
    public double rate;
    public double hours;
    public double units;

    public String getInvoiceComponents() {
        return invoiceComponents;
    }

    public void setInvoiceComponents(String invoiceComponents) {
        this.invoiceComponents = invoiceComponents;
    }

    public LocalDate getWorkedDate() {
        return workedDate;
    }

    public void setWorkedDate(LocalDate workedDate) {
        this.workedDate = workedDate;

    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
        System.out.println(startTime);
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

}
