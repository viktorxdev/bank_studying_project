package ru.sberbank.viktormamontov.dao.mapper;

import ru.sberbank.viktormamontov.entity.Counterparty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CounterpartyMapper {

    public static Counterparty getCounterpartyFromResultSet(ResultSet rs) throws SQLException {

        long id = rs.getLong("id");
        String name = rs.getString("name");
        String information = rs.getString("information");

        return new Counterparty(id, name, information);
    }
}
