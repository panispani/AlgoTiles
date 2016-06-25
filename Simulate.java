import java.util.ArrayList;

public class Simulate {

    // Commands to numbers mappings
    public static final int MOVE = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int IF = 4;
    public static final int FOR = 5;
    public static final int ELSE = 6;
    public static final int END = 7;

    // Game element mappings
    public static final int BOMB = 1;
    public static final int GRASS = 2;
    public static final int ROCK = 3;
    public static final int FLAG = 4;
    public static final int PLAYERUP = 5;
    public static final int PLAYERLEFT = 6;
    public static final int PLAYERDOWN = 7;
    public static final int PLAYERRIGHT = 8;

    // used to store player position
    private static class Point {
        int x;
        int y;
        int type;

        public Point() {
            x = 0;
            y = 0;
            type = PLAYERUP;
        }
    }

    /*
     * Simulates game with the given commands
     */
    public static void simulate(int[][] grid, int[] initial_commands) {
        if(!checkValidity(initial_commands)) {
            //printErrorMessage();
            return;
        }
        ArrayList<Integer> commands = removeLoops(initial_commands);
        Point playerPos = findPlayer(grid);
        for(int i = 0; i < commands.size(); i++) {
            int command = commands.get(i);
            switch(command) {
                case MOVE:
                    if(!move(grid, player)) {
                        //printErrorMessage();
                        return;
                    }
                    break;
                case LEFT:
                    player.type++;
                    if(player.type > PLAYERRIGHT) {
                        player.type = PLAYERUP;
                    }
                    grid[player.x][player.y] = player.type;
                    break;
                case RIGHT:
                    player.type--;
                    if(player.type < PLAYERUP) {
                        player.type = PLAYERRIGHT;
                    }
                    grid[player.x][player.y] = player.type;
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

    private static boolean move(int[][] grid, Point player) {
        switch(player.type) {
                        case PLAYERUP:
                            break;
                    }

    }

   /*
     * Returns true in case of valid input commands
     */
    public static boolean checkValidity(int[] commands) {
        return false;
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
                if(isPlayer(grid[i][j])) {
                    player.x = i;
                    player.y = j;
                    return player;
                }
            }
        }
        System.out.println("Error in finding player");
        return player;
    }

    /*
     * Returns true if input element id is a player
     */
    public static boolean isPlayer(int id) {
        return id >= PLAYERUP && id <= PLAYERRIGHT;
    }
}
