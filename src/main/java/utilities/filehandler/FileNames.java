package utilities.filehandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileNames {

    TEST_DATA("test_data.json"),
    LOGIN_DATA("login_data.json"),
    RESPONSE_NODES("response_nodes.json"),
    BASE_API_PARAMS("base_api_params.json"),
    METHODS("methods.json"),
    PARAMS("parameters.json"),
    PHOTO("photo.jpg");

    private final String fileName;
}
