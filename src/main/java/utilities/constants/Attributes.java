package utilities.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Attributes {

    HREF("href"),
    DATA_FROM_ID("data-from-id");

    private final String attributeName;
}
