package com.xtons.samples.iText7.demo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;

public class DemoCrop implements Demo {
	public static final String IMAGE = "./resources/sample.jpg";
	public static final String MASK = "./resources/mask.jpg";
	public static final String MASKPNG = "./resources/mask.png";

	public static ImageData makeBlackAndWhitePng(String image) throws IOException {
        BufferedImage bi = ImageIO.read(new File(image));
        BufferedImage newBi = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_USHORT_GRAY);
        newBi.getGraphics().drawImage(bi, 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newBi, "png", baos);
        return ImageDataFactory.create(baos.toByteArray());
    }
	
	@Override
	public void run(PdfDocument pdfDoc, Document doc, boolean isFirst) throws IOException {
		PdfPage page = pdfDoc.addNewPage();
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

        ImageData imgMask = makeBlackAndWhitePng(MASK);
        imgMask.makeMask();
        imgData.setImageMask(imgMask);
        Image img = new Image(imgData);
        img.scaleAbsolute(300, 300);
        img.setFixedPosition(0, 0);
        doc.add(img);

        imgMask = makeBlackAndWhitePng(MASKPNG);
        imgMask.makeMask();
        imgData.setImageMask(imgMask);
        img = new Image(imgData);
        img.scaleAbsolute(300, 300);
        img.setFixedPosition(300, 0);
        doc.add(img);
        
		PdfCanvas canvas = new PdfCanvas(page);
		float[] matrix;
		matrix = new float[] {(float) (Math.cos(Math.PI/4)*imgData.getWidth()*72/600), (float) (0-Math.sin(Math.PI/4)*imgData.getHeight()*72/600), 144,
				(float) (Math.sin(Math.PI/4)*imgData.getWidth()*72/600), (float) (Math.cos(Math.PI/4)*imgData.getHeight()*72/600), 144};
		canvas.addImage(imgData, matrix[0], matrix[3], matrix[1], matrix[4], matrix[2], matrix[5]);
}

}
