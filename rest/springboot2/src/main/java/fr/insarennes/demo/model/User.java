package fr.insarennes.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
	private String name;
	private String address;
	private String id;
	private String pwd;

	public void patch(final User user) {
		if(user.getAddress() != null) {
			address = user.getAddress();
		}
		if(user.getName() != null) {
			name = user.getName();
		}
	}
}
