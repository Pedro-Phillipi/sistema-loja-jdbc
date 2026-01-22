package store.services;

import java.math.BigDecimal;
import java.util.List;

import store.domain.OrderItems;
import store.domain.Orders;
import store.domain.Products;
import store.repository.OrderItemsRepository;
import store.repository.OrderRepository;
import store.repository.ProductsRepository;

public class OrderService {

	public static void placeOrder(Orders order, List<OrderItems> items) {

		if (items.isEmpty()) {
			System.out.println("Erro: Não é possível salvar um pedido sem itens.");
			return;
		}
		
		BigDecimal totalCalculado = BigDecimal.ZERO;

		for (OrderItems item : items) {
			Products product = ProductsRepository.findById(item.getProductId());

			if (product != null) {
				item.setUnit_price(product.getPrice());
				BigDecimal subtotal = item.getUnitPrice().multiply(new BigDecimal(item.getQuantity()));
				totalCalculado = totalCalculado.add(subtotal);

			} else {
				System.out.println("Produto ID: " + item.getProductId() + " não encontrado!");
				return;
			}
		}

		order.setTotalAmount(totalCalculado);

		Integer orderId = OrderRepository.save(order);
		
		if (orderId != null) {
			System.out.println("Pedido: " +orderId + " gerado com sucesso! Gravando itens...");
			for (OrderItems item : items) {
				item.setOrderId(orderId);
				OrderItemsRepository.save(item);
			}
			System.out.println("Venda concluída! ID do Pedido: " +orderId + " | Total: R$ " +totalCalculado);
		}
		
	}
	
	public static List<Orders> findAll() {
		return OrderRepository.findAll();
	}

	public static Orders findById(Integer id) {
		return OrderRepository.findById(id);
	}
	
	public static List<Orders> findByCustomerId(Integer customerId) {
		return OrderRepository.findByCustomerId(customerId);
	}
	
	public static List<OrderItems> findItemsByOrderId(Integer orderId) {
		return OrderItemsRepository.findByOrderId(orderId);
	}
	
}
