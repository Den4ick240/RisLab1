package ru.nsu.zhigalov.ris.db;

import generated.Node;

import java.sql.*;

public class PreparedNodeDao extends CommittingDao<Node> {
    private static final String preparedSqlString =
            "insert into nodes (id, lat, lon, usr, uid, visible, version, changeset, timestamp) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final PreparedStatement preparedStatement;

    public PreparedNodeDao(Connection connection) throws SQLException {
        super(connection);
        preparedStatement = connection.prepareStatement(preparedSqlString);
    }

    @Override
    public void insert(Node node) throws SQLException {
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
    }
}
