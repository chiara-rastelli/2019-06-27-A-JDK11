package it.polito.tdp.crimes.db;

import it.polito.tdp.crimes.model.Coppia;
import it.polito.tdp.crimes.model.Event;

public class TestDao {

	public static void main(String[] args) {
		EventsDao dao = new EventsDao();
		//for(Event e : dao.listAllEvents())
		//	System.out.println(e);
	/*	
	for (String s :	dao.listTypeFromCategory("aggravated-assault")) {
		System.out.println(s);
	}
	
	for (int i : dao.listAllYears())
		System.out.println(i);
	
	for (Coppia c : dao.getCoppie("aggravated-assault", 2015))
		System.out.println(c);
	*/
	}
		
}
