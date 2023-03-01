package utilities.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthenticationFields {

    LOGIN("login"),
    PASSWORD("password");

    private final String fieldName;
}
