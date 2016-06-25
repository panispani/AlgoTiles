import java.util.ArrayList;

public class Simulate {

    // Commands to numbers mappings
    public static final int MOVE = 1;
    public static final int LEFT= 2;
    public static final int RIGHT = 3;
    public static final int IF = 4;
    public static final int FOR = 5;
    public static final int ELSE = 6;
    public static final int END = 7;

    // Game element mappings
    public static final int BOMB = 1;
    public static final int GRASS = 2;
    public static final int ROCK = 3;
    public static final int PLAYER = 4;
    public static final int FLAG = 5;

    // used to store player position
    private static class Point {
        int x;
        int y;

        public Point() {
            x = 0;
            y = 0;
        }
    }

    /*
     * Simulates game with the given commands
     */
    public static void simulate(int[][] grid, int[] initial_commands) {
        ArrayList<Integer> commands = removeLoops(initial_commands);
        Point playerPos = findPlayer(grid);
        for(int i = 0; i < commands.size(); i++) {
            int command = commands.get(i);
            switch(command) {
                case MOVE:
                    break;
                case LEFT:
                    break;
                case RIGHT:
                    break;
                case IF:
                    //also remove number
                    break;
                case ELSE:
                    break;
                case END:
                    break;
                default:
                    System.out.println("Wrong command id");
            }
            //draw();
        }
    }

    /*
     * Removes loops from input instructions
     * Unfolds loops returns arraylist
     */
    private static ArrayList<Integer> removeLoops(int[] init_commands) {
        return null;
    }

    /*
     * Finds position of player in grid
     * Assumes only one player is around
     */
    private static Point findPlayer(int[][] grid) {
        Point player = new Point();
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == PLAYER) {
                    player.x = i;
                    player.y = j;
                    return player;
                }
            }
        }
        System.out.println("Error in finding player");
        return player;
    }
}
