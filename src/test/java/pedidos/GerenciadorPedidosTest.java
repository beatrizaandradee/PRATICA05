package pedidos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class GerenciadorPedidosTest {
    private GerenciadorPedidos gerenciador;
    private PedidoEletronico eletronico;
    private PedidoFisico fisico;
    private PedidoAssinatura assinatura;

    @BeforeEach
    void setUp() throws Exception {
        gerenciador = new GerenciadorPedidos();
        eletronico = new PedidoEletronico(1, "Maria");
        fisico = new PedidoFisico(2, "Ana", 15.0);
        assinatura = new PedidoAssinatura(3, "Lia", 2);
        eletronico.adicionarItem(new ItemPedido("Fone", 100.0, 1));
        fisico.adicionarItem(new ItemPedido("Livro", 30.0, 1));
        assinatura.adicionarItem(new ItemPedido("Plano", 50.0, 1));
        gerenciador.adicionar(eletronico);
        gerenciador.adicionar(fisico);
        gerenciador.adicionar(assinatura);
    }

    @AfterEach
    void tearDown(TestInfo info) {
        System.out.println("Teste finalizado: " + info.getDisplayName());
    }

    @Test
    void buscaPedidoExistente() throws Exception {
        assertSame(fisico, gerenciador.buscar(2));
    }

    @Test
    void buscaPedidoInexistenteLancaPedidoNaoEncontradoException() {
        assertThrows(PedidoNaoEncontradoException.class, () -> gerenciador.buscar(99));
    }

    @Test
    void removePedido() throws Exception {
        gerenciador.remover(1);
        assertEquals(2, gerenciador.quantidade());
        assertThrows(PedidoNaoEncontradoException.class, () -> gerenciador.buscar(1));
    }

    @Test
    void adicionarNuloLancaIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> gerenciador.adicionar(null));
    }

    @Test
    void processarAlteraStatusDoPedido() throws Exception {
        gerenciador.processar(3);
        assertEquals(StatusPedido.PROCESSANDO, assinatura.getStatus());
    }

    @Test
    void processarPedidoEntregueLancaStatusInvalidoException() throws Exception {
        gerenciador.processar(1);
        gerenciador.processar(1);
        gerenciador.processar(1);
        assertThrows(StatusInvalidoException.class, () -> gerenciador.processar(1));
    }

    @Test
    void iteradorPercorreTodosOsPedidosEmOrdem() {
        Iterator<Pedido> it = gerenciador.iterator();
        assertSame(eletronico, it.next());
        assertSame(fisico, it.next());
        assertSame(assinatura, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void iteradorSemMaisElementosLancaNoSuchElementException() {
        Iterator<Pedido> it = gerenciador.iterator();
        it.next();
        it.next();
        it.next();
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    void valorFinalDeTodosOsTiposDePedido() throws Exception {
        double total = 0;
        for (Pedido p : gerenciador) {
            total += p.calcularValorFinal();
        }
        // 90 (eletrônico) + 45 (físico: 30 + 15 frete) + 85 (assinatura: 50*0,85*2)
        assertEquals(220.0, total, 0.001);
    }
}
