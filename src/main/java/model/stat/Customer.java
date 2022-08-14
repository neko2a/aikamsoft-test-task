package model.stat;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@JsonAutoDetect
public class Customer {
    private String name;
    private ArrayList<Purchase> purchases;
    private int totalExpenses;
}
