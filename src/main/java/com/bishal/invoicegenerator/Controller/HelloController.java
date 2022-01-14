package com.bishal.invoicegenerator.Controller;

import com.bishal.invoicegenerator.Model.BillTo;
import com.bishal.invoicegenerator.Model.CompanyOwner;
import com.bishal.invoicegenerator.Model.InvoiceDetails;
import com.bishal.invoicegenerator.Model.GeneratePDFInvoice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

public class HelloController {
    @FXML
    public TabPane tabPane;
    public TextField companyName_Owner;
    public TextField ABN;
    public DatePicker periodStarting;
    public DatePicker periodEnding;
    public TextField jobTitle;
    public TextField billToName;
    public TextField street;
    public TextField suburb;
    public TextField state;
    public TextField postalCode;
    public TextField companyName_billTo;
    public TextField invoiceComponents;
    public DatePicker workedDate;
    public TextField startTime;
    public TextField endTime;
    public TextField rate;

    long days;
    LocalDate startDate;
    LocalDate endingDate;
    @FXML
    protected void companyOwnerNextButtonClick() {
        CompanyOwner ownerName = new CompanyOwner();
        ownerName.setCompanyName(companyName_Owner.getText());
        String abn = ABN.getText();
        if (ABN.getText().matches("^[0-9]{11}$")){
                System.out.println("Inside if loop");
                ownerName.setAbn(ABN.getText());
        }
        else {
            System.out.println("Inside else loop");
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "ABN should be of 11 digits only and no alphabets or symbols");
            alert.show();
            return;
        }

        String a = ownerName.getAbn();
        startDate = ownerName.setPeriodStartingDate(periodStarting.getValue());
        endingDate = ownerName.setPeriodEndingDate(periodEnding.getValue());
        days = Period.between(startDate, endingDate).getDays() + 1;
        System.out.println(days + "Days");
        ownerName.setJobTitle(jobTitle.getText());
        System.out.println(ownerName.getCompanyName() +" " + a +" " + ownerName.getPeriodStartingDate() +" "
                + ownerName.getPeriodEndingDate() +" " + ownerName.getJobTitle());

        tabPane.getSelectionModel().selectNext();
    }

    @FXML
    public void billToNextButtonClick() {
        BillTo bill = new BillTo();
        bill.setClientName(billToName.getText());
        bill.setStreet(street.getText());
        bill.setSuburb(suburb.getText());
        bill.setState(state.getText());
        if (postalCode.getText().matches("^[0-9]{4}$")){
            System.out.println("Inside if loop");
            bill.setPostalCode(Integer.parseInt(postalCode.getText()));
        }
        else {
            System.out.println("Inside else loop");
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Postal code should be of 4 digits only and no alphabets or symbols");
            alert.show();
        }

        bill.setClientCompanyName(companyName_billTo.getText());
        System.out.println(bill.getClientName() +" " + bill.getStreet() +" " + bill.getSuburb() +" "
                + bill.getState()+" " + bill.getPostalCode()+" " + bill.getClientCompanyName());

        tabPane.getSelectionModel().selectNext();
    }


    int counter = 0;
    long totalMinutes;
    @FXML
    public void addComponents() throws ParseException {

        counter++;
//        if (counter <= days) { //this checks if the number of days between period starting and ending is
//        equal to number of input to be accepted for invoice details tab
            InvoiceDetails invoiceDetails = new InvoiceDetails();
            invoiceDetails.setInvoiceComponents(invoiceComponents.getText());
            invoiceDetails.setWorkedDate(workedDate.getValue());
            if (startTime.getText().matches("^[0-9]{1,2}(\\:[0-9][0-9])$") && endTime.getText().matches("^[0-9]{1,2}(\\:[0-9][0-9])$") ){
                System.out.println("Inside if loop");
                invoiceDetails.setStartTime(startTime.getText());
                invoiceDetails.setEndTime(endTime.getText());
                String s = startTime.getText();
                String e = endTime.getText();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                LocalTime date1 = (LocalTime.parse(s));
                LocalTime date2 = (LocalTime.parse(e));
                System.out.println(date1.getMinute()+" " + date1);
                totalMinutes = (date2.getHour()*60 + date2.getMinute()) - (date1.getHour()*60 + date1.getMinute());
                //long diffMinutes = difference / 1000;
                System.out.println( "minutes " + totalMinutes +" and " +date2.getHour());
            }
            else {
                System.out.println("Inside else loop");
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Start time and end time must be in HH:MM format");
                alert.show();
                return;
            }


            if (rate.getText().matches("^[0-9]{1,4}(\\.[0-9][0-9])$")){
                System.out.println("Inside if loop");
                invoiceDetails.setRate(Double.parseDouble(rate.getText()));
            }
            else {
                System.out.println("Inside else loop");
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Rate should be numerical only and no alphabets or symbols");
                alert.show();
                return;
            }

            System.out.println(invoiceDetails.getInvoiceComponents() +" " + invoiceDetails.getWorkedDate() +" " +
                    invoiceDetails.getStartTime() +" " + invoiceDetails.getEndTime()+" " + invoiceDetails.getRate()+" ");

            invoiceComponents.clear();
            workedDate.setValue(null);
            startTime.clear();
            endTime.clear();
            rate.clear();

        }
//        else {
//            Alert alert = new Alert(Alert.AlertType.ERROR,
//                    "Your working period is of " +days +" day/s so you can't enter more than working period starting "
//                            +startDate +" and ending at " +endingDate);
//            alert.show();
//        }
//
//    }

    GeneratePDFInvoice print = new GeneratePDFInvoice();
    @FXML
    public void generateButtonClick(ActionEvent actionEvent) {
        print.print();
    }
}