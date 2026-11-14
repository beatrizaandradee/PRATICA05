package pedidos;

import java.util.Objects;

/** Item de um pedido. */
public class ItemPedido {
    private final String descricao;
    private final double precoUnitario;
    private final int quantidade;

    public ItemPedido(String descricao, double precoUnitario, int quantidade) {
        this.descricao = Objects.requireNonNull(descricao, "descrição não pode ser nula");
        if (precoUnitario < 0) {
            throw new IllegalArgumentException("Preço unitário não pode ser negativo.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public String getDescricao() { return descricao; }
    public double getPrecoUnitario() { return precoUnitario; }
    public int getQuantidade() { return quantidade; }

    public double getSubtotal() {
        return precoUnitario * quantidade;
    }
}
