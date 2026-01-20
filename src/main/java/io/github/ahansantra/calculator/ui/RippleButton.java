package io.github.ahansantra.calculator.ui;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RippleButton extends JButton {

    private float radius=0, alpha=0;
    private Timer timer;
    private static Clip click;

    static {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                    RippleButton.class.getResource("/sounds/click.wav"));
            click = AudioSystem.getClip();
            click.open(audio);
        } catch (Exception e) {}
    }

    public RippleButton(String text){ super(text); init(); }
    public RippleButton(Icon icon){ super(icon); init(); }

    private void init(){
        setBorder(null);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                startRipple();
                play();
            }
        });
    }

    private void play(){
        if(click==null) return;
        if(click.isRunning()) click.stop();
        click.setFramePosition(0);
        click.start();
    }

    private void startRipple(){
        radius=0; alpha=0.35f;
        if(timer!=null) timer.stop();
        timer=new Timer(16,e->{
            radius+=8; alpha-=0.03f;
            if(alpha<=0) timer.stop();
            repaint();
        });
        timer.start();
    }

    protected void paintComponent(Graphics g){
        Graphics2D g2=(Graphics2D)g;
        g2.setColor(getBackground());
        g2.fillRect(0,0,getWidth(),getHeight());

        if(alpha>0){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
            g2.setColor(new Color(200,200,200));
            int r=(int)radius;
            g2.fillOval(getWidth()/2-r,getHeight()/2-r,r*2,r*2);
        }

        g2.setComposite(AlphaComposite.SrcOver);
        super.paintComponent(g2);
    }
}
