package co.mundial2026.view;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {

    private Image backgroundImage;

    public ImagePanel(String imagePath) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
            backgroundImage = icon.getImage();
        } catch (Exception e) {
            backgroundImage = null;
        }

        setOpaque(false);
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(AppTheme.NEGRO_FONDO);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (backgroundImage != null) {
            int imgW = backgroundImage.getWidth(this);
            int imgH = backgroundImage.getHeight(this);

            if (imgW > 0 && imgH > 0) {
                double panelW = getWidth();
                double panelH = getHeight();

                double scale = Math.min(panelW / imgW, panelH / imgH);

                int drawW = (int) (imgW * scale);
                int drawH = (int) (imgH * scale);

                int x = (getWidth() - drawW) / 2;
                int y = (getHeight() - drawH) / 2;

                g2d.drawImage(backgroundImage, x, y, drawW, drawH, this);
            }
        }

        g2d.dispose();
    }
}