package store.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Orders {

	private Integer id;
	private Integer customerId;
	private LocalDateTime orderDate;
	private BigDecimal totalAmount;
	
	public Orders() {
	}

	public Orders(Integer customerId, BigDecimal totalAmount) {
		this.customerId = customerId;
		this.totalAmount = totalAmount;
	}
	
	public Orders(Integer customerId) {
		this.customerId = customerId;
		this.totalAmount = BigDecimal.ZERO;
		this.orderDate = LocalDateTime.now();
	}
	
	public Orders(Integer id, Integer customerId, BigDecimal totalAmount, LocalDateTime orderDate) {
        this.id = id;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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
		Orders other = (Orders) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Orders [id=" + id + ", customer_id=" + customerId + ", orderDate=" + orderDate + ", totalAmount="
				+ totalAmount + "]";
	}
	
}
