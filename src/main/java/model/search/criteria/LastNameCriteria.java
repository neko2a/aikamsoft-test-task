package model.search.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class LastNameCriteria extends Criteria {
    private final String lastName;
}
