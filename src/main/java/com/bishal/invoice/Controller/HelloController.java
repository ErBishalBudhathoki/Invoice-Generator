package com.bishal.invoice.Controller;

import com.bishal.invoice.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Map;

public class HelloController {
    @FXML
    public Pane pane_iod,pane_billTo,pane_invDet,pane_sendEmail;
    public Button invoiceOwner,billTo,invoiceDetail,sendEmail;
    public TextField passwordBox;

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

    public DatePicker workedDate;
    public TextField startTime;
    public TextField endTime;
    public TextField rate;
    public ComboBox<String> invoiceComponents;
    long days;
    LocalDate startDate;
    LocalDate endingDate;
    Database db = new Database();
    InvoiceDetails invoiceDetails = new InvoiceDetails();
    public ComboBox<String> btName;
    public void initialize () throws SQLException {
        pane_iod.toFront();
        //invoiceComponents.setPromptText("Please select the invoice components");
        Map<String, String> inv = invoiceDetails.invDet();
        for (Map.Entry<String, String> entry: inv.entrySet()) {
            invoiceComponents.getItems().add(String.valueOf(entry));
        }
        try {
            ArrayList<String> btName = db.loadbtName();
            for (String s : btName) {
                this.btName.getItems().add(String.valueOf(s));
            }
        } catch (NullPointerException n) {
            System.out.println(n);
        }

//        icOptions.setValue("A");
//        System.out.println(icOptions.getValue());
    }
    public HelloController() throws SQLException, ClassNotFoundException {
//        icOptions.getItems().addAll("abc");
//        ComboBox emailComboBox = new ComboBox();

    }
    @FXML
    protected void handleButtonAction(ActionEvent actionEvent) {
        if (actionEvent.getSource() == invoiceOwner) {
            pane_iod.toFront();
        } else if (actionEvent.getSource() == billTo) {
            pane_billTo.toFront();
        }  else if (actionEvent.getSource() == invoiceDetail) {
            pane_invDet.toFront();
        }  else if (actionEvent.getSource() == sendEmail) {
            pane_sendEmail.toFront();
        }
    }
    @FXML
    protected void loadNameButton() throws SQLException {
        ArrayList<String> loadName = db.loadNameDetals(btName.getValue());
        try {
            if (!loadName.isEmpty()) {
                billToName.setText(loadName.get(0));
                street.setText(loadName.get(1));
                suburb.setText(loadName.get(2));
                state.setText(loadName.get(3));
                postalCode.setText(loadName.get(4));
                companyName_billTo.setText(loadName.get(5));
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "No data in the database");
                alert.show();
            }
        } catch (NullPointerException nullPointerException) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "No data selected or no data in the database");
            alert.show();
        }

    }

    public boolean checkABNLength(long abn){
        boolean value = Long.toString(abn).matches("^[0-9]{11}$");
        return value;
    }
    ArrayList result;
    CompanyOwner ownerName = new CompanyOwner();
    @FXML
    protected void companyOwnerNextButtonClick() {

        ownerName.setCompanyName(companyName_Owner.getText());
        String companyName = ownerName.getCompanyName();
//        BigInteger bigIntFromString = new BigInteger(ABN.getText());
        Long abn = Long.valueOf(ABN.getText());
        //int a = Integer.parseInt(ABN.getText());
        if (checkABNLength(abn)) {
            System.out.println("Inside if loop");
            ownerName.setAbn(abn);

        }
        else {
            System.out.println("Inside else loop");
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "ABN should be of 11 digits only and no alphabets or symbols");
            alert.show();
            return;
        }
        //int intFromBigInt = bigIntFromString.intValue();
        Long abn1 = ownerName.getAbn();
        System.out.println(abn1 +"ABN");
        try {
            abn1 = ownerName.getAbn();
            System.out.println("ABN: " + abn1);
        } catch (NumberFormatException e) {
            System.out.println("not a number");
        }

        try {
            startDate = ownerName.setPeriodStartingDate(periodStarting.getValue());
            endingDate = ownerName.setPeriodEndingDate(periodEnding.getValue());
            days = Period.between(startDate, endingDate).getDays() + 1;
            System.out.println(days + "Days");
            ownerName.setJobTitle(jobTitle.getText());
            System.out.println(companyName + " " + abn1 + " " + ownerName.getPeriodStartingDate() + " "
                    + ownerName.getPeriodEndingDate() + " " + ownerName.getJobTitle());
            result = null;
            result = db.checkABN(abn1);
//            String s =  result.get(0);

            //System.out.println("Result: " +result);
            if (result == null) { //if record does not exist
                db.insertIOD(companyName, abn1, ownerName.getPeriodStartingDate(), ownerName.getPeriodEndingDate(),
                        ownerName.getJobTitle());
            } else {
                if (db.containsPeriod((Integer) result.get(0), startDate, endingDate)) {
                    System.out.println("Details Exist" + result.get(0));
                    db.setLast_inserted_id((Integer) result.get(0));
                } else {
                    //db.setLast_inserted_id( (int)result.get(0)); //check here ???
                    db.insertIOD(companyName, abn1, ownerName.getPeriodStartingDate(), ownerName.getPeriodEndingDate(),
                            ownerName.getJobTitle());
                }

            }
//            db.insertIOD(companyName, abn1, ownerName.getPeriodStartingDate(), ownerName.getPeriodEndingDate(),
//                    ownerName.getJobTitle());
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Empty Fields");
            alert.show();
        }
//        tabPane.getSelectionModel().selectNext();
        pane_billTo.toFront();
    }

    @FXML
    protected void check() throws SQLException {
        try {


            if (checkABNLength(Long.parseLong(ABN.getText()))) {
                result = db.checkABN(Long.valueOf(ABN.getText()));
                if (result != null) {
                    String compName = (String) result.get(2);
                    String jobTtle = (String) result.get(1);

//        companyName_Owner.setText(result.getString("companyName"));
                    jobTitle.setText(jobTtle);
                    companyName_Owner.setText(compName);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "ABN does not exist");
                    alert.show();
                }
            } else {
                System.out.println("Inside else loop");
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "ABN should be of 11 digits only and no alphabets or symbols");
                alert.show();
//            return;
            }

        } catch (NumberFormatException numberFormatException) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "ABN should be not be empty");
            alert.show();
        }
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
        try {
            ArrayList result = db.checkBillTo(bill.getClientName(), db.getLast_inserted_id());
            System.out.println("Result:" +result);
            if ( !street.getText().isEmpty() && !suburb.getText().isEmpty()
                    && !state.getText().isEmpty() && !postalCode.getText().isEmpty() &&
                    !companyName_billTo.getText().isEmpty()) {
                if (result == null ) {
                    db.insertBillTo(bill.getClientName(), bill.getStreet(), bill.getSuburb(), bill.getState(),
                            bill.getPostalCode(), bill.getClientCompanyName());
                    pane_invDet.toFront();
                }
                else {
                    //System.out.println("Hello: "+(String) result.get(2) +""+result.get(3) +""+result.get(4) +""+result.get(5) +""+result.get(1));
                   billToName.setText((String) result.get(1));
                   street.setText((String)result.get(2));
                    suburb.setText((String) result.get(3));
                    state.setText((String) result.get(4));
                    postalCode.setText(String.valueOf(result.get(5)));
                    companyName_billTo.setText((String) result.get(6));
                    int setID = Integer.parseInt((String) result.get(0));
                    db.setLast_inserted_id(setID);
                    pane_invDet.toFront();
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    boolean isWithinRange(Date testDate) {
        java.util.Date d1 =  Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.util.Date d2 =  Date.from(endingDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        if (testDate.before(d1) || testDate.after(d2)) {
            System.out.println("Date Test Fail");
            return false;
        } else {
            return true;
        }

    }

    int counter = 0;
    long totalMinutes = 0;
    @FXML
    public void addComponents() throws ParseException {

        counter++;
//        if (counter <= days) { //this checks if the number of days between period starting and ending is
//        equal to number of input to be accepted for invoice details tab


//
//        icOptions.setValue("abc");
        try {
            String s = invoiceDetails.setInvoiceComponents(invoiceComponents.getValue());
            System.out.println(s);
            if (isWithinRange(Date.valueOf(workedDate.getValue()))) {
                invoiceDetails.setWorkedDate(workedDate.getValue());
            }


            if (startTime.getText().matches("^[0-9]{1,2}(\\:[0-9][0-9])$") && endTime.getText().matches("^[0-9]{1,2}(\\:[0-9][0-9])$") ){
                System.out.println("Inside if loop");

                String startTimeText = startTime.getText();
                String endTimeText = endTime.getText();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                LocalTime date1 = (LocalTime.parse(startTimeText));
                LocalTime date2 = (LocalTime.parse(endTimeText));

                System.out.println(date1.getMinute()+" Date1 " + date1);
                totalMinutes = (date2.getHour()*60 + date2.getMinute()) - (date1.getHour()*60 + date1.getMinute());
                double hours = totalMinutes / 60.00;

                System.out.println( "minutes " + totalMinutes +" and " +date2.getHour() +" Hour");
                Time sTime = Time.valueOf((LocalTime.parse(startTimeText)));
                Time eTime = Time.valueOf((LocalTime.parse(endTimeText)));

                invoiceDetails.setStartTime(sTime);
                invoiceDetails.setEndTime(eTime);
                invoiceDetails.setHours(hours);
                invoiceDetails.setUnits(hours);
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

        } catch (Exception e) {
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "There is some empty fields");
            alert.show();
        }

//            System.out.println(invoiceDetails.getInvoiceComponents() +" " + invoiceDetails.getWorkedDate() +" " +
//                    invoiceDetails.getStartTime() +" " + invoiceDetails.getEndTime()+" " + invoiceDetails.getRate()+" "
//                    + invoiceDetails.getHours()+" " +invoiceDetails.getUnits());
        try {
            if (isWithinRange(Date.valueOf(workedDate.getValue()))) {
                int result = db.checkInvComp(Date.valueOf(invoiceDetails.getWorkedDate()), invoiceDetails.getStartTime(),
                        invoiceDetails.getEndTime(), db.getLast_inserted_id());
                if (result == 0) {
                    db.insertInvoiceTo(invoiceDetails.getInvoiceComponents(), Date.valueOf(invoiceDetails.getWorkedDate()),
                            invoiceDetails.getStartTime(), invoiceDetails.getEndTime(), invoiceDetails.getHours(),
                            invoiceDetails.getUnits(), invoiceDetails.getRate());
                } else {
                    db.setIDForInvComp( result);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
            invoiceComponents.getSelectionModel().clearSelection();
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
        System.out.println("Invoice Generated");
        print.print();
    }

    EmailSender es = new EmailSender();
//    @FXML
//    public  void sendEmail(ActionEvent actionEvent) {
//
//
//    }

    @FXML
    public void sendEmail(ActionEvent actionEvent) throws SQLException {
        //GeneratePDFInvoice generatePDFInvoice = new GeneratePDFInvoice();
        String id = GeneratePDFInvoice.updatedInvID;
        System.out.println("ID here: "+id);
        if (passwordBox.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Password field empty");
            alert.show();
        }
        if (id == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Period Ending Date is empty");
            alert.showAndWait();

            pane_iod.toFront();
        } else {
            es.emailSender(passwordBox.getText(), id);
        }

    }

}