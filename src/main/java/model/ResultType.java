package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultType {
    SEARCH("search"),
    STAT("stat"),
    ERROR("error");

    private final String type;
}
