public class Tablero {
    protected int ancho;
    protected int alto;
    protected Ente[][] entes;

    public Tablero(int ancho, int alto){
        this.ancho = ancho;
        this.alto = alto;
        this.entes = new Ente[ancho][alto];
    }
}
