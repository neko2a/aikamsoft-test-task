package model.search.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ProductNameAndMinTimesCriteria extends Criteria {
    private final String productName;
    private final int minTimes;
}
