package ru.nsu.zhigalov.ris;


import generated.Node;
import org.apache.commons.cli.*;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.zhigalov.ris.db.Dao;
import ru.nsu.zhigalov.ris.db.DatabaseController;
import ru.nsu.zhigalov.ris.db.StringNodeDao;
import ru.nsu.zhigalov.ris.db.StringTagDao;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.sql.SQLException;

public class Application {
    public static final Logger logger = LogManager.getLogger(Application.class);
    public static final String HELLO_WORLD_STRING = "Hello world!";
    static final String INPUT_OPTION = "x";
    static final String OUTPUT_OPTION = "h";
    static final String STAT_OPTION = "s";
    static final String LENGTH_OPTION = "l";
    static final String[] SCRIPT_NAMES = {"nodes", "tags"};



    public static void main(String[] args) {
        logger.info(HELLO_WORLD_STRING);
        System.out.println(HELLO_WORLD_STRING);
        Options options = new Options();
        options.addOption(INPUT_OPTION, "xml", true, "Input XML file");
        options.addOption(OUTPUT_OPTION, "head", true, "Output XML file");
        options.addOption(STAT_OPTION, "stat", false, "Print statistic for XML file");
        options.addOption(Option.builder()
                .option(LENGTH_OPTION)
                .longOpt("length")
                .hasArg()
                .desc("Amount of bytes to be read from file")
                .type(Number.class)
                .build()
        );

        DefaultParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            String inputPath = cmd.getOptionValue(INPUT_OPTION);
            String outputPath = cmd.getOptionValue(OUTPUT_OPTION);
            long length = ((Number) cmd.getParsedOptionValue(LENGTH_OPTION)).longValue();
            boolean calculateStat = cmd.hasOption(STAT_OPTION);
            logger.info(String.format("Cmd arguments: x: %s, h: %s, l:%d, s:%b", inputPath, outputPath, length, calculateStat));

            if (outputPath != null)
                extractBytesFromBz2ToXml(inputPath, outputPath, length);

            try (var databaseController = new DatabaseController(SCRIPT_NAMES)) {
                databaseController.cleanDatabase();
                databaseController.createDatabase();
                if (calculateStat)
                    countStat(inputPath, new StringNodeDao(databaseController.getConnection(), new StringTagDao(databaseController.getConnection())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            System.out.println("Error parsing command line arguments: ");
            System.out.println(e.getMessage());
        }
    }

    private static void extractBytesFromBz2ToXml(String archiveFilePath, String outputXmlFilePath, long length) {
        try (var input = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(archiveFilePath)));
             var output = new FileOutputStream(outputXmlFilePath)
        ) {
            IOUtils.copyRange(input, length, output);
        } catch (IOException e) {
            logger.error(String.format("Error reading file %s", archiveFilePath));
            logger.error(e.getStackTrace());
        }
    }

    private static void countStat(String inputFilePath, Dao<Node> nodeDao) {
        try (var input = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(inputFilePath)))) {
            System.out.println(new JaxbStatCounter(nodeDao).countStat(input));
        } catch (IOException | XMLStreamException | JAXBException | SQLException e) {
            e.printStackTrace();
        }
    }
}
