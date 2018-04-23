import "jest";
import { DnD } from "../main/interaction.js";
import { createMouseEvent } from "./EventStubs.js";
import { Pencil } from "../main/controler.js";

let dnd;
let canvas;
let interactor;
jest.mock('../main/controler.js');


beforeEach(() => {
    jest.clearAllMocks();
    interactor = new Pencil();
    document.documentElement.innerHTML = "<html><div><canvas id='canvas'/></div></html>";
    canvas = document.getElementById("canvas");
    dnd = new DnD(canvas, interactor);
});

test("Press DnD sets all the coordinates", () => {
    canvas.dispatchEvent(createMouseEvent("mousedown", canvas, 10, 11));
    expect(dnd.startX).toBe(10);
    expect(dnd.startY).toBe(11);
    expect(dnd.endX).toBe(10);
    expect(dnd.endY).toBe(11);
});

test("Press-Move DnD updates the end coordinates", () => {
    canvas.dispatchEvent(createMouseEvent("mousedown", canvas, 10, 11));
    canvas.dispatchEvent(createMouseEvent("mousemove", canvas, 12, 13));
    expect(dnd.startX).toBe(10);
    expect(dnd.startY).toBe(11);
    expect(dnd.endX).toBe(12);
    expect(dnd.endY).toBe(13);
});


test("Press-Move-Move DnD updates the end coordinates", () => {
    canvas.dispatchEvent(createMouseEvent("mousedown", canvas, 10, 11));
    canvas.dispatchEvent(createMouseEvent("mousemove", canvas, 12, 13));
    canvas.dispatchEvent(createMouseEvent("mousemove", canvas, 14, 15));
    expect(dnd.startX).toBe(10);
    expect(dnd.startY).toBe(11);
    expect(dnd.endX).toBe(14);
    expect(dnd.endY).toBe(15);
});

test("Press-Move-Move-release DnD is ok", () => {
    canvas.dispatchEvent(createMouseEvent("mousedown", canvas, 10, 11));
    canvas.dispatchEvent(createMouseEvent("mousemove", canvas, 12, 13));
    canvas.dispatchEvent(createMouseEvent("mousemove", canvas, 14, 15));
    canvas.dispatchEvent(createMouseEvent("mouseup", canvas, 14, 15));
    expect(dnd.startX).toBe(10);
    expect(dnd.startY).toBe(11);
    expect(dnd.endX).toBe(14);
    expect(dnd.endY).toBe(15);
});

test("Two DnD update values correctly", () => {
    canvas.dispatchEvent(createMouseEvent("mousedown", canvas, 10, 11));
    canvas.dispatchEvent(createMouseEvent("mousemove", canvas, 12, 13));
    canvas.dispatchEvent(createMouseEvent("mouseup", canvas, 12, 13));
    canvas.dispatchEvent(createMouseEvent("mousedown", canvas, 20, 21));
    expect(dnd.startX).toBe(20);
    expect(dnd.startY).toBe(21);
});

test("DnD not updated on move if no press", () => {
    canvas.dispatchEvent(createMouseEvent("mousemove", canvas, 12, 13));
    expect(dnd.endX).toBe(0);
    expect(dnd.endX).toBe(0);
});

test("Interactor notified start", () => {
    canvas.dispatchEvent(createMouseEvent("mousedown", canvas, 10, 11));
    expect(interactor.onInteractionStart).toHaveBeenCalledTimes(1);
    expect(interactor.onInteractionStart).toHaveBeenCalledWith(dnd);
});

test("Interactor notified update", () => {
    canvas.dispatchEvent(createMouseEvent("mousedown", canvas, 10, 11));
    canvas.dispatchEvent(createMouseEvent("mousemove", canvas, 12, 13));
    expect(interactor.onInteractionUpdate).toHaveBeenCalledTimes(1);
    expect(interactor.onInteractionUpdate).toHaveBeenCalledWith(dnd);
});

test("Interactor notified update twice", () => {
    canvas.dispatchEvent(createMouseEvent("mousedown", canvas, 10, 11));
    canvas.dispatchEvent(createMouseEvent("mousemove", canvas, 12, 13));
    canvas.dispatchEvent(createMouseEvent("mousemove", canvas, 14, 15));
    expect(interactor.onInteractionUpdate).toHaveBeenCalledTimes(2);
    expect(interactor.onInteractionUpdate).toHaveBeenCalledWith(dnd);
});

test("Interactor notified update twice", () => {
    canvas.dispatchEvent(createMouseEvent("mousedown", canvas, 10, 11));
    canvas.dispatchEvent(createMouseEvent("mousemove", canvas, 12, 13));
    canvas.dispatchEvent(createMouseEvent("mouseup", canvas, 12, 13));
    expect(interactor.onInteractionEnd).toHaveBeenCalledTimes(1);
    expect(interactor.onInteractionEnd).toHaveBeenCalledWith(dnd);
});