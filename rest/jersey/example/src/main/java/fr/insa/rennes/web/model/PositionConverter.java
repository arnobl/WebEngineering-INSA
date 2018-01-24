package fr.insa.rennes.web.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PositionConverter implements AttributeConverter<Position, String> {
	@Override
	public String convertToDatabaseColumn(final Position position) {
		if(position != null) {
			switch(position) {
				case CATCHER: return "C";
				case PITCHER: return "P";
			}
		}

		return "";
	}

	@Override
	public Position convertToEntityAttribute(final String posStr) {
		if(posStr != null) {
			switch(posStr) {
				case "C": return Position.CATCHER;
				case "P": return Position.PITCHER;
			}
		}

		return null;
	}
}