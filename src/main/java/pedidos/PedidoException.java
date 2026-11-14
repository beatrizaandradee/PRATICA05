package pedidos;

/** Exceção base (personalizada) do sistema de pedidos. */
public class PedidoException extends Exception {
    public PedidoException(String mensagem) {
        super(mensagem);
    }
}
