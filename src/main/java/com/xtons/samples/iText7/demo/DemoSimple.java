package com.xtons.samples.iText7.demo;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;

public class DemoSimple implements Demo {
	public static final String IMAGE = "./resources/sample.jpg";

	@Override
	public void run(PdfDocument pdfDoc, Document doc, boolean isFirst) throws MalformedURLException, FileNotFoundException {
		pdfDoc.addNewPage();
		if( !isFirst )
			doc.add(new AreaBreak());
		Image image = new Image(ImageDataFactory.create(IMAGE));
		image = new Image(ImageDataFactory.create(IMAGE));
		doc.add(image);
	}

}
