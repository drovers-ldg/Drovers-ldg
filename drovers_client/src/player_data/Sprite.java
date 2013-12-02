package player_data;

import java.awt.Graphics;
import java.awt.Image;

public class Sprite
{
    private Image image;
    
    public Sprite(Image image){
        this.image = image;
    }
    
    public int getWidth(){
        return image.getWidth(null);
    }

    public int getHeight(){
        return image.getHeight(null);
    }
    
    public Image getImage(){
    	return image;
    }
   
    public void draw(Graphics g, int x, int y){
        g.drawImage(image, x, y, null);
    }

    public void draw(Graphics g, int x, int y, int size_x, int size_y){
        g.drawImage(image, x, y, size_x, size_y, null);
    }
}