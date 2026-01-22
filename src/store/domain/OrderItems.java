package store.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItems {

	private Integer id;
	private Integer orderId;
	private Integer productId;
	private Integer quantity;
	private BigDecimal unitPrice;

	public OrderItems() {
	}

	public OrderItems(Integer orderId, Integer productId, Integer quantity, BigDecimal unitPrice) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnit_price(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItems other = (OrderItems) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "OrderItems [id=" + id + ", order_id=" + orderId + ", product_id=" + productId + ", quantity="
				+ quantity + ", unit_price=" + unitPrice + "]";
	}
	
}
