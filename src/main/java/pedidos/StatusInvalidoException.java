package pedidos;

/** Lançada quando se tenta uma mudança de status não permitida. */
public class StatusInvalidoException extends PedidoException {
    public StatusInvalidoException(String mensagem) {
        super(mensagem);
    }
}
