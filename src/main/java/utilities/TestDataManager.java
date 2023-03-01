package utilities;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import utilities.filehandler.FileNames;

import java.util.Map;

public class TestDataManager {

    private static final ISettingsFile TEST_DATA = new JsonSettingsFile(FileNames.TEST_DATA.getFileName());
    private static final ISettingsFile LOGIN_DATA = new JsonSettingsFile(FileNames.LOGIN_DATA.getFileName());
    private static final ISettingsFile RESPONSE_NODES = new JsonSettingsFile(FileNames.RESPONSE_NODES.getFileName());
    private static final ISettingsFile METHODS = new JsonSettingsFile(FileNames.METHODS.getFileName());
    private static final ISettingsFile PARAMS = new JsonSettingsFile(FileNames.PARAMS.getFileName());

    public String getTestData(String path) {
        return TEST_DATA.getValue(path).toString();
    }

    public String getLoginData(String path) {
        return LOGIN_DATA.getValue("/" + path).toString();
    }

    public String getResponseNode(String path) {
        return RESPONSE_NODES.getValue(path).toString();
    }

    public String getMethod(String path) {
        return METHODS.getValue(path).toString();
    }

    public String getParameter(String path) {
        return PARAMS.getValue(path).toString();
    }

    public String getDataById(String DataId) {
        return getTestData("/ownerId") + "_" + DataId;
    }

    public void addParameterToMap(Map parameterMap, String parameter, String value) {
        parameterMap.put(parameter, value);
    }

    public String getAttachment(String photoId) {
        return getParameter("/photo") + getDataById(photoId);
    }
}