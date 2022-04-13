package fr.insarennes.demo.service;

import fr.insarennes.demo.model.User;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class DataService {
	private final Set<String> txts;
	private User user;

	public DataService() {
		super();
		txts = new HashSet<>();
		txts.add("foo");
		txts.add("bar");
		user = new User("Foo", "here", "1", "p1");
	}
}
