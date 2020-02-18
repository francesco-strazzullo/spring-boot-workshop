package it.flowing.workshop.repository;


import it.flowing.workshop.DBConfig;
import it.flowing.workshop.model.User;
import it.flowing.workshop.model.UserId;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PostgreSQLUserRepository implements UserRepository {

    private DBConfig dbConfig;

    @Autowired
    public PostgreSQLUserRepository(DBConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    public List<User> list() {
        List<User> toReturn = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(
                    dbConfig.getUrl(),
                    dbConfig.getUsername(),
                    dbConfig.getPassword()
            );
            stmt = connection.createStatement();
            rs = stmt.executeQuery("select * from users");
            while (rs.next()) {
                User user = new User(
                        UserId.create(rs.getString("id")),
                        rs.getString("name")
                );

                toReturn.add(user);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* ignored */}
            }
        }

        return toReturn;
    }

    @Override
    public Optional<User> get(UserId id) {
        return Optional.empty();
    }

    @Override
    public User insert(User toInsert) {
        throw new NotImplementedException("Not Implemented");
    }

    @Override
    public User update(User toUpdate) {
        throw new NotImplementedException("Not Implemented");
    }

    @Override
    public void delete(UserId id) {
        throw new NotImplementedException("Not Implemented");
    }
}
