
export function createMouseEvent(type, target, x, y) {
    return new MouseEvent(type, {
        view: window,
        bubbles: true,
        cancelable: false,
        detail: 1,
        screenX: x,
        screenY: y,
        clientX: x,
        clientY: y,
        ctrlKey: false,
        altKey: false,
        shiftKey: false,
        metaKey: false,
        button: 0,
        relatedTarget: target
    });
}
