public class No<T> {
    T info;
    No<T> dir;
    No<T> esq;

    public No(T info){
        this.info = info;
    }
}
