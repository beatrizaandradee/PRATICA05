package pedidos;

/** Lançada quando um pedido não é encontrado no gerenciador. */
public class PedidoNaoEncontradoException extends PedidoException {
    public PedidoNaoEncontradoException(int id) {
        super("Pedido " + id + " não encontrado.");
    }
}
