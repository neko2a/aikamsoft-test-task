package model.stat;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import model.ResultType;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@JsonAutoDetect
public class Stat {
    private final String type = ResultType.STAT.getType();
    private final int totalDays;
    private final ArrayList<Customer> customers;
    private final int totalExpenses;
    private final double avgExpenses;

}
