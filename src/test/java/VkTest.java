import forms.pages.MainPage;
import forms.pages.MyPage;
import forms.pages.PasswordPage;
import forms.pages.UserPage;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import rest.RequestHandler;
import utilities.StringGenerator;
import utilities.TestDataManager;
import utilities.constants.AuthenticationFields;
import utilities.filehandler.FileNames;
import utilities.filehandler.JsonHandler;

import java.util.Map;

public class VkTest extends BaseTest {

    @Test
    public void runTest() {
        RequestHandler requestHandler;
        PasswordPage passwordPage;
        TestDataManager data;
        UserPage userPage;
        MyPage myPage;

        MainPage mainPage = new MainPage();
        data = new TestDataManager();
        mainPage.inputLogin(data.getLoginData(AuthenticationFields.LOGIN.getFieldName()));
        mainPage.clickSubmitButton();
        passwordPage = new PasswordPage();
        passwordPage.inputPassword(data.getLoginData(AuthenticationFields.PASSWORD.getFieldName()));
        passwordPage.clickSubmitButton();
        userPage = new UserPage();
        userPage.clickMyPageButton();
        requestHandler = new RequestHandler();

        String randomString = StringGenerator.generateRandomString(Integer.parseInt(data.getTestData("/stringLength")));
        Map<String, ?> newMethodParams = JsonHandler.parseJsonToMapWithBaseParams(FileNames.BASE_API_PARAMS);
        data.addParameterToMap(newMethodParams, data.getParameter("/sendMessage"), randomString);
        Response newWallPostResponse = requestHandler.sendPostRequest(newMethodParams,
                data.getMethod("/createNewPost"));
        Assert.assertEquals(newWallPostResponse.statusCode(), HttpStatus.SC_OK, "Wall post was not created");

        String postID = String.valueOf(requestHandler.getDataFromResponse(newWallPostResponse,
                data.getResponseNode("/postIdNode"), Integer.class));
        Map<String, ?> postParams = JsonHandler.parseJsonToMapWithBaseParams(FileNames.BASE_API_PARAMS);
        data.addParameterToMap(postParams, data.getParameter("/getPostsById"), data.getDataById(postID));
        Response postDataResponse = requestHandler.sendGetRequest(postParams, data.getMethod("/getPostById"));
        Assert.assertEquals(postDataResponse.statusCode(), HttpStatus.SC_OK, "Get request was not sent");

        String textFromPost = requestHandler.getDataFromResponse(postDataResponse,
                data.getResponseNode("/textNode"), String.class);
        Assert.assertEquals(textFromPost, randomString,
                "Text from the published post is not equal to generated text");

        Map<String, ?> uploadServerParams = JsonHandler.parseJsonToMapWithBaseParams(FileNames.BASE_API_PARAMS);
        Response uploadServerResponse = requestHandler.sendPostRequest(uploadServerParams,
                data.getMethod("/getWallUploadServer"));
        Assert.assertEquals(uploadServerResponse.statusCode(), HttpStatus.SC_OK,
                "Photo was not uploaded to server");

        String uploadUrl = requestHandler.getDataFromResponse(uploadServerResponse,
                data.getResponseNode("/uploadUrl"), String.class);
        Response uploadPhotoToServerResponse = requestHandler.sendPostRequestWithPhoto(uploadUrl);
        Assert.assertEquals(uploadPhotoToServerResponse.statusCode(), HttpStatus.SC_OK,
                "Photo was not uploaded to server");

        String server = String.valueOf(requestHandler.getDataFromResponse(uploadPhotoToServerResponse,
                data.getResponseNode("/serverNode"), Integer.class));
        String photo = requestHandler.getDataFromResponse(uploadPhotoToServerResponse,
                data.getResponseNode("/photoNode"), String.class);
        String hash = requestHandler.getDataFromResponse(uploadPhotoToServerResponse,
                data.getResponseNode("/hashNode"), String.class);
        Map<String, ?> uploadPhotoToTheWallParams = JsonHandler.parseJsonToMapWithBaseParams(FileNames.BASE_API_PARAMS);
        data.addParameterToMap(uploadPhotoToTheWallParams, data.getParameter("/server"), server);
        data.addParameterToMap(uploadPhotoToTheWallParams, data.getParameter("/photo"), photo);
        data.addParameterToMap(uploadPhotoToTheWallParams, data.getParameter("/hash"), hash);
        Response uploadPhotoToTheWallResponse = requestHandler.sendPostRequest(uploadPhotoToTheWallParams,
                data.getMethod("/saveWallPhoto"));
        Assert.assertEquals(uploadPhotoToTheWallResponse.statusCode(), HttpStatus.SC_OK,
                "Photo was not uploaded to the album");

        String photoId = String.valueOf(requestHandler.getDataFromResponse(uploadPhotoToTheWallResponse,
                data.getResponseNode("/photoId"), Integer.class));
        String newRandomString = StringGenerator.generateRandomString(Integer.parseInt(data.getTestData("/stringLength")));
        Map<String, ?> editWallPostParams = JsonHandler.parseJsonToMapWithBaseParams(FileNames.BASE_API_PARAMS);
        data.addParameterToMap(editWallPostParams, data.getParameter("/attachments"), data.getAttachment(photoId));
        data.addParameterToMap(editWallPostParams, data.getParameter("/sendMessage"), newRandomString);
        data.addParameterToMap(editWallPostParams, data.getParameter("/post"), postID);
        Response wallPostEditResponse = requestHandler.sendPostRequest(editWallPostParams, data.getMethod("/editPost"));
        Assert.assertEquals(wallPostEditResponse.statusCode(), HttpStatus.SC_OK,
                "Wall post was not edited");

        MyPage.setWallPostLocators(postID);
        myPage = new MyPage();
        myPage.scrollToLikeButton();
        Assert.assertTrue(myPage.getPhotoHref().contains(data.getAttachment(photoId)), "Photo was not uploaded");

        String randomComment = StringGenerator.generateRandomString(Integer.parseInt(data.getTestData("/stringLength")));
        Map<String, ?> createCommentParams = JsonHandler.parseJsonToMapWithBaseParams(FileNames.BASE_API_PARAMS);
        data.addParameterToMap(createCommentParams, data.getParameter("/post"), postID);
        data.addParameterToMap(createCommentParams, data.getParameter("/sendMessage"), randomComment);
        Response addCommentResponse = requestHandler.sendPostRequest(createCommentParams, data.getMethod("/addComment"));
        Assert.assertEquals(addCommentResponse.statusCode(), HttpStatus.SC_OK,
                "Post was not commented");

        myPage.clickNextCommentButton();
        Assert.assertEquals(myPage.getOwnerIdFromComment(), data.getTestData("/ownerId"),
                "Post was not commented by needed user");

        myPage.clickLikeButton();
        Map<String, ?> getLikeParams = JsonHandler.parseJsonToMapWithBaseParams(FileNames.BASE_API_PARAMS);
        data.addParameterToMap(getLikeParams, data.getParameter("/post"), postID);
        Response likeResponse = requestHandler.sendPostRequest(getLikeParams, data.getMethod("/getLikes"));
        Assert.assertEquals(likeResponse.statusCode(), HttpStatus.SC_OK,
                "Post was not liked");

        String likeOwnerId = String.valueOf(requestHandler.getDataFromResponse(likeResponse,
                data.getResponseNode("/likeOwnerId"), Integer.class));
        Assert.assertEquals(likeOwnerId, data.getTestData("/ownerId"), "Like was not made by needed user");

        Map<String, ?> deleteWallParams = JsonHandler.parseJsonToMapWithBaseParams(FileNames.BASE_API_PARAMS);
        data.addParameterToMap(deleteWallParams, data.getParameter("/post"), postID);
        Response postDeleteResponse = requestHandler.sendPostRequest(deleteWallParams, data.getMethod("/deleteWall"));
        Assert.assertEquals(postDeleteResponse.statusCode(), HttpStatus.SC_OK,
                "Post was not deleted");

        Assert.assertFalse(myPage.isDeletedPostDisplayed(), "The post was not deleted");
    }
}
