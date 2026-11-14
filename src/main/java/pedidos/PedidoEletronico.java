package pedidos;

/** Pedido eletrônico: 10% de desconto sobre o subtotal. */
public class PedidoEletronico extends Pedido {
    public static final double DESCONTO = 0.10;

    public PedidoEletronico(int id, String cliente) {
        super(id, cliente);
    }

    @Override
    public double calcularValorFinal() throws PedidoInvalidoException {
        validarItens();
        return calcularSubtotal() * (1 - DESCONTO);
    }
}
