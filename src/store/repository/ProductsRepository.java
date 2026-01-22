package store.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector.ConnectionFactory;
import store.domain.Products;

public class ProductsRepository {

	public static void save(Products products) {
		String sql = "INSERT INTO products (name, price, quantity) values (?, ?, ?);";
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, products.getName());
			ps.setBigDecimal(2, products.getPrice());
			ps.setInt(3, products.getQuantity());
			ps.execute();
			System.out.println("Produto inserido com sucesso!");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void delete(Integer id) {
		String sql = "DELETE FROM products WHERE id = ?;";
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, id);
			ps.execute();
			System.out.println("Produto apagado com sucesso!");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void update(Products product) {
		String sql = "UPDATE products SET name = ?, price = ?, quantity = ? WHERE id = ?;";
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, product.getName());
			ps.setBigDecimal(2, product.getPrice());
			ps.setInt(3, product.getQuantity());
			ps.setInt(4, product.getId());
			ps.execute();
			System.out.println("Produto alterado com sucesso!");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Products findById(Integer id) {
		String sql = "SELECT * FROM products WHERE id = ?;";
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return new Products (
					rs.getInt("id"),
					rs.getString("name"),
					rs.getBigDecimal("price"),
					rs.getInt("quantity")
						);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Products> findAll() {
		
		String sql = "SELECT * FROM products;";
		List<Products> productsList = new ArrayList<>();
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
			
			while (rs.next()) {
				Products p = new Products(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getBigDecimal("price"),
						rs.getInt("quantity")
						);
				productsList.add(p);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return productsList;
	}
	
	public static List<Products> findByName(String name) {
		String sql = "SELECT * FROM products WHERE name LIKE ?;";
		
		List<Products> productsList = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setString(1, "%" + name + "%");
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Products product = new Products(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getBigDecimal("price"),
						rs.getInt("quantity")
						);
				productsList.add(product);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return productsList;
	}
	
}
