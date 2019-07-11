package dev;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

public class EntityTest {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		hello();
		
		Person person2 = new Person();
		Table updatedTable3 = person2.getClass().getAnnotation(Table.class);
		System.out.println(updatedTable3.schema());  //? still no change!
	}
	
	public static void hello() throws NoSuchMethodException, SecurityException
	{
		Person person = new Person();
		//System.out.println(System.getProperty("java.class.path"));
		// change schema of person table

		
		try {
			final Table table = person.getClass().getAnnotation(Table.class); // sh
			System.out.println(table.schema());
			
			Annotation newTable = new Table() { 
// ?? So table is a class or interface?
// interface can neither be instantiated not initialized, so this @Table is a class but 
// then what does @interface Table mean in the documentation?
				
// ? WARNING: All illegal access operations will be denied in a future release. 
//				any workaround?

		        @Override
		        public String schema() {
		            return "newTableSchema";
		        }

		        @Override
		        public Class<? extends Annotation> annotationType() {
//		            return Table.class;
		        	return table.annotationType();  
		        }
				@Override
				public UniqueConstraint[] uniqueConstraints() {
//					return new UniqueConstraint[] {};
					return table.uniqueConstraints();
				}

				@Override
				public String catalog() {
					return table.catalog();
				}

				@Override
				public String name() {
					return table.name();
				}
		    };
		    
			Method method = Class.class.getDeclaredMethod("annotationData", null);
			method.setAccessible(true);  // ? WARNING: All illegal access operations will be denied in a future release. 
//			any workaround?
			
			Object annotationData = method.invoke(Person.class);
			
			Field annotations = annotationData.getClass().getDeclaredField("annotations");
			annotations.setAccessible(true);
			
			
			Map<Class<? extends Annotation>, Annotation> map  = (Map<Class<? extends Annotation>, Annotation>) annotations.get(annotationData);
			map.put(Table.class, newTable); // ?? BIG ONE: only option is to fully replace the Table, any way to only change schema and keep the rest as before?
			
			Table updatedTable = person.getClass().getAnnotation(Table.class);
			System.out.println(updatedTable.schema());
		} catch (Exception e)
		{
			e.printStackTrace();
		}	
		
		// ? forever changed...
		// how to only change the schema for one Person instance/one @Test scenario
		Table updatedTable = person.getClass().getAnnotation(Table.class);
		System.out.println(updatedTable.schema());
		
		Table updatedTable2 = Person.class.getAnnotation(Table.class);
		System.out.println(updatedTable2.schema());
		
		Person person2 = new Person();
		Table updatedTable3 = person2.getClass().getAnnotation(Table.class);
		System.out.println(updatedTable3.schema());
	}
}

/*
 * Resources:
 * https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-lang/src/main/java/com/baeldung/java/reflection/GreetingAnnotation.java
 * */