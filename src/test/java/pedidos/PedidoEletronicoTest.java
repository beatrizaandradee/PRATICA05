package pedidos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class PedidoEletronicoTest {
    private PedidoEletronico pedido;

    @BeforeEach
    void setUp() throws Exception {
        pedido = new PedidoEletronico(1, "Maria");
        pedido.adicionarItem(new ItemPedido("Mouse", 50.0, 2));      // 100
        pedido.adicionarItem(new ItemPedido("Teclado", 100.0, 1));   // 100
    }

    @AfterEach
    void tearDown(TestInfo info) {
        System.out.println("Teste finalizado: " + info.getDisplayName());
    }

    @Test
    void calculaValorFinalComDesconto() throws Exception {
        assertEquals(180.0, pedido.calcularValorFinal(), 0.001);
    }

    @Test
    void semItensLancaPedidoInvalidoException() {
        PedidoEletronico vazio = new PedidoEletronico(2, "Joao");
        assertThrows(PedidoInvalidoException.class, vazio::calcularValorFinal);
    }

    @Test
    void clienteNuloLancaNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PedidoEletronico(3, null));
    }

    @Test
    void itemNuloLancaNullPointerException() {
        assertThrows(NullPointerException.class, () -> pedido.adicionarItem(null));
    }

    @Test
    void listaDeItensNaoPodeSerModificada() {
        assertThrows(UnsupportedOperationException.class,
                () -> pedido.getItens().add(new ItemPedido("X", 1.0, 1)));
    }

    @Test
    void processarAlteraStatusEmSequencia() throws Exception {
        assertEquals(StatusPedido.CRIADO, pedido.getStatus());
        pedido.processar();
        assertEquals(StatusPedido.PROCESSANDO, pedido.getStatus());
        pedido.processar();
        assertEquals(StatusPedido.ENVIADO, pedido.getStatus());
        pedido.processar();
        assertEquals(StatusPedido.ENTREGUE, pedido.getStatus());
    }

    @Test
    void processarSemItensLancaPedidoInvalidoException() {
        PedidoEletronico vazio = new PedidoEletronico(2, "Joao");
        assertThrows(PedidoInvalidoException.class, vazio::processar);
        assertEquals(StatusPedido.CRIADO, vazio.getStatus());
    }

    @Test
    void pedidoEntregueNaoPodeSerAlterado() throws Exception {
        pedido.processar();
        pedido.processar();
        pedido.processar();
        assertEquals(StatusPedido.ENTREGUE, pedido.getStatus());
        assertThrows(StatusInvalidoException.class, pedido::processar);
        assertThrows(StatusInvalidoException.class, pedido::cancelar);
        assertThrows(StatusInvalidoException.class,
                () -> pedido.adicionarItem(new ItemPedido("Cabo", 10.0, 1)));
        assertEquals(StatusPedido.ENTREGUE, pedido.getStatus());
    }

    @Test
    void cancelarPedidoCriado() throws Exception {
        pedido.cancelar();
        assertEquals(StatusPedido.CANCELADO, pedido.getStatus());
    }

    @Test
    void naoCancelaPedidoEnviado() throws Exception {
        pedido.processar();
        pedido.processar();
        assertThrows(StatusInvalidoException.class, pedido::cancelar);
    }
}
