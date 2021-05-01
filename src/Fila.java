public class Fila<T> {

    private No<T> inicio;
    private No<T> fim;


    public void enfileirar(T info) { //inserir no final
        No<T> aux = new No<T>(info);

        if(this.size()==0) {
            inicio = aux;

        }else {
            fim.dir = aux; // adicionando o valor do novo nó no campo da direita do último nó
            aux.esq = fim;// aqui o nó final vira o nó recém criado
        }
        fim = aux;

    }

    // método para imprimir os elementos da lista no vídeo
    public void imprimir() {
        No<T> aux = inicio;
        int i = 1 ;
        while(aux != null) {
            System.out.println(i+"° - "+aux.info);
            aux = aux.dir;
            i++;
        }

    }

    public No<T> get(int posicao){
        No<T> aux = inicio;

        for(int i = 0 ; i<posicao; i++){
            aux = aux.dir;
        }
        return aux;

    }


    public No<T> front(){ //retorna o elemento da frente
        return inicio;
    }


    //método para remover um nó
    public No<T> desinfileirar() {
        No<T> aux = inicio;
        if (this.size()>0) {// tem nó para ser removido
            if (this.size()==1) { // apago a referência dos dois lados
                inicio = null;
                fim = null;
            } else {
                aux.dir.esq = null;
                inicio = inicio.dir;
                aux.dir = null;
            }
        }
        return aux;
    }

    public int size(){
        No<T> aux = inicio;
        int tamanho = 0;

        while(aux != null){
            tamanho++;
            aux = aux.dir;
        }

        return tamanho;
    }

    public Boolean isEmpty(){

        if(this.size()==0){
            return true;
        }
        return false;
    }


}
