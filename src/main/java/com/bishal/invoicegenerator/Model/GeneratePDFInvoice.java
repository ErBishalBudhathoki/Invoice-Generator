package com.bishal.invoicegenerator.Model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.FileOutputStream;

public class GeneratePDFInvoice
{
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
         String S = "Pratiksha Tiwari" + "\n"+ System.lineSeparator() + "ABN:                             48213562270"
                 + "\n" + "Period Starting:                06/11/2021"
                 + "\n" + "Period Ending:                  12/11/2021"
                 + "\n" + "Hours Completed:                          37";

         PdfPCell cellx = new PdfPCell(new Paragraph(S));
         cellx.setBorderColor(BaseColor.BLACK);
         cellx.setPadding(5);
         table1.addCell(cellx);
         table1.setSpacingAfter(15f);
         document.add(table1);

//			document.add(Chunk.NEWLINE);        //Blank line
         document.add(new LineSeparator());
//			document.add(Chunk.NEWLINE);        //Blank line

         String S2 = "Bill To: " + "Matthew Tremain                                                                       Invoice Number: 20211107"
                 + "\n" + "             2/94 Carlton Cres, Summerhill                                          Job Title: Home Care Assistance"
                 + "\n" + "             NSW, 2131"
                 + "\n" + "             (Country WIde Care)";
         Paragraph p2 = new Paragraph(S2);
         document.add(p2);
         document.add(Chunk.NEWLINE);        //Blank line

         //Font fontH1 = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
         PdfPTable table2 = new PdfPTable(5); // 5 columns.
         table2.setWidthPercentage(100); //Width 100%
         //Set Column widths
         float[] columnWidths2 = {2.3f,1.2f,1f,0.8f,1.3f};
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

         table2.addCell(invoiceComponents);
         table2.addCell(timeWorked);
         table2.addCell(hours);
         table2.addCell(rate);
         table2.addCell(totalAmount);

         table2.addCell(invoiceComponents);
         table2.addCell(timeWorked);
         table2.addCell(hours);
         table2.addCell(rate);
         table2.addCell(totalAmount);

         table2.setSpacingAfter(-25f);
         document.add(table2);
         document.add(Chunk.NEWLINE);

         Chunk p3 = new Chunk("Total: $1540");
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
      } catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}