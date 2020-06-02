package it.polito.tdp.food.db;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Food> listaCibi(int porzioni){
		
		String sql = "SELECT fo.food_code AS fcode, fo.display_name AS fname, COUNT(DISTINCT po.portion_id) AS num " + 
				"FROM `portion` AS po, food AS fo " + 
				"WHERE fo.food_code=po.food_code " + 
				"GROUP BY fo.food_code "+
				"HAVING num=? "+
				"ORDER BY fo.display_name";
		
		List<Food> listaCibi = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, porzioni);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					
					listaCibi.add(new Food(res.getInt("fcode"), res.getString("fname")));
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return listaCibi;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Double getAdiacenza(int codiceCibo1, int codiceCibo2) {

		String sql = "SELECT DISTINCT f1.food_code AS c1, food1.display_name AS n1, f2.food_code AS c2, food2.display_name AS n2, (SUM(c.condiment_calories)/COUNT(DISTINCT f1.condiment_code)) AS media " + 
				"FROM food_condiment AS f1, food_condiment AS f2, condiment AS c, food AS food1, food AS food2 " + 
				"WHERE f1.food_code= ? AND f2.food_code = ? AND f1.condiment_code=f2.condiment_code " + 
				"AND c.condiment_code=f1.condiment_code AND food1.food_code=f1.food_code AND food2.food_code=f2.food_code";
		
		Double a = null;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, codiceCibo1);
			st.setInt(2, codiceCibo2);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				try {
					
					a = res.getDouble("media");

				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			conn.close();
			return a;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
