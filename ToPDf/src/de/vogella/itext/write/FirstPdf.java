package de.vogella.itext.write;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class FirstPdf {
	public static void main(String[] args) {
		String path="D:\\Downloads\\Minhchung_PhanI_TC1_TC11(23-Oct-2018)\\";
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String fname1=listOfFiles[i].getName();

//				if(fname.contains(" - marker.pdf")) {
//					listOfFiles[i].delete();
//				}
				if(!fname1.contains(" - marker.pdf")) {
					String fname=fname1.replace(".pdf", " - marker.pdf");
					System.out.println("File " + fname);
					Document document = new Document();
					try {
						PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path+fname));
						document.open();
//						  BaseFont courier = BaseFont.createFont(BaseFont.COURIER, BaseFont.UT, BaseFont.EMBEDDED); 
						BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//						  Font font =FontFactory.getFont("sanskrit", "Identity-H", true); 
//						  Chunk chunk = new Chunk(fname1,font); 
//						document.add(new Paragraph(chunk));
						Paragraph p = new Paragraph(fname1, new Font(bf, 22));
						document.add(p);
						document.close();
						writer.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} 

		}
	}
}