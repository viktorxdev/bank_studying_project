package ru.sberbank.viktormamontov.dao.mapper;

import ru.sberbank.viktormamontov.entity.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper {

    public static Client getClientFromResultSet(ResultSet rs) throws SQLException {

        long id = rs.getLong("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");

        return new Client(id, firstName, lastName);

    }
}
