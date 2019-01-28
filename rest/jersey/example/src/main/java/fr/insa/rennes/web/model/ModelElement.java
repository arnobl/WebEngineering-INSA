package fr.insa.rennes.web.model;

import fr.insa.rennes.web.utils.IntStringAdapter;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@MappedSuperclass
public abstract class ModelElement {
	@Id
	@GeneratedValue
	@XmlID
	@XmlJavaTypeAdapter(type = int.class, value = IntStringAdapter.class)
	@XmlAttribute
	protected int id;

	protected ModelElement() {
		super();
	}

	public int getId() {
		return id;
	}

	protected void setId(final int id) {
		this.id = id;
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) {
			return true;
		}
		if(!(o instanceof ModelElement)) {
			return false;
		}
		final ModelElement that = (ModelElement) o;
		return getId() == that.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
