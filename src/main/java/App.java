import controller.DatabaseConnection;
import model.Error;
import org.tinylog.Logger;
import service.SearchService;
import service.StatService;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) {

        if (args.length != 3) {
            Logger.error(new Error("Wrong argument amount."));
        } else {
            String type = args[0];
            String inputName = args[1];
            String outputName = args[2];

            if (!type.equals("search") && !type.equals("stat")) {
                Logger.error(new Error("Wrong operation type."));
            } else {
                try {
                    Connection connection = DatabaseConnection.getConnection();
                    FileReader input = new FileReader(inputName);
                    FileWriter output = new FileWriter(outputName);

                    if (type.equals("search")) {
                        SearchService searchService = new SearchService(connection);
                        searchService.search(input, output);
                    } else {
                        StatService statService = new StatService(connection);
                        statService.stat(input, output);
                    }

                    connection.close();
                } catch (IOException | SQLException e) {
                    Logger.error(new Error(e.getMessage()));
                }
            }
        }
    }


}
