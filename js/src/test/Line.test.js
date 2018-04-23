import "jest";
import { Line } from "../main/model.js";

let line;

beforeEach(() => {
    line = new Line(1, 2, 3, 4, 'red', 11);
});

test("testX1", () => {
    expect(line.x1).toBe(1);
});

test("testX2", () => {
    expect(line.x2).toBe(3);
});

test("testY1", () => {
    expect(line.y1).toBe(2);
});

test("testY2", () => {
    expect(line.y2).toBe(4);
});

test("testColor", () => {
    expect(line.color).toBe('red');
});

test("testLinewidth", () => {
    expect(line.linewidth).toBe(11);
});