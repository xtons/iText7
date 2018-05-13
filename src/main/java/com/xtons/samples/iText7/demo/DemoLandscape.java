package com.xtons.samples.iText7.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;

public class DemoLandscape implements Demo {
	public static final String IMAGE = "./resources/sample.jpg";

	@Override
	public void run(PdfDocument pdfDoc, Document doc, boolean isFirst) throws MalformedURLException, FileNotFoundException {
		pdfDoc.addNewPage(PageSize.A4.rotate());
		if( !isFirst )
			doc.add(new AreaBreak());		Image image = new Image(ImageDataFactory.create(IMAGE));
		image = new Image(ImageDataFactory.create(IMAGE));
		doc.add(image);
	}

}
