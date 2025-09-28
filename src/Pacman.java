import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;
public class Pacman extends JPanel{
    class Block{
        int x;
        int y;
        int width;
        int height;
            Image image;
        int startx;
        int starty;
        Block(Image image,int x,int y,int width,int height){
            this.image=image;
            this.x=x;
            this.y=y;
            this.width=width;
            this.height=height;
            this.startx=x;
            this.starty=y;

        }
    }
    private int rowcount=21;
    private int columncount=19;
    private int tilesize=32;
    private int boardwidth=columncount*tilesize;
    private int boardheight=rowcount*tilesize;

    private Image wallImage;
    private Image blueghost;
    private Image redghost;
    private Image pinkghost;
    private Image orangeghost;
    private Image pacmanUp;
    private Image pacmandown;
    private Image pacmanleft;
    private Image pacmanright;
    //X = wall, O = skip, P = pac man, ' ' = food
    //Ghosts: b = blue, o = orange, p = pink, r = red
    private String[] tileMap = {
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXrXX X XXXX",
        "O       bpo       O",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     P     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX" 
    };
    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    Block pacman;
    Pacman(){
        setPreferredSize(new Dimension(boardwidth,boardheight));
        setBackground(Color.BLACK);
        wallImage=new ImageIcon(getClass().getResource("./wall.png")).getImage();
        blueghost=new ImageIcon(getClass().getResource("./blueGhost.png")).getImage();
        redghost=new ImageIcon(getClass().getResource("./redGhost.png")).getImage();
        pinkghost=new ImageIcon(getClass().getResource("./pinkGhost.png")).getImage();
        orangeghost=new ImageIcon(getClass().getResource("./orangeGhost.png")).getImage();
        pacmanUp=new ImageIcon(getClass().getResource("./pacmanUp.png")).getImage();
        pacmandown=new ImageIcon(getClass().getResource("./pacmanDown.png")).getImage();
        pacmanleft=new ImageIcon(getClass().getResource("./pacmanLeft.png")).getImage();
        pacmanright=new ImageIcon(getClass().getResource("./pacmanRight.png")).getImage();

        loadmap();
    }
    public void loadmap(){
        walls=new HashSet<Block>();
        foods=new HashSet<Block>();
        ghosts=new HashSet<Block>();
        for(int r=0;r<rowcount;r++){
            for(int c=0;c<columncount;c++){
                String row=tileMap[r];
                char tilemapchar=row.charAt(c);
                int x=c*tilesize;
                int y=r*tilesize;
                if(tilemapchar=='X'){
                    Block wall=new Block(wallImage,x,y,tilesize,tilesize);
                    walls.add(wall);
                }
                else if(tilemapchar=='b'){//blueghost
                    Block ghost=new Block(blueghost, x, y, tilesize,tilesize);
                    ghosts.add(ghost);
                }
                else if(tilemapchar=='o'){//oranegghost
                    Block ghost=new Block(orangeghost, x, y, tilesize, tilesize);
                    ghosts.add(ghost);
                }
                else if(tilemapchar=='p'){//pinkghost
                    Block ghost=new Block(pinkghost, x, y, tilesize, tilesize);
                    ghosts.add(ghost);
                }
                else if(tilemapchar=='r'){//blueghost
                    Block ghost=new Block(redghost, x, y, tilesize, tilesize);
                    ghosts.add(ghost);
                }
                else if(tilemapchar=='P'){
                    pacman=new Block(pacmanright,x,y,tilesize,tilesize);
                }
                else if(tilemapchar==' '){
                    Block food=new Block(null,x+14,y+14,4,4);
                    foods.add(food);
                }

            }
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
        g.drawImage(pacman.image,pacman.x, pacman.y, pacman.width, pacman.height,null);
        for(Block ghost:ghosts){
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
        }
        for(Block wall:walls){
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }

        g.setColor(Color.WHITE);
        for(Block food:foods){
            g.fillRect(food.x, food.y, food.width, food.height);
        }
    }

}
