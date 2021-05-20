import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;


//-------------- Siguientes pasos -----------------
/*
    2021-mayo-19:
        Reloj y cronómetro funcionales
        Trazado de tablero y un ente (jugador)
        Configuración general del programa (corre, no se traba...)

    Transformar todos los procesos a hilos de ejecución
    Movimiento del jugador (uno) (y dos cursores)
    Entes en movimiento con colisión
    Animación de movimiento de casilla en casilla
    Movimiento solo en el ritmo
    Trayectorias de movimiento de los entes-no-jugador
    Ajuste del "offset"
    Lectura de música y "mapa"
 */

public class VentanaKati extends JFrame{

    private Canvas canvas;
    private Tablero tablero;
    private Jugador jugador;
    private Color colorCuadricula;
    private long contadorPulsos;
    private long ventanaTemporal;
    private long t_pulso;

    public VentanaKati(){
        Inicializar();
        Controles();
        FormatoConfig();
        Formato();
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);

        //Temporal para forzar un tablero
        tablero = new Tablero(8,8);
        jugador = new Jugador();
        ventanaTemporal = 50;
        //Cronometro C = new Cronometro(this, 144);

    }

    private void Formato(){
    }

    private void FormatoConfig() {
        //Haciendo unos ajustitos temporales
        this.setSize(1000, 1000);
        this.setLocationRelativeTo(null);
    }

    private void Controles() {
        canvas = new Canvas();
        canvas.setSize(this.getWidth(), this.getHeight());
        canvas.setFocusable(false);
        canvas.setBackground(Color.BLACK);
        add(canvas);
        pack();

        this.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                long t_presionado = System.currentTimeMillis();

                if(Math.abs(t_presionado - t_pulso) < ventanaTemporal || Math.abs(t_pulso - t_presionado) > 450){
                    if(e.getKeyCode() == KeyEvent.VK_UP){
                        if(jugador.y > 0){
                            jugador.y --;
                            System.out.println(" -- Botón arriba -- ");
                        }

                    }
                    if(e.getKeyCode() == KeyEvent.VK_DOWN){
                        if(jugador.y < 7){
                            jugador.y ++;
                            System.out.println(" -- Botón abajo --");
                        }
                    }
                    if(e.getKeyCode() == KeyEvent.VK_LEFT){
                        if(jugador.x > 0){
                            jugador.x --;
                            System.out.println(" -- Botón izquierda --");
                        }

                    }
                    if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                        if(jugador.x < 7){
                            jugador.x ++;
                            System.out.println(" -- Botón derecha -- ");
                        }
                    }
                }
                System.out.println("Dif pulsos:"+(t_pulso - t_presionado));


            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ALT) {

                }
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }


        });
    }

    private void Inicializar() {
        //this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //this.setResizable(false);
        //this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        colorCuadricula = Color.red;
    }

    protected int ActualizarEstado(){

        return 1;
    }

    protected int DibujarEstado(){

        canvas.setBackground(Color.BLACK);

        Graphics g = canvas.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0, this.getWidth(), this.getHeight());
        g.setColor(colorCuadricula);

        //g.fillRect(10,10,100,100);

        /*if(anchoCuadro > altoCuadro){
            anchoCuadro = altoCuadro;
        }
        if(altoCuadro > anchoCuadro){
            altoCuadro = anchoCuadro;
        }*/

        int anchoCuadro = 100;
        int altoCuadro = 100;

        for(int i = 1; i <= tablero.ancho; i++){
            for(int j = 1; j <= tablero.alto; j++){
                g.drawRect(i*anchoCuadro, j*altoCuadro, anchoCuadro, altoCuadro);
                if(i == jugador.x + 1 && j == jugador.y + 1){
                    g.fillRect(i*anchoCuadro + 10, j*anchoCuadro + 10, anchoCuadro-20, altoCuadro-20);
                }
            }
        }
        return 1;
    }

    protected void Tictoc(){

        //colorCuadricula = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
        TocaSonido("pulso_base.wav");

        if(contadorPulsos%2 == 0){
            colorCuadricula = Color.red;
            //System.out.print("Cambio de color cuadrícula --");
        }
        if(contadorPulsos%2 == 1){
            colorCuadricula = Color.pink;
            //System.out.print("Cambio de color cuadrícula -- ");
        }

        contadorPulsos++;
        t_pulso = System.currentTimeMillis();

    }

    void TocaSonido(String archivo) {
        try{
            File f = new File("./material/"+archivo);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        }catch (LineUnavailableException | IOException |UnsupportedAudioFileException e) {
            System.err.println("Error al leer archivo de audio");
        }

    }

}
