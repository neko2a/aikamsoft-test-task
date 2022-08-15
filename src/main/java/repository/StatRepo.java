package repository;

import lombok.AllArgsConstructor;
import model.Error;
import model.stat.Customer;
import model.stat.Purchase;
import model.stat.Stat;
import org.tinylog.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;

@AllArgsConstructor
public class StatRepo {
    private final Connection connection;

    public Stat findPurchaseInDateRange(String startDate, String endDate) {
        String query = String.format("select last_name,\n" +
                "       first_name,\n" +
                "       product_name,\n" +
                "       sum(price) as sum_price\n" +
                "from purchase\n" +
                "         join customer on customer.id = purchase.customer_id\n" +
                "         join product on product.id = purchase.product_id\n" +
                "where purchase_date between '%s' and '%s'\n" +
                "  and extract(isodow from purchase_date) < 6\n" +
                "group by last_name, first_name, product_name\n" +
                "order by last_name, first_name, sum_price", startDate, endDate);

        ArrayList<Customer> customers = new ArrayList<>();
        int workingDays = findWorkingDays(startDate, endDate);
        if (workingDays == 0) {
            return new Stat(0, customers, 0, 0);
        }

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            LinkedHashMap<String, ArrayList<Purchase>> map = new LinkedHashMap<>();

            String name = "";
            String nameSplitter = " ";

            while (resultSet.next()) {
                String lastName = resultSet.getString(1);
                String firstName = resultSet.getString(2);
                Purchase purchase = new Purchase(resultSet.getString(3),
                        Integer.parseInt(resultSet.getString(4)));
                name = lastName + nameSplitter + firstName;

                if (!map.containsKey(name)) {
                    ArrayList<Purchase> list = new ArrayList<>();
                    list.add(purchase);
                    map.put(name, list);
                } else {
                    map.get(name).add(purchase);
                }
            }
            // Пустой возврат от БД
            if (name.equals("")) {
                return new Stat(0, customers, 0, 0);
            }


            for (String key : map.keySet()) {
//                System.out.println(map.get(key));
                int customerTotalExpenses = findExpensesByCustomerInDateRange(key.split(nameSplitter)[0],
                        key.split(nameSplitter)[1], startDate, endDate);
                map.get(key).sort(Comparator.comparingInt(Purchase::getExpenses).reversed());
                customers.add(new Customer(key, map.get(key), customerTotalExpenses));
            }
            customers.sort(Comparator.comparingInt(Customer::getTotalExpenses).reversed());
            return new Stat(workingDays, customers,
                    findTotalExpensesInDateRange(startDate, endDate), findAverageExpensesInDateRange(startDate, endDate));
        } catch (SQLException e) {
            Logger.error(new Error(e.getMessage()));
        }
        return new Stat(0, customers, 0, 0);
    }

    private int findExpensesByCustomerInDateRange(String lastName, String firstName, String startDate, String endDate) {
        String query = String.format("select sum(price)\n" +
                "from purchase\n" +
                "         join customer on customer.id = purchase.customer_id\n" +
                "         join product on product.id = purchase.product_id\n" +
                "where purchase_date between '%s' and '%s'\n" +
                "  and extract(isodow from purchase_date) < 6\n" +
                "  and last_name = '%s'\n" +
                "  and first_name = '%s'\n", startDate, endDate, lastName, firstName);

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            resultSet.next();
            return Integer.parseInt(resultSet.getString(1));
        } catch (SQLException e) {
            Logger.error(new Error(e.getMessage()));
        }
        return 0;
    }

    private int findTotalExpensesInDateRange(String startDate, String endDate) {
        String query = String.format("select sum(price)\n" +
                "from purchase\n" +
                "         join product on product.id = purchase.product_id\n" +
                "where purchase_date between '%s' and '%s'\n" +
                "  and extract(isodow from purchase_date) < 6", startDate, endDate);

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            resultSet.next();
            return Integer.parseInt(resultSet.getString(1));
        } catch (SQLException e) {
            Logger.error(new Error(e.getMessage()));
        }
        return 0;
    }

    private double findAverageExpensesInDateRange(String startDate, String endDate) {
        String query = String.format("select avg(price)\n" +
                "from purchase\n" +
                "         join product on product.id = purchase.product_id\n" +
                "where purchase_date between '%s' and '%s'\n" +
                "  and extract(isodow from purchase_date) < 6", startDate, endDate);

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            resultSet.next();
            return Double.parseDouble(resultSet.getString(1));
        } catch (SQLException e) {
            Logger.error(new Error(e.getMessage()));
        }
        return 0;
    }

    private int findWorkingDays(String startDate, String endDate) {
        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = LocalDate.parse(endDate);
        int workingDays = 0;

        while (sDate.isBefore(eDate) || sDate.equals(eDate)) {
            if (!DayOfWeek.SATURDAY.equals(sDate.getDayOfWeek())
                    & !DayOfWeek.SUNDAY.equals(sDate.getDayOfWeek())) {
                workingDays++;
            }
            sDate = sDate.plusDays(1);
        }

        return workingDays;
    }
}
