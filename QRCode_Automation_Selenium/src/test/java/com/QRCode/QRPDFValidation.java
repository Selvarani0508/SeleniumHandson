package com.QRCode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRPDFValidation {

	public static void main(String[] args) {
		// Hard-coded path to the PDF file
		String pdfFilePath = "C:\\eclipse-workspace\\QRCode_Automation_Selenium\\src\\test\\resources\\PDF file\\QRPDF.pdf"; // Change this to your actual path

		try {
			// Extract the QR code URL from the PDF
			String decodedUrl = extractQRCodeFromPDF(pdfFilePath);
			if (decodedUrl != null) {
				System.out.println("Decoded URL: " + decodedUrl);

				// Launch Chrome and open the URL using WebDriverManager
				WebDriverManager.chromedriver().setup();
				WebDriver driver = new ChromeDriver();
				driver.get(decodedUrl);

				// Close the driver after use
				//driver.quit();
			} else {
				System.out.println("No QR Code found in the PDF.");
			}
		} catch (Exception e) {
			System.err.println("Error occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static String extractQRCodeFromPDF(String pdfFilePath) throws IOException, NotFoundException {
		// Load the PDF document
		try (PDDocument pdfDocument = PDDocument.load(new File(pdfFilePath))) {
			PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);
			// Render the first page of the PDF to an image
			BufferedImage pdfImage = pdfRenderer.renderImageWithDPI(0, 300); // 300 DPI improves quality

			// Save the image temporarily (optional)
			// ImageIO.write(pdfImage, "PNG", new File("extracted_image.png"));

			// Decode the QR code from the image
			return decodeQRCode(pdfImage);
		}
	}

	private static String decodeQRCode(BufferedImage bufferedImage) throws NotFoundException {
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		try {
			Result result = new MultiFormatReader().decode(bitmap);
			return result.getText();
		} catch (NotFoundException e) {
			return null; // No QR Code found
		}
	}
}