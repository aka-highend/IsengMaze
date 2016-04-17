/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bikin_maze;

/**
 *
 * @author fachrur_122
 */
public class Test_Maze {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MazeGenerator mg = new MazeGenerator(7, 12, false);
        mg.display();
    }
    
}
