 /*******************************************************************************
 * Copyright (c) 2018, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

jQuery.fn.colorPicker = function (callback) {
    $.colorPicker(this, callback);
    return this;
};

jQuery.colorPicker = function (htmlBlock, callback) {
    var htmlBlock = $(htmlBlock).get(0);
    return htmlBlock.colorPicker || (htmlBlock.colorPicker = new jQuery._colorPicker(htmlBlock, callback));
}

jQuery._colorPicker = function (htmlBlock, callback) {

    var picker = this;

    var e = $('.colorPicker', htmlBlock);
    picker.ring = $('.ring', htmlBlock).get(0);
    // Dimensions
    picker.radius = 32;
    picker.square = 39;
    picker.width = 70;

  // Fix background PNGs in IE6
    if (navigator.appVersion.match(/MSIE [0-6]\./)) {
        $('*', e).each(function () {
            if (this.currentStyle.backgroundImage != 'none') {
                var image = this.currentStyle.backgroundImage;
                image = this.currentStyle.backgroundImage.substring(5, image.length - 2);
                $(this).css({
                    'backgroundImage': 'none',
                    'filter': "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=crop, src='" + image + "')"
                });
            }
        });
    }

    ////////////////////////////////////////////////////
    // Here we connect the color picker with a function/callback or element.
    ///////
    picker.attachCallback = function (callback) {
        // Unbind
        if (typeof picker.callback == 'object') {
            $(picker.callback).unbind('keyup', picker.updateValue);
        }

        // Reset color
        picker.color = null;

        // Bind callback or element
        if (typeof callback == 'function') {
            picker.callback = callback;
        }
        else if (typeof callback == 'object' || typeof callback == 'string') {
            picker.callback = $(callback);
            picker.callback.bind('keyup', picker.updateValue);
            if (picker.callback.get(0).value) {
                picker.setColor(picker.callback.get(0).value);
            }
        }
        return this;
    }

    picker.updateValue = function (event) {
        if (this.value && this.value != picker.color) {
            picker.setColor(this.value);
        }
    }

    ////////////////////////////////////////////////////
    // Change color with HTML syntax #123456
    ///////
    picker.setColor = function (color) {
        var rgb = stringToRGB(color);
        if (picker.color != color && rgb) {
            picker.color = color;
            picker.rgb = rgb;
            picker.hsl = rgbToHsl(picker.rgb);
            picker.hsv = hslToHsv(picker.hsl);
            picker.updateDisplay();
        }
        return this;
    }

    ////////////////////////////////////////////////////
    // Change color with HSL triplet [0..1, 0..1, 0..1]
    ///////
    picker.setHSL = function (hsl) {
        picker.hsl = hsl.map(Number);
        picker.hsv = hslToHsv(picker.hsl);
        picker.rgb = hslToRgb(picker.hsl);
        picker.color = rgbToString(picker.rgb);
        picker.updateDisplay();
        return this;
    }

    ////////////////////////////////////////////////////
    // Change color with Red, Green and Blue values of 0..255
    ///////
    picker.setRGB = function (r, g, b) {
        picker.rgb = [r/255, g/255, b/255];
        picker.hsl = rgbToHsl(picker.rgb);
        picker.hsv = hslToHsv(picker.hsl);
        picker.color = rgbToString(picker.rgb);
        picker.updateDisplay();
        return this;
    }

    ////////////////////////////////////////////////////
    // Change color with HSV triplet [0..1, 0..1, 0..1]
    ///////
    picker.setHSV = function (hsv) {
        picker.hsv = hsv.map(Number);
        picker.hsl = hsvToHsl(picker.hsv);
        picker.rgb = hslToRgb(picker.hsl);
        picker.color = rgbToString(picker.rgb);
        picker.updateDisplay();
        return this;
    }

    ////////////////////////////////////////////////////
    // Return color as RGB triplet [0..255, 0..255, 0..255]
    ///////
    picker.getRGB = function () {
        return [Math.round(picker.rgb[0] * 255), Math.round(picker.rgb[1] * 255), Math.round(picker.rgb[2] * 255) ];
    }

    
    ////////////////////////////////////////////////////
    // Retrieve the coordinates of event relative to the center
    // of the picker widget.
    ///////
    picker.pointerCoordinates = function (event) {
        var x = 0
        var y = 0
        var reference = picker.ring
        var element = event.target || event.srcElement

        if (typeof event.pageX != 'undefined') {
            // Find mouse position
            var mousePos = { x: event.pageX, y: event.pageY }

            // Find picker position
            var pickerPos = { x: $(reference).offset().left, y: $(reference).offset().top }
           
            // Set relative coordinates
            x = mousePos.x - pickerPos.x
            y = mousePos.y - pickerPos.y
        }
       
        // Calculate event distance from the middle of the widget
        return { x: x - picker.width / 2, y: y - picker.width / 2 };
    }

    ////////////////////////////////////////////////////
    // Mousedown handler
    ///////
    picker.mousedown = function (event) {
        // Capture mouse
        if (!document.isDragged) {
            $(document).bind('mousemove', picker.mousemove).bind('mouseup', picker.mouseup);
            document.isDragged = true;
        }

        // Check which area is being dragged
        var position = picker.pointerCoordinates(event);
        picker.pointerDrag = 2 * Math.max(Math.abs(position.x), Math.abs(position.y)) > picker.square;

        // Process
        picker.mousemove(event);
        return false;
    }

    ////////////////////////////////////////////////////
    // Mousemove handler
    ///////
    picker.mousemove = function (event) {
        // Get coordinates relative to colorPicker center
        var pos = picker.pointerCoordinates(event);

        // Set new HSL parameters
        if (picker.pointerDrag) {
            var hue = Math.atan2(pos.x, -pos.y) / 6.28;
            if (hue < 0) 
            	hue += 1
            picker.setHSL([hue, picker.hsl[1], picker.hsl[2]]);
            window['global_Value_'+htmlBlock.id+'_hue']=hue;
        }
        return false;
    }

    ////////////////////////////////////////////////////
    // Mouseup handler
    ///////
    picker.mouseup = function () {
        // Uncapture mouse
        $(document).unbind('mousemove', picker.mousemove);
        $(document).unbind('mouseup', picker.mouseup);
        document.isDragged = false;
    }

    ////////////////////////////////////////////////////
    // Update the markers and styles
    ///////
    picker.updateDisplay = function () {
        // Marker position update
        var angle = picker.hsl[0] * 6.28;
        $('.ringPointer', e).css({
            left: Math.round(Math.sin(angle) * picker.radius + picker.width / 2) + 'px',
            top: Math.round(-Math.cos(angle) * picker.radius + picker.width / 2) + 'px'
        });

        // Current color visualization
        $('.color', e).css('backgroundColor', rgbToString(hslToRgb([picker.hsl[0], 1, 0.5])));

        // Linked elements or callback
        if (typeof picker.callback == 'object') {
            // Set background/foreground color
            $(picker.callback).css({
                backgroundColor: picker.color,
                color: picker.hsl[2] > 0.5 ? '#000' : '#fff'
            });


            // Change linked value
            $(picker.callback).each(function() {
                if (this.value && this.value != picker.color) {
                    this.value = picker.color;
                }
            });
        }
        else if (typeof picker.callback == 'function') {
            picker.callback.call(picker, picker.color);
        }
    }

    ////////////////////////////////////////////////////
    // Get absolute position of element
    ///////
    picker.absolutePosition = function (el) {
        var pos = { x: el.offsetLeft, y: el.offsetTop };
        // Resolve relative to offsetParent
        if (el.offsetParent) {
            var tmp = picker.absolutePosition(el.offsetParent);
            pos.x += tmp.x;
            pos.y += tmp.y;
        }
        return pos;
    };
    

    // Install mousedown handler
    $('*', e).mousedown(picker.mousedown);

    // Init color
    picker.setColor('#000000');

    // Set linked elements/callback
    if (callback) {
        picker.attachCallback(callback);
    }
}