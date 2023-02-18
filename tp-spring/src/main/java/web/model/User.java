package web.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
	private long id;
	private String name;
	private final List<TodoList> lists;

	public User(final String name) {
		super();
		lists = new ArrayList<>();
		this.name = name;
	}
}
