package store.services;

import java.util.List;

import store.domain.Products;
import store.repository.ProductsRepository;

public class ProductService {

	public static void save(Products product) {
		ProductsRepository.save(product);
	}
	
	public static void delete(Integer id) {
		ProductsRepository.delete(id);
	}
	
	public static void update(Products product) {
		ProductsRepository.update(product);
	}
	
	public static Products findById(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("ID inválido para busca.");
		}
		return ProductsRepository.findById(id);
	}
	
	public static List<Products> findAll() {
		return ProductsRepository.findAll();
	}
	
	public static List<Products> findByName(String name) {
		
		if (name == null) {
			return List.of();
		}
		return ProductsRepository.findByName(name);
	}
	
}
