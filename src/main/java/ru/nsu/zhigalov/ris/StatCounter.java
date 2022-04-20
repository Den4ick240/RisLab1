package ru.nsu.zhigalov.ris;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.HashMap;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

public class StatCounter {

    private static final int USER_ATTRIBUTE = 4;
    private Statistics statistics;
    private XMLStreamReader reader;
    private static final String NODE = "node";
    private static final String TAG = "tag";

    public Statistics countStat(InputStream inputStream) throws XMLStreamException {
        statistics = new Statistics(new HashMap<>(), new HashMap<>());

        reader = XMLInputFactory.newInstance().createXMLStreamReader(inputStream);
        while (reader.hasNext()) {
            countEvent(reader.next());
        }

        return statistics;
    }

    private void countEvent(int eventType) throws XMLStreamException {
        if (eventType != START_ELEMENT) return;

        String localName = reader.getLocalName();
        if (!localName.equals(NODE)) return;
        countNode();
    }

    private void countNode() throws XMLStreamException {
        String username = reader.getAttributeValue("", "user");
        String changeset = reader.getAttributeValue("", "changeset");
        statistics.addUserEdit(username, changeset);

        while (reader.hasNext()) {
            var eventType = reader.next();
            if (eventType != END_ELEMENT && eventType != START_ELEMENT) continue;
            String localName = reader.getLocalName();
            if (eventType == END_ELEMENT && localName.equals(NODE))
                return;
            if (eventType == START_ELEMENT && localName.equals(TAG))
                countTag();
        }
    }

    private void countTag() {
        String k = reader.getAttributeValue("", "k");
        statistics.addKeyNodeAmount(k);
    }
}
