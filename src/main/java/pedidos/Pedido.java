package pedidos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** Classe abstrata base para todos os tipos de pedido. */
public abstract class Pedido {
    private final int id;
    private final String cliente;
    private final List<ItemPedido> itens = new ArrayList<>();
    private StatusPedido status = StatusPedido.CRIADO;

    protected Pedido(int id, String cliente) {
        this.id = id;
        this.cliente = Objects.requireNonNull(cliente, "cliente não pode ser nulo");
    }

    public int getId() { return id; }
    public String getCliente() { return cliente; }
    public StatusPedido getStatus() { return status; }

    /** Lista não modificável dos itens. */
    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public void adicionarItem(ItemPedido item) throws StatusInvalidoException {
        Objects.requireNonNull(item, "item não pode ser nulo");
        if (status.isFinal()) {
            throw new StatusInvalidoException(
                    "Pedido com status " + status + " não pode ser alterado.");
        }
        itens.add(item);
    }

    public double calcularSubtotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    /** Cada tipo de pedido define sua regra de cálculo do valor final. */
    public abstract double calcularValorFinal() throws PedidoInvalidoException;

    protected void validarItens() throws PedidoInvalidoException {
        if (itens.isEmpty()) {
            throw new PedidoInvalidoException("Pedido " + id + " não possui itens.");
        }
    }

    /** Avança o pedido para o próximo status do fluxo. */
    public void processar() throws PedidoException {
        if (status == StatusPedido.CRIADO) {
            validarItens();
        }
        status = status.proximo();
    }

    /** Cancela o pedido, se ainda for possível. */
    public void cancelar() throws StatusInvalidoException {
        if (!status.podeCancelar()) {
            throw new StatusInvalidoException(
                    "Pedido com status " + status + " não pode ser cancelado.");
        }
        status = StatusPedido.CANCELADO;
    }
}
