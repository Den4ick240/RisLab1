package ru.nsu.zhigalov.ris.db;

import generated.Node;

import java.sql.SQLException;

public class CountingNodeDao implements Dao<Node> {
    private int counter = 0;
    private long time = 0;
    private final Dao<Node> nodeDao;

    public CountingNodeDao(Dao<Node> nodeDao) {
        this.nodeDao = nodeDao;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public void insert(Node obj) throws SQLException {
        counter += 1 + obj.getTag().size();
        long startTime = System.currentTimeMillis();
        nodeDao.insert(obj);
        time += (System.currentTimeMillis() - startTime);
    }

    @Override
    public void commit() throws SQLException {
        long startTime = System.currentTimeMillis();
        nodeDao.commit();
        time += (System.currentTimeMillis() - startTime);
    }

    public long getTimeMillis() {
        return time;
    }
}
