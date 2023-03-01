package rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.TestDataManager;
import utilities.filehandler.FileHelper;
import utilities.filehandler.FileNames;

import java.util.Map;

public class RequestHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    public Response sendGetRequest(Map<String, ?> params, String endpoint) {
        LOGGER.info("Sending GET request");
        return RestAssured.given().queryParams(params).when().request(Method.GET, endpoint);
    }

    public Response sendPostRequest(Map<String, ?> params, String endpoint) {
        LOGGER.info("Sending POST request");
        return RestAssured.given().queryParams(params).when().request(Method.POST, endpoint);
    }

    public Response sendPostRequestWithPhoto(String uploadUrl) {
        TestDataManager data = new TestDataManager();
        LOGGER.info("Sending POST request");
        return RestAssured.given().multiPart(data.getParameter("/photo"),
                FileHelper.getResourceFileByName(FileNames.PHOTO.getFileName())).when().post(uploadUrl);
    }

    public <T> T getDataFromResponse(Response response, String node, Class<T> type) {
        Object result = response.jsonPath().get(node);
        if (type == String.class) {
            return type.cast(result.toString());
        } else if (type == Integer.class) {
            return type.cast(Integer.valueOf(result.toString()));
        }
        LOGGER.fatal("Unsupported type: " + type.getName());
        throw new IllegalArgumentException();
    }
}