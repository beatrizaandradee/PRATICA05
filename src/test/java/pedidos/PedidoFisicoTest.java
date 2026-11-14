package pedidos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class PedidoFisicoTest {
    private PedidoFisico pedido;

    @BeforeEach
    void setUp() throws Exception {
        pedido = new PedidoFisico(10, "Ana", 20.0);
        pedido.adicionarItem(new ItemPedido("Livro", 40.0, 2)); // 80
    }

    @AfterEach
    void tearDown(TestInfo info) {
        System.out.println("Teste finalizado: " + info.getDisplayName());
    }

    @Test
    void valorFinalIncluiFrete() throws Exception {
        assertEquals(100.0, pedido.calcularValorFinal(), 0.001);
    }

    @Test
    void freteGratisAPartirDoLimite() throws Exception {
        pedido.adicionarItem(new ItemPedido("Caderno", 120.0, 1)); // subtotal 200
        assertEquals(200.0, pedido.calcularValorFinal(), 0.001);
    }

    @Test
    void semItensLancaPedidoInvalidoException() {
        PedidoFisico vazio = new PedidoFisico(11, "Bia", 10.0);
        assertThrows(PedidoInvalidoException.class, vazio::calcularValorFinal);
    }

    @Test
    void freteNegativoLancaIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new PedidoFisico(12, "Caio", -1.0));
    }

    @Test
    void processarAlteraStatus() throws Exception {
        pedido.processar();
        assertEquals(StatusPedido.PROCESSANDO, pedido.getStatus());
    }

    @Test
    void pedidoEntregueNaoPodeSerAlterado() throws Exception {
        pedido.processar();
        pedido.processar();
        pedido.processar();
        StatusInvalidoException e = assertThrows(StatusInvalidoException.class, pedido::processar);
        assertTrue(e.getMessage().contains("ENTREGUE"));
    }
}
