package ru.sberbank.viktormamontov.entity.mapper;

import ru.sberbank.viktormamontov.entity.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper {

    public static Client getClientFromResultSet(ResultSet rs) {
        Client client = null;
        try {
            long id = rs.getLong("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            client = new Client(id, firstName, lastName);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return client;
    }
}
