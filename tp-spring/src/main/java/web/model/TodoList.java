package web.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TodoList {
	private long id;
	private String name;
	private final List<Todo> todos;

	public TodoList(final String name) {
		super();
		this.name = name;
		todos = new ArrayList<>();
	}
}
