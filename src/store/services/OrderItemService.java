package store.services;

import java.util.List;

import store.domain.OrderItems;
import store.repository.OrderItemsRepository;

public class OrderItemService {

	public static void save(OrderItems item) {
		OrderItemsRepository.save(item);
	}
	
	public static List<OrderItems> findByOrderId(Integer orderId) {
		return OrderItemsRepository.findByOrderId(orderId);
	}
	
}
