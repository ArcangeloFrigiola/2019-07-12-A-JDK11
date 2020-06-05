package it.polito.tdp.food.model;

public class FoodAndCalories {
	
	private Food cibo;
	private Double calorieCongiunte;
	/**
	 * @param cibo
	 * @param calorieCongiunte
	 */
	public FoodAndCalories(Food cibo, Double calorieCongiunte) {
		super();
		this.cibo = cibo;
		this.calorieCongiunte = calorieCongiunte;
	}
	public Food getCibo() {
		return cibo;
	}
	public Double getCalorieCongiunte() {
		return calorieCongiunte;
	}
	
	
}
