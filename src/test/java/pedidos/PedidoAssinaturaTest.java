package pedidos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class PedidoAssinaturaTest {
    private PedidoAssinatura pedido;

    @BeforeEach
    void setUp() throws Exception {
        pedido = new PedidoAssinatura(20, "Lia", 3);
        pedido.adicionarItem(new ItemPedido("Plano mensal", 100.0, 1));
    }

    @AfterEach
    void tearDown(TestInfo info) {
        System.out.println("Teste finalizado: " + info.getDisplayName());
    }

    @Test
    void valorFinalComDescontoEMeses() throws Exception {
        assertEquals(255.0, pedido.calcularValorFinal(), 0.001); // 100 * 0,85 * 3
    }

    @Test
    void semItensLancaPedidoInvalidoException() {
        PedidoAssinatura vazio = new PedidoAssinatura(21, "Teo", 1);
        assertThrows(PedidoInvalidoException.class, vazio::calcularValorFinal);
    }

    @Test
    void mesesInvalidosLancaIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new PedidoAssinatura(22, "Teo", 0));
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
        assertThrows(StatusInvalidoException.class, pedido::processar);
    }
}
