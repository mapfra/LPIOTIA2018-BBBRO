function hslToHsv(hsl) {
    var h = hsl[0];
    var v = hsl[2]+hsl[1]*(hsl[2]<.5?hsl[2]:1-hsl[2])
    var s = 2*hsl[1]*(hsl[2]<.5?hsl[2]:1-hsl[2])/v;
    return [h, s, v];
}

function hsvToHsl(hsv) {
    var h = hsv[0];
    var l = (2-hsv[1])*hsv[2]/2;
    var s = 0;
    if (l>0)
        s = hsv[1]*hsv[2]/(l<0.5?2*l:2-2*l); 
    return [h, s, l];
}

function hueToRgb(m1, m2, h) {
    h = (h < 0) ? h + 1 : ((h > 1) ? h - 1 : h);
    if (h * 6 < 1) return m1 + (m2 - m1) * h * 6;
    if (h * 2 < 1) return m2;
    if (h * 3 < 2) return m1 + (m2 - m1) * (0.66666 - h) * 6;
    return m1;
}

function hslToRgb(hsl) {
    var m1, m2, r, g, b;
    var h = hsl[0], s = hsl[1], l = hsl[2];
    m2 = (l <= 0.5) ? l * (1 + s) : l + s - l * s;
    m1 = l * 2 - m2;
    return [hueToRgb(m1, m2, h+0.33333),
            hueToRgb(m1, m2, h),
            hueToRgb(m1, m2, h-0.33333)];
}

function rgbToHsl(rgb) {
    var min, max, delta, h, s, l;
    var r = rgb[0], g = rgb[1], b = rgb[2];
    min = Math.min(r, Math.min(g, b));
    max = Math.max(r, Math.max(g, b));
    delta = max - min;
    l = (min + max) / 2;
    s = 0;
    if (l > 0 && l < 1) {
        s = delta / (l < 0.5 ? (2 * l) : (2 - 2 * l));
    }
    h = 0;
    if (delta > 0) {
        if (max == r && max != g) h += (g - b) / delta;
        if (max == g && max != b) h += (2 + (b - r) / delta);
        if (max == b && max != r) h += (4 + (r - g) / delta);
        h /= 6;
    }
    return [h, s, l];
}

/* Convert RGB color to string and vice versa (for CSS use) */
function rgbToString(rgb) {
    var r = Math.round(rgb[0] * 255);
    var g = Math.round(rgb[1] * 255);
    var b = Math.round(rgb[2] * 255);
    return '#' + (r < 16 ? '0' : '') + r.toString(16) +
                 (g < 16 ? '0' : '') + g.toString(16) +
                 (b < 16 ? '0' : '') + b.toString(16);
}

function stringToRGB(color) {
    if (color.length == 7) {
        return [parseInt('0x' + color.substring(1, 3)) / 255,
                parseInt('0x' + color.substring(3, 5)) / 255,
                parseInt('0x' + color.substring(5, 7)) / 255];
    }
    else if (color.length == 4) {
        return [parseInt('0x' + color.substring(1, 2)) / 15,
                parseInt('0x' + color.substring(2, 3)) / 15,
                parseInt('0x' + color.substring(3, 4)) / 15];
    }
}
