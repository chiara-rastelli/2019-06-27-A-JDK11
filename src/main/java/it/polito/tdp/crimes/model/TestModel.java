package it.polito.tdp.crimes.model;

public class TestModel {

	public static void main(String[] args) {
		Model model = new Model();
		model.setGrafo("aggravated-assault", 2015);
		for (Coppia c : model.getCoppiePesoMasismo())
			System.out.println(c);
		
		
	}

}
