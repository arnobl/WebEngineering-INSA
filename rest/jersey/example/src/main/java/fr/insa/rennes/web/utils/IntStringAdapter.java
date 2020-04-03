package fr.insa.rennes.web.utils;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class IntStringAdapter extends XmlAdapter<String, Integer> {
	@Override
	public Integer unmarshal(final String string) {
		return DatatypeConverter.parseInt(string);
	}

	@Override
	public String marshal(final Integer value) {
		return DatatypeConverter.printInt(value);
	}
}
