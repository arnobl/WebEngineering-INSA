package fr.insa.rennes.info;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.json.JSONObject;

public class App {
	public static void main(final String[] args) {
		try {
			final JSONObject json = new JSONObject(Files.lines(Paths.get("src/main/resources/person.json")).collect(Collectors.joining("\n")));

			System.out.println("ID card: " + json.getString("idcard"));
			System.out.println("Name: " + json.getString("name"));
			System.out.println("addresses: " + json.getJSONArray("address").join(", "));
			final JSONObject phones = json.getJSONObject("phones");
			System.out.println("Phones: " + phones);
			System.out.println("Work phone: " + phones.getString("work"));
			System.out.println("Home phone: " + phones.getString("home"));
		}catch(final IOException e) {
			e.printStackTrace();
		}
	}
}
