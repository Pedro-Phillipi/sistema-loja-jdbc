package store.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector.ConnectionFactory;
import store.domain.Customers;

public class CustomersRepository {

	public static void save(Customers customer) {
		String sql = "INSERT INTO customers (name, email, cpf) VALUES (?, ?, ?);";
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, customer.getName());
			ps.setString(2, customer.getEmail());
			ps.setString(3, customer.getCpf());
			ps.execute();
			System.out.println("Cliente adicionado com sucesso!");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void delete(Integer id) {
		String sql = "DELETE FROM customers WHERE id = ?;";
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, id);
			ps.execute();
			System.out.println("Cliente deletado com sucesso!");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void update(Customers customer) {
		String sql = "UPDATE customers SET name = ?, email = ?, cpf = ? WHERE id = ?;";
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, customer.getName());
			ps.setString(2, customer.getEmail());
			ps.setString(3, customer.getCpf());
			ps.setInt(4, customer.getId());
			ps.execute();
			System.out.println("Cliente alterado com sucesso!");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Customers> findAll() {
		
		String sql = "SELECT * FROM customers;";
		List<Customers> customersList = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
			
			while (rs.next()) {
				Customers customer = new Customers(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("email"),
						rs.getString("cpf")
						);
				customersList.add(customer);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return customersList;
	}
	
	public static Customers findById(Integer id) {
		String sql = "SELECT * FROM customers where id = ?;";
		
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);){
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Customers(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("email"),
						rs.getString("cpf")
						);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Customers> findByName(String name) {
		
		String sql = "SELECT * FROM customers WHERE name LIKE ?;";
		List<Customers> customerList = new ArrayList<>();
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setString(1, "%" + name + "%");
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Customers customer = new Customers(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("email"),
						rs.getString("cpf")
						);
						customerList.add(customer);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return customerList;
	}
	
}
