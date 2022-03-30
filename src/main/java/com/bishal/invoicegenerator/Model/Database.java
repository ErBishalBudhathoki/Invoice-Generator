package com.bishal.invoicegenerator.Model;

import com.mysql.cj.exceptions.UnableToConnectException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class Database {
    //    public static void main(String[] args) {
    String MySQLURL = "jdbc:mysql://localhost:3306/?autoReconnect=true&useSSL=false";
    String databaseUserName = "root";
    String databasePassword = "12345";
    public static int last_inserted_id;
    public static int pID;

    public void setLast_inserted_id(int id) {
        System.out.println(id);
        last_inserted_id = id;
        System.out.println(last_inserted_id);
    }

    public int getLast_inserted_id() {
        System.out.println(last_inserted_id);
        return last_inserted_id;
    }

    public void setIDForInvComp(int pId) {
        System.out.println(pId);
        pID = pId;
        System.out.println(pID);
    }

    public int getIDForInvComp() {
        System.out.println(pID);
        return pID;
    }

    public Database() {
    }

    public void getDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (
                Connection conn = getConnection(MySQLURL, databaseUserName, databasePassword);
                Statement stmt = conn.createStatement()) {


            //stmt1 = stmt;
            String selectDB = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = 'INVOICE' ";
            ResultSet rs = stmt.executeQuery(selectDB);
            if (rs.next()) {
                System.out.println("Database exist");
            } else {
                System.out.println("Database does not exist");
                String sql = "CREATE DATABASE INVOICE";
                stmt.executeUpdate(sql);
                System.out.println("Database created successfully...");
            }

            String createTableIOD = "CREATE TABLE IF NOT EXISTS INVOICE.INVOICEOWNERDETAIL " +
                    "( id INT NOT NULL AUTO_INCREMENT, " +
                    " companyName VARCHAR(255), " +
                    " ABN BIGINT(11), " +
                    " periodStarting VARCHAR(10), " +
                    " periodEnding Varchar(10), " +
                    " JobTitle VARCHAR(25), " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(createTableIOD);
            System.out.println(" Invoice owner detail Table created successfully...");

            String createTableBT = "CREATE TABLE IF NOT EXISTS INVOICE.BILLTO " +
                    "(btID INT NOT NULL AUTO_INCREMENT, " +
                    " ClientName VARCHAR(255), " +
                    " Street VARCHAR(255), " +
                    " Suburb VARCHAR(255), " +
                    " State VARCHAR(255), " +
                    " PostalCode INT(4), " +
                    " CompanyName VARCHAR(255), " +
                    " bt_id INT NOT NULL, " +
                    " PRIMARY KEY ( btID ), " +
                    " CONSTRAINT FK_id FOREIGN KEY  (bt_id)   REFERENCES INVOICE.invoiceownerdetail(id) )";

            stmt.executeUpdate(createTableBT);
            System.out.println(" Bill To Table created successfully...");

            String createTableID = "CREATE TABLE IF NOT EXISTS INVOICE.INVOICEDETAIL " +
                    "(invID INT NOT NULL AUTO_INCREMENT, " +
                    " InvoiceComponent VARCHAR(255), " +
                    " WorkedDate DATE, " +
                    " StartTime TIME, " +
                    " EndTime TIME, " +
                    " Hours DOUBLE, " +
                    " Units DOUBLE, " +
                    " Rate DOUBLE, " +
                    " invdet_ID INT NOT NULL, " +
                    " PRIMARY KEY ( invID ), " +
                    " CONSTRAINT FK_ids FOREIGN KEY  (invdet_ID)   REFERENCES invoiceownerdetail(id) )";
            stmt.executeUpdate(createTableID);
            System.out.println(" Invoice Detail Table created successfully...");
        } catch (UnableToConnectException e) {
            System.out.println("Connection Fail" +e);
        }


    }

    public ArrayList<Long> loadABN() throws SQLException {
        try (Connection conn = getConnection(MySQLURL, databaseUserName, databasePassword);

             PreparedStatement loadABNs = conn.prepareStatement("SELECT ABN FROM INVOICE.invoiceownerdetail " +
                     "ORDER BY id ")){

            ResultSet loadABN = loadABNs.executeQuery();
            System.out.println("ResultSet: "+loadABN);
            if (loadABN.next()) { //getInt("id") > 0
                ArrayList<Long> a = new ArrayList();
                a.add(loadABN.getLong("ABN"));

                System.out.println("Array List:" +a +" " +a.get(0));
                return a;
            } else {
                System.out.println("Record does not exist");
                return null;
            }
        }
    }

    public ArrayList<Object> checkABN(Long abn) throws SQLException {
        try (Connection conn = getConnection(MySQLURL, databaseUserName, databasePassword);

             PreparedStatement chkABN = conn.prepareStatement("SELECT id, companyName, JobTitle, ABN FROM INVOICE.invoiceownerdetail " +
                     "WHERE ABN = ? ")){
            chkABN.setLong(1, (abn));
            ResultSet chkABNRs = chkABN.executeQuery();
            System.out.println("ResultSet: "+chkABNRs);
            if (chkABNRs.next()) { //getInt("id") > 0
                ArrayList<Object> a = new ArrayList<>();
                a.add(chkABNRs.getInt("id"));
                a.add(chkABNRs.getString("JobTitle"));
                a.add(chkABNRs.getString("companyName"));
                a.add(chkABNRs.getString("ABN"));
                System.out.println("Array List:" +a +" " +a.get(0));
                return a;
            } else {
                System.out.println("Record does not exist");
                return null;
            }
        }
    }



public boolean iod = false;
    public void insertIOD(String companyNames, Long abn, LocalDate periodStartingDate, LocalDate periodEndingDate, String jobTitle) throws SQLException, ClassNotFoundException {
        try (Connection conn = getConnection(MySQLURL, databaseUserName, databasePassword);

             PreparedStatement stmt = conn.prepareStatement("INSERT INTO INVOICE.invoiceownerdetail (companyName, ABN, periodStarting, periodEnding, JobTitle)" +
                     " VALUES( ?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, companyNames);
            stmt.setLong(2, (abn));
            stmt.setDate(3, Date.valueOf(periodStartingDate));
            stmt.setDate(4, Date.valueOf(periodEndingDate));
            stmt.setString(5, jobTitle);
            stmt.executeUpdate();
            ResultSet rs= stmt.getGeneratedKeys();
            //ResultSet r = stmt.executeQuery();
            if(rs.first()){ //obtaining id
                last_inserted_id=rs.getInt(1);
                setLast_inserted_id(last_inserted_id);
            }
//            if(r.next()){ //obtaining id
//                last_inserted_id=r.getInt("id");
//                setLast_inserted_id(last_inserted_id);
//            }
            iod =true;
            System.out.print(getLast_inserted_id());
            System.out.println(" Invoice owner Detail Table inserted successfully...");
        }
    }

    public ArrayList<Object> checkBillTo(String nameClient, int currentUID) throws SQLException {
        try (Connection conn = getConnection(MySQLURL, databaseUserName, databasePassword);

             PreparedStatement chkName = conn.prepareStatement("SELECT * FROM INVOICE.billto " +
                     "WHERE ClientName = ? AND bt_id = ?")) {
            chkName.setString(1, (nameClient));
            chkName.setInt(2, currentUID);
            //chkName.executeUpdate();
            ArrayList<Object> billToList = new ArrayList<>();
            ResultSet chkNameID = chkName.executeQuery();
            if (chkNameID.next()) {
                billToList.add(chkNameID.getString("btID"));
                billToList.add(chkNameID.getString("ClientName"));
                billToList.add(chkNameID.getString("Street"));
                billToList.add(chkNameID.getString("Suburb"));
                billToList.add(chkNameID.getString("State"));
                billToList.add(chkNameID.getInt("PostalCode"));
                billToList.add(chkNameID.getString("CompanyName"));

                return billToList;
            } else {
                return null;
            }

        }
    }
    public boolean bt = false;
    public void insertBillTo(String clientName, String street, String suburb, String state, int postalCode, String clientCompanyName) throws SQLException {
        try (Connection conn = getConnection(MySQLURL, databaseUserName, databasePassword);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO INVOICE.billto(ClientName, Street, Suburb, State, PostalCode, CompanyName, bt_id) " +
                     "VALUES( ?,?,?,?,?,?,?)")) {
            stmt.setString(1, clientName);
            stmt.setString(2, (street));
            stmt.setString(3, suburb);
            stmt.setString(4, state);
            stmt.setInt(5, postalCode);
            stmt.setString(6, clientCompanyName);
            stmt.setString(7, String.valueOf(last_inserted_id));
            stmt.executeUpdate();
        }
        bt =true;
        System.out.println(" Bill to Table inserted successfully...");
    }

    public int checkInvComp(Date date, Time t1, Time t2, int fid ) throws SQLException {
        try (Connection conn = getConnection(MySQLURL, databaseUserName, databasePassword);

             PreparedStatement chkDetails = conn.prepareStatement("SELECT invID FROM INVOICE.invoicedetail " +
                     "WHERE WorkedDate = ? AND StartTime = ? AND EndTime = ? AND invdet_ID = ?")) {
            chkDetails.setDate(1, Date.valueOf(String.valueOf((date))));
            chkDetails.setTime(2, Time.valueOf(String.valueOf(t1)));
            chkDetails.setTime(3, Time.valueOf(String.valueOf(t2)));
            chkDetails.setInt(4, fid);

            ResultSet chkDetailID = chkDetails.executeQuery();
            if (chkDetailID.next()) {
                return chkDetailID.getInt(1);
            } else {
                return 0;
            }

        }
    }

    public boolean ivt = false;
    public void insertInvoiceTo(String invoiceComponents, Date workedDate, Time startTime,
                                Time endTime,Double hours, Double units, Double rate) throws SQLException {
        try (Connection conn = getConnection(MySQLURL, databaseUserName, databasePassword);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO INVOICE.invoicedetail(" +
                     "InvoiceComponent, WorkedDate, StartTime, EndTime, Hours, Units, Rate, invdet_ID) "
                     + "VALUES( ?,?,?,?,?,?,?, ?)")) {
            stmt.setString(1, invoiceComponents);
            stmt.setDate(2, workedDate);
            stmt.setTime(3, startTime);
            stmt.setTime(4, endTime);
            stmt.setDouble(5, hours);
            stmt.setDouble(6, units);
            stmt.setDouble(7, rate);
            stmt.setString(8, String.valueOf(last_inserted_id));
            stmt.executeUpdate();
            //stmt.execute();
            ivt = true;
            System.out.println(" Invoice component Detail Table inserted successfully...");
        }

    }

    public boolean containsPeriod(Integer id, LocalDate startDate, LocalDate endingDate) throws SQLException {
        Connection conn = getConnection(MySQLURL, databaseUserName, databasePassword);
        PreparedStatement myRs = conn.prepareStatement("select * from invoice.invoiceownerdetail WHERE id = ? AND periodStarting = ? AND periodEnding = ?" );
        myRs.setInt(1, id);
        myRs.setDate(2, Date.valueOf(startDate));
        myRs.setDate(3, Date.valueOf(endingDate));
        ResultSet detail = myRs.executeQuery();
        if (detail.next()) {
            System.out.println(" Details Exist");
            return true;
        }else {
            return false;
        }

    }
    String query;
    public List<Object> getOwnerDetails() {
        Integer id = 0;
        String compName = "";
        String abn = null;
        String psd = null;
        String ped = null;
        String jd = null;

        try {
            if (iod) {
                query = "select * from invoice.invoiceownerdetail ORDER BY id DESC LIMIT 1 ";
            } else {
                query = "select * from invoice.invoiceownerdetail WHERE id = " +getLast_inserted_id();
            }
            Connection conn = getConnection(MySQLURL, databaseUserName, databasePassword);
            PreparedStatement myRs = conn.prepareStatement(query);
            ResultSet rs = myRs.executeQuery();

            while (rs.next()) {

                id = Integer.parseInt(rs.getString("id"));
                System.out.println(id);
                compName = rs.getString("companyName");
                abn = rs.getString("abn");
                psd = rs.getString("periodStarting");
                ped = rs.getString("periodEnding");
                jd = rs.getString("jobTitle");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Arrays.asList(id, compName, abn, psd, ped, jd );
    }



    public List<Object> getBillToDetails() {
        Integer id = 0;
        String clientName = "";
        String street = null;
        String suburb = null;
        String state = null;
        String postalCode = null;
        String clientCompanyName = null;
        try {
            if (bt) {
                query = "select * from invoice.billto ORDER BY btID DESC LIMIT 1 ";
            } else {
                query = "select * from invoice.billto WHERE bt_id = " +getLast_inserted_id();
            }
            Connection conn = getConnection(MySQLURL, databaseUserName, databasePassword);
            PreparedStatement myRs = conn.prepareStatement(query);
            ResultSet rs1 = myRs.executeQuery();

            while (rs1.next()) {

                id = Integer.parseInt(rs1.getString("btID"));
                System.out.println(id);
                clientName = rs1.getString("clientName");
                street = rs1.getString("Street");
                suburb = rs1.getString("Suburb");
                state = rs1.getString("State");
                postalCode = rs1.getString("PostalCode");
                clientCompanyName = rs1.getString("CompanyName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Arrays.asList(id, clientName, street, suburb, state, postalCode, clientCompanyName);
    }

    public List<Object> getWorkStartEndDetails() {
        Integer id = 0;
        String compName = "";
        String abn = null;
        String psd = null;
        String ped = null;
        String jd = null;
        try {
            Connection conn = getConnection(MySQLURL, databaseUserName, databasePassword);
            PreparedStatement myRs1 = conn.prepareStatement("select periodStarting, periodEnding from invoice.invoiceownerdetail WHERE id = 1");
            ResultSet rs1 = myRs1.executeQuery();

            while (rs1.next()) {

//                id = Integer.parseInt(rs1.getString("id"));
//                System.out.println(id);
//                compName = rs1.getString("companyName");
//                abn = rs1.getString("abn");
                psd = rs1.getString("periodStarting");
                ped = rs1.getString("periodEnding");
//                jd = rs1.getString("jobTitle");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Arrays.asList( psd, ped );
    }



    public ArrayList<String> getInvoiceDetails(int a) throws SQLException {
        Integer id = 0;
        String invoiceComponents = "";
        String workedDate = null;
        String startTime = null;
        String endTime = null;
        String rate = null;
        String hours = null;
        String units = null;
        System.out.println((last_inserted_id));
        ResultSet rs2;
        System.out.println("AA: " +a);
        ArrayList<String> invoiceResultList = null;
        try {
            if (ivt) {
                query = "select * from invoice.invoicedetail ORDER BY invID DESC LIMIT 1 ";
            } else {
                query = "select * from invoice.invoicedetail WHERE invdet_id = " +getLast_inserted_id();
            }
            Connection conn = getConnection(MySQLURL, databaseUserName, databasePassword);
            String sd = (String) getWorkStartEndDetails().get(0);
            String ed = (String) getWorkStartEndDetails().get(1);
            System.out.println("sd and ed: " + sd + ed);
            PreparedStatement myRs = conn.prepareStatement(query);
            //myRs.setInt(1, (a));
            //myRs.setString(2, ed);
            //myRs.setString(3, String.valueOf(last_inserted_id));
            rs2 = myRs.executeQuery();

            //Stores properties of a ResultSet object, including column count
            ResultSetMetaData rsmd = rs2.getMetaData();
            int columnCount = rsmd.getColumnCount();
            System.out.println("Column count :" +columnCount);
            invoiceResultList = new ArrayList<>(columnCount);
//            PreparedStatement myRs = conn.prepareStatement("select * from invoice.invoicedetail WHERE WorkedDate BETWEEN ? AND ?");
//            myRs.setString(1, psd);
//            myRs.setString(2, ped);
            while (rs2.next()) {

                id = Integer.parseInt(rs2.getString("invID"));
                System.out.println(id);
//                invoiceComponents = rs2.getString("InvoiceComponent");
//                workedDate = rs2.getString("WorkedDate");
//                startTime = rs2.getString("StartTime");
//                endTime = rs2.getString("EndTime");
//                rate = rs2.getString("Rate");
//                hours = rs2.getString("Hours");
//                units = rs2.getString("Units");
                System.out.println(rs2.getString(7));
                int i = 1;
                while (i <= columnCount) {
                    invoiceResultList.add(rs2.getString(i++));

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("List resultset: " +invoiceResultList);
        System.out.println("Size: \n" +invoiceResultList.size());
        //return Arrays.asList(id, invoiceComponents, workedDate, startTime, endTime, rate, hours, units);
        return (invoiceResultList);
    }

    public ArrayList<String> loadbtName() throws SQLException {
        Connection conn = getConnection(MySQLURL, databaseUserName, databasePassword);
        PreparedStatement myRs = conn.prepareStatement("select ClientName from invoice.billto ORDER BY btID");
        ResultSet loadName = myRs.executeQuery();
        ArrayList<String> extractedName = new ArrayList<>();
        while (loadName.next()) {
            extractedName.add(loadName.getString("ClientName"));

        }
        if (extractedName.isEmpty()) {
            return null;
        }else {
            return extractedName;
        }

    }

    public ArrayList<String> loadNameDetals(String value) throws SQLException {
        Connection conn = getConnection(MySQLURL, databaseUserName, databasePassword);
        PreparedStatement myRs = conn.prepareStatement("select * from invoice.billto WHERE ClientName = ?" );
        myRs.setString(1, value);
        ResultSet detail = myRs.executeQuery();
        ArrayList<String> details = new ArrayList<>();
        while (detail.next()) {
            details.add(detail.getString(2));
            details.add(detail.getString(3));
            details.add(detail.getString(4));
            details.add(detail.getString(5));
            details.add(detail.getString(6));
            details.add(detail.getString(7));

        }
        if (details.isEmpty()) {
            return null;
        }else {
            return details;
        }

    }


//public ArrayList<ArrayList<String> > getInvoiceDetails() throws SQLException {
//    Integer id = 0;
//    String invoiceComponents = "";
//    String workedDate = null;
//    String startTime = null;
//    String endTime = null;
//    String rate = null;
//    String hours = null;
//    String units = null;
//
//    ResultSet rs2;
//    ArrayList<ArrayList<String> > invoiceResultList = null;
//    try {
//        Connection conn = getConnection(MySQLURL, databaseUserName, databasePassword);
//        String sd = (String) getWorkStartEndDetails().get(0);
//        String ed = (String) getWorkStartEndDetails().get(1);
//        System.out.println("sd and ed: " + sd + ed);
//        PreparedStatement myRs = conn.prepareStatement("select * from invoice.invoicedetail WHERE workedDate >= ? AND workedDate <= ?");
//        myRs.setString(1, sd);
//        myRs.setString(2, ed);
//        rs2 = myRs.executeQuery();
//
//        //Stores properties of a ResultSet object, including column count
//        ResultSetMetaData rsmd = rs2.getMetaData();
//        int columnCount = rsmd.getColumnCount();
//        System.out.println("Column count :" +columnCount);
//        invoiceResultList = new ArrayList<ArrayList<String> >();;
//
//        while (rs2.next()) {
//
//            id = Integer.parseInt(rs2.getString("id2"));
//            System.out.println(id);
//            invoiceComponents = rs2.getString("InvoiceComponent");
//            workedDate = rs2.getString("WorkedDate");
//            startTime = rs2.getString("StartTime");
//            endTime = rs2.getString("EndTime");
//            rate = rs2.getString("Rate");
//            hours = rs2.getString("Hours");
//            units = rs2.getString("Units");
//            System.out.println(rs2.getString(7));
//            int i = 0;
//            for (i = 0; i <= (columnCount/7); i++) {
//                int k = 0;
//               for (int j = 0; j <= (columnCount/3); j++) {
//                    for (k = 0; k <= )
//                   invoiceResultList.get(i).add(j, rs2.getString(k));
//                   k++;
//               }
//            }
//            System.out.println("List resultset: " +invoiceResultList);
//            System.out.println("Size: \n" +invoiceResultList.size());
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    //return Arrays.asList(id, invoiceComponents, workedDate, startTime, endTime, rate, hours, units);
//    return (invoiceResultList);
//}
}



