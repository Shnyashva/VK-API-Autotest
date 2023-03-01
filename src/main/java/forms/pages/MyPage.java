package forms.pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import utilities.constants.Attributes;

public class MyPage extends Form {

    private static String dynamicWallPostLocator;
    private static String dynamicWallPostCommentLocator;
    private static String dynamicWallPostLikeLocator;
    private static String dynamicNotShownWallPostLocator;
    private static String dynamicNextCommentWallPostLocator;
    private final ITextBox POST_TEXT_BOX = AqualityServices.getElementFactory()
            .getTextBox(By.xpath(dynamicWallPostLocator), "Post Text Box");
    private final ITextBox POST_COMMENT_TEXT_BOX = AqualityServices.getElementFactory()
            .getTextBox(By.xpath(dynamicWallPostCommentLocator), "Post Comment Text Box");
    private final IButton NEXT_COMMENT_BUTTON = AqualityServices.getElementFactory()
            .getButton(By.xpath(dynamicNextCommentWallPostLocator), "Show Next Comment Button");
    private final IButton LIKE_BUTTON = AqualityServices.getElementFactory()
            .getButton(By.xpath(dynamicWallPostLikeLocator), "Post Like Button");
    private final ITextBox NOT_SHOWN_POST = AqualityServices.getElementFactory()
            .getTextBox(By.xpath(dynamicNotShownWallPostLocator), "Restore Post Text Box");

    public MyPage() {
        super(By.xpath(".//div[contains(@class, 'gradient')]"), "My Page");
    }

    public static void setWallPostLocators(String postId) {
        String wallPostLocator = ".//div[contains(@id, '%s')]//div[contains(@class, 'page_post')]//a";
        String wallPostCommentLocator = ".//div[contains(@id, '%s')]//div[contains(@class, 'reply_author')]//a";
        String wallPostLikeLocator = ".//div[contains(@id, '%s')]//div[contains(@class, 'PostButtonReactions__icon')]";
        String notShownWallPostLocator = ".//div[contains(@id, '%s') and contains(@class, 'unshown')]";
        String nextCommentWallPostLocator = ".//div[contains(@id, '%s')]//span[contains(@class, 'next_label')]";
        dynamicWallPostLocator = String.format(wallPostLocator, postId);
        dynamicWallPostCommentLocator = String.format(wallPostCommentLocator, postId);
        dynamicWallPostLikeLocator = String.format(wallPostLikeLocator, postId);
        dynamicNotShownWallPostLocator = String.format(notShownWallPostLocator, postId);
        dynamicNextCommentWallPostLocator = String.format(nextCommentWallPostLocator, postId);
    }

    public String getPhotoHref() {
        return POST_TEXT_BOX.getAttribute(Attributes.HREF.getAttributeName());
    }

    public String getOwnerIdFromComment() {
        return POST_COMMENT_TEXT_BOX.getAttribute(Attributes.DATA_FROM_ID.getAttributeName());
    }

    public void clickLikeButton() {
        LIKE_BUTTON.click();
    }

    public boolean isDeletedPostDisplayed() {
        return NOT_SHOWN_POST.state().isDisplayed();
    }

    public void clickNextCommentButton() {
        NEXT_COMMENT_BUTTON.click();
    }

    public void scrollToLikeButton() {
        LIKE_BUTTON.getJsActions().scrollToTheCenter();
    }
}
