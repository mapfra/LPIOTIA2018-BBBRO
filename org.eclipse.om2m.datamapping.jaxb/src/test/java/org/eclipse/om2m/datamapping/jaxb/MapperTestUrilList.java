package org.eclipse.om2m.datamapping.jaxb;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.resource.URIList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MapperTestUrilList extends AbstractMapperTest {

	private Mapper xmlMapper;
	private Mapper jsonMapper;

	@Before
	public void setUp() throws Exception {
		xmlMapper = new Mapper(MimeMediaType.XML);
		jsonMapper = new Mapper(MimeMediaType.JSON);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testObjToStringXml() {
		System.out.println("\n testObjToStringXml");

		URIList uriList = new URIList();
		uriList.getListOfUri().add("tof");
		uriList.getListOfUri().add("plouf");
		String s = xmlMapper.objToString(uriList);
		System.out.println("xmlMapper");
		System.out.println(s);
		System.out.println("fin xmlMapper");

		String expectedResult = readFile("src/test/resources/urilist.xml");
		System.out.println("expected");
		System.out.println(expectedResult);
		System.out.println("fin expected");

	}

	@Test
	public void testStringToObjXml() {
		System.out.println("\n testStringToObjXml");
		String xml = readFile("src/test/resources/urilist.xml");

		Object object = xmlMapper.stringToObj(xml);
		assertTrue(object != null);
		assertTrue(object instanceof URIList);

		URIList uriList = (URIList) object;

		assertFalse(uriList.getListOfUri().isEmpty());
		assertTrue(uriList.getListOfUri().size() == 2);
		assertTrue(uriList.getListOfUri().contains("plouf"));
		assertTrue(uriList.getListOfUri().contains("tof"));
	}

	@Test
	public void testObjToStringJSON() {
		System.out.println("\n testObjToStringJSON");

		URIList uriList = new URIList();
		uriList.getListOfUri().add("tof");
		uriList.getListOfUri().add("plouf");
		String s = jsonMapper.objToString(uriList);
		System.out.println(s);
		String expectedString = "{\r\n" + "   \"m2m:uril\" : [ \"tof\", \"plouf\" ]\r\n" + "}";
		assertTrue(expectedString.equals(s));
	}

	@Test
	public void testStringToObjJSON() {
		System.out.println("\n testStringToObjJSON");

		String json = "{\r\n" + "   \"m2m:uril\" : [ \"tof\", \"plouf\" ]\r\n" + "}";

		Object object = jsonMapper.stringToObj(json);
		assertTrue(object != null);
		assertTrue(object instanceof URIList);

		URIList uriList = (URIList) object;

		// impossible de passer de string à object.

	}

}
