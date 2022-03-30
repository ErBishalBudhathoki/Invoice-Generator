package com.bishal.invoice.Model;

import java.sql.Time;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class InvoiceDetails {
    public String invoiceComponents;
    public LocalDate workedDate;
    public Time startTime;
    public Time endTime;
    public double rate;
    public double hours;
    public double units;
    Map<String, String> comp = new HashMap<>();
    public Map<String, String> invDet() {
        comp.put("01_010_0107_1_1","Assistance With Self-Care Activities - Night-Time Sleepover");
        comp.put("01_011_0107_1_1","Assistance With Self-Care Activities - Standard - Weekday Daytime");
        comp.put("01_011_0107_1_1_T","Assistance With Self-Care Activities - Standard - Weekday Daytime - TTP");
        comp.put("01_012_0107_1_1","Assistance With Self-Care Activities - Standard - Public Holiday");
        comp.put("01_012_0107_1_1_T","Assistance With Self-Care Activities - Standard - Public Holiday - TTP");
        comp.put("01_013_0107_1_1","Assistance With Self-Care Activities - Standard - Saturday");
        comp.put("01_013_0107_1_1_T","Assistance With Self-Care Activities - Standard - Saturday - TTP");
        comp.put("01_014_0107_1_1","Assistance With Self-Care Activities - Standard - Sunday");
        comp.put("01_014_0107_1_1_T","Assistance With Self-Care Activities - Standard - Sunday - TTP");
        comp.put("01_015_0107_1_1","Assistance With Self-Care Activities - Standard - Weekday Evening");
        comp.put("01_015_0107_1_1_T","Assistance With Self-Care Activities - Standard - Weekday Evening - TTP");
        return comp;
    }

    public String getInvoiceComponents() {
        return invoiceComponents;
    }

    public String setInvoiceComponents(String invoiceComponents) {
        this.invoiceComponents = invoiceComponents;
        return invoiceComponents;
    }

    public LocalDate getWorkedDate() {
        return workedDate;
    }

    public void setWorkedDate(LocalDate workedDate) {
        this.workedDate = workedDate;

    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
        System.out.println(startTime);
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
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
