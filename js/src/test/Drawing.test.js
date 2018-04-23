import "jest";
import { Drawing } from "../main/model.js";

let drawing;

beforeEach(() => {
    drawing = new Drawing();
});

test("testArray", () => {
    expect(drawing.shapes).toHaveLength(0);
});