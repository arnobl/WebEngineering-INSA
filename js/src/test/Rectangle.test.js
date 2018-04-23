import "jest";
import { Rectangle } from "../main/model.js";

let rec;

beforeEach(() => {
    rec = new Rectangle(1, 2, 3, 4, 'red', 11);
});

test("testX", () => {
    expect(rec.x).toBe(1);
});

test("testY", () => {
    expect(rec.y).toBe(2);
});

test("testW", () => {
    expect(rec.w).toBe(3);
});

test("testH", () => {
    expect(rec.h).toBe(4);
});

test("testColor", () => {
    expect(rec.color).toBe('red');
});

test("testLinewidth", () => {
    expect(rec.linewidth).toBe(11);
});