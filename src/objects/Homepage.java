package objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Homepage {
	
	public static final String URL = "https://www.humanity.com/";
	public static final String BTN_ABOUT_US = "//*[@id=\"navbarSupportedContent\"]/ul/li[6]/a";
	
	
	public static void clickAboutUsBtn(WebDriver driver) {
		driver.get(URL); 
		driver.findElement(By.xpath(BTN_ABOUT_US)); 
		
	}

}
