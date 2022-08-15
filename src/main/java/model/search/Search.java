package model.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.ResultType;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Search {
    private final String type = ResultType.SEARCH.getType();
    private final ArrayList<Results> results;
}
