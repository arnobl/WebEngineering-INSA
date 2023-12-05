package tpspring.model;

import java.util.ArrayList;
import java.util.List;

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
