package com.xtons.samples.iText7.demo;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.xtons.samples.iText7.MatrixUtil;

public class DemoCrop implements Demo {
	public static final String IMAGE = "./resources/sample.jpg";

	@Override
	public void run(PdfDocument pdfDoc, Document doc, boolean isFirst) throws MalformedURLException, FileNotFoundException {
		pdfDoc.addNewPage();
		if( !isFirst )
			doc.add(new AreaBreak());
		ImageData imgData = ImageDataFactory.create(IMAGE);
		Image image = new Image(imgData, doc.getLeftMargin(),
				PageSize.A4.getHeight() - imgData.getHeight() - doc.getTopMargin());
		
		image.setFixedPosition(0, 0);
		Rectangle rectangle = new Rectangle(300, 300);
		PdfFormXObject template = new PdfFormXObject(rectangle);
		try( Canvas canvas = new Canvas(template, pdfDoc) ){
			canvas.add(image);
		};
		Image croppedImage = new Image(template);
		doc.add(croppedImage);
		
		image.setFixedPosition(-1200, -600);
		rectangle = new Rectangle(500, 300);
		template = new PdfFormXObject(rectangle);
		try( Canvas canvas = new Canvas(template, pdfDoc)){
			canvas.add(image);
		}
		croppedImage = new Image(template);
		doc.add(croppedImage);
		
	}

}
