package fr.insarennes.model;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Enseignant extends CalendarElement {
	private String name;

	Enseignant() {
		this("enseignant");
	}

	public Enseignant(final String n) {
		super();
		name = Objects.requireNonNull(n);
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return MoreObjects
			.toStringHelper(this)
			.add("name", name)
			.add("id", id)
			.toString();
	}

//	@Override
//	public boolean equals(final Object o) {
//		if(this == o) {
//			return true;
//		}
//		if(!(o instanceof Enseignant)) {
//			return false;
//		}
//		if(!super.equals(o)) {
//			return false;
//		}
//		final Enseignant that = (Enseignant) o;
//		return Objects.equals(getName(), that.getName());
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(super.hashCode(), getName());
//	}
}
