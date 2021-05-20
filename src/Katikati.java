public class Katikati {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args){
        VentanaKati VK = new VentanaKati();
        new Thread((Runnable) new Cronometro(VK,144)).start();
    }
}
