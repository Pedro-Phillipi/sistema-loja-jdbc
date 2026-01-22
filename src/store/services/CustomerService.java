package store.services;

import java.util.List;

import store.domain.Customers;
import store.repository.CustomersRepository;

public class CustomerService {

	public static void save(Customers customer) {
		CustomersRepository.save(customer);
	}
	
	public static void delete(Integer id) {
		CustomersRepository.delete(id);
	}
	
	public static void update(Customers customer) {
		CustomersRepository.update(customer);
	}
	
	public static List<Customers> findAll() {
		return CustomersRepository.findAll();
	}
	
	public static Customers findById(Integer id) {
		
		if (id == null || id <= 0) {
			return null;
		}
		return CustomersRepository.findById(id);
	}
	
	public static List<Customers> findByName(String name) {
		
		if (name == null) {
			return List.of();
		}
		return CustomersRepository.findByName(name);
	}
	
}
