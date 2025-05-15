package com.QRCode;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeAutomation {

    public static void main(String[] args) throws WriterException, IOException {

        // Define the URL to be encoded in the QR code
        String url = "https://idemia-mobile-id.com/testqr";

        // Generate the QR code image
        String filePath = "qrcode.png"; // Path to save the QR code image
        int width = 350;
        int height = 350;

        BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height);

        // Convert BitMatrix to BufferedImage
        BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
        ImageIO.write(image, "png", new File(filePath));

        // Open the generated QR code in a new browser tab
        //System.setProperty("webdriver.chrome.driver", "chromedriver"); // Use the system property for driver location
        ChromeOptions options = new ChromeOptions();
        
        
        
        options.addArguments("--no-sandbox"); 
        options.addArguments("--disable-dev-shm-usage"); 
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(url); // Open the URL directly in the browser
        } finally {
            driver.quit();
        }
    }
}