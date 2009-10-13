/*
 * Created on Aug 15, 2004
 *
 */
package com.myconnector.util;

import java.util.List;

import junit.framework.TestCase;

/** 
 *
 * @author Nilesh Kapadia (nileshka@gmail.com)
 */
public class ConvertUtilTests extends TestCase {

	public void testArray2List() {
		Object[] oa = new Object[4];
		oa[0] = "Test";
		oa[1] = new Integer(500);
		oa[2] = new Double(100);
		oa[3] = null;
		List list = ConvertUtil.array2List(oa);		
		assertNotNull("Not null", list);		
		assertNotNull("Element not null", list.get(0));
		assertNotNull("Element not null", list.get(1));
		assertNotNull("Element not null", list.get(2));
		assertNull("Element is null", list.get(3));
		assertEquals("Is correct instance", oa[0], list.get(0));
		assertEquals("Is correct instance", oa[1], list.get(1));
		assertEquals("Is correct instance", oa[2], list.get(2));
		assertEquals("Correct number of elements", 4, list.size());
	}
	
	public void testArray2ListNullInput() {
		List list = ConvertUtil.array2List(null);
		assertNotNull("Not null", list);
		assertEquals("List is empty", 0, list.size());
	}
	
	public void testArray2ListIncompleteArray() {
		Object[] oa = new Object[4];
		oa[0] = "Test";
		List list = ConvertUtil.array2List(oa);		
		assertNotNull("Not null", list);		
		assertNotNull("Element not null", list.get(0));
		assertNull("Element is null", list.get(1));
		assertNull("Element is null", list.get(2));
		assertNull("Element is null", list.get(3));
		assertEquals("Is correct instance", oa[0], list.get(0));
		assertEquals("Is correct instance", oa[1], list.get(1));
		assertEquals("Is correct instance", oa[2], list.get(2));
		assertEquals("Correct number of elements", 4, list.size());		
	}

	public void testConvertTextToStringList1() {
		String text = "test1\n\rtest2\r\ntest3\ntest4\rtest5\ntest6";
		List<String> ret = ConvertUtil.convertTextToStringList(text);
		assertEquals(6, ret.size());
	}
}
