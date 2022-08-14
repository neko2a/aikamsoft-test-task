package model.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.search.criteria.Criteria;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Results {
    private final Criteria criteria;
    private final ArrayList<Customer> results;
}
