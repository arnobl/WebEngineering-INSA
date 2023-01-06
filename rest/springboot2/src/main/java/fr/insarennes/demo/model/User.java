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
	private String phone;
	private String pwd;

	// Only the controller V1 uses this method
	// The use of DTOs makes this method obsolete (see controller V3)
	public void patch(final User user) {
		if(user.getAddress() != null) {
			address = user.getAddress();
		}
		if(user.getPhone() != null) {
			phone = user.getPhone();
		}
		if(user.getName() != null) {
			name = user.getName();
		}
	}
}
