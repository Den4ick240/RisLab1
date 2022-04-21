package ru.nsu.zhigalov.ris.db;

import generated.Node;
import generated.Tag;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public class StringNodeDao extends AbstractNestedNodeDao {
    private static final String formatString = "insert into nodes (id, lat, lon, usr, uid, visible, version, changeset, timestamp) values " +
            "(%s, %s, %s, '%s', %s, %b, %s, %s, timestamp '%s')";
    private final Connection connection;

    public StringNodeDao(Connection connection, Dao<TagEntity> tagEntityDao) {
        super(tagEntityDao);
        this.connection = connection;
    }

    @Override
    protected void insertNode(Node node) throws SQLException {
        String sql = String.format(formatString,
                node.getId(),
                node.getLat(),
                node.getLon(),
                node.getUser().replace('\'', '\\'),
                node.getUid(),
                node.isVisible(),
                node.getVersion(),
                node.getChangeset(),
                Timestamp.from(node.getTimestamp().toGregorianCalendar().toInstant()));
        connection.prepareStatement(
                sql
        ).execute();
        connection.commit();
    }
}
