package store.main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import store.domain.Customers;
import store.domain.OrderItems;
import store.domain.Orders;
import store.domain.Products;
import store.repository.CustomersRepository;
import store.repository.ProductsRepository;
import store.services.CustomerService;
import store.services.OrderService;
import store.services.ProductService;

public class MenuLoja {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		boolean sistemaRodando = true;

		while (sistemaRodando) {

			System.out.println("1. Clientes");
			System.out.println("2. Produtos");
			System.out.println("0. Sair");
			System.out.print("Escolha uma das opções: ");

			int opcao = lerInteiro();

			switch (opcao) {
			case 1:
				menuClientes();
				break;
			case 2:
				menuProdutos();
				break;
			case 0:
				sistemaRodando = false;
				System.out.println("Saindo do sistema...");
				break;
			default:
				System.out.println("Opção Inválida!");
			}

		}

	}

	public static void menuClientes() {

		boolean noMenuClientes = true;

		while (noMenuClientes) {
			System.out.println("");
			System.out.println("1. Cadastrar Cliente");
			System.out.println("2. Listar todos os Clientes");
			System.out.println("3. Comprar um produto");
			System.out.println("4. Listar Histórico de Compras do Cliente");
			System.out.println("5. Atualizar Cliente");
			System.out.println("6. Deletar Cliente");
			System.out.println("7. Voltar ao Menu Principal");
			System.out.println("8. Sair do Sistema");
			System.out.print("Opção: ");

			int opcao = lerInteiro();

			switch (opcao) {
			case 1:
				cadastrarCliente();
				break;
			case 2:
				listarClientes();
				break;
			case 3:
				comprarProduto();
				break;
			case 4:
				verHistoricoDeCompras();
				break;
			case 5:
				atualizarCliente();
				break;
			case 6:
				deletarCliente();
				break;
			case 7:
				noMenuClientes = false;
				break;
			case 8:
				System.out.println("Saindo do sistema...");
				System.exit(0);
				break;
			default:
				System.out.println("Opção inválida!");
			}

		}

	}

	public static void menuProdutos() {

		boolean noMenuProdutos = true;

		while (noMenuProdutos) {
			System.out.println("1. Cadastrar Produto");
			System.out.println("2. Listar todos os Produtos");
			System.out.println("3. Voltar");
			System.out.print("Opção: ");

			int opcao = lerInteiro();

			switch (opcao) {
			case 1:
				System.out.print("Nome do Produto: ");
				String nome = scanner.nextLine();
				System.out.print("Preço: ");
				BigDecimal preco = new BigDecimal(scanner.nextLine().replace(",", "."));
				System.out.print("Quantidade: ");
				Integer qtd = lerInteiro();
				ProductService.save(new Products(nome, preco, qtd));
				break;
			case 2:
				ProductService.findAll().forEach(System.out::println);
				break;
			case 3:
				noMenuProdutos = false;
				break;
			default:
				System.out.println("Opção inválida!");
			}

		}

	}

	public static void cadastrarCliente() {

		System.out.print("Nome: ");
		String nome = scanner.nextLine();
		System.out.print("Email: ");
		String email = scanner.nextLine();
		System.out.print("CPF: ");
		String cpf = scanner.nextLine();

		Customers c = new Customers(null, nome, email, cpf);
		CustomersRepository.save(c);

	}

	public static int lerInteiro() {

		try {
			return Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			return -1;
		}

	}

	public static void listarClientes() {

		List<Customers> lista = CustomerService.findAll();

		if (lista.isEmpty())
			System.out.println("Vazio.");

		for (Customers c : lista) {
			System.out.printf("ID %d | Nome: %s | Email: %s | CPF: %s\n", c.getId(), c.getName(), c.getEmail(),
					c.getCpf());
		}
	}

	public static void comprarProduto() {

		System.out.println("Digite o ID do Cliente: ");
		Integer idCliente = lerInteiro();

		if (CustomerService.findById(idCliente) == null) {
			System.out.println("Cliente não encontrado! Venda cancelada.");
			return;
		}

		List<OrderItems> carrinho = new ArrayList<>();
		boolean adicionando = true;

		while (adicionando) {

			System.out.println("Adicionar Produto ao Carrinho");

			List<Products> produtos = ProductService.findAll();

			for (Products p : produtos) {
				System.out.printf("ID: %d | %s | R$ %.2f\n", p.getId(), p.getName(), p.getPrice());
			}

			System.out.print("Digite o ID do produto: ");
			Integer idProd = lerInteiro();
			System.out.print("Digite a quantidade: ");
			Integer qtd = lerInteiro();

			OrderItems item = new OrderItems();
			item.setProductId(idProd);
			item.setQuantity(qtd);

			carrinho.add(item);
			System.out.println("Item adicionado ao carrinho!");
			System.out.print("Adicionar mais produtos? (1-Sim / 0 - Não): ");
			if (lerInteiro() != -1) {
				adicionando = false;
			}
		}

		if (!carrinho.isEmpty()) {
			System.out.println("Fechando pedido...");

			Orders novoPedido = new Orders(idCliente);
			OrderService.placeOrder(novoPedido, carrinho);
		} else {
			System.out.println("Venda cancelada (Carrinho vazio).");
		}
	}

	public static void verHistoricoDeCompras() {

		System.out.print("Digite o ID do Cliente: ");
		Integer idCliente = lerInteiro();

		List<Orders> pedidos = OrderService.findByCustomerId(idCliente);
		System.out.println("");
		if (pedidos.isEmpty()) {
			System.out.println("Esse cliente ainda não realizou compras.");
			return;
		}

		for (Orders pedido : pedidos) {
			System.out.println("--------------------");
			System.out.printf("PEDIDO #%d | Data %s | Total: R$ %.2f\n", pedido.getId(), pedido.getOrderDate(),
					pedido.getTotalAmount());
			System.out.println("    Itens deste pedido:");

			List<OrderItems> itens = OrderService.findItemsByOrderId(pedido.getId());

			for (OrderItems item : itens) {
				Products prod = ProductService.findById(item.getProductId());
				String nomeProduto = (prod != null) ? prod.getName() : "Produto Excluído";

				System.out.printf("   -> %dx %s (R$ %.2f un.)\n", item.getQuantity(), nomeProduto, item.getUnitPrice());
			}

		}
		System.out.println("------------------------------------");
	}

	public static void atualizarCliente() {
		listarClientes();
		System.out.print("\nDigite o ID do cliente para alterar: ");
		Integer id = lerInteiro();

		Customers clienteExistente = CustomerService.findById(id);

		if (clienteExistente != null) {

			System.out.println("Alterando dados de: " + clienteExistente.getName());
			System.out.print("Novo Nome: ");
			String novoNome = scanner.nextLine();

			System.out.print("Novo Email: ");
			String novoEmail = scanner.nextLine();

			System.out.print("Novo CPF: ");
			String novoCpf = scanner.nextLine();

			clienteExistente.setName(novoNome);
			clienteExistente.setEmail(novoEmail);
			clienteExistente.setCpf(novoCpf);

			CustomersRepository.update(clienteExistente);

		} else {
			System.out.println("Cliente não encontrado.");
		}

	}

	public static void deletarCliente() {

		System.out.print("\nDigite o ID do cliente para DELETAR: ");
		Integer id = lerInteiro();
		Customers c = CustomerService.findById(id);

		if (c != null) {
			CustomersRepository.delete(id);
		} else {
			System.out.println("Cliente não encontrado.");
		}

	}

}
