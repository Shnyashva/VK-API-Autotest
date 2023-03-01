package forms.pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class PasswordPage extends Form {

    private final ITextBox PASSWORD_TEXT_BOX = AqualityServices.getElementFactory()
            .getTextBox(By.name("password"), "Password Text Box");
    private final IButton SUBMIT_BUTTON = AqualityServices.getElementFactory()
            .getButton(By.xpath(".//button[@type='submit']"), "Submit Button");

    public PasswordPage() {
        super(By.name("password"), "Password Input Page");
    }

    public void inputPassword(String password) {
        PASSWORD_TEXT_BOX.clearAndTypeSecret(password);
    }

    public void clickSubmitButton() {
        SUBMIT_BUTTON.clickAndWait();
    }
}
