package model.search.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class BadCustomerCriteria extends Criteria {
    private final int limit;
}
