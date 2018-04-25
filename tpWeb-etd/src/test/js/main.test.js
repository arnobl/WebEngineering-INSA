// const fs = require("fs");
// const jsdom = require("jsdom");
// const { JSDOM } = jsdom;

let searchbutton;
let nommatiere;
let info;

// const options = {
//     runScripts: "dangerously",
//         resources: "usable",
//          includeNodeLocations: true
//   };

beforeEach(() => {
//     virtualConsole = new jsdom.VirtualConsole();
//     virtualConsole.sendTo(console);
//   // jsdom's fromFile, fromURL do not work properly so need to use NodeJS' readFileSync as a workaround.
//   dom = new JSDOM(fs.readFileSync("src/main/webapp/index.html"), 
//     { pretendToBeVisual: true, virtualConsole, resources: "usable", runScripts: "dangerously"});
   
//   //console.log(dom.serialize());

//   const scriptElt = dom.window.document.createElement('script');
//   const script = fs.readFileSync("src/main/webapp/js/main.js");
//   scriptElt.textContent = script;
//   dom.window.document.head.append(scriptElt);


  searchbutton = document.getElementById("searchbutton");
  nommatiere = document.getElementById("nommatiere");
  info = document.getElementById("info");
});

test("Test search button defined", () => {
  expect(searchbutton).toBeDefined();
  expect(searchbutton).not.toBeNull();
});

test("Test nommatiere defined", () => {
  expect(nommatiere).toBeDefined();
  expect(nommatiere).not.toBeNull();
});

test("Test info defined", () => {
  expect(info).toBeDefined();
  expect(info).not.toBeNull();
});

test("Test search matiere Web", () => {
    searchbutton.addEventListener('click', function() {
        console.log('test');
      });

  nommatiere.value = "Web";
  searchbutton.click(); 
  // Demo. Nothing to do since for the exercise no user interface element is defined (the console is used).
});
