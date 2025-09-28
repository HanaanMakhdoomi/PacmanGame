import javax.swing.JFrame;
public class App{
    public static void main(String[] args){
        int rowcount=21;
        int columncount=19;
        int tilesize=32;
        int boardwidth=columncount*tilesize;
        int boardheight=rowcount*tilesize;
        JFrame frame=new JFrame("Pac Man");
        Pacman pacmanGame=new Pacman();
        frame.add(pacmanGame);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(boardwidth,boardheight);
        frame.pack();
        frame.setVisible(true);




    }
}