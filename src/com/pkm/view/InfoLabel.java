package com.pkm.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.AbstractBorder;

import com.pkm.utils.CircleBorder;
/**
 * @web https://www.jc-mouse.net/
 * @author Mouse
 */
public class InfoLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	
    private AbstractBorder circleBorder = new CircleBorder();       
    private int lineBorder=1; 
    private Color lineColor= Color.BLACK;
	
	public InfoLabel(String text) {
		super(text);
        setBounds(175, 675, 100, 100);
        setPreferredSize(new Dimension(100, 100));   
        setOpaque(false);
        setHorizontalAlignment(CENTER);       
        setVisible(true);       
        setBorder(circleBorder);
        setFont(new Font("sanserif", Font.ITALIC, 20));
	}

    //Color de borde
    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color color) {
        circleBorder = new CircleBorder(color, lineBorder);
        lineColor = color;
        setBorder(circleBorder); 
    }

    //Grosor de borde
    public int getLineBorder() {
        return lineBorder;        
    }

    public void setLineBorder(int lineBorder) {
        circleBorder = new CircleBorder(lineColor, lineBorder);
        this.lineBorder = lineBorder;        
        setBorder(circleBorder); 
    }
	
}
