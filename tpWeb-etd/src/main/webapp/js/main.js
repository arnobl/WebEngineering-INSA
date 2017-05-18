"use strict"; // Required for chrome
let server = "http://localhost:8080/"; // The address of the server as defined in the Main class.
// The 'let' keyword is similary to 'var' with a more script scope limitated to the current code block in which the variable is defined.

// Getting the button named 'searchbutton'
// Setting the function that will be called while clicking on the button
document.getElementById('searchbutton').onclick = function() {
	let nommat = document.getElementById('nommat').value; // Getting the content of the text field called 'nomens'
	let req = new XMLHttpRequest(); // To be able to send REST commands

	req.onreadystatechange = function() { // This function will be called each time the state of the response changes.
		if (req.readyState != 4) return; // While not finished, do nothing.

		if(req.status==200) {// The HTTP code 200 is 'OK'
		    console.log(req.responseType + " " + req.response);
		    let mat = JSON.parse(req.response);
		    console.log(mat); // Open the Firefox / Chrome console to explore the structure of the object.
            document.getElementById('info').innerHTML = mat.name; // maybe mat[0].name or something else. Look at the console.
		} else {
			document.getElementById('info').innerHTML = "Cannot be retrieved";
		}
	};

    // Sending a REST command to the server.
	req.open("GET", server+"calendar/mat/"+nommat, true);// TODO May have to update the URI according to your definition in your CalendarResource.java
	req.send();
};




