package store.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector.ConnectionFactory;
import store.domain.OrderItems;

public class OrderItemsRepository {

	public static void save(OrderItems item) {

		String sql = "INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?);";

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, item.getOrderId());
			ps.setInt(2, item.getProductId());
			ps.setInt(3, item.getQuantity());
			ps.setBigDecimal(4, item.getUnitPrice());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<OrderItems> findByOrderId(Integer orderId) {
		
		String sql = "SELECT * FROM order_items WHERE order_id = ?;";
		List<OrderItems> items = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ){
			
			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				OrderItems item = new OrderItems();
				
				item.setId(rs.getInt("id"));
				
				item.setOrderId(rs.getInt("order_id"));
				item.setProductId(rs.getInt("product_id"));
				item.setQuantity(rs.getInt("quantity"));
				item.setUnit_price(rs.getBigDecimal("unit_price"));
				
				items.add(item);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
}
