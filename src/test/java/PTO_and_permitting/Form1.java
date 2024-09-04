package PTO_and_permitting;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Form1 {
	
	public static WebDriver driver;
	
	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		Form1  f1 = new Form1();
		
		 f1.login_Application(0, 0);
		 f1.TC1();
		 f1.cell(0, 0);
	}
	@BeforeClass
	public void OpenBrowser() {
	ChromeOptions option = new ChromeOptions();
    option.addArguments("--remote-allow-origins=*");
    WebDriverManager.chromedriver().setup();  
    driver = new ChromeDriver(option);
    driver.get("https://www.sce.com/residential");
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
}
	
	@BeforeMethod
	public void login_Application(int Key,  int Keys) throws EncryptedDocumentException, IOException {
		
		FileInputStream  file = new FileInputStream("C:\\pto_Permitting excel sheet\\New Microsoft Excel Worksheet.xlsx");
		
		Sheet sh= WorkbookFactory.create(file).getSheet("Sheet1");
		int numRows = sh.getPhysicalNumberOfRows();
		for(int i=0; i<=numRows; i++) {
			Row row = sh.getRow(i);
			if (row != null) {
		        int numCols = row.getLastCellNum();// get number of column in a single row 
			    for(int j=0; j< numCols; j++) {
			    	String cell = row.getCell(j).getStringCellValue(); // Get the cell
				    //String value = sh.getRow(row).getCell(Keys).getStringCellValue();
				    System.out.println(cell);
		}
			   
			}
			 
		}
//		driver.get("https://www.sce.com/residential/generating-your-own-power/net-energy-metering?from=/nem");
	}
	
	@Test
	public void TC1() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[text()='Log IN/Register']")).click();
		WebElement userName = driver.findElement(By.xpath("//input[@placeholder='User ID / Email']"));
		userName.sendKeys(cell(0,0));
	}
	
	private CharSequence cell(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@AfterMethod
	public void Logout_Application() {
	}
	
	@AfterClass
	public void ClosedBrowser() {
		driver.close();
	}



}
