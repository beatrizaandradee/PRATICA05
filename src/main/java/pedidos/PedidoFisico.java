package pedidos;

/** Pedido físico: valor final = subtotal + frete (frete grátis a partir de R$ 200,00). */
public class PedidoFisico extends Pedido {
    public static final double LIMITE_FRETE_GRATIS = 200.0;

    private final double frete;

    public PedidoFisico(int id, String cliente, double frete) {
        super(id, cliente);
        if (frete < 0) {
            throw new IllegalArgumentException("Frete não pode ser negativo.");
        }
        this.frete = frete;
    }

    public double getFrete() { return frete; }

    @Override
    public double calcularValorFinal() throws PedidoInvalidoException {
        validarItens();
        double subtotal = calcularSubtotal();
        return subtotal >= LIMITE_FRETE_GRATIS ? subtotal : subtotal + frete;
    }
}
