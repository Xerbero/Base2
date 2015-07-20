package base2;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main extends Canvas implements Runnable{
    
    static ScheduledThreadPoolExecutor executor;
    JFrame jFrame;
    BufferStrategy bufferStrategy;
    Graphics2D g2d;
    BufferedImage img;

    public static void main(String[] args) {
        executor = new ScheduledThreadPoolExecutor(6);
        executor.submit(new Main());
    }
    
    public Main(){
        short
                w = 800,
                h = 600;
        
        setMaximumSize(new Dimension(w, h));
        setMinimumSize(new Dimension(w, h));
        setPreferredSize(new Dimension(w, h));
        
        jFrame = new JFrame("Prova");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());
        
        jFrame.add(this, BorderLayout.CENTER);
        jFrame.pack();
        
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        
        try {
            img = ImageIO.read(new File("folder/BallMask.png"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        if (getBufferStrategy() == null){
            createBufferStrategy(3);
        }
        
        while(!executor.isShutdown()){
            bufferStrategy = getBufferStrategy();
            g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
            g2d.setColor(Color.red);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.drawImage(img, 50, 50, null);
            bufferStrategy.show();
        }
    }
}
