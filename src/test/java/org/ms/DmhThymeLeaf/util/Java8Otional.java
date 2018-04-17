package org.ms.DmhThymeLeaf.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.ms.DmhThymeLeaf.entity.DmhUser;

public class Java8Otional {
	
	@Test
	public void whenCreatesEmptyOptional_thenCorrect() {
	    Optional<String> empty = Optional.empty();
	    assertFalse(empty.isPresent());
	}
	
	@Test
	public void givenNonNullOptionalOf(){
		String name= "Prabu";
		Optional<String> opt = Optional.of(name);
		System.out.println(opt.toString());
		assertEquals("Optional[Prabu]", opt.toString());
	}
	
	@Test(expected = NullPointerException.class)
	public void excepectNullPOinter(){
		String str = null;
		Optional<String> opt = Optional.of(str);
	}
	
	
	@Test
	public void givenNonNullOptionalOfNullable(){
		String name= "Prabu";
		Optional<String> opt = Optional.ofNullable(name);
		System.out.println(opt.toString());
		assertEquals("Optional[Prabu]", opt.toString());
	}
	
	@Test()
	public void nullPOinterOfNullbale(){
		String str = null;
		Optional<String> opt = Optional.ofNullable(str);
		System.out.println(opt.toString());
		assertEquals("Optional.empty", opt.toString());
	}
	
	
	@Test()
	public void ispresentOfNull(){
		String name= "Prabu";
		Optional<String> opt = Optional.ofNullable(name);
		assertTrue(opt.isPresent());
		
		opt = opt.ofNullable(null);
		System.out.println();
		assertFalse(opt.isPresent());
	}
	
	
	@Test
	public void ifPresentLambda(){
		String name= "Prabu";
		Optional<String> opt = Optional.ofNullable(name);
		opt.ifPresent(this::appender);
		opt.ifPresent(s -> System.out.println(s.length()));
		
		Optional.ofNullable(name).ifPresent(new Java8Otional()::appender);
		
	}
	
	@Test
	public void whenOrElseWorks_thenCorrect() {
	    String nullName = null;
	   // String newstr = 
	    assertEquals("test", Optional.ofNullable(nullName).orElse("test"));
	    assertEquals("test", Optional.ofNullable(nullName).orElseGet(() -> "test"));
	}
	
	@Test
	public void whenOrElseWorks_withValue() {
	    String nullName = "Prabu";
	   // String newstr = 
	    assertEquals("Prabu", Optional.ofNullable(nullName).orElse("test"));
	    assertEquals("Prabu", Optional.ofNullable(nullName).orElseGet(() -> "test"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void orElseThrowExeption(){
		 String nullName = null;
		 Optional.ofNullable(nullName).orElseThrow(IllegalArgumentException::new);
	}
	
	
	@Test
	public void givenOptional_whenGetsValue_thenCorrect() {
	    Optional<String> opt = Optional.of("Prabu");
	    assertEquals("Prabu", opt.get());
	}
	
	
	@Test
    public void whenFiltersWithOptional_thenCorrect() {
        assertFalse(pricesinRange(new myInnerClass(10.0)));
        assertTrue(pricesinRange(new myInnerClass(120)));
       // assertFalse(pricesinRange(new myInnerClass(null)));
        assertTrue(pricesinRange(new myInnerClass(150.5)));
        assertFalse(pricesinRange(null));
    }
	
	
	@Test
	public void somerandomListTest(){
		List<String> companyNames = Arrays.asList(
			      "paypal", "oracle", "", "microsoft", "", "apple");
		//int i = Optional.ofNullable(companyNames).map(List::size).orElse(0).intValue();
		assertEquals(6, Optional.ofNullable(companyNames).map(List::size).orElse(0).intValue());
		
		
	}
	
	
	@Test
	public void getAllListElements(){
		List<String> myList = Arrays.asList("one", "two", "three", "four");
		//Optional.ofNullable(myList).map(List::size).filter(s->s>0).
		
		//List<String> = Optional.ofNullable(myList).orElse(myList.get(index))
		
		List<String> newList = myList.stream().collect(Collectors.toList());
		
		myList = null;
	//	Optional.ofNullable(myList).ifPresent();
		//newList = myList.stream().map(o -> Optional.ofNullable(o)).collect(Collectors.toList());
		//	myList.stream().filter(Optional::isPresent).map()
			
		
			
		assertNotNull(newList);
		
	}
	
	
	@Test
	public void checkPassword(){
		String password = "mySecret";
		
		int idate = 20170630;
		if(idate>Integer.MAX_VALUE){
			System.out.println("LOL");
		}
		//System.out.println(Integer.parseInt("﻿20170630"));
		System.out.println(Integer.parseInt("20170630"));
		//System.out.println("Sum of x+y = " + Integer.parseInt("﻿10"));
		System.out.println("Sum of x+y = " + Integer.parseInt("10"));
		
		assertTrue(Optional.ofNullable(password)
									.map(String::toLowerCase)
											.filter(s -> s.equalsIgnoreCase("mySecret")).isPresent());
	}
	
	
	
	
	class myInnerClass{
		private double price;
		myInnerClass(double price){
			this.price = price;
		}
		
		public void setPrice(double price){
			this.price = price;
		}
		
		public double getPrice(){
			return this.price;
		}
	}
	
	
	public boolean pricesinRange(myInnerClass innc){
		return Optional.ofNullable(innc)
				.map(myInnerClass::getPrice)
					.filter(p -> p > 50)
						.filter(p -> p < 200).isPresent();
	}
	
	
	private String appender(String s){
		System.out.println("in the print appender" +s);
		return s + "hai..";
	}
	
	

}
