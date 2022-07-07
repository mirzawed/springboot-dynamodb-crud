package com.example.aws.springbootdynamodb.generator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

import com.example.aws.springbootdynamodb.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFGenerator {

	private static final Logger logger = LoggerFactory.getLogger(PDFGenerator.class);

	public static ByteArrayInputStream customerPDFReport(List<Employee> employees) {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfWriter.getInstance(document, out);
			document.open();


			logger.info("Add Text to PDF file---START");
			Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
			Paragraph para = new Paragraph("Employee Table", font);
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			document.add(Chunk.NEWLINE);

			logger.info("Add Text to PDF file---END");


			logger.info("create PDF table having 4 columns---START");

			PdfPTable table = new PdfPTable(4);
			document.add(table);

			logger.info("create PDF table having 4 columns---END");


			logger.info("Add PDF Table Header---START");
			Stream.of("empid", " Name ", "email ","department")
					.forEach(headerTitle -> {
						PdfPCell header = new PdfPCell();
						Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
						header.setBackgroundColor(BaseColor.LIGHT_GRAY);
						header.setHorizontalAlignment(Element.ALIGN_CENTER);
						header.setBorderWidth(2);
						header.setPhrase(new Phrase(headerTitle, headFont));
						table.addCell(header);
					});
			logger.info("Add PDF Table Header---END");


			logger.info("Iterate The Data---START");
			for (Employee employee : employees) {
				PdfPCell idCell = new PdfPCell(new Phrase(employee.getEmpid().toString()));
				idCell.setPaddingLeft(4);
				idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(idCell);

				PdfPCell nameCell = new PdfPCell(new Phrase(employee.getName()));
				nameCell.setPaddingLeft(4);
				nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(nameCell);

				PdfPCell emailCell = new PdfPCell(new Phrase(String.valueOf(employee.getEmail())));
				emailCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				emailCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				emailCell.setPaddingRight(4);
				table.addCell(emailCell);


				PdfPCell departmentCell = new PdfPCell(new Phrase(String.valueOf(employee.getDepartment())));
				departmentCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				departmentCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				departmentCell.setPaddingRight(4);
				table.addCell(departmentCell);
			}
			logger.info("Iterate The Data---END");

			document.add(table);

			document.close();
		} catch (DocumentException e) {
			logger.error(e.toString());
		}

		return new ByteArrayInputStream(out.toByteArray());
	}
}