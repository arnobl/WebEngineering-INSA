package tpspring.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class SpecificTodo extends Todo {
	private String specificAttr;

	/**
	 * Temporary constructor for TP1
	 */
	public SpecificTodo(long id, String title) {
		super(id, title);
		specificAttr = "specific";
	}
}
