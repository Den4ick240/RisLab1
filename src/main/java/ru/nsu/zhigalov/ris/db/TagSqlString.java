package ru.nsu.zhigalov.ris.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TagSqlString {
    public static final String formatString = "insert into tags (node_id, k, v) values (%s, '%s', '%s')";
    public static final String preparedSqlString = "insert into tags (node_id, k, v) values (?, ?, ?)";
    public static String format(TagEntity tag) {
        return String.format(TagSqlString.formatString,
                tag.getNodeId(),
                tag.getK().replace('\'', '\\'),
                tag.getV().replace('\'', '\\')
        );
    }
    public static void prepare(TagEntity tagEntity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, tagEntity.getNodeId().intValue());
        preparedStatement.setString(2, tagEntity.getK());
        preparedStatement.setString(3, tagEntity.getV());
    }
}
