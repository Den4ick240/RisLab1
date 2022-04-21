package ru.nsu.zhigalov.ris.db;

public class TagSqlString {
    public static final String formatString = "insert into tags (node_id, k, v) values (%s, '%s', '%s')";
    public static String format(TagEntity tag) {
        return String.format(TagSqlString.formatString,
                tag.nodeId,
                tag.k.replace('\'', '\\'),
                tag.v.replace('\'', '\\')
        );
    }
}
