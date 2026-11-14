package pedidos;

/** Lançada quando um pedido não atende às regras (ex.: sem itens). */
public class PedidoInvalidoException extends PedidoException {
    public PedidoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
