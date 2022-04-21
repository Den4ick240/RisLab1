package ru.nsu.zhigalov.ris.db;

public class TagEntity {
    public final String nodeId;
    public final String k;
    public final String v;

    public TagEntity(String nodeId, String k, String v) {
        this.nodeId = nodeId;
        this.k = k;
        this.v = v;
    }
}
