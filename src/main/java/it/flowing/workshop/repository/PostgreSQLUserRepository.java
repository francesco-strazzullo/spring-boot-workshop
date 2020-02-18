package it.flowing.workshop.repository;


import it.flowing.workshop.DBConfig;
import it.flowing.workshop.model.User;
import it.flowing.workshop.model.UserId;
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

            if (rs.next()) {
                return Optional.of(new User(
                        UserId.create(rs.getString("id")),
                        rs.getString("name")
                ));
            }

            connection.close();

            return Optional.empty();
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

        return Optional.empty();
    }

    @Override
    public User insert(User toInsert) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(
                    dbConfig.getUrl(),
                    dbConfig.getUsername(),
                    dbConfig.getPassword()
            );
            stmt = connection.prepareStatement("INSERT INTO users (\"name\") VALUES (?) RETURNING ID", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, toInsert.name);
            stmt.execute();

            rs = stmt.getGeneratedKeys();

            if (rs != null && rs.next()) {
                connection.close();
                return toInsert.withId(
                        UserId.create(rs.getString(1))
                );
            }

            connection.close();

            throw new RuntimeException("No id generated");
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    }

    @Override
    public User update(User toUpdate) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(
                    dbConfig.getUrl(),
                    dbConfig.getUsername(),
                    dbConfig.getPassword()
            );

            stmt = connection.prepareStatement("UPDATE users SET \"name\" = ? WHERE id::text = ?");
            stmt.setString(1, toUpdate.name);
            stmt.setString(2, toUpdate.id.toString());
            stmt.execute();

            connection.close();

            return toUpdate;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    }

    @Override
    public void delete(UserId id) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(
                    dbConfig.getUrl(),
                    dbConfig.getUsername(),
                    dbConfig.getPassword()
            );

            stmt = connection.prepareStatement("DELETE FROM users WHERE id::text = ?");
            stmt.setString(1, id.toString());
            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    }
}
