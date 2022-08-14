package service;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AllArgsConstructor;
import model.ResultType;
import model.search.Results;
import model.search.Search;
import model.search.criteria.BadCustomerCriteria;
import model.search.criteria.ExpensesRangeCriteria;
import model.search.criteria.LastNameCriteria;
import model.search.criteria.ProductNameAndMinTimesCriteria;
import repository.CustomerRepo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@AllArgsConstructor
public class SearchService {
    private final Connection connection;

    public void search(FileReader input, FileWriter output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            CustomerRepo searchRepo = new CustomerRepo(connection);
            Map<?, ?> map = mapper.readValue(input, Map.class);
            ArrayList<Results> results = new ArrayList<>();
            Search search = new Search(ResultType.SEARCH, results);

            for (Map.Entry<?, ?> entry : map.entrySet()) {
                ArrayList<LinkedHashMap> list = (ArrayList) entry.getValue();
                for (LinkedHashMap element : list) {
                    if (element.containsKey("lastName")) {
                        String lastName = (String) element.get("lastName");

                        results.add(new Results(new LastNameCriteria(lastName),
                                searchRepo.findCustomersByLastName(lastName)));
                    } else if (element.containsKey("productName") && element.containsKey("minTimes")) {
                        String productName = (String) element.get("productName");
                        int minTimes = (int) element.get("minTimes");

                        results.add(new Results(new ProductNameAndMinTimesCriteria(productName, minTimes),
                                searchRepo.findCustomersByProductAndMinTimes(productName, minTimes)));
                    } else if (element.containsKey("minExpenses") && element.containsKey("maxExpenses")) {
                        int minExpenses = (int) element.get("minExpenses");
                        int maxExpenses = (int) element.get("maxExpenses");

                        results.add(new Results(new ExpensesRangeCriteria(minExpenses, maxExpenses),
                                searchRepo.findCustomersByIntervalExpenses(minExpenses, maxExpenses)));
                    } else if (element.containsKey("badCustomers")) {
                        int limit = (int) element.get("badCustomers");

                        results.add(new Results(new BadCustomerCriteria(limit),
                                searchRepo.findBadCustomers(limit)));
                    } else {
                        throw new IllegalArgumentException("Couldn't parse json");
                    }
                }
            }
            writer.writeValue(output, search);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
