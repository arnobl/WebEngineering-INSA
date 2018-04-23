
// https://developer.mozilla.org/fr/docs/Web/JavaScript/Reference/Classes

export class DnD {
	constructor(widget, interactor) {
		// Define the DnD attributes: startX, startY, endX, endY, pressed

		// Process the three mouse event: 'mousedown', 'mousemove', and 'moveup'.
	}

	getMousePos(canvas, evt) {
		const rect = canvas.getBoundingClientRect();
		return {
			x: evt.clientX - rect.left,
			y: evt.clientY - rect.top
		};
	}
}


