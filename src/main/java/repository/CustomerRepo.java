package repository;

import lombok.AllArgsConstructor;
import model.Error;
import model.search.Customer;
import org.tinylog.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@AllArgsConstructor
public class CustomerRepo {

    private final Connection connection;

    public ArrayList<Customer> findCustomersByLastName(String lastName) {
        String query = String.format("select last_name, first_name\n" +
                "from customer\n" +
                "where last_name = '%s'\n" +
                "order by first_name", lastName);

        return findCustomers(query);
    }

    public ArrayList<Customer> findCustomersByProductAndMinTimes(String productName, int minTimes) {
        String query = String.format("select last_name,\n" +
                "       first_name\n" +
                "from customer\n" +
                "         join purchase p on customer.id = p.customer_id\n" +
                "         join product on product.id = p.product_id\n" +
                "where product_name = '%s'\n" +
                "group by customer_id, last_name, first_name\n" +
                "having count(customer_id) >= %d", productName, minTimes);

        return findCustomers(query);
    }

    public ArrayList<Customer> findCustomersByIntervalExpenses(int minExpenses, int maxExpenses) {
        String query = String.format("select last_name,\n" +
                "       first_name\n" +
                "from customer\n" +
                "         join purchase p on customer.id = p.customer_id\n" +
                "         join product on product.id = p.product_id\n" +
                "group by customer_id, last_name, first_name\n" +
                "having sum(price) between %d and %d", minExpenses, maxExpenses);

        return findCustomers(query);
    }

    public ArrayList<Customer> findBadCustomers(int limit) {
        String query = String.format("select last_name,\n" +
                "       first_name\n" +
                "from customer\n" +
                "         join purchase p on customer.id = p.customer_id\n" +
                "         join product on product.id = p.product_id\n" +
                "group by customer_id, last_name, first_name\n" +
                "order by sum(price)\n" +
                "limit %d", limit);

        return findCustomers(query);
    }

    private ArrayList<Customer> findCustomers(String query) {
        ArrayList<Customer> customers = new ArrayList<>();

        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getString("last_name"),
                        resultSet.getString("first_name")));
            }
        } catch (SQLException e) {
            Logger.error(new Error(e.getMessage()));
        }

        return customers;
    }
}

