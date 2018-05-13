package com.xtons.samples.iText7.demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;

public interface Demo {
	public static Demo getInstance( String name ) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		return (Demo) Class.forName( "com.xtons.samples.iText7.demo.Demo".concat(name) ).newInstance();
	}
	public abstract void run(PdfDocument pdfDoc, Document doc, boolean isFirst) throws IOException;

}
