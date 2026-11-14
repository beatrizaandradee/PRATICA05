package pedidos;

/** Pedido de assinatura: valor mensal com 15% de desconto, multiplicado pelos meses contratados. */
public class PedidoAssinatura extends Pedido {
    public static final double DESCONTO = 0.15;

    private final int meses;

    public PedidoAssinatura(int id, String cliente, int meses) {
        super(id, cliente);
        if (meses <= 0) {
            throw new IllegalArgumentException("Número de meses deve ser maior que zero.");
        }
        this.meses = meses;
    }

    public int getMeses() { return meses; }

    @Override
    public double calcularValorFinal() throws PedidoInvalidoException {
        validarItens();
        return calcularSubtotal() * (1 - DESCONTO) * meses;
    }
}
