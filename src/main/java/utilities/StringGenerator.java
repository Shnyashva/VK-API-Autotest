package utilities;

import org.apache.commons.lang3.RandomStringUtils;

public class StringGenerator {

    public static String generateRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }
}