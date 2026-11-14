package pedidos;

import java.util.Scanner;

/** Apenas interface com o usuário (menu). Nenhuma regra de negócio ou teste aqui. */
public class Main {
    public static void main(String[] args) {
        GerenciadorPedidos gerenciador = new GerenciadorPedidos();
        Scanner sc = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n1 - Novo pedido eletrônico");
            System.out.println("2 - Novo pedido físico");
            System.out.println("3 - Novo pedido de assinatura");
            System.out.println("4 - Processar pedido");
            System.out.println("5 - Listar pedidos");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(sc.nextLine().trim());
            try {
                switch (opcao) {
                    case 1: gerenciador.adicionar(lerItens(sc, new PedidoEletronico(lerInt(sc, "Id: "), lerTexto(sc, "Cliente: ")))); break;
                    case 2: gerenciador.adicionar(lerItens(sc, new PedidoFisico(lerInt(sc, "Id: "), lerTexto(sc, "Cliente: "), lerDouble(sc, "Frete: ")))); break;
                    case 3: gerenciador.adicionar(lerItens(sc, new PedidoAssinatura(lerInt(sc, "Id: "), lerTexto(sc, "Cliente: "), lerInt(sc, "Meses: ")))); break;
                    case 4: gerenciador.processar(lerInt(sc, "Id do pedido: ")); System.out.println("Pedido processado."); break;
                    case 5: listar(gerenciador); break;
                    case 0: System.out.println("Encerrando..."); break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (PedidoException | IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static Pedido lerItens(Scanner sc, Pedido pedido) throws PedidoException {
        int n = lerInt(sc, "Quantidade de itens: ");
        for (int i = 1; i <= n; i++) {
            pedido.adicionarItem(new ItemPedido(lerTexto(sc, "Item " + i + " - descrição: "),
                    lerDouble(sc, "Preço unitário: "), lerInt(sc, "Quantidade: ")));
        }
        return pedido;
    }

    private static void listar(GerenciadorPedidos g) {
        for (Pedido p : g) {
            try {
                System.out.printf("#%d %s [%s] - R$ %.2f%n", p.getId(), p.getCliente(), p.getStatus(), p.calcularValorFinal());
            } catch (PedidoInvalidoException e) {
                System.out.printf("#%d %s [%s] - %s%n", p.getId(), p.getCliente(), p.getStatus(), e.getMessage());
            }
        }
    }

    private static String lerTexto(Scanner sc, String msg) { System.out.print(msg); return sc.nextLine(); }
    private static int lerInt(Scanner sc, String msg) { return Integer.parseInt(lerTexto(sc, msg).trim()); }
    private static double lerDouble(Scanner sc, String msg) { return Double.parseDouble(lerTexto(sc, msg).trim().replace(',', '.')); }
}
