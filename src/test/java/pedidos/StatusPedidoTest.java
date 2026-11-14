package pedidos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class StatusPedidoTest {
    private StatusPedido status;

    @BeforeEach
    void setUp() {
        status = StatusPedido.CRIADO;
    }

    @AfterEach
    void tearDown(TestInfo info) {
        System.out.println("Teste finalizado: " + info.getDisplayName());
    }

    @Test
    void fluxoNormalDeStatus() throws Exception {
        assertEquals(StatusPedido.PROCESSANDO, status.proximo());
        assertEquals(StatusPedido.ENVIADO, StatusPedido.PROCESSANDO.proximo());
        assertEquals(StatusPedido.ENTREGUE, StatusPedido.ENVIADO.proximo());
    }

    @Test
    void entregueEhStatusFinalENaoAvanca() {
        assertTrue(StatusPedido.ENTREGUE.isFinal());
        assertThrows(StatusInvalidoException.class, StatusPedido.ENTREGUE::proximo);
    }

    @Test
    void canceladoEhStatusFinalENaoAvanca() {
        assertTrue(StatusPedido.CANCELADO.isFinal());
        assertThrows(StatusInvalidoException.class, StatusPedido.CANCELADO::proximo);
    }

    @Test
    void statusNaoFinais() {
        assertFalse(status.isFinal());
        assertFalse(StatusPedido.ENVIADO.isFinal());
    }

    @Test
    void regraDeCancelamento() {
        assertTrue(StatusPedido.CRIADO.podeCancelar());
        assertTrue(StatusPedido.PROCESSANDO.podeCancelar());
        assertFalse(StatusPedido.ENVIADO.podeCancelar());
        assertFalse(StatusPedido.ENTREGUE.podeCancelar());
    }
}
