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
                        //printRetryMessage();
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
                    //also remove number, else and end, dont remove the if itself
                    resolveIfStatement(grid, player, commands, i);
                    break;
                default:
                    System.out.println("Wrong command id or end of program");
                    //return
            }
            //draw(); TODO: IMPORTANT
        }
    }

    /*
     * Removes if/else false branches. Only true branch is left
     * and executed sequentially
     * input index is the index of the if command
     */
    public static void resolveIfStatement(int[][] grid,
            Point player, ArrayList<Integer> commands, int index) {
        index++;
        int num = commands.get(index);
        boolean condition = checkAhead(grid, player, num);
        int elsePos = findElse(commands, index);
        int endPos = findEnd(commands, index);
        if(elsePos < 0) {
            //if - end
            if(condition) {
                commands.remove(endPos);
                commands.remove(index); //number
            } else {
                commands.removeRange(index, endPos + 1);
            }
        } else {
            //if - else - end
            if(condition) {
                commands.removeRange(elsePos, endPos + 1);
                commands.remove(index);
            } else {
                commands.removeRange(index, elsePos + 1);
            }
        }
    }

    /*
     * Finds end in commands, returns its pos
     */ 
    private static int findEnd(ArrayList<Integer> commands, int index) {
        for(int i = index; i < commands.size(); ++i) {
            if (commands.get(i) == END) {
                return i;
            } 
        } 
        System.out.println("Error: End not found"); 
        return 0;
    }

    /*
     * Finds else in commands if any, returns its pos
     * returns -1 if no else is found
     */ 
    private static int findElse(ArrayList<Integer> commands, int index) {
         for(int i = index; i < commands.size(); ++i) {
            if (commands.get(i) == ELSE) {
                return i;
            } else if(commands.get(i) == END) {
                return -1;
            } 
        } 
        return -1;   
    }

    /*
     * Checks boolean condition of space being free "num" squares away
     */ 
    private static boolean checkAhead(int[][] grid, Point player, int num) {
        return false; 
    }

    /*
     * Try to move player in his pointing direction
     * in case of enemy ahead don't move player and set the LOST global variable
     * Return true upon success
     */
    private static boolean move(int[][] grid, Point player) {
        //update both player and grid
        boolean moved = false;
        switch(player.type) {
            case PLAYERUP:
                break;
            case PLAYERLEFT:
                break;
            case PLAYERDOWN:
                break;
            case PLAYERRIGHT:
                break;
        }
        return moved;
    }

   /*
     * Returns true in case of valid input commands
     */
    private static boolean checkValidity(int[] commands) {
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
