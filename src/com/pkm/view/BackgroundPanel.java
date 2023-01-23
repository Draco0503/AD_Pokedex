package com.pkm.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage bgImg;
	
	public BackgroundPanel() throws Exception {
		super(new BorderLayout());
		bgImg = ImageIO.read(new File("./resources/img/bgImage.png"));

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImg, 0, 0, 450, 913, this);
	}
}
