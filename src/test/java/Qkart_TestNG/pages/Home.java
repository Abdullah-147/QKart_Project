package Qkart_TestNG.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
	WebDriver driver;
	String url = "https://crio-qkart-frontend-qa.vercel.app";
	WebDriverWait wait;

	public Home(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	public void navigateToHome() {
		if (!driver.getCurrentUrl().equals(this.url)) {
			driver.get(this.url);
		}
	}

	public Boolean PerformLogout() throws InterruptedException {
		try {
			// Find and click on the Logout Button
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("MuiButton-text")));
			WebElement logout_button = driver.findElement(By.className("MuiButton-text"));
			logout_button.click();

			// SLEEP_STMT_10: Wait for Logout to complete

			// Wait for Logout to Complete
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Login']")));
			// Thread.sleep(3000);

			return true;
		} catch (Exception e) {
			// Error while logout
			return false;
		}
	}

	/*
	 * Returns Boolean if searching for the given product name occurs without any
	 * errors
	 */
	public Boolean searchForProduct(String product) {
		try {
			// TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
			// Clear the contents of the search box and Enter the product name in the search
			// box
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='search']")));
			WebElement searchBox = driver.findElement(By.cssSelector("input[name='search']"));
			searchBox.clear();
			searchBox.sendKeys(product);
			return true;
		} catch (Exception e) {
			System.out.println("Error while searching for a product: " + e.getMessage());
			return false;
		}
	}

	/*
	 * Returns Array of Web Elements that are search results and return the same
	 */
	public List<WebElement> getSearchResults() {
		List<WebElement> searchResults = new ArrayList<WebElement>();
		try {
			// TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
			// Find all webelements corresponding to the card content section of each of
			// search results
			Thread.sleep(3000);
			// wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'elevation')]")));
			searchResults = driver.findElements(By.xpath("//div[contains(@class,'elevation')]"));
			return searchResults;
		} catch (Exception e) {
			System.out.println("There were no search results: " + e.getMessage());
			return searchResults;

		}
	}

	/*
	 * Returns Boolean based on if the "No products found" text is displayed
	 */
	public Boolean isNoResultFound() {
		Boolean status = false;
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h4")));
			WebElement noItemsMessage = driver.findElement(By.tagName("h4"));
			// TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
			// Check the presence of "No products found" text in the web page. Assign status
			// = true if the element is *displayed* else set status = false
			if (noItemsMessage.isDisplayed())
				status = true;
			return status;
		} catch (Exception e) {
			return status;
		}
	}

	/*
	 * Return Boolean if add product to cart is successful
	 */
	public Boolean addProductToCart(String productName) {
		try {
			// TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
			/*
			 * Iterate through each product on the page to find the WebElement corresponding
			 * to the matching productName
			 * 
			 * Click on the "ADD TO CART" button for that element
			 * 
			 * Return true if these operations succeeds
			 */
			Boolean b = false;
			// wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[contains(@class,'elevation')]//button"),0));
			wait.until(ExpectedConditions.textToBePresentInElementLocated(
					By.xpath("//div[contains(@class,'elevation')]//p[1]"), productName));
			List<WebElement> prodList = driver.findElements(By.xpath("//div[contains(@class,'elevation')]//p[1]"));
			for (int i = 0; i < prodList.size(); i++) {
				if (prodList.get(i).getText().equals(productName)) {
					// wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[text()='Add
					// to cart'])["+k+"]"))).click();
					// Thread.sleep(3000);
					int k = i + 1;
					String xpath = "(//button[text()='Add to cart'])[" + k + "]";
					driver.findElement(By.xpath(xpath)).click();
					Thread.sleep(2000);
					b = true;
					break;
				}
			}
			if (!b)
				System.out.println("Unable to find the given product");
			return b;
		} catch (Exception e) {
			System.out.println("Exception while performing add to cart: " + e.getMessage());
			return false;
		}
	}

	/*
	 * Return Boolean denoting the status of clicking on the checkout button
	 */
	public Boolean clickCheckout() {
		Boolean status = false;
		try {
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class,'checkout')]")));
			driver.findElement(By.xpath("//button[contains(@class,'checkout')]")).click();
			status = true;
			return status;
		} catch (Exception e) {
			System.out.println("Exception while clicking on Checkout: " + e.getMessage());
			return status;
		}
	}

	/*
	 * Return Boolean denoting the status of change quantity of product in cart
	 * operation
	 */
	public Boolean changeProductQuantityinCart(String productName, String qty) {
		int quantity = Integer.parseInt(qty);
		try {
			// TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 06: MILESTONE 5
			String xpathQty = "//div[contains(text(),'" + productName
					+ "')]/parent::div//*[contains(@data-testid,'qty')]";
			WebElement qtyy = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathQty)));
			int defaultQty = Integer.parseInt(qtyy.getText());
			if (defaultQty < quantity) {
				String xpathIncr = "//div[contains(text(),'" + productName
						+ "')]/parent::div/div[2]//button//*[contains(@data-testid,'Add')]";
				WebElement incrementButton = driver.findElement(By.xpath(xpathIncr));
				while (defaultQty != quantity) {
					incrementButton.click();
					defaultQty++;
					wait.until(ExpectedConditions.textToBe(By.xpath(xpathQty), "" + defaultQty));
				}
				return true;
			} else if (defaultQty > quantity) {
				String xpath = "//div[text()='" + productName
						+ "']/parent::div/div[2]//button//*[contains(@data-testid,'Remove')]";
				WebElement decrementButton = driver.findElement(By.xpath(xpath));
				while (defaultQty != quantity) {
					decrementButton.click();
					Thread.sleep(2000);
					defaultQty--;
				}
				return true;
			} else if (defaultQty == quantity) {
				return true;
			}
			// Find the item on the cart with the matching productName

			// Increment or decrement the quantity of the matching product until the current
			// quantity is reached (Note: Keep a look out when then input quantity is 0,
			// here we need to remove the item completely from the cart)

			return false;
		} catch (Exception e) {
			if (quantity == 0)
				return true;
			System.out.println("exception occurred when updating cart: " + e.getMessage());
			return false;
		}
	}

	/*
	 * Return Boolean denoting if the cart contains items as expected
	 */
	public Boolean verifyCartContents(List<String> expectedCartContents) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'cart Mui')]")));
			WebElement cartParent = driver.findElement(By.xpath("//div[contains(@class,'cart Mui')]"));
			List<WebElement> cartContents = cartParent
					.findElements(By.xpath("//div[contains(@class,'css-1gjj37g')]/child::div[1]"));

			ArrayList<String> actualCartContents = new ArrayList<String>() {
			};
			for (WebElement cartItem : cartContents) {
				actualCartContents.add(cartItem.getText());
			}

			for (String expected : expectedCartContents) {
				if (!actualCartContents.contains(expected)) {
					return false;
				}
			}

			return true;

		} catch (Exception e) {
			System.out.println("Exception while verifying cart contents: " + e.getMessage());
			return false;
		}
	}

	public Boolean checkIfCartContainsItem(String productName) {
		Boolean status = false;
		wait.until(ExpectedConditions
				.textToBe(By.xpath("//div[contains(@class,'cart')]//div[text()='" + productName + "']"), productName));
		status = driver.findElement(By.xpath("//div[contains(@class,'cart')]//div[text()='" + productName + "']"))
				.getText().equalsIgnoreCase(productName);
		return status;
	}

	public Boolean navigateToPageAndValidate(String LinkText, String urlEndsWith) {
		Boolean status = false;
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText(LinkText))).click();
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			driver.switchTo().window(window);
			if (driver.getCurrentUrl().endsWith(urlEndsWith))
				break;
		}
		status = driver.findElement(By.tagName("h2")).getText().equalsIgnoreCase(LinkText);
		return status;
	}

	public Boolean closeAllTabs(String parentWindow) {
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			if (!window.equals(parentWindow)) {
				driver.switchTo().window(window);
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
		return driver.getWindowHandle().equals(parentWindow);
	}

	public Boolean validateContactUsForm(String userName, String email, String message) {
		Boolean status = false;
		driver.findElement(By.xpath("//p[text()='Contact us']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[class*=contact]")));
		driver.findElement(By.cssSelector("input[name=email][placeholder=Name]")).sendKeys(userName);
		driver.findElement(By.cssSelector("input[name=email][placeholder=Email]")).sendKeys(email);
		driver.findElement(By.cssSelector("input[name=email][placeholder=Message]")).sendKeys(message);
		driver.findElement(By.xpath("//button[text()=' Contact Now']")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("h3[class*=contact]")));
		List<WebElement> list = driver.findElements(By.cssSelector("h3[class*=contact]"));
		status = list.size() == 0;
		return status;
	}
}
