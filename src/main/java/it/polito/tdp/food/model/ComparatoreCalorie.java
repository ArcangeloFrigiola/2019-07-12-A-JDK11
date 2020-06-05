package it.polito.tdp.food.model;

import java.util.Comparator;

public class ComparatoreCalorie implements Comparator<FoodAndCalories>{

	@Override
	public int compare(FoodAndCalories o1, FoodAndCalories o2) {
		// TODO Auto-generated method stub
		return -(o1.getCalorieCongiunte().compareTo(o2.getCalorieCongiunte()));
	}



}
