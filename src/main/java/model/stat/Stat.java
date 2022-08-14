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
    private ResultType type;
    private int totalDays;
    private ArrayList<Customer> customers;
    private int totalExpenses;
    private double avgExpenses;

}
