package com.bishal.invoice.Model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import javafx.scene.control.Alert;

import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GeneratePDFInvoice
{
   Database db = new Database();
   public static String invNumID, updatedInvID;
   public GeneratePDFInvoice() throws SQLException {
   }

   public void print()
   {
      Document document = new Document();
      try
      {
         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Invoice.pdf"));
         document.open();

         Paragraph paragraphOne = new Paragraph("INVOICE",
                 FontFactory.getFont(FontFactory.TIMES_ROMAN,28, Font.BOLD, BaseColor.BLACK));
         paragraphOne.setAlignment(Element.ALIGN_RIGHT);

         document.add(paragraphOne);

         PdfPTable table1 = new PdfPTable(1); // 3 columns.
         table1.setWidthPercentage(40); //Width 100%
         //Set Column widths
         float[] columnWidths1 = {1f};
         table1.setWidths(columnWidths1);
         table1.setHorizontalAlignment(Element.ALIGN_LEFT);

         db.getOwnerDetails();
//         System.out.println(db.getOwnerDetails() +"Method call");
         List<Object> getDetails = db.getOwnerDetails();
         //System.out.println(getDetails.getKey() + " get details id");
         System.out.println(getDetails +"get details");
         Integer id = null;
         String ownerName = (String) getDetails.get(1);
         String abn = (String) getDetails.get(2);
         String ps = (String) getDetails.get(3);
         String pe = (String) getDetails.get(4);
         invNumID = pe.replace("-", "");
         updatedInvID = pe;
         String jobTitle = (String) getDetails.get(5);

//         db.getInvoiceDetails();
//         System.out.println("Invoice Details: " +db.getInvoiceDetails());

//         System.out.println(db.getOwnerDetails() +"Method call");

         int a = db.getLast_inserted_id();
         System.out.println("A: " +a);
         ArrayList getInvoiceDetails = (ArrayList) db.getInvoiceDetails(a);
         //System.out.println(getDetails.getKey() + " get details id");
         System.out.println("Array list from generatePDF: " +getInvoiceDetails);
         System.out.println(getInvoiceDetails.size() +"get invoice details size");
         double totalAmounts = 0;
//         for (int i = 0; i < (getInvoiceDetails.size()/8); i++) {
         int k = 0;
         double grandTotal = 0;
         double grandhours = 0;
         try {
            do {
               String units = String.valueOf(getInvoiceDetails.get(k + 6));
               String r = String.valueOf(getInvoiceDetails.get(k + 7));
               totalAmounts = (Double.parseDouble(units) * Double.parseDouble(r));
               grandhours = grandhours + Double.parseDouble(units);
               grandTotal = totalAmounts + grandTotal;
               k = k +9;
            } while (k < (getInvoiceDetails.size() ));
         } catch (IndexOutOfBoundsException e) {
            System.out.println(e);
         }


         PdfPTable table4 = new PdfPTable(2);
         table4.setWidthPercentage(40); //Width 100%
         //Set Column widths
         float[] cWidth = {1.3f, 0.9f};
         table4.setWidths(cWidth);
         table4.setHorizontalAlignment(Element.ALIGN_LEFT);
         PdfPCell[] cell1 = new PdfPCell[12];



         cell1[0] = new PdfPCell(new Phrase(ownerName));
         cell1[0].setUseVariableBorders(true);
         cell1[0].setBorderColorRight(BaseColor.WHITE);
         cell1[0].setBorderColorBottom(BaseColor.WHITE);
         cell1[0].setFixedHeight(20f);
         cell1[0].setPaddingLeft(10);
//         cell1[0].setPaddingTop(5);
//         cell1[0].setPaddingBottom(10);


         table4.addCell(cell1[0]);

         cell1[1] = new PdfPCell(new Phrase(" "));
         cell1[1].setUseVariableBorders(true);
         cell1[1].setFixedHeight(20f);
         cell1[1].setBorderColorLeft(BaseColor.WHITE);
         cell1[1].setBorderColorBottom(BaseColor.WHITE);
         cell1[1].setPaddingLeft(10);
//         cell1[1].setPaddingTop(5);
//         cell1[1].setPaddingBottom(10);
         table4.addCell(cell1[1]);

         cell1[2] = new PdfPCell(new Phrase("ABN:"));
         cell1[2].setUseVariableBorders(true);
         cell1[2].setBorderColorRight(BaseColor.WHITE);
         cell1[2].setBorderColorBottom(BaseColor.WHITE);
         cell1[2].setPaddingLeft(10);
         cell1[2].setFixedHeight(20f);
//         cell1[2].setPaddingTop(5);
//         cell1[2].setPaddingBottom(10);
         table4.addCell(cell1[2]);

         cell1[3] = new PdfPCell(new Phrase(abn));
         cell1[3].setUseVariableBorders(true);
         cell1[3].setBorderColorLeft(BaseColor.WHITE);
         cell1[3].setBorderColorBottom(BaseColor.WHITE);
         cell1[3].setPaddingLeft(10);
//         cell1[3].setPaddingTop(5);
//         cell1[3].setPaddingBottom(10);
         cell1[3].setFixedHeight(20f);
         cell1[3].setHorizontalAlignment(Element.ALIGN_RIGHT);
         table4.addCell(cell1[3]);

         cell1[4] = new PdfPCell(new Phrase("Period Starting:"));
         cell1[4].setUseVariableBorders(true);
         cell1[4].setBorderColorRight(BaseColor.WHITE);
         cell1[4].setBorderColorTop(BaseColor.WHITE);
         cell1[4].setBorderColorBottom(BaseColor.WHITE);
         cell1[4].setPaddingLeft(10);
//         cell1[4].setPaddingTop(5);
//         cell1[4].setPaddingBottom(10);
         cell1[4].setFixedHeight(20f);
         table4.addCell(cell1[4]);

         cell1[5] = new PdfPCell(new Phrase(ps));
         cell1[5].setUseVariableBorders(true);
         cell1[5].setBorderColorLeft(BaseColor.WHITE);
         cell1[5].setBorderColorTop(BaseColor.WHITE);
         cell1[5].setBorderColorBottom(BaseColor.WHITE);
         cell1[5].setPaddingLeft(10);
//         cell1[5].setPaddingTop(5);
//         cell1[5].setPaddingBottom(10);
         cell1[5].setFixedHeight(20f);
         cell1[5].setHorizontalAlignment(Element.ALIGN_RIGHT);
         table4.addCell( cell1[5]);

         cell1[6] = new PdfPCell(new Phrase("Period Ending:"));
         cell1[6].setUseVariableBorders(true);
         cell1[6].setBorderColorRight(BaseColor.WHITE);
         cell1[6].setBorderColorTop(BaseColor.WHITE);
         cell1[6].setBorderColorBottom(BaseColor.WHITE);
         cell1[6].setPaddingLeft(10);
//         cell1[6].setPaddingTop(5);
//         cell1[6].setPaddingBottom(10);
         cell1[6].setFixedHeight(20f);
         table4.addCell(cell1[6]);

         cell1[7] = new PdfPCell(new Phrase(pe));
         cell1[7].setUseVariableBorders(true);
         cell1[7].setBorderColorLeft(BaseColor.WHITE);
         cell1[7].setBorderColorTop(BaseColor.WHITE);
         cell1[7].setBorderColorBottom(BaseColor.WHITE);
         cell1[7].setPaddingLeft(10);
//         cell1[7].setPaddingTop(5);
//         cell1[7].setPaddingBottom(10);
         cell1[7].setFixedHeight(20f);
         cell1[7].setHorizontalAlignment(Element.ALIGN_RIGHT);
         table4.addCell(cell1[7]);

         cell1[8] = new PdfPCell(new Phrase("Total Amount:"));
         cell1[8].setUseVariableBorders(true);
         cell1[8].setBorderColorRight(BaseColor.WHITE);
         cell1[8].setBorderColorTop(BaseColor.WHITE);
         cell1[8].setBorderColorBottom(BaseColor.WHITE);
         cell1[8].setPaddingLeft(10);
//         cell1[8].setPaddingTop(5);
//         cell1[8].setPaddingBottom(10);
         cell1[8].setFixedHeight(20f);
         table4.addCell(cell1[8]);

         cell1[9] = new PdfPCell(new Phrase("$"+grandTotal));
         cell1[9].setUseVariableBorders(true);
         cell1[9].setBorderColorLeft(BaseColor.WHITE);
         cell1[9].setBorderColorTop(BaseColor.WHITE);
         cell1[9].setBorderColorBottom(BaseColor.WHITE);
         cell1[9].setPaddingLeft(10);
//         cell1[9].setPaddingTop(5);
//         cell1[9].setPaddingBottom(10);
         cell1[9].setFixedHeight(20f);
         cell1[9].setHorizontalAlignment(Element.ALIGN_RIGHT);
         table4.addCell(cell1[9]);

         cell1[10] = new PdfPCell(new Phrase("Hours Completed:"));
         cell1[10].setUseVariableBorders(true);
         cell1[10].setBorderColorRight(BaseColor.WHITE);
         cell1[10].setBorderColorTop(BaseColor.WHITE);

         cell1[10].setPaddingLeft(10);
//         cell1[10].setPaddingTop(5);
//         cell1[10].setPaddingBottom(10);
         cell1[10].setFixedHeight(20f);
         table4.addCell(cell1[10]);

         cell1[11] = new PdfPCell(new Phrase(""+grandhours));
         cell1[11].setUseVariableBorders(true);
         cell1[11].setBorderColorLeft(BaseColor.WHITE);
         cell1[11].setBorderColorTop(BaseColor.WHITE);
         cell1[11].setPaddingLeft(10);
//         cell1[11].setPaddingTop(5);
//         cell1[11].setPaddingBottom(10);
         cell1[11].setFixedHeight(20f);
         cell1[11].setHorizontalAlignment(Element.ALIGN_RIGHT);
         table4.addCell(cell1[11]);
         table4.setSpacingAfter(20f);
         document.add(table4);

//         String S = ownerName + "\n"+ System.lineSeparator() + "ABN:                             "+abn
//                 + "\n" + "Period Starting:                "+ps
//                 + "\n" + "Period Ending:                 "+pe
//                 + "\n" + "Total Amount:                           $"+grandTotal
//                 + "\n" + "Hours Completed:                         "+grandhours;
//
//         PdfPCell cellx = new PdfPCell(new Paragraph(S));
//         cellx.setBorderColor(BaseColor.BLACK);
//         cellx.setPadding(5);
//         table1.addCell(cellx);
//         table1.setSpacingAfter(20f);
//         document.add(table1);

//			document.add(Chunk.NEWLINE);        //Blank line
         document.add(new LineSeparator());
//			document.add(Chunk.NEWLINE);        //Blank line

         db.getBillToDetails();
//         System.out.println(db.getOwnerDetails() +"Method call");
         List<Object> getBillToDetails = db.getBillToDetails();
         //System.out.println(getDetails.getKey() + " get details id");
         System.out.println(getBillToDetails +"get bill to details");

         String clientName = "";
         clientName = (String) getBillToDetails.get(1);
         String street = "";
         street = (String) getBillToDetails.get(2);
         String suburb = "";
         suburb = (String) getBillToDetails.get(3);
         String state = "";
         state = (String) getBillToDetails.get(4);
         String postalCode = "";
         postalCode = (String) getBillToDetails.get(5);
         String companyName = "";
         companyName = (String) getBillToDetails.get(6);
         String cn = companyName;

//         String S2 = "Bill To:  "+clientName+"                                                       Invoice Number: 20211107"
//                 + "\n" + "             "+street+", "+suburb+"                                          Job Title: "+jobTitle
//                 + "\n" + "             "+state+", "+postalCode
//                 + "\n" + "             ("+companyName+")";
//         Paragraph p2 = new Paragraph(S2);
//         document.add(p2);
//         document.add(Chunk.NEWLINE);        //Blank line
         try {
            PdfPTable table1a = new PdfPTable(4); // 5 columns.

            table1a.setWidthPercentage(100); //Width 100%
            //Set Column widths
            float[] columnWidths2a = {0.8f,3.8f,1.8f,2.4f};
            table1a.setWidths(columnWidths2a);
            table1a.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table1a.setSpacingBefore(15f);
            PdfPCell cell = new PdfPCell(new Phrase("Bill To:"));
            cell.setBorderColor(BaseColor.WHITE);
            table1a.addCell(cell);

            PdfPCell cName = new PdfPCell(Phrase.getInstance(clientName));
            cName.setBorderColor(BaseColor.WHITE);
            table1a.addCell(cName);

            PdfPCell invNum = new PdfPCell(new Phrase("Invoice Number:"));
            invNum.setBorderColor(BaseColor.WHITE);
            table1a.addCell(invNum);

            PdfPCell in = new PdfPCell(new Phrase(invNumID));
            in.setBorderColor(BaseColor.WHITE);
            table1a.addCell(in);

            PdfPCell empty1 = new PdfPCell(new Phrase(""));
            empty1.setBorderColor(BaseColor.WHITE);
            table1a.addCell(empty1);

            PdfPCell stNamewSub = new PdfPCell(Phrase.getInstance(street+", " +suburb));
            stNamewSub.setBorderColor(BaseColor.WHITE);
            table1a.addCell(stNamewSub);

            PdfPCell jtitle = new PdfPCell(new Phrase("Job Title:"));
            jtitle.setBorderColor(BaseColor.WHITE);
            table1a.addCell(jtitle);

            PdfPCell jTitleValue = new PdfPCell(Phrase.getInstance(jobTitle));
            jTitleValue.setBorderColor(BaseColor.WHITE);
            table1a.addCell(jTitleValue);

            PdfPCell empty2 = new PdfPCell(new Phrase(""));
            empty2.setBorderColor(BaseColor.WHITE);
            table1a.addCell(empty2);

            PdfPCell statewPostal = new PdfPCell(Phrase.getInstance(state +", " +postalCode));
            statewPostal.setBorderColor(BaseColor.WHITE);
            table1a.addCell(statewPostal);

            PdfPCell empty3 = new PdfPCell(new Phrase(" "));
            empty3.setBorderColor(BaseColor.WHITE);
            table1a.addCell(empty3);

            PdfPCell empty4 = new PdfPCell(new Phrase(" "));
            empty4.setBorderColor(BaseColor.WHITE);
            table1a.addCell(empty4);

            PdfPCell empty5 = new PdfPCell(new Phrase(" "));
            empty5.setBorderColor(BaseColor.WHITE);
            table1a.addCell(empty5);

            PdfPCell compNames = new PdfPCell(Phrase.getInstance("("+cn+")"));
            compNames.setBorderColor(BaseColor.WHITE);
            table1a.addCell(compNames);

            PdfPCell empty6 = new PdfPCell(new Phrase(" "));
            empty6.setBorderColor(BaseColor.WHITE);
            table1a.addCell(empty6);

            PdfPCell empty7 = new PdfPCell(new Phrase(" "));
            empty7.setBorderColor(BaseColor.WHITE);
            table1a.addCell(empty7);
            table1a.setSpacingAfter(-60f);
            document.add(table1a);
            document.add(Chunk.NEWLINE);
         } catch (NullPointerException n) {
            System.out.println(n);
         }
         //Font fontH1 = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
         PdfPTable table2 = new PdfPTable(5); // 5 columns.
         table2.setWidthPercentage(100); //Width 100%
         //Set Column widths
         float[] columnWidths2 = {2.3f,1.6f,1f,0.6f,1.1f};
         table2.setWidths(columnWidths2);
         table2.setHorizontalAlignment(Element.ALIGN_LEFT);



         PdfPCell invoiceComponents = new PdfPCell(new Paragraph(Font.BOLD, "Invoice Components"));
         invoiceComponents.setBorderColor(BaseColor.BLACK);
         invoiceComponents.setPadding(5);


         PdfPCell timeWorked = new PdfPCell(new Paragraph("Time Worked"));
         timeWorked.setBorderColor(BaseColor.BLACK);
         timeWorked.setPadding(5);

         PdfPCell hours = new PdfPCell(new Paragraph("Hours/Units"));
         hours.setBorderColor(BaseColor.BLACK);
         hours.setPadding(5);

         PdfPCell rate = new PdfPCell(new Paragraph("Rate"));
         rate.setBorderColor(BaseColor.BLACK);
         rate.setPadding(5);

         PdfPCell totalAmount = new PdfPCell(new Paragraph("Total Amount"));
         totalAmount.setBorderColor(BaseColor.BLACK);
         totalAmount.setPadding(5);

         table2.addCell(invoiceComponents);
         table2.addCell(timeWorked);
         table2.addCell(hours);
         table2.addCell(rate);
         table2.addCell(totalAmount);

//         db.getInvoiceDetails();
//         System.out.println("Invoice Details: " +db.getInvoiceDetails());
//
////         System.out.println(db.getOwnerDetails() +"Method call");
//         ArrayList getInvoiceDetails = (ArrayList) db.getInvoiceDetails();
//         //System.out.println(getDetails.getKey() + " get details id");
//         System.out.println("Array list from generatePDF: " +getInvoiceDetails);
//         System.out.println(getInvoiceDetails.size() +"get invoice details size");
//         double totalAmounts = 0;
////         for (int i = 0; i < (getInvoiceDetails.size()/8); i++) {
//            int j = 0;
//         double grandTotal = 0;
         PdfPCell[] cells = new PdfPCell[5];
         int j =0;
         do {

               String ids = String.valueOf(getInvoiceDetails.get(j));
               String ic = String.valueOf(getInvoiceDetails.get(j + 1));
               String wTime = String.valueOf(getInvoiceDetails.get(j + 2)) + "-" + String.valueOf(getInvoiceDetails.get(j + 3)) + " to "
                       + String.valueOf(getInvoiceDetails.get(j + 4)) + " - " + state + " (" + getInvoiceDetails.get(j + 5) + " )";
//         String suburb = (String) getInvoiceDetails.get(3);
               String units = String.valueOf(getInvoiceDetails.get(j + 6));
               String r = String.valueOf(getInvoiceDetails.get(j + 7));
               totalAmounts = (Double.parseDouble(units) * Double.parseDouble(r));
               cells[0] = new PdfPCell(new Phrase(ic));
               cells[0].setPaddingLeft(10);
               cells[0].setPaddingTop(5);
               cells[0].setPaddingBottom(10);
               table2.addCell(cells[0]);
            cells[1] = new PdfPCell(new Phrase(wTime));
            cells[1].setPaddingLeft(10);
            cells[1].setPaddingTop(5);
            cells[1].setPaddingBottom(10);
               table2.addCell(cells[1]);
            cells[2] = new PdfPCell(new Phrase(units));
            cells[2].setPaddingRight(6);
            cells[2].setPaddingTop(5);
            cells[2].setPaddingBottom(10);
            cells[2].setHorizontalAlignment(Element.ALIGN_RIGHT);
               table2.addCell(cells[2]);
            cells[3] = new PdfPCell(new Phrase(r));
            cells[3].setPaddingRight(6);
            cells[3].setPaddingTop(5);
            cells[3].setPaddingBottom(10);
            cells[3].setHorizontalAlignment(Element.ALIGN_RIGHT);
               table2.addCell(cells[3]);
            cells[4] = new PdfPCell(new Phrase(String.valueOf(totalAmounts)));
            cells[4].setPaddingRight(6);
            cells[4].setPaddingTop(5);
            cells[4].setPaddingBottom(10);
            cells[4].setHorizontalAlignment(Element.ALIGN_RIGHT);
               table2.addCell(  cells[4] );
//               grandTotal = totalAmounts + grandTotal;
               j = j +9;
            } while (j < (getInvoiceDetails.size() ));

//         }
         //String ic = String.valueOf(getInvoiceDetails.get(1));
         //String wTime = String.valueOf(getInvoiceDetails.get(2))+ "-" + String.valueOf(getInvoiceDetails.get(3)) +" to "
         //        +String.valueOf(getInvoiceDetails.get(4)) +" - "+state+" (" +getInvoiceDetails.get(6) +" )";
//         String suburb = (String) getInvoiceDetails.get(3);
//         String hrs = String.valueOf(getInvoiceDetails.get(6));
//         String r = String.valueOf(getInvoiceDetails.get(5));
         //String companyName = (String) getInvoiceDetails.get(6);



//         table2.addCell(invoiceComponents);
//         table2.addCell(timeWorked);
//         table2.addCell(hours);
//         table2.addCell(rate);
         //table2.addCell(totalAmount);

         //table2.setSpacingAfter(-25f);
         document.add(table2);
//         document.add(Chunk.NEWLINE);

         Chunk p3 = new Chunk("Total: $" + grandTotal);
         p3.setUnderline(0.2f, -2f); // 0.1 thick, -2 y-location
         Paragraph p = new Paragraph(p3);
         p.setAlignment(Element.ALIGN_RIGHT);
         document.add(p);
         document.add(Chunk.NEWLINE);

         PdfPTable table3 = new PdfPTable(1); // 3 columns.
         table3.setWidthPercentage(45); //Width 100%
         table3.setHorizontalAlignment(Element.ALIGN_LEFT);

         String S1 = "Bank Details"
                 + "\n"
                 + System.lineSeparator()
                 + "Bank Name: Commonwealth Bank"
                 + "\n" + "Account Name: Pratiksha Tiwari"
                 + "\n" + "BSB: 062692"
                 + "\n" + "Account Number: 47022414";

         PdfPCell celly = new PdfPCell(new Paragraph(S1));
         celly.setBorderColor(BaseColor.BLACK);
         celly.setPadding(15);
         table3.addCell(celly);
         document.add(table3);

         document.close();
         writer.close();
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println(e);
         Alert alert = new Alert(Alert.AlertType.ERROR,
                 "Empty fields, please check from beginning");
         alert.show();
      }
   }
}