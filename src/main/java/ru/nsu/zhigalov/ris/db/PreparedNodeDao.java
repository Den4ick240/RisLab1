package ru.nsu.zhigalov.ris.db;

import generated.Node;

import java.sql.*;

public class PreparedNodeDao extends AbstractNestedNodeDao {
    private static final String preparedSqlString =
            "insert into nodes (id, lat, lon, usr, uid, visible, version, changeset, timestamp) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final PreparedStatement preparedStatement;
    private final Connection connection;

    public PreparedNodeDao(Connection connection, Dao<TagEntity> tagEntityDao) throws SQLException {
        super(tagEntityDao);
        this.connection = connection;
        preparedStatement = connection.prepareStatement(preparedSqlString);
    }

    @Override
    protected void insertNode(Node node) throws SQLException {
        preparedStatement.setInt(1, node.getId().intValue());
        preparedStatement.setDouble(2, node.getLat());
        preparedStatement.setDouble(3, node.getLon());
        preparedStatement.setString(4, node.getUser());
        preparedStatement.setInt(5, node.getUid().intValue());
        preparedStatement.setObject(6, node.isVisible(), Types.BOOLEAN);
        preparedStatement.setInt(7, node.getVersion().intValue());
        preparedStatement.setInt(8, node.getChangeset().intValue());
        preparedStatement.setTimestamp(9, Timestamp.from(node.getTimestamp().toGregorianCalendar().toInstant()));
        preparedStatement.execute();
        connection.commit();
    }
}
