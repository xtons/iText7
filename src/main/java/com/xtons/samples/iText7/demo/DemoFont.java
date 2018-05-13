package com.xtons.samples.iText7.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.ParagraphRenderer;

public class DemoFont implements Demo {
	public static final String SERIF = "c:/Windows/Fonts/SourceHanSerif.ttc,8";
	public static final String SANS = "c:/Windows/Fonts/SourceHanSans.ttc,8";
	public static final String LOGO = "c:/Windows/Fonts/simkai.ttf";

	@Override
	public void run(PdfDocument pdfDoc, Document doc, boolean isFirst) throws IOException {
		PdfPage page = pdfDoc.addNewPage();
		if( !isFirst )
			doc.add(new AreaBreak());	    int pageNumber = pdfDoc.getPageNumber(page);
		PdfFont font = PdfFontFactory.createFont(SERIF, PdfEncodings.IDENTITY_H);
		String text = "思源宋体 ".concat(font.getFontProgram().getFontNames().getFontName());
		Paragraph p = new Paragraph(text).setFont(font).setFontSize(36.0f);
		doc.add(p);
		// Create renderer tree
		IRenderer renderer = p.createRendererSubTree();
		// Do not forget setParent(). Set the dimensions of the viewport as needed
		LayoutResult result = renderer.setParent(doc.getRenderer()).layout(new LayoutContext(new LayoutArea(pageNumber,
				new Rectangle(PageSize.A4.getWidth() - doc.getLeftMargin() - doc.getRightMargin(), 1000))));
		// LayoutResult#getOccupiedArea() contains the information you need
		doc.add(new Paragraph(String.format("上行宽：%f; 高：%f", result.getOccupiedArea().getBBox().getWidth(),
				result.getOccupiedArea().getBBox().getHeight())).setFont(font));

		font = PdfFontFactory.createFont(SANS, PdfEncodings.IDENTITY_H);
		text = "思源黑体 ".concat(font.getFontProgram().getFontNames().getFontName());
		p = new Paragraph(text).setFont(font).setFontSize(36.0f).setBorder(new SolidBorder(1));
		doc.add(p);
		renderer = p.createRendererSubTree();
		result = renderer.setParent(doc.getRenderer()).layout(new LayoutContext(new LayoutArea(pageNumber,
				new Rectangle(PageSize.A4.getWidth() - doc.getLeftMargin() - doc.getRightMargin(), 1000))));
		doc.add(new Paragraph(String.format("上行宽：%f; 高：%f", result.getOccupiedArea().getBBox().getWidth(),
				result.getOccupiedArea().getBBox().getHeight())).setFont(font));

		font = PdfFontFactory.createFont(LOGO, PdfEncodings.IDENTITY_H);
		text = "系统楷体".concat(font.getFontProgram().getFontNames().getFontName());
		p = new Paragraph(text).setFont(font).setFontSize(36.0f);
		doc.add(p);
		renderer = p.createRendererSubTree();
		result = renderer.setParent(doc.getRenderer()).layout(new LayoutContext(new LayoutArea(pageNumber,
				new Rectangle(PageSize.A4.getWidth() - doc.getLeftMargin() - doc.getRightMargin(), 1000))));
		doc.add(new Paragraph(String.format("上行宽：%f; 高：%f", result.getOccupiedArea().getBBox().getWidth(),
				result.getOccupiedArea().getBBox().getHeight())).setFont(font));
		
		p = new Paragraph("相对位置（28，28）处写入文本").setFont(font).setFontSize(36.0f).setBorder(new SolidBorder(1));
		p.setRelativePosition(28.0f, 28.0f, 0f, 0.0f);
		doc.add(p);
	
		// 参考 https://developers.itextpdf.com/content/best-itext-questions-stackoverview/absolute-positioning-text/itext7-how-add-text-inside-rectangle
		final Rectangle rect = new Rectangle(50, PageSize.A4.getHeight() - 50, 100, 50);
//		PdfCanvas canvas = new PdfCanvas(page);
//		canvas.setFillColor(Color.makeColor(colorSpace));
//		canvas.rectangle(150, 600, 250, 150);
//		canvas.fillStroke();
	    p = new Paragraph("在指定位置内尝试写入多行\n文本").setFont(font).setFontSize(10.0f);
	    p.setNextRenderer(new ParagraphRenderer(p) {
	        @Override
	        public List<Rectangle> initElementAreas(LayoutArea area) {
	            List<Rectangle> list = new ArrayList<Rectangle>();
	            list.add(rect);
	            return list;
	        }
	    });
	    doc.add(p);
	}

}
