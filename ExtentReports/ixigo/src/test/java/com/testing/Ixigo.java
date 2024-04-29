package com.testing;

import java.util.ArrayList;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Ixigo {

    WebDriver driver;
    JavascriptExecutor JS;
    ExtentReports ER;
    ExtentSparkReporter ESR;
    ExtentTest test;

    @BeforeTest
    public void BeforeTest()
    {
        ER=new ExtentReports();
        ESR=new ExtentSparkReporter("D:\\Programs\\Web Develouping\\Software Testing\\Extent Reports\\IxigoTest.html");
        ER.attachReporter(ESR);
    }

    @BeforeMethod
    public void BeforeMethod()throws Exception
    {
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        driver.get("https://www.ixigo.com");
        driver.manage().window().maximize();
        JS=(JavascriptExecutor)driver;
        Thread.sleep(3000);
    }

    @DataProvider(name = "SRC DES")
    public String[][] DataProvider()throws Exception
    {
        String[][] a=new String[1][2];
        FileInputStream fis=new FileInputStream("D:\\Programs\\Web Develouping\\Software Testing\\Extent Reports\\IxiGodata.xlsx");
        XSSFWorkbook wb=new XSSFWorkbook(fis);
        Sheet S=wb.getSheetAt(0);

        a[0][0]=S.getRow(1).getCell(0).toString();
        a[0][1]=S.getRow(1).getCell(1).toString();

        wb.close();
        fis.close();

        return a;

    }

    @Test(dataProvider = "SRC DES")
    public void test1(String a,String b)throws Exception
    {
        test=ER.createTest("Test 1");
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[1]/div[1]/div/button[2]")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[2]/div/div/div[2]/input")).sendKeys(a);
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[1]/div[3]/div[1]/li")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[1]/div")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[2]/div/div/div[2]/input")).sendKeys(b);
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/div[3]/div[1]/li")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[1]/div")).click();
        Thread.sleep(2000);
        WebElement nBut=driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[3]/div/div[1]/div[1]/button[3]"));
        for(int i=0;i<7;i++,Thread.sleep(2000))
        nBut.click();
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[3]/div/div[1]/div[2]/div[2]/div/div/div[2]/button[11]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[3]/div/div[1]/div[2]/div[1]/div/div/div[2]/button[13]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div/div")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[2]/div/div[1]/div[1]/div[2]/div/button[2]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[2]/div/div[1]/div[2]/div[2]/div/button[3]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[2]/div/div[1]/div[5]/div/div[3]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[3]/div[2]/div/div[2]/button")).click();
        Thread.sleep(3000);
        String RetDate=driver.findElement(By.xpath("/html/body/main/div[2]/div[1]/div[3]/div[2]/div[2]/div[2]/div/div[1]/div/div/p[2]")).getText();
        System.out.println(RetDate);
        if(RetDate.contains("08 Nov"))
        {
            test.log(Status.PASS, "Test Passed");
            assert(true);
        }
        else
        {
            test.log(Status.FAIL, "Test Failed");
            assert(false);
        }
    }
    
    @Test
    public void test2()throws Exception
    {
        test=ER.createTest("Test 2");
        JS.executeScript("window.scrollBy(0,3000,'smooth')", "");
        Thread.sleep(2000);
        driver.findElement(By.linkText("About Us")).click();
        Thread.sleep(3000);
        ArrayList<String>Tabs=new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(Tabs.get(1));
        Thread.sleep(2000);
        if(driver.getCurrentUrl().contains("about"))
        {
            test.log(Status.PASS, "Test Passed");
            assert(true);
        }
        else
        {
            test.log(Status.FAIL, "Test Failed");
            assert(false);
        }
    }

    @AfterMethod
    public void AfterMethod()throws Exception
    {
        ER.flush();
        Thread.sleep(3000);
        driver.quit();
    }

    @AfterTest
    public void AfterTest()throws Exception
    {
        Desktop.getDesktop().browse(new File("D:\\Programs\\Web Develouping\\Software Testing\\Extent Reports\\IxigoTest.html").toURI());
    }

}
