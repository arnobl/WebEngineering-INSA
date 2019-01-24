package fr.insa.rennes.web.resource;

import com.google.common.base.MoreObjects;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A utility class for marshalling several objects
 * @param <T>
 */
@XmlRootElement
public class Data<T> {
	private final List<T> values;

	public Data() {
		super();
		values = new ArrayList<>();
	}

	public Data(final T... elts) {
		this();
		values.addAll(Arrays.asList(elts));
	}

	@XmlAnyElement(lax=true)
	public List<T> getValues() {
		return values;
	}

	@Override
	public String toString() {
		return MoreObjects
			.toStringHelper(this)
			.add("values", values)
			.toString();
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof Data)) {
			return false;
		}
		final Data<?> lists = (Data<?>) o;
		return Objects.equals(getValues(), lists.getValues());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getValues());
	}
}
