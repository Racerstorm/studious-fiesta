package SeleniumPractice.DemoProjectSelenium;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;



public class CarouselHandling 
{
	static WebDriver driver=null;
	
    public static void main( String[] args )
    {
    	String driverPath = "C:\\Automation\\WebDrivers\\chromedriver.exe";
    	System.setProperty("webdriver.chrome.driver", driverPath);
    	ChromeOptions options = new ChromeOptions();
    	options.addArguments("--start-maximized");
		options.addArguments("disable-infobars");
		
		 driver = new ChromeDriver(options);
    	
		
		driver.get("https://www.noon.com/uae-en/");
	//	String str = driver.findElement(By.xpath("(//h3[text()='Recommended For You']/../../../div[2]//*/a/div)[1]")).getAttribute("title"); 
		//System.out.print(str);
		//getProductsfromCarousel("Recommended For You");
		//getProductsfromCarousel("Save big on mobiles & tablets");
		//getProductsfromCarousel("Top picks in electronics");
		dynamicScroll();
		
		getProductsfromCarousel("Top picks in laptops");
		getProductsfromCarousel("Save more with beauty gift boxes");
		getProductsfromCarousel("Your Ramadan skincare routine");
		getProductsfromCarousel("Bestselling fragrances");
		//driver.quit();
		
    }
    
    public static void getProductsfromCarousel(String categoryName) 
    {
    	List<String> list=new ArrayList<String>();
    	String categoryXpath = "//h3[text()='"+categoryName+"']/../../../div[2]//*/a/div";
    	String carouselbutton = "//h3[text()='"+categoryName+"']/../../../*//div[contains(@class,'swiper-button-next')]";
    	String productXpathUnderCategory="";
    	 	int categoryItemsCount = driver.findElements(By.xpath(categoryXpath)).size();
    	
    	//Scroll to the category on the page
    	
//    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", scrolltoSection);
//    	try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
    	
    	WebElement carouselButtonElement = driver.findElement(By.xpath(carouselbutton));
    	
    	for(int i =1;i<=categoryItemsCount;i++)
    	{
    		productXpathUnderCategory="("+categoryXpath+")["+i+"]";
    		
    		while(i<=categoryItemsCount)
    		{
		    		productXpathUnderCategory="("+categoryXpath+")["+i+"]";
		    		WebElement product=driver.findElement(By.xpath(productXpathUnderCategory));
		    		if(product.isDisplayed())
		    		{
		    		list.add(product.getAttribute("title"));
		    		i++;
		    		}
		    		else if(carouselButtonElement.isDisplayed())
		    		{
		    			carouselButtonElement.click();
		        	//WebElement productinCarousel=driver.findElement(By.xpath(categoryContainer));
		    		}
		    		
		    		else
		    		{
		    			System.out.print("Reached at the end of the carousel");
		    		}
    		}
    	
    	}
    	
    	System.out.print("The '"+categoryName+"' category contains "+categoryItemsCount+" number of products.	\n");
    	Collections.sort(list); 
    	Iterator<String> itr = list.iterator();
        while(itr.hasNext()) 
        {
        	System.out.println(itr.next());
        }
        System.out.print("\n-----------------------------------------------------------------------------------------------------\n");
    	
    	
    }
    
    public static void scrollpage(String elem)
    {
    	//WebDriverWait wait = new WebDriverWait(driver, 10);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		 Boolean flag=true;
    	
			// javascriptexecutor to scroll the page
    		
    		//js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			while(flag)
			{
			js.executeScript("scroll(0, 100);");
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)							
					.withTimeout(Duration.ofSeconds(30))
					.pollingEvery(Duration.ofSeconds(1))
					.ignoring(NoSuchElementException.class);

			WebElement clickseleniumlink = wait.until(new Function<WebDriver, WebElement>()
			{
				public WebElement apply(WebDriver driver ) {
					return driver.findElement(By.xpath("elem"));
					
				}
				
			});
			}
			flag=false;
    	
    	

    }
    
    public static void dynamicScroll()
    {
    	JavascriptExecutor js = (JavascriptExecutor)driver;
    	long initialheight = (Long) (js.executeScript("return document.body.scrollHeight"));
    	
    	while(true)
    	{
    		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
    		try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		long currentHeight = (Long) (js.executeScript("return document.body.scrollHeight")); 
    		if(initialheight==currentHeight)
    		{
    			break;
    		}
    		
    		initialheight=currentHeight;
    	}
    }

}
