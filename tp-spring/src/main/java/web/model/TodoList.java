package web.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoList {
	private long id;
	private String name;
	private List<Todo> todos;
	private String owner;

	public TodoList(final String name) {
		super();
		this.name = name;
		todos = new ArrayList<>();
	}
}
