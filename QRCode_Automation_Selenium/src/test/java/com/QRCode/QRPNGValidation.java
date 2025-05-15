package com.QRCode;


		
		import com.google.zxing.*;
		import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
		import com.google.zxing.common.HybridBinarizer;
		import io.github.bonigarcia.wdm.WebDriverManager;
		import org.openqa.selenium.WebDriver;
		import org.openqa.selenium.chrome.ChromeDriver;
		 
		import javax.imageio.ImageIO;
		import java.awt.image.BufferedImage;
		import java.io.File;
		import java.io.IOException;
		 
		public class QRPNGValidation {
		 
		    public static void main(String[] args) {
		        try {
		            // Path to the PNG file
		            String qrCodeImagePath = "C:\\eclipse-workspace\\QRCode_Automation_Selenium\\src\\test\\resources\\Resource\\QR_IMG.png";
		            		
		            		// Exact QR code file = "C:\\eclipse-workspace\\QRCode_Automation_Selenium\\src\\test\\resources\\Resource\\QR_Image.png";
		 
		            // Decode the QR code
		            String decodedUrl = decodeQRCode(qrCodeImagePath);
		            if (decodedUrl != null) {
		                System.out.println("Decoded URL: " + decodedUrl);
		 
		                // Launch Chrome and open the URL using WebDriverManager
		                WebDriverManager.chromedriver().setup();
		                WebDriver driver = new ChromeDriver();
		                driver.get(decodedUrl);
		 
		                // Optionally, add code to close the driver later
		                 //driver.quit();
		            } else {
		                System.out.println("No QR Code found in the image.");
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		 
		    private static String decodeQRCode(String filePath) throws IOException, NotFoundException {
		        BufferedImage bufferedImage = ImageIO.read(new File(filePath));
		        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
		        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		 
		        Result result;
		        try {
		            result = new MultiFormatReader().decode(bitmap);
		            return result.getText();
		        } catch (NotFoundException e) {
		            // Handle the error if the QR code is not found
		            return null;
		        }
		    }
		
		}

	


