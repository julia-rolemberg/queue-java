import java.util.Objects;

public class Reserva implements Pagamento{

    private Cliente cliente;
    private boolean pagamentoAVista;

    public Reserva(Cliente cliente, boolean pagamentoAVista){
        this.cliente = cliente;
        this.pagamentoAVista = pagamentoAVista;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public String toString() {
        String tipoCliente= null;
        String tipoPag;
        if(cliente instanceof PessoaFisica){
            tipoCliente = "PF";
        }else if(cliente instanceof PessoaJuridica){
            tipoCliente = "PJ";
        }

        if(pagamentoAVista == false){ // falso
            tipoPag = "parcelado";
        }else{
            tipoPag = "à vista";
        }

        return "Reserva{" +
                tipoCliente+" - " +
                "cliente=" + cliente.getNome() +
                ", pagamento=" + tipoPag +
                '}';
    }


    @Override
    public double calcularPagamento() {
        if (this.pagamentoAVista == true) {
            return 3200 * 0.9;
        }
        return 3200;
    }
}
