package fr.insa.rennes.web.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

// data transfer object
// Instead of transferring a model instance, we define here a DTO:
// an object that exposes specific attributes of the source object (here PlayerCard)
// It is verbose, but it avoids putting annotation on the model (when not possible for
// example)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class PlayerCardDTO {
	String date;
	String img;
	int id;

	PlayerCardDTO() {
		this(0, null, null);
	}

	PlayerCardDTO(final int id, final String date, final String img) {
		super();
		this.date = date;
		this.img = img;
		this.id = id;
	}
}
