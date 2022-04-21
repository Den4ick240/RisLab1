package ru.nsu.zhigalov.ris.db;

import generated.Node;
import generated.Tag;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public class StringNodeDao implements Dao<Node>{
    private static final String formatString = "insert into nodes (id, lat, lon, usr, uid, visible, version, changeset, timestamp) values " +
            "(%s, %s, %s, '%s', %s, %b, %s, %s, timestamp '%s')";
    private final Connection connection;
    private final Dao<TagEntity> tagEntityDao;

    public StringNodeDao(Connection connection, Dao<TagEntity> tagEntityDao) {
        this.connection = connection;
        this.tagEntityDao = tagEntityDao;
    }

    @Override
    public void insert(Node obj) throws SQLException {
        String sql = String.format(formatString,
                obj.getId(),
                obj.getLat(),
                obj.getLon(),
                obj.getUser().replace('\'', '\\'),
                obj.getUid(),
                obj.isVisible(),
                obj.getVersion(),
                obj.getChangeset(),
                Timestamp.from(obj.getTimestamp().toGregorianCalendar().toInstant()));
        connection.prepareStatement(
                sql
        ).execute();
        for (Tag tag : obj.getTag()) {
            tagEntityDao.insert(new TagEntity(obj.getId().toString(), tag.getK(), tag.getV()));
        }
        connection.commit();
    }
}
