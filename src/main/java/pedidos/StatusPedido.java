package pedidos;

/**
 * Status possíveis de um pedido.
 * Regra de negócio: pedidos ENTREGUE (ou CANCELADO) não podem mais ser alterados.
 */
public enum StatusPedido {
    CRIADO, PROCESSANDO, ENVIADO, ENTREGUE, CANCELADO;

    /** Indica se o status é final, ou seja, o pedido não pode mais ser alterado. */
    public boolean isFinal() {
        return this == ENTREGUE || this == CANCELADO;
    }

    /** Próximo status do fluxo normal (CRIADO -> PROCESSANDO -> ENVIADO -> ENTREGUE). */
    public StatusPedido proximo() throws StatusInvalidoException {
        switch (this) {
            case CRIADO:     return PROCESSANDO;
            case PROCESSANDO: return ENVIADO;
            case ENVIADO:    return ENTREGUE;
            default:
                throw new StatusInvalidoException(
                        "Pedido com status " + this + " não pode ser alterado.");
        }
    }

    /** Cancelamento só é permitido enquanto o pedido ainda não foi enviado. */
    public boolean podeCancelar() {
        return this == CRIADO || this == PROCESSANDO;
    }
}
