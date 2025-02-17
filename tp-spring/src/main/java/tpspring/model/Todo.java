package tpspring.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
	protected long id;
	protected String title;
	protected String description;
	protected List<Category> categories;

	protected TodoList list;

	protected String owner;

	/**
	 * Temporary constructor for TP1
	 */
	public Todo(long id, String title) {
		this.id = id;
		this.title = title;
		description = "";
		categories = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", title=" + title
				+ ", description=" + description + ", categories=" + categories + "]";
	}
}
