package web.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Todo {
	protected long id;
	protected String title;
	protected String description;
	protected List<Category> categories;

	@JsonIgnore
	protected TodoList list;

	protected String owner;

    @Override
    public String toString() {
        return "Todo [id=" + id + ", title=" + title
                + ", description=" + description + ", categories=" + categories + "]";
    }
}
