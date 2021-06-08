import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestT {
    public static void main(String[] args) throws InterruptedException, IOException {
        // declaration and instantiation of objects/variables
    	System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
	    WebDriver driver = new ChromeDriver();
	    
        driver.manage().window().maximize();
        String baseUrl = "https://www.humanity.com/about";


        
        driver.get(baseUrl);
        takeSnapShot(driver);
        login(driver, "mackabozana@gmail.com", "Katanac123");
        addEmployee(driver);
        uploadImg(driver);
        changeTwoThingsInProfile(driver);
        changeLanguage(driver);
        addFromExel(driver);


    }

    public static void takeSnapShot(WebDriver webdriver) {
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(webdriver);
        try {
            ImageIO.write(screenshot.getImage(), "PNG", new File("C:\\Zavrsni projekat\\Zavrsni projekat\\img"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public static void login(WebDriver driver, String email, String passwordInput) {
        driver.findElement(By.linkText("LOGIN")).click();

        WebElement username = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.name("password"));

        username.sendKeys(email);
        password.sendKeys(passwordInput);
        driver.findElement(By.name("login")).click();

    }

    public static void addEmployee(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("#sn_staff")).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement ew = driver.findElement(By.cssSelector("#act_primary"));
        ew.click();


        WebElement name = driver.findElement(By.id("_asf1"));
        WebElement lastName = driver.findElement(By.id("_asl1"));
        WebElement email = driver.findElement(By.id("_ase1"));

        name.sendKeys("name test");
        lastName.sendKeys("last name test");
        email.sendKeys("boza@boz.com");

        driver.findElement(By.id("_as_save_multiple")).click();
        WebElement status = driver.findElement(By.id("_status"));
        String s = status.getText();
        if (!s.contains("invalid")) {
            System.out.println("uspesno");
        } else
            System.out.println("ne uspesno");

    }

    private static void uploadImg(WebDriver driver) throws InterruptedException {
        Thread.sleep(3000);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("#sn_staff")).click();
        Thread.sleep(3000);
        driver.findElement(
                By.cssSelector("div.StaffListTable-employee-row:nth-child(1) > div:nth-child(2) > a:nth-child(1)")).click();
        driver.findElement(
                By.cssSelector(".EmployeeTop > a:nth-child(5)")).click();
        WebElement upload = driver.findElement(By.name("Filedata"));
        WebElement name = driver.findElement(By.name("first_name"));
        WebElement save = driver.findElement(By.name("update"));
        upload.sendKeys("C:\\Zavrsni projekat\\Zavrsni projekat\\img");
        name.clear();
        name.sendKeys("boza");
        save.click();
    }

    private static void addFromExel(WebDriver driver) throws IOException {
        File myFile = new File("C:\\Zavrsni projekat\\Zavrsni projekat\\src\\selenium.xlsx");
        FileInputStream fis = new FileInputStream(myFile);

        // Finds the workbook instance for XLSX file
        XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);

        // Return first sheet from the XLSX workbook
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);

        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = mySheet.iterator();
        ArrayList<Employees> employees = new ArrayList<>();
        // Traversing over each row of XLSX file
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            // For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            int i = 0;
            String name = null;
            String lastName = null;
            String email = null;
            while (cellIterator.hasNext()) {

                i++;
                Cell cell = cellIterator.next();
                if (i == 1) {
                    name = cell.getStringCellValue();
                } else if (i == 2) {
                    lastName = cell.getStringCellValue();

                } else if (i == 3) {
                    email = cell.getStringCellValue();
                    employees.add(new Employees(name, lastName, email));
                    i = 0;

                }
               /* System.out.println("cell.getStringCellValue() = " + cell.getStringCellValue());
                System.out.println("i = " + i);*/

            }

        }
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("#sn_staff")).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement ew = driver.findElement(By.cssSelector("#act_primary"));
        ew.click();


        for (int i = 0; i < employees.size(); i++) {
            WebElement name = driver.findElement(By.id("_asf" + (i + 1)));
            WebElement lastName = driver.findElement(By.id("_asl" + (i + 1)));
            WebElement email = driver.findElement(By.id("_ase" + (i + 1)));

            name.sendKeys(employees.get(i).getName());
            lastName.sendKeys(employees.get(i).getLastName());
            email.sendKeys(employees.get(i).getEmail());


        } driver.findElement(By.id("_as_save_multiple")).click();
        WebElement status = driver.findElement(By.id("_status"));
        String s = status.getText();
        if (!s.contains("invalid")) {
            System.out.println("uspesno");
        } else
            System.out.println("ne uspesno");

    }


    private static void changeLanguage(WebDriver driver) throws IOException, InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("#sn_admin")).click();
        Select language = new Select(driver.findElement(By.name("language")));
        Thread.sleep(3000);
        language.selectByIndex(3);

        WebElement email = driver.findElement(By.name("pref_email"));
        WebElement sms = driver.findElement(By.name("pref_sms"));
        WebElement mobile = driver.findElement(By.name("pref_mobile_push"));

        if (email.isSelected()) {
            email.click();
        }
        if (sms.isSelected()) {
            sms.click();
        }
        if (mobile.isSelected()) {
            mobile.click();
        }
        driver.findElement(By.id("_save_settings_button")).click();


    }

    private static void changeTwoThingsInProfile(WebDriver driver) throws InterruptedException {
        Thread.sleep(3000);

        driver.findElement(By.id("wrap_us_menu")).click();
        Thread.sleep(3000);
        driver.findElement(By.cssSelector(".userm > a:nth-child(5)")).click();

        Thread.sleep(3000);

        WebElement nickname = driver.findElement(By.name("nick_name"));
        WebElement address = driver.findElement(By.name("address"));
        WebElement city = driver.findElement(By.name("city"));
        nickname.clear();
        address.clear();
        city.clear();
        nickname.sendKeys("maki");
        address.sendKeys("Prvomajska");
        city.sendKeys("zemun");

        driver.findElement(By.name("update")).click();
    }
}