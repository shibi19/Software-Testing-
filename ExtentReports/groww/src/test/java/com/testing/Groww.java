package com.testing;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

// import com.relevantcodes.extentreports.ExtentReports;
// import com.relevantcodes.extentreports.ExtentTest;
// import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Groww {
    WebDriver driver;
    ExtentReports ER;
    ExtentTest test;

    @BeforeTest
    public void BeforeTest()
    {
        ER=new ExtentReports();
        ExtentSparkReporter ESR=new ExtentSparkReporter("D:\\Programs\\Web Develouping\\Software Testing\\Extent Reports\\Groww.html");
        ER.attachReporter(ESR);
        test=ER.createTest("Groww");
    }

    @Test
    public void checkBrowser()throws Exception
    {
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        driver.get("https://www.Groww.in");
        driver.manage().window().maximize();;
        Thread.sleep(3000);
        JavascriptExecutor JS=(JavascriptExecutor)driver;
        JS.executeScript("window.scrollBy(0,4000)", "");
        Thread.sleep(3000);
        driver.findElement(By.linkText("Calculators")).click();
        Thread.sleep(3000);
        if(driver.findElement(By.className("displayBase")).getText().equals("Calculators"))
        {
            String aa=captureScreenshot("Calculator.jpg");
            test.addScreenCaptureFromPath(aa);
        }
        else
        {
            test.log(Status.FAIL,"Failed To Redirect Calculator Page");
            return;
        }
        Thread.sleep(3000);
        JS.executeScript("window.scrollBy(0,1000)", "");

        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div[2]/a[15]/div/p[1]")).click();

        Thread.sleep(3000);
        WebElement LA=driver.findElement(By.xpath("//*[@id='LOAN_AMOUNT']"));
        LA.clear();
        LA.sendKeys(valuefromExcel(0));

        Thread.sleep(3000);
        WebElement RA=driver.findElement(By.xpath("//*[@id='RATE_OF_INTEREST']"));
        RA.clear();
        RA.sendKeys(valuefromExcel(1));

        Thread.sleep(3000);
        WebElement Y=driver.findElement(By.xpath("//*[@id='LOAN_TENURE']"));
        Y.clear();
        Y.sendKeys(valuefromExcel(2));

        Thread.sleep(3000);
        JS.executeScript("window.scrollBy(0,400)", "");

        Thread.sleep(3000);
        if(driver.findElement(By.xpath("//*[@id='root']/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/div/p")).getText().equals("Your Amortization Details (Yearly/Monthly)"))
        {
            System.out.println(driver.findElement(By.xpath("//*[@id='root']/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/div/p")).getText());
            String aa=captureScreenshot("Details.jpg");
            test.addScreenCaptureFromPath(aa);
        }
        else
        {
            test.log(Status.FAIL, "Fail to Redirect Home Loan EMI PAge");
            return;
        }

        Thread.sleep(3000);
        test.log(Status.PASS, "Test is Passed");
        assert(true);
    }

    @AfterTest
    public void AfterTest()throws Exception
    {
        ER.flush();
        Desktop.getDesktop().browse(new File("D:\\Programs\\Web Develouping\\Software Testing\\Extent Reports\\Groww.html").toURI());
        driver.quit();
    }

    public String captureScreenshot(String a)throws Exception
    {
        TakesScreenshot TS=(TakesScreenshot)driver;
        File src=TS.getScreenshotAs(OutputType.FILE);
        File dsn=new File("D:\\Programs\\Web Develouping\\Software Testing\\Extent Reports\\"+a);
        FileUtils.copyFile(src, dsn);
        return dsn.getAbsolutePath();
    }

    public static String valuefromExcel(int a)throws Exception
    {
        FileInputStream fis=new FileInputStream("D:\\Programs\\Web Develouping\\Software Testing\\Extent Reports\\Growwdata.xls");
        HSSFWorkbook wb=new HSSFWorkbook(fis);
        Sheet S=wb.getSheetAt(0);
        String SS=S.getRow(a).getCell(1).toString();
        wb.close();
        fis.close();
        return SS;

    }
}
