package fr.insa.rennes.web.model;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {
	@Override
	public Date convertToDatabaseColumn(final LocalDate localDate) {
		return localDate == null ? null : Date.valueOf(localDate);
	}

	@Override
	public LocalDate convertToEntityAttribute(final Date date) {
		return date == null ? null : date.toLocalDate();
	}
}