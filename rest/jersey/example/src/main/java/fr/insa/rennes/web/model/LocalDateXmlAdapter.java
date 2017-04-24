package fr.insa.rennes.web.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateXmlAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(final String v) throws Exception {
        return LocalDate.parse(v, DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public String marshal(final LocalDate v) throws Exception {
        return v.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
