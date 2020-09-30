package demo_testng;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class automation_assignment {
    String url="https://in.bookmyshow.com/";
    public static WebDriver driver;
    @BeforeMethod
    @Parameters("browser")
    public  void setup(String browser) throws InterruptedException {
        if (browser.equalsIgnoreCase("Chrome")) {
            System.setProperty("webdriver.chrome.driver","C:\\Users\\chinn\\IdeaProjects\\automation_assignment\\DriverList\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.get(url);
            driver.manage().window().maximize();
            Thread.sleep(5000);
            WebDriverWait wait=new WebDriverWait(driver, 200);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inp_RegionSearch_top")));
            driver.findElement(By.id("inp_RegionSearch_top")).sendKeys("Kochi");
            driver.findElement(By.id("inp_RegionSearch_top")).sendKeys(Keys.ENTER);
            Thread.sleep(5000);
            WebDriverWait wait1=new WebDriverWait(driver, 200);
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-search-box")));

        } else if (browser.equalsIgnoreCase("IE")) {
            System.setProperty("webdriver.ie.driver", "C:\\Users\\chinn\\IdeaProjects\\automation_assignment\\DriverList\\IEDriverServer.exe");
            driver = new InternetExplorerDriver();
            driver.get(url);
            driver.manage().window().maximize();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            WebDriverWait wait2=new WebDriverWait(driver, 200);
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-search-box")));
            driver.findElement(By.id("inp_RegionSearch_top")).sendKeys("Kochi");
            driver.findElement(By.id("inp_RegionSearch_top")).sendKeys(Keys.ENTER);
            WebDriverWait wait3=new WebDriverWait(driver, 200);
            wait3.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-search-box")));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
    }


    @Test(priority = 0)
    public void searchMovie() throws InterruptedException, IOException {
        driver.findElement(By.id("input-search-box")).sendKeys("Fantasy Island");
        Thread.sleep(2000);
        driver.findElement(By.id("input-search-box")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        String title= driver.findElement(By.id("eventTitle")).getText();
        Assert.assertEquals("Fantasy Island",title.trim());
    }

    @Test(priority = 1)
    public void invalidMovie() throws InterruptedException {
        driver.findElement(By.id("input-search-box")).sendKeys("qq");
        Thread.sleep(2000);
        driver.findElement(By.id("input-search-box")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        String message=driver.findElement(By.xpath("//div[@class='__data-not-found']/div[2]")).getText();
        Assert.assertEquals("Oops! No results found",message);
    }

    @Test(priority = 2)
    public void searchCast() throws InterruptedException {
        Boolean found=false;
        Boolean foundMusician=false;
        driver.findElement(By.id("input-search-box")).sendKeys("Onward");
        Thread.sleep(2000);
        driver.findElement(By.id("input-search-box")).sendKeys(Keys.ENTER);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement Element = driver.findElement(By.xpath("//div[@id='cast']"));
        js.executeScript("arguments[0].scrollIntoView();", Element);
        List<WebElement>casts= driver.findElements(By.xpath("//a[@class='member-name banner-container']/div"));
        for (WebElement web:casts) {
            String[] list=web.getText().split("\n");
            String role=list[1];
            String name=list[0];
            if(name.equals("Tom Holland") && role.equals("Actor")){
                found=true;
            }
            if(name.equals("Mychael Danna") && role.equals("Musician")){
                foundMusician=true;
                break;
            }
        }
        Assert.assertTrue(found);
        Assert.assertTrue(foundMusician);
    }



    @Test(priority = 3)
    public void ListYourShow() throws InterruptedException {
        boolean found=false;
        WebDriverWait wait=new WebDriverWait(driver, 200);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navbar\"]/div[3]/div/div[2]/ul/li[1]/a")));
        driver.findElement(By.xpath("//*[@id=\"navbar\"]/div[3]/div/div[2]/ul/li[1]/a")).click();
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement Element = driver.findElement(By.xpath("//div[@class='grid-box']"));
        js.executeScript("arguments[0].scrollIntoView();", Element);
        List<String>showsInList=new ArrayList<>();
        showsInList.add("Online Streaming");
        showsInList.add("Performances");
        showsInList.add("Experiences");
        showsInList.add("Expositions");
        showsInList.add("Parties");
        showsInList.add("Sports");
        showsInList.add("Conferences");
        List<WebElement>shows=driver.findElements(By.xpath("//div[@class='__txt']"));
        for (WebElement ele:shows) {
            System.out.println(ele.getText());
            if(showsInList.contains(ele.getText())){
                found = true;
            }

        }
        Assert.assertTrue(found);

    }



    @Test(priority = 4)
    public void invalidCast() throws InterruptedException {
        Boolean found=false;
        Boolean foundMusician=false;
        driver.findElement(By.id("input-search-box")).sendKeys("Onward");
        Thread.sleep(2000);
        driver.findElement(By.id("input-search-box")).sendKeys(Keys.ENTER);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement Element = driver.findElement(By.xpath("//div[@id='cast']"));
        js.executeScript("arguments[0].scrollIntoView();", Element);
        driver.findElement(By.xpath("//*[@id=\"crew-carousel\"]/div/button[2]")).click();
        List<WebElement>casts= driver.findElements(By.xpath("//a[@class='member-name banner-container']/div"));
        for (WebElement web:casts) {
            String[] list=web.getText().split("\n");
            System.out.println(list.length+"   "+web.getText());
            if(list.length>=2){
                String name=list[0];
                String role=list[1];
                if(name.equals("Tom Cruise") && role.equals("Actor")){
                    found=true;
                }
                if(name.equals("Michael Jackson") && role.equals("Musician")){
                    foundMusician=true;
                }
            }

        }
        Assert.assertFalse(found);
        Assert.assertFalse(foundMusician);

    }
    @Test(priority = 5)
    public void rewardInOffer(){
        Boolean found=false;
        WebDriverWait wait=new WebDriverWait(driver, 200);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/header/nav/div[3]/div/div[2]/ul/li[3]/a")));
        driver.findElement(By.xpath("/html/body/div[5]/header/nav/div[3]/div/div[2]/ul/li[3]/a")).click();
        WebElement reward=driver.findElement(By.xpath("/html/body/div[5]/div[2]/section[1]/div/ul/li[4]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", reward);
        reward.click();

        List<String>rewards=new ArrayList<>();
        rewards.add("Reward Points Redemption");
        rewards.add("SimplyCLICK SBI Card Rewards Offer");
        rewards.add("PAYBACK POINTS");
        List<WebElement>rewardList=driver.findElements(By.xpath("//div[@class='__description']/h4"));
        for (WebElement ele:rewardList) {
            if(rewards.contains(ele.getText())){
                found=true;
            }
        }
        Assert.assertTrue(found);

    }

    @Test(priority = 6)
    public void moreLists() throws InterruptedException {
        boolean found=false;
        WebDriverWait wait=new WebDriverWait(driver, 200);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navbar\"]/div[3]/div/div[2]/ul/li[1]/a")));
        driver.findElement(By.xpath("//*[@id=\"navbar\"]/div[3]/div/div[2]/ul/li[1]/a")).click();
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement Element = driver.findElement(By.xpath("//div[@class='grid-box']"));
        js.executeScript("arguments[0].scrollIntoView();", Element);
        List<String>showsInList=new ArrayList<>();
        showsInList.add("Online Sales & Marketing");
        showsInList.add("Pricing");
        showsInList.add("Food & beverages, stalls and the works!");
        showsInList.add("On ground support  & gate entry management");
        showsInList.add("Reports & business insights");
        showsInList.add("POS, RFID, Turnstiles & more...");
        List<WebElement>shows=driver.findElements(By.xpath("//div[@class='__txt']"));
        for (WebElement ele:shows) {
            System.out.println(ele.getText());
            if(showsInList.contains(ele.getText())){
                found = true;
            }

        }
        Assert.assertTrue(found);
    }


    @Test(priority = 7)
    public void iciciReward(){
        WebDriverWait wait=new WebDriverWait(driver, 200);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/header/nav/div[3]/div/div[2]/ul/li[3]/a")));
        driver.findElement(By.xpath("/html/body/div[5]/header/nav/div[3]/div/div[2]/ul/li[3]/a")).click();
        WebElement element=driver.findElement(By.xpath("//input[@id='ajax-typeahead']"));
        element.sendKeys("ICICI Bank 25% Discount Offer");
        element.sendKeys(Keys.ENTER);
        String value=driver.findElement(By.xpath("(//div[@class='__description']/h4)[4]")).getText();
        Assert.assertEquals("ICICI BANK CREDIT CARD 25% DISCOUNT OFFER" +
                "",value);
    }



    @Test(priority = 8)
    public void footer(){
        Boolean foundFooter=false;
        WebDriverWait wait=new WebDriverWait(driver, 200);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='row']/div/h4")));
        WebElement element= driver.findElement(By.xpath("//div[@class='row']/div/h4"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
        List<String>footer=new ArrayList<>();
        footer.add("automation_assignment APP ");
        footer.add("automation_assignment NEWS");
        footer.add("EXCLUSIVES");
        footer.add("HELP");
        List<WebElement> footerList=driver.findElements(By.xpath("//div[@class='row']/div/h4"));
        for (WebElement ele:footerList) {
            if(footer.contains(ele.getText())){
                foundFooter=true;
            }
        }
        Assert.assertTrue(foundFooter);
    }

    @Test(priority = 9)
    public void language() throws InterruptedException {
        WebDriverWait wait=new WebDriverWait(driver, 200);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='lang-select']/a")));
        driver.findElement(By.xpath("//div[@class='lang-select']/a")).click();
        List<WebElement>langList=driver.findElements(By.xpath("//ul[@class='options']/li/label"));
        for (WebElement ele:langList) {
            System.out.println(ele.getText());
        }
        Thread.sleep(2000);
    }

    @Test(priority = 10)
    public void selectRegion() throws InterruptedException {
        driver.findElement(By.id("dTopRgnDD")).click();
        driver.findElement(By.id("inp_RegionSearch_top")).sendKeys("Goa");
        driver.findElement(By.id("inp_RegionSearch_top")).sendKeys(Keys.ENTER);
        Thread.sleep(5000);
        WebDriverWait wait=new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("spnSelectedRegion")));
        String area=driver.findElement(By.id("spnSelectedRegion")).getText();
        Assert.assertEquals("Goa",area);

    }

    @Test(priority = 11)
    public void kotakMahindra(){
        Boolean found=false;
        WebDriverWait wait=new WebDriverWait(driver, 200);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/header/nav/div[3]/div/div[2]/ul/li[3]/a")));
        driver.findElement(By.xpath("/html/body/div[5]/header/nav/div[3]/div/div[2]/ul/li[3]/a")).click();
        WebElement reward=driver.findElement(By.xpath("/html/body/div[5]/div[2]/section[1]/div/ul/li[4]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", reward);
        List<WebElement>rewardList=driver.findElements(By.xpath("//div[@class='__description']/h4"));
        for (WebElement ele:rewardList) {
            if("Kotak Mahindra Offer".equals(ele.getText())){
                found=true;
            }
        }
        Assert.assertFalse(found);
    }
    @AfterMethod
    public void closeBrowser(){
        driver.quit();

    }

    public static String takeScreenshot() throws IOException {
        File screenshot=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        Random random= new Random();
        String file="ss"+random.nextInt(1000)+".png";
        String fileName=System.getProperty("user.dir")+"\\reports\\"+file;
        File destinationFile=new File(fileName);
        FileUtils.copyFile(screenshot,destinationFile);
        return file;
    }


}
