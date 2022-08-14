import service.SearchService;
import service.StatService;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/aikam";
    private static final String DB_Username = "postgres";
    private static final String DB_Password = "postgres";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_Username, DB_Password);
            SearchService searchService = new SearchService(connection);
            StatService statService = new StatService(connection);


            FileReader input = new FileReader("src/main/resources/inputSearch.json");
            FileWriter output = new FileWriter("src/main/resources/outSearch.json");
            searchService.search(input, output);

            input = new FileReader("src/main/resources/inputStat.json");
            output = new FileWriter("src/main/resources/outStat.json");
            statService.stat(input, output);


            connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
}
