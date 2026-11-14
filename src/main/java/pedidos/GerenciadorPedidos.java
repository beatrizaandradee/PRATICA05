package pedidos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/** Gerencia a coleção de pedidos e permite percorrê-los com um iterador próprio. */
public class GerenciadorPedidos implements Iterable<Pedido> {
    private final List<Pedido> pedidos = new ArrayList<>();

    public void adicionar(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não pode ser nulo.");
        }
        pedidos.add(pedido);
    }

    public Pedido buscar(int id) throws PedidoNaoEncontradoException {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new PedidoNaoEncontradoException(id);
    }

    public void remover(int id) throws PedidoNaoEncontradoException {
        pedidos.remove(buscar(id));
    }

    public int quantidade() {
        return pedidos.size();
    }

    /** Processa (avança o status de) um pedido pelo id. */
    public void processar(int id) throws PedidoException {
        buscar(id).processar();
    }

    @Override
    public Iterator<Pedido> iterator() {
        return new Iterator<>() {
            private int posicao = 0;

            @Override
            public boolean hasNext() {
                return posicao < pedidos.size();
            }

            @Override
            public Pedido next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Não há mais pedidos.");
                }
                return pedidos.get(posicao++);
            }
        };
    }
}
