package ru.nsu.zhigalov.ris.db;

import generated.Node;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

public class NodeSqlString {
    public static final String formatString = "insert into nodes (id, lat, lon, usr, uid, visible, version, changeset, timestamp) values " +
            "(%s, %f, %f, '%s', %s, %b, %s, %s, timestamp '%s')";
    public static final String preparedSqlString =
            "insert into nodes (id, lat, lon, usr, uid, visible, version, changeset, timestamp) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static String format(Node node) {
        return String.format(formatString,
                node.getId(),
                node.getLat(),
                node.getLon(),
                node.getUser().replace('\'', '\\'),
                node.getUid(),
                node.isVisible(),
                node.getVersion(),
                node.getChangeset(),
                Timestamp.from(node.getTimestamp().toGregorianCalendar().toInstant()));
    }

    public static void prepare(Node node, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, node.getId().longValue());
        preparedStatement.setDouble(2, node.getLat());
        preparedStatement.setDouble(3, node.getLon());
        preparedStatement.setString(4, node.getUser());
        preparedStatement.setInt(5, node.getUid().intValue());
        preparedStatement.setObject(6, node.isVisible(), Types.BOOLEAN);
        preparedStatement.setInt(7, node.getVersion().intValue());
        preparedStatement.setInt(8, node.getChangeset().intValue());
        preparedStatement.setTimestamp(9, Timestamp.from(node.getTimestamp().toGregorianCalendar().toInstant()));
    }
}
