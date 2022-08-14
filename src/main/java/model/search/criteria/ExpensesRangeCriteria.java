package model.search.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ExpensesRangeCriteria extends Criteria {
    private final int minExpenses;
    private final int maxExpenses;

}
