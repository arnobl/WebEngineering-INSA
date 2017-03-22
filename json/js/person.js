
// To run: node person.js

let person = {
	"idcard":"1843739",
	"name":"John Doe",
	"address":["Adress 1", "Adress 2"],
	"phones": {
		"work": "+339999999",
		"home": "+338888888"
	}
};

console.log("ID card: " + person.idcard); // person["idcard"] also works
console.log("name: " + person.name);

for (i in person.address) {
    console.log("address #" + i + ": " + person.address[i]);
}

console.log("Work phones: " + person.phones.work);
console.log("Home phones: " + person.phones.home);


let jsonText = "{\"idcard\":\"1843739\"}";
try {
	let jsonObj = JSON.parse(jsonText);
	console.log("Parsed JSON text: " + jsonObj.idcard);
}catch(err) {
	console.log("Cannot parse the JSON text");
}