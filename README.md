# Prática 5 - Testes Automatizados com JUnit (Sistema de Pedidos)

Projeto Maven (Java 17+, JUnit 5.10).

- Executar os testes: `mvn test`
- Executar o menu: classe `pedidos.Main`
- Sem Maven (NetBeans/Eclipse/IntelliJ): adicione `junit-jupiter-api` e `junit-jupiter-engine` em Libraries.

Classes de domínio: Pedido (abstrata), PedidoFisico, PedidoEletronico, PedidoAssinatura,
ItemPedido, StatusPedido (enum), GerenciadorPedidos. Exceções: PedidoException,
PedidoInvalidoException, StatusInvalidoException, PedidoNaoEncontradoException.
