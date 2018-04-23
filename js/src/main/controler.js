//import { DnD } from "./interaction.js";
//import { Rectangle, Line } from "./model.js";

export const EditingMode = {
	rect: 0,
	line: 1
};

export class Pencil {
	constructor(drawing, canvas) {
		this.drawing = drawing;
		this.canvas = canvas;
		this.currEditingMode = EditingMode.rect;
		this.currLineWidth = 5;
		this.currColour = '#000000';
		this.currentShape = undefined;

		// Register the widgets of the document to callback methods
	}

	// Implement the three methods onInteractionStart, onInteractionUpdate, and onInteractionEnd
}
