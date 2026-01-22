package store.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import connector.ConnectionFactory;
import store.domain.Orders;

public class OrderRepository {

	public static Integer save(Orders order) {
		String sql = "INSERT INTO orders (customer_id, total_amount, order_date) VALUES (?, ?, ?);";
		
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			ps.setInt(1, order.getCustomerId());
			ps.setBigDecimal(2, order.getTotalAmount());
			LocalDateTime dataGravar = (order.getOrderDate() != null) ? order.getOrderDate() : LocalDateTime.now();
			ps.setTimestamp(3, Timestamp.valueOf(dataGravar));
			ps.execute();
			
			try (ResultSet rs = ps.getGeneratedKeys()){
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Orders findById(Integer id) {
		
		String sql = "SELECT * FROM orders where id = ?;";
		
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);){
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				Orders order = new Orders();
				order.setId((rs.getInt("id")));
				order.setCustomerId(rs.getInt("customer_id"));
				order.setTotalAmount(rs.getBigDecimal("total_amount"));
				
				java.sql.Timestamp ts = rs.getTimestamp("order_date");
				
				if (ts != null) {
					order.setOrderDate(ts.toLocalDateTime());
				}
				return order;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
public static List<Orders> findByCustomerId(Integer customerId) {
		
		String sql = "SELECT * FROM orders WHERE customer_id = ?;";
		List<Orders> ordersList = new ArrayList<>();
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);){
			
			ps.setInt(1, customerId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Orders order = new Orders();
				order.setId((rs.getInt("id")));
				order.setCustomerId(rs.getInt("customer_id"));
				order.setTotalAmount(rs.getBigDecimal("total_amount"));
				
				java.sql.Timestamp ts = rs.getTimestamp("order_date");
				
				if (ts != null) {
					order.setOrderDate(ts.toLocalDateTime());
				}
				ordersList.add(order);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return ordersList;
	}
	
	public static List<Orders> findAll() {
		
		String sql = "SELECT * FROM orders;";
		List<Orders> orderList = new ArrayList<>();
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
			
			while (rs.next()) {
				
				Orders order = new Orders();
				order.setId(rs.getInt("id"));
				order.setCustomerId(rs.getInt("customerId"));
				order.setTotalAmount(rs.getBigDecimal("totalAmount"));
				
				java.sql.Timestamp ts = rs.getTimestamp("order_date");
				
				if (ts != null) {
					order.setOrderDate(ts.toLocalDateTime());
				}
				orderList.add(order);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}
	
}
