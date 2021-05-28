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
    public Client getById(long id) {

        Client client = null;
        try (Connection conn = DriverManager.getConnection(DbUtil.DB_URL, DbUtil.USER, DbUtil.PASS);
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM clients WHERE id=?")) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            client = ClientMapper.getClientFromResultSet(resultSet);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return client;
    }
}
