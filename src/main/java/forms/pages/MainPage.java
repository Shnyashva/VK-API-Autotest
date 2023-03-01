package forms.pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MainPage extends Form {

    private final ITextBox LOGIN_TEXT_BOX = AqualityServices.getElementFactory()
            .getTextBox(By.id("index_email"), "Login Text Box");
    private final IButton SUBMIT_BUTTON = AqualityServices.getElementFactory()
            .getButton(By.xpath(".//button[contains(@class, 'signInButton')]"), "Submit Button");

    public MainPage() {
        super(By.id("index_login"), "Main Page");
    }

    public void inputLogin(String login) {
        LOGIN_TEXT_BOX.clearAndTypeSecret(login);
    }

    public void clickSubmitButton() {
        SUBMIT_BUTTON.clickAndWait();
    }
}
