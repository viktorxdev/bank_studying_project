package ru.sberbank.viktormamontov.dao;

import ru.sberbank.viktormamontov.DbUtil;
import ru.sberbank.viktormamontov.entity.Client;
import ru.sberbank.viktormamontov.dao.mapper.ClientMapper;

import java.sql.*;

public class ClientDaoImpl implements ClientDao {

    private static ClientDaoImpl instance;
    private ClientDaoImpl() {}

    public static ClientDaoImpl getInstance() {
        if (instance == null) {
            instance = new ClientDaoImpl();
        }
        return instance;
    }

    @Override
    public Client getById(long id) throws SQLException {

        Client client = null;
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM clients WHERE id=?")) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            client = ClientMapper.getClientFromResultSet(resultSet);
        }
        return client;
    }
}
