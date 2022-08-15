package service;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AllArgsConstructor;
import model.Error;
import model.stat.Stat;
import model.stat.StatRequest;
import org.tinylog.Logger;
import repository.StatRepo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;

@AllArgsConstructor
public class StatService {
    private final Connection connection;

    public void stat(FileReader input, FileWriter output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        StatRepo statRepo = new StatRepo(connection);
        try {
            StatRequest dates = mapper.readValue(input, StatRequest.class);
            Stat statResult = statRepo.findPurchaseInDateRange(dates.getStartDate(), dates.getEndDate());
            writer.writeValue(output, statResult);
        } catch (IOException e) {
            Logger.error(new Error(e.getMessage()));
        }
    }
}
