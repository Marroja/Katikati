public class Cronometro {
    VentanaKati VK;
    double fd;
    public Cronometro(VentanaKati VK, double frecuencia_deseada){
        this.VK = VK;
        this.fd = frecuencia_deseada;
        Anda();
    }

    public void Anda(){
        double t0_reloj = System.currentTimeMillis();
        double t0_crono = t0_reloj;             //t0_reloj es para contador IPS/APS y tiempo, t0_crono es el del cronómetro musical
        double t1;
        double tm = t0_reloj;                   //tm es "tiempo móvil" de móvil t0 y t1
        double ppm = 120;                       //120 solo por poner un número ahorita
        double tpp = 1/(ppm/60/1000);           //tpp es el tiempo pulso a pulso (tiempo entre pulsos de la música)
                                                //En principio el número de arriba debería poder cambiar
        int APS = 0;
        int IPS = 0;
        while(true){
            t1 = System.currentTimeMillis();
            if(t1 - t0_crono > tpp){
                //System.out.println("Pulso musical");
                VK.Tictoc();                    //Este no es el medidor por segundos
                                                //Este evento desencadena el evento que ocurre cada pulso de la música
                t0_crono = System.currentTimeMillis();
            }
            if(t1 - t0_reloj > 1000) {
                //System.out.println("APS:" + APS + "  IPS:" + IPS);
                t0_reloj = System.currentTimeMillis();
                tm = t0_reloj;
                APS = 0;                        //Actualizaciones por segundo
                IPS = 0;                        //Imágenes por segundo
            }

            APS += VK.ActualizarEstado();

            if(t1 - tm >= 1000/fd){
                tm += 1000/fd;
                IPS += VK.DibujarEstado();
            }
        }
    }
}
