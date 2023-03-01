package forms.pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class UserPage extends Form {

    private final IButton MY_PAGE_BUTTON = AqualityServices.getElementFactory()
            .getButton(By.xpath(".//li[@id='l_pr']//span[contains(@class, 'left_label')]"), "My Page Button");

    public UserPage() {
        super(By.xpath(".//li[@id='l_pr']//span[contains(@class, 'left_label')]"), "User Page");
    }

    public void clickMyPageButton() {
        MY_PAGE_BUTTON.state().waitForDisplayed();
        MY_PAGE_BUTTON.state().waitForClickable();
        MY_PAGE_BUTTON.clickAndWait();
    }
}
