package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Error {
    private final String type = ResultType.ERROR.getType();
    private final String message;

    @Override
    public String toString() {
        return String.format("{\n  \"type\": \"%s\",\n" +
                "  \"message\": \"%s\"\n}", type, message);
    }
}
