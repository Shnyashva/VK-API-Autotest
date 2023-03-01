package utilities.filehandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;

public class JsonHandler {

    private static final Logger LOGGER = LogManager.getLogger();
    private static ObjectMapper mapper;

    private static ObjectMapper getObjectMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }

    public static Map<String, ?> parseJsonToMapWithBaseParams(FileNames filename) {
        try {
            LOGGER.info("Parsing JSON to map");
            return getObjectMapper().readValue(FileHelper.getResourceFileByName(filename.getFileName()), Map.class);
        } catch (IOException e) {
            LOGGER.fatal("Json file not found");
            throw new RuntimeException(e);
        }
    }
}
