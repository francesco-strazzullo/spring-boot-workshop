package it.flowing.workshop.repository;


import it.flowing.workshop.DBConfig;
import it.flowing.workshop.model.User;
import it.flowing.workshop.model.UserId;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PostgreSQLUserRepository implements UserRepository {

    private static class Queries {
        public static final String SELECT_ALL = "SELECT * FROM users";
        public static final String SELECT_ONE = "SELECT * FROM users WHERE id::text = ?";
        public static final String INSERT = "INSERT INTO users (\"name\") VALUES (?) RETURNING ID";
        public static final String UPDATE = "UPDATE users SET \"name\" = ? WHERE id::text = ?";
        public static final String DELETE = "DELETE FROM users WHERE id::text = ?";
    }

    private DBConfig dbConfig;
    private MapListHandler beanListHandler = new MapListHandler();
    private QueryRunner runner = new QueryRunner();

    private Function<Map, User> mapToUser = row -> new User(
            UserId.create(row.get("id").toString()),
            row.get("name").toString()
    );

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(
                dbConfig.url,
                dbConfig.username,
                dbConfig.password
        );
    }

    @Autowired
    public PostgreSQLUserRepository(DBConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    public List<User> list() {
        Connection connection = null;
        try {
            connection = createConnection();

            List<Map<String, Object>> results = runner.query(
                    connection,
                    Queries.SELECT_ALL,
                    beanListHandler
            );

            return results
                    .stream()
                    .map(mapToUser)
                    .collect(Collectors.toList());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Override
    public Optional<User> get(UserId id) {
        Connection connection = null;
        try {
            connection = createConnection();

            List<Map<String, Object>> rows = runner.query(
                    connection,
                    Queries.SELECT_ONE,
                    beanListHandler,
                    id.toString()
            );

            if (rows != null && rows.size() >= 1) {
                Map row = rows.get(0);
                return Optional.of(mapToUser.apply(row));
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Override
    public User insert(User toInsert) {
        Connection connection = null;

        try {
            connection = createConnection();
            ScalarHandler<UUID> uuidHandler = new ScalarHandler<>();
            UUID id = runner.insert(
                    connection,
                    Queries.INSERT,
                    uuidHandler,
                    toInsert.name
            );

            if (id == null) {
                throw new RuntimeException("No id generated");
            }

            return toInsert.withId(
                    UserId.create(id.toString())
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Override
    public User update(User toUpdate) {
        Connection connection = null;

        try {
            connection = createConnection();

            runner.update(
                    connection,
                    Queries.UPDATE,
                    toUpdate.name,
                    toUpdate.id.toString()
            );

            return toUpdate;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Override
    public void delete(UserId id) {
        Connection connection = null;

        try {
            connection = createConnection();

            runner.update(
                    connection,
                    Queries.DELETE,
                    id.toString()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }
}
