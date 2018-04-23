
// Vanilla JS exercise (ie with no library)
// Learning basic: JavaScript constructs, npm usages, DOM manipulation, JavaScript testing with Jest

import {DnD} from "./interaction.js";
//import {Drawing} from "./model.js";
import "./view.js";
//import {Pencil, EditingMode} from "./controler.js";

const canvas = document.getElementById('myCanvas');

// Temporary code.
new DnD(canvas);
const ctx = canvas.getContext('2d');
ctx.fillStyle = '#F0F0F0'; // set canvas' background color
ctx.fillRect(0, 0, canvas.width, canvas.height);  // now fill the canvas

// other temporary code (do not forget the import)
//var rec = new Rectangle(10, 20, 50, 100, 5, '#00CCC0');
//rec.paint(ctx);
//var line = new Line(10, 20, 50, 100, 5, '#00CCC0');
//line.paint(ctx);
// tester également Dessin.
//

/*const drawing = new Drawing();

const pencil = new Pencil(drawing, canvas);
pencil.currColour = document.getElementById('colour').value;
pencil.currEditingMode = document.getElementById('butRect').checked ? EditingMode.rect : EditingMode.line;

drawing.paint(canvas);*/


