package ru.nsu.zhigalov.ris;


import generated.Node;
import org.apache.commons.cli.*;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.zhigalov.ris.db.*;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class TestMain {
    public static final Logger logger = LogManager.getLogger(TestMain.class);
    static final String INPUT_OPTION = "x";
    static final String OUTPUT_OPTION = "h";
    static final String STAT_OPTION = "s";
    static final String LENGTH_OPTION = "l";
    static final String[] SCRIPT_NAMES = {"nodes", "tags", "extensions"};
    static final int batchSize = 100000;


    public static void main(String[] args) {
        parseCommandLineAndRunTest(args);
    }

    private static void parseCommandLineAndRunTest(String[] args) {
        try {
            CommandLine cmd = parseCommandLine(args);
            String inputPath = cmd.getOptionValue(INPUT_OPTION);
            String outputPath = cmd.getOptionValue(OUTPUT_OPTION);
            Long length = cmd.hasOption(LENGTH_OPTION) ? (((Number) cmd.getParsedOptionValue(LENGTH_OPTION)).longValue()) : null;
            boolean calculateStat = cmd.hasOption(STAT_OPTION);
            logger.info(String.format("Cmd arguments: x: %s, h: %s, l:%d, s:%b", inputPath, outputPath, length, calculateStat));

            if (outputPath != null && length != null)
                extractBytesFromBz2ToXml(inputPath, outputPath, length);

            if (calculateStat)
                testCalculateStat(inputPath, length);
        } catch (ParseException e) {
            System.out.println("Error parsing command line arguments: ");
            System.out.println(e.getMessage());
        }
    }

    private static CommandLine parseCommandLine(String[] args) throws ParseException {
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
        return parser.parse(options, args);
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

    private static void testCalculateStat(String inputPath, Long length) {
        try (var databaseController = new DatabaseController(SCRIPT_NAMES)) {
            Connection connection = databaseController.getConnection();

            var batch = new Batch(connection.createStatement(), batchSize);

            Dao<Node> stringNodeDao = new NestedNodeDao(new StringNodeDao(connection), new StringTagDao(connection));
            Dao<Node> preparedNodeDao = new NestedNodeDao(new PreparedNodeDao(connection), new PreparedTagDao(connection));
            Dao<Node> batchNodeDao =  new NestedNodeDao(new BatchNodeDao(connection ,batch), new BatchTagDao(connection, batch));

            TestSubject[] testSubjects = new TestSubject[]{
                    new TestSubject("New statement every time", stringNodeDao),
                    new TestSubject("Prepared statement", preparedNodeDao),
                    new TestSubject("Batch statement", batchNodeDao)
            };

            String separator = "--------------------------------";
            for (var testSubject : testSubjects) {
                databaseController.cleanDatabase();
                databaseController.createDatabase();
                var countingDao = new CountingNodeDao(testSubject.dao);
                var stat = countStat(inputPath, countingDao, length);
                countingDao.commit();
                var time = countingDao.getTimeMillis();
                String result = String.format(
                        "\nStrategy: %s\nMilliseconds: %d\nWrites per second: %f\n",
                        testSubject.name, time, countingDao.getCounter() / (((float) time) / 1000)
                );
                System.out.println(separator);
                System.out.println(result);
            }
            System.out.println(separator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Statistics countStat(String inputFilePath, Dao<Node> nodeDao, Long length) {
        try (var input = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(inputFilePath)))) {
            return new JaxbStatCounter(nodeDao).countStat(input, length);
        } catch (IOException | XMLStreamException | JAXBException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
