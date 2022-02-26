package ru.nsu.zhigalov.ris;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {
    public static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Hello world!");
    }
}
