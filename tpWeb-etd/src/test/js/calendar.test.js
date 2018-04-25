const fs = require("fs");
const jsdom = require("jsdom");
const { JSDOM } = jsdom;

let virtualConsole;
let semaine;
let ressource;
let loadCalendar;
let dom;

beforeEach(() => {
    virtualConsole = new jsdom.VirtualConsole();
  // fs.readFileSync("src/main/webapp/calendar.html"),
  dom = JSDOM.fromURL("http://localhost:8080/myCalendarApp/index.html", 
    { pretendToBeVisual: true, virtualConsole, resources: "usable", runScripts: "dangerously" }).then(dom => {
        console.log(dom.serialize());
      });
  loadCalendar = dom.window.document.getElementById("loadCalendar");
  ressource = dom.window.document.getElementById("ressource");
  semaine = dom.window.document.getElementById("semaine");
});

test("Test semaine defined", () => {
    expect(semaine).toBeDefined();
    expect(semaine).not.toBeNull();
  });
  
  test("Test ressource defined", () => {
    expect(ressource).toBeDefined();
    expect(ressource).not.toBeNull();
  });
  
  test("Test loadCalendar defined", () => {
    expect(loadCalendar).toBeDefined();
    expect(loadCalendar).not.toBeNull();
  });

  test("Test search matiere Web", () => {
    semaine.value = "1";
    ressource.value = "3";//TODO to change
    loadCalendar.click();
    console.log(dom.window.document.getElementsByTagName("table"));
    // Demo. Nothing to do since for the exercise no user interface element is defined (the console is used).
  });

