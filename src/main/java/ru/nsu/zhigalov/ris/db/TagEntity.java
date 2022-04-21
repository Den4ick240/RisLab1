package ru.nsu.zhigalov.ris.db;

import java.math.BigInteger;

public class TagEntity {
    public final BigInteger nodeId;
    public final String k;
    public final String v;

    public TagEntity(BigInteger nodeId, String k, String v) {
        this.nodeId = nodeId;
        this.k = k;
        this.v = v;
    }
}
