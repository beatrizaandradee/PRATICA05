package pedidos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class ItemPedidoTest {
    private ItemPedido item;

    @BeforeEach
    void setUp() {
        item = new ItemPedido("Caneta", 2.5, 4);
    }

    @AfterEach
    void tearDown(TestInfo info) {
        System.out.println("Teste finalizado: " + info.getDisplayName());
    }

    @Test
    void calculaSubtotal() {
        assertEquals(10.0, item.getSubtotal(), 0.001);
    }

    @Test
    void precoNegativoLancaIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ItemPedido("X", -1.0, 1));
    }

    @Test
    void quantidadeZeroLancaIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ItemPedido("X", 1.0, 0));
    }

    @Test
    void descricaoNulaLancaNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ItemPedido(null, 1.0, 1));
    }
}
