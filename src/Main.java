import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        List<Reserva> reservas = new ArrayList<>(); //maximo 5 reservas
        Fila<Reserva> listaEspera = new Fila<>();//fila de Espera

        Scanner sc = new Scanner(System.in);
        boolean running = true;
        int opcao=0;
        while (running) {
            System.out.println();
            printMenu();
            System.out.print("> ");

            try {
                opcao = sc.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Você deve informar um número");
                opcao = 0;
            }
            sc.nextLine();

            switch (opcao) {
                case 1:

                    Reserva reserva = reservarMesa();
                    if(reservas.size()>=5){
                        System.out.println("Lista de espera");
                        listaEspera.enfileirar(reserva);
                    }else{
                        reservas.add(reserva);
                    }
                    break;
                case 2:
                    pesquisarReserva(reservas, listaEspera);
                    break;
                case 3:
                    imprimir(reservas);
                    break;
                case 4:
                    listaEspera.imprimir();
                    break;
                case 5:
                    cancelarReserva(reservas, listaEspera);
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida");
            }

        }
        System.out.println("Finalizado.");


    }
    public static void printMenu() {
        System.out.println("--- Restaurante SABOR SOFISTICADO ---");
        System.out.println("1. Reservar mesa");
        System.out.println("2. Pesquisar reserva");
        System.out.println("3. Imprimir reservas");
        System.out.println("4. Imprimir lista de espera");
        System.out.println("5. Cancelar reserva");
        System.out.println("6. Finalizar");
    }

    public static Reserva reservarMesa(){
        Scanner sc = new Scanner(System.in);
        Cliente cliente = null;

        System.out.print("nome: ");
        String nome = sc.nextLine();

        String tipoPessoa = inputTipoPessoa();
        switch (tipoPessoa) {
            case "j":
                System.out.print("cnpj: ");
                String cnpj = sc.nextLine();
                cliente = new PessoaJuridica(nome, cnpj);
                break;
            case "f":
                System.out.print("cpf: ");
                String cpf = sc.nextLine();
                cliente = new PessoaFisica(nome, cpf);
                break;
        }
        boolean formaPagamento = inputFormaPagamento();

        return new Reserva(cliente, formaPagamento);

    }

    public static void pesquisarReserva(List<Reserva> reservas, Fila<Reserva> listaEspera){
        Scanner sc = new Scanner(System.in);
        System.out.print("informe seu id(cpf ou cnpj): ");
        String id = sc.nextLine();

        int index = buscar(reservas, id);

        if(index != -1){
            System.out.println("Possui reserva");
        }else {
            index = buscarEspera(listaEspera, id);
            if(index!= -1){
                System.out.println("Posição na lista de espera: "+(index+1));
            }else{
                System.out.println("Não possui reserva");
            }
        }
    }

    public static int buscarEspera(Fila<Reserva> listaEspera, String id){

        Cliente cliente = null;
        int index = -1; //não encontrado

        for(int i = 0; i< listaEspera.size(); i++){
            cliente = listaEspera.get(i).info.getCliente();
            if(cliente instanceof PessoaFisica) {
                PessoaFisica pf = (PessoaFisica) cliente;
                if(pf.getCpf().equals(id)) {
                    index = i;
                }
            }else if(cliente instanceof PessoaJuridica){
                PessoaJuridica pj = (PessoaJuridica) cliente;
                if(pj.getCnpj().equals(id)){
                    index= i;
                }
            }
        }
        return index;
    }



    public static int buscar(List<Reserva> reservas, String id){
        Cliente cliente = null;
        int index = -1; //não encontrado
        for(int i = 0; i< reservas.size(); i++){
            cliente = reservas.get(i).getCliente();
            if(cliente instanceof PessoaFisica) {
                PessoaFisica pf = (PessoaFisica) cliente;
                if(pf.getCpf().equals(id)) {
                    index = i;
                }
            }else if(cliente instanceof PessoaJuridica){
                PessoaJuridica pj = (PessoaJuridica) cliente;
                if(pj.getCnpj().equals(id)){
                    index= i;
                }
            }
        }
        return index;
    }

    public static void cancelarReserva(List<Reserva> reservas, Fila<Reserva> listaEspera){
        Scanner sc = new Scanner(System.in);
        System.out.print("informe seu id(cpf ou cnpj): ");
        String id = sc.nextLine();

        int index = buscar(reservas, id);
        if(index != -1){
            reservas.remove(reservas.get(index));
            reservas.add(listaEspera.desinfileirar().info);
        }else{
            System.out.println("Reserva não encontrada");
        }
    }

    public static void imprimir(List<Reserva> reservas){

        for(int i = 0; i < reservas.size(); i++){
            System.out.println(reservas.get(i));
        }

    }

    public static void imprimir(List<Reserva> reservas, int inicio){ //lista de espera
        System.out.println("--lista de espera--");
        for(int i = inicio; i < reservas.size(); i++){
            System.out.println(reservas.get(i) + " - posição:"+(i+1));
        }
    }

    private static boolean inputFormaPagamento() {
        String opcao = null;
        while (opcao == null) {
            System.out.print("forma de pagamento à vista(1) ou parcelado(2): ");
            opcao = new Scanner(System.in).nextLine();
            if (!"1".equals(opcao) && !"2".equals(opcao)) {
                opcao = null;
                System.out.println("Informe 1 ou 2");
            }
        }
        if(opcao.equals("2")){
            return false;
        }
        return true; //à vista
    }

    private static String inputTipoPessoa() {
        String tipoPessoa = null;
        while (tipoPessoa == null) {
            System.out.print("Pessoa Juridica (j) ou Pessoa Fisica (f): ");
            tipoPessoa = new Scanner(System.in).nextLine();
            if (!"f".equals(tipoPessoa) && !"j".equals(tipoPessoa)) {
                tipoPessoa = null;
                System.out.println("Informe f ou j");
            }
        }
        return tipoPessoa;
    }
}
