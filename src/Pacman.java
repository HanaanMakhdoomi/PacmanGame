import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.*;
import java.util.Random;

public class Pacman extends JPanel implements ActionListener, KeyListener{
    class Block{
        int x;
        int y;
        int width;
        int height;
            Image image;
        int startx;
        int starty;
        char direction='U';
        int velx=0;
        int vely=0;

        Block(Image image,int x,int y,int width,int height){
            this.image=image;
            this.x=x;
            this.y=y;
            this.width=width;
            this.height=height;
            this.startx=x;
            this.starty=y;

        }
        void updatedirection(char direction){
            char prevdirec=this.direction;
            this.direction=direction;
            updatevelocity();
            this.x+=this.velx;
            this.y+=this.vely;
            for(Block wall:walls){
                if(collision(this,wall)){
                    this.x-=this.velx;
                    this.y-=this.vely;
                    this.direction=prevdirec;
                    updatevelocity();
                }
            }
        }
        void updatevelocity(){
            if(this.direction=='U'){
                this.velx=0;
                this.vely=-tilesize/4;
            }
            else if(this.direction=='D'){
                this.velx=0;
                this.vely=tilesize/4;
            }
            else if(this.direction=='L'){
                this.velx=-tilesize/4;
                this.vely=0;
            }
            else if(this.direction=='R'){
                this.vely=0;
                this.velx=tilesize/4;
            }
        }
        void reset(){
            this.x=this.startx;
            this.y=this.starty;
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
        "X       bpo       X",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     XPX     X  X",
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
    Timer gameloop;
    char[] directions={'U','D','L','R'};
    Random random=new Random();
    int score=0;
    int lives=3;
    boolean gameover=false;

    Pacman(){
        setPreferredSize(new Dimension(boardwidth,boardheight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
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
        for(Block ghost:ghosts){
            char newdirection=directions[random.nextInt(4)];
            ghost.updatedirection(newdirection);

        }
        gameloop=new Timer(50,this);
       gameloop.start();

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
        g.setFont((new Font("Arial",Font.PLAIN,18)));
        if(gameover){
            g.drawString("Game Over:" +String.valueOf(score),tilesize/2,tilesize/2);
        }
        else{
            g.drawString("x" +String.valueOf(lives)+" Score: "+ String.valueOf(score),tilesize/2,tilesize/2);

        }
    }
        public void move(){
            pacman.x+=pacman.velx;
            pacman.y+=pacman.vely;
            for(Block wall:walls){
                if(collision(pacman, wall)){
                    pacman.x-=pacman.velx;
                    pacman.y-=pacman.vely;
                    break;
                }
            }
            
            for(Block ghost:ghosts){
                    if(collision(ghost, pacman)){
                        lives-=1;
                        if(lives==0){
                            gameover=true;
                            return;
                        }
                        resetposition();
                    }
                    ghost.x+=ghost.velx;
                    ghost.y+=ghost.vely;
                    for(Block wall:walls){
                        if(collision(ghost, wall)){
                            ghost.x-=ghost.velx;
                            ghost.y-=ghost.vely;
                            char newdirection=directions[random.nextInt(4)];
                            ghost.updatedirection(newdirection);
                            break;
                        }
                    }
                
            }
            Block foodeaten=null;
            for(Block food:foods){
                if(collision(pacman,food)){
                    foodeaten=food;
                    score+=10;
                }
            }
            foods.remove(foodeaten);
            if(foods.isEmpty()){
                loadmap();
                resetposition();
            }
        }
        public void resetposition(){
            pacman.reset();
            pacman.velx=0;
            pacman.vely=0;
            for(Block ghost:ghosts){
                ghost.reset();
                char newdirection=directions[random.nextInt(4)];
                ghost.updatedirection(newdirection);
            }
        }
        public boolean collision(Block a,Block b){
            return a.x<b.x+b.width && 
            a.x+a.width>b.x&&
            a.y<b.y+b.height&&
            a.y+a.height>b.y;
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
       repaint();
       if(gameover){
        gameloop.stop();
       }
    }
    @Override
    public void keyTyped(KeyEvent e) {
       }
    @Override
    public void keyPressed(KeyEvent e) {
        
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(gameover){
            loadmap();
            resetposition();
            lives=3;
            score=0;
            gameover=false;
            gameloop.start();
        }
        System.out.println("KeyEvent: "+e.getKeyCode());
        if(e.getKeyCode()==KeyEvent.VK_UP){
            pacman.updatedirection('U');

        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN){
            pacman.updatedirection('D');
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT){
            pacman.updatedirection('L');
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            pacman.updatedirection('R');
        }
        if(pacman.direction=='U'){
            pacman.image=pacmanUp;
        }
       else if(pacman.direction=='D'){
            pacman.image=pacmandown;
        }
       else if(pacman.direction=='R'){
            pacman.image=pacmanright;
        }
       else if(pacman.direction=='D'){
            pacman.image=pacmanleft;
        }
    }

}
