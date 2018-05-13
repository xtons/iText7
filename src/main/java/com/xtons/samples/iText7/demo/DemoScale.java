package com.xtons.samples.iText7.demo;

import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.xtons.samples.iText7.MatrixUtil;

public class DemoScale implements Demo {
	public static final String DEST = "./target/Scale.pdf";
	public static final String IMAGE = "./resources/sample.jpg";

	@Override
	public void run(PdfDocument pdfDoc, Document doc, boolean isFirst)
			throws MalformedURLException, FileNotFoundException {
		pdfDoc.addNewPage();
		if (!isFirst)
			doc.add(new AreaBreak());
		ImageData imgData = ImageDataFactory.create(IMAGE);
		Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO,
				"imgData.getWidth and imgData.getHeight return {0},{1}.",
				new Object[] { imgData.getWidth(), imgData.getHeight() });
		Image image = new Image(imgData, doc.getLeftMargin(),
				PageSize.A4.getHeight() - imgData.getHeight() - doc.getTopMargin());
		doc.add(image);

		pdfDoc.addNewPage();
		doc.add(new AreaBreak());
		image = new Image(imgData, doc.getLeftMargin(),
				PageSize.A4.getHeight() - imgData.getHeight() * 72 / 300 - doc.getTopMargin());
		image.scaleToFit(imgData.getWidth() * 72 / 300, imgData.getHeight() * 72 / 300);
		doc.add(image);

		image = new Image(imgData, doc.getLeftMargin() + 50, PageSize.A4.getHeight() - imgData.getHeight() * 72 / 300
				- imgData.getHeight() * 72 / 600 - doc.getTopMargin() - 50);
		image.scaleToFit(imgData.getWidth() * 72 / 600, imgData.getHeight() * 72 / 600);
		doc.add(image);

		image.setRotationAngle(Math.PI / 4);
		doc.add(image);

		PdfPage page = pdfDoc.addNewPage();
		doc.add(new AreaBreak());

		PdfCanvas canvas = new PdfCanvas(page);
		float[] matrix;
		float w = imgData.getWidth()*72/600, h=imgData.getHeight()*72/600;
		// 缩放
		// | x 0 0 | |1| |x|
		// | 0 y 0 |*|1|=|y|
		// | 0 0 1 | |1| |1|
		matrix = new float[] {w, 0, 0, 0, h, 0};
		canvas.addImage(imgData, matrix[0], matrix[3], matrix[1], matrix[4], matrix[2], matrix[5]);
		// 平移
		// | x 0 A | |1| |x+A|
		// | 0 y B |*|1|=|y+B|
		// | 0 0 1 | |1| | 1 |
		matrix = new float[] {w, 0, 72, 0, h, 72};
		canvas.addImage(imgData, matrix[0], matrix[3], matrix[1], matrix[4], matrix[2], matrix[5]);
		// 旋转
		// | cos^ -sin^ 0 | |x| |cos^x-sin^y|
		// | sin^  cos^ 0 |*|y|=|sin^x+cos^y|
		// |    0     0 1 | |1| |      1    |
		matrix = new float[] {(float) (Math.cos(Math.PI/4)*imgData.getWidth()*72/600), (float) (0-Math.sin(Math.PI/4)*imgData.getHeight()*72/600), 144,
				(float) (Math.sin(Math.PI/4)*imgData.getWidth()*72/600), (float) (Math.cos(Math.PI/4)*imgData.getHeight()*72/600), 144};
		canvas.addImage(imgData, matrix[0], matrix[3], matrix[1], matrix[4], matrix[2], matrix[5]);
		// 镜像
		// | -1 0 A | |x| |-x+A|
		// | 0  1 B |*|y|=| y+B|
		// | 0  0 1 | |1| |  1 |
		matrix = new float[] {-imgData.getWidth()*72/600, 0, imgData.getWidth()*72/600+216, 0, imgData.getHeight()*72/600, 216};
		canvas.addImage(imgData, matrix[0], matrix[3], matrix[1], matrix[4], matrix[2], matrix[5]);
		// 联合运算时早操作的矩阵放右边，晚操作的矩阵放左边。
		// |1 0 288+h| |cos90 -sin90 0| |w 0 0| |0,-h,288+h|
		// |0 1 288  |*|sin90  cos90 0|*|0 h 0|=|w, 0,288  |
		// |0 0   1  | |   0      0  1| |0 0 1| |0, 0,  1  |
		matrix = new float[] {0, -h, 288+h, w, 0, 288};
		canvas.addImage(imgData, matrix[0], matrix[3], matrix[1], matrix[4], matrix[2], matrix[5]);
		// 以下是继续镜像后再做平移的运算结果
		matrix = new float[] {0,h,360,w,0,360};
		canvas.addImage(imgData, matrix[0], matrix[3], matrix[1], matrix[4], matrix[2], matrix[5]);
		// 用MatrixUtil
		matrix = MatrixUtil.multiply( MatrixUtil.multiply(MatrixUtil.multiply(MatrixUtil.getMirror_V(), 
				MatrixUtil.getShift((float) (Math.sin(Math.PI/6)*h), -PageSize.A4.getHeight())),
				MatrixUtil.getRotate(Math.PI/6)),
				MatrixUtil.getScale(w, h));
		canvas.addImage(imgData, matrix[0], matrix[3], matrix[1], matrix[4], matrix[2], matrix[5]);
	}

}
