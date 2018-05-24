package org.eclipse.om2m.sdt.home.hue;

import junit.framework.TestCase;

public class HomeLightTest extends TestCase {

	public void testRGBToHSV() {
		int[] hsv = HomeLight.RGBtoHSB(200, 100, 50);

		assertTrue(hsv[0] == 20);
		assertTrue(hsv[1] == 75);
		assertTrue(hsv[2] == 78);
	}

	public void testHSVtoRGB() {
		int[] rgb = HomeLight.HSVtoRGB(20, 75, 78);

		assertTrue(rgb[0] == 199);
		assertTrue(rgb[1] == 99);
		assertTrue(rgb[2] == 50);

		rgb = HomeLight.HSVtoRGB(258, 57, 99);

		assertTrue(rgb[0] == 152);
		assertTrue(rgb[1] == 109);
		assertTrue(rgb[2] == 252);
	}

}
