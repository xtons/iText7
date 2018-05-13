package com.xtons.samples.iText7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.xtons.samples.iText7.demo.Demo;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	final String cases[] = {
    		"Simple", "Landscape", "Font", "Scale"	
    	};
    	File dest = new File("./demo.pdf");
		try ( PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
				Document doc = new Document(pdfDoc, PageSize.A4) ) {
			Arrays.stream(cases).forEach(name->{
						try {
							Demo.getInstance(name).run(pdfDoc, doc, name.equals(cases[0]));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
