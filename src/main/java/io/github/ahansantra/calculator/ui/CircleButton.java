package io.github.ahansantra.calculator;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CircleButton extends JButton {

    private float radius=0, alpha=0;
    private Timer timer;
    private static Clip click;

    static {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                    CircleButton.class.getResource("/sounds/click.wav"));
            click = AudioSystem.getClip();
            click.open(audio);
        } catch (Exception e) {
            System.out.println("Sound not loaded");
        }
    }

    public CircleButton(String text){
        super(text);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        setPreferredSize(new Dimension(72,72));
        setMinimumSize(new Dimension(72,72));
        setMaximumSize(new Dimension(72,72));

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
            radius+=6; alpha-=0.03f;
            if(alpha<=0) timer.stop();
            repaint();
        });
        timer.start();
    }

    protected void paintComponent(Graphics g){
        Graphics2D g2=(Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillOval(6,6,getWidth()-12,getHeight()-12);

        if(alpha>0){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
            g2.setColor(new Color(200,200,200));
            int r=(int)radius;
            g2.fillOval(getWidth()/2-r,getHeight()/2-r,r*2,r*2);
        }

        g2.setComposite(AlphaComposite.SrcOver);
        g2.setColor(getForeground());
        FontMetrics fm=g2.getFontMetrics();
        g2.drawString(getText(),(getWidth()-fm.stringWidth(getText()))/2,
                (getHeight()+fm.getAscent())/2-4);
    }

    public boolean contains(int x,int y){
        int r=getWidth()/2;
        return Math.pow(x-r,2)+Math.pow(y-r,2)<=r*r;
    }
}
