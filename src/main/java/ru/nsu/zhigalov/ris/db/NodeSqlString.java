package ru.nsu.zhigalov.ris.db;

import generated.Node;

import java.sql.Timestamp;

public class NodeSqlString {
    public static final String formatString = "insert into nodes (id, lat, lon, usr, uid, visible, version, changeset, timestamp) values " +
            "(%s, %s, %s, '%s', %s, %b, %s, %s, timestamp '%s')";

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
}
