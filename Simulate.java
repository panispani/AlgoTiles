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
     * Finds end od index in commands, returns its pos
     */ 
    private static int findEnd(ArrayList<Integer> commands, int index) {
        int endsToFind = 1;
        for(int i = index; i < commands.size(); i++) {
            if (commands.get(i) == END) {
                endsToFind--;
                if(endsToFind == 0) {
                    return i;
                }
            } else if (commands.get(i) == IF || commands.get(i) == FOR) {
                endsToFind++;
            } 

        } 
        System.out.println("Error: End not found"); 
        return 0;
    }

    /*
     * Finds else of index in commands if any, returns its pos
     * returns -1 if no else is found
     */ 
    private static int findElse(ArrayList<Integer> commands, int index) {
        int elseToFind = 1;
        for(int i = index; i < commands.size(); i++) {
            if (commands.get(i) == ELSE) {
                elseToFind--;
                if(elseToFind == 0) {
                    return i;
                }
            } else if(commands.get(i) == END) {
                elseToFind--;
                return -1;
            } else if(commands.get(i) == IF) {
                elseToFind++;
            } else if(commands.get(i) == FOR) {
                i = findEnd(commands, i) + 1;
            }
        } 
        return -1;   
    }

    /*
     * Checks boolean condition of space being free "num" squares away
     */ 
    private static boolean checkAhead(int[][] grid, Point player, int num) {
        switch(player.type) {
            case PLAYERUP:
                return player.y - num >= 0 
                    && grid[player.x][player.y - num] == GRASS;
                break;
            case PLAYERLEFT:
                return player.x - num >= 0
                    && grid[player.x - num][player.y] == GRASS;
                break;
            case PLAYERDOWN:
                return player.y + num < grid.length 
                    && grid[player.x][player.y + num] == GRASS;
                break;
            case PLAYERRIGHT:
                return player.x + num < grid[0].length
                    && grid[player.x + num][player.y] == GRASS;
                break;
        }
        return false; 
    }

    /*
     * Try to move player in his pointing direction
     * in case of enemy ahead don't move player
     * Return true upon success
     */
    private static boolean move(int[][] grid, Point player) {
        //update both player and grid
        boolean lost = false;
        switch(player.type) {
            case PLAYERUP:
                if(player.y - 1 >= 0 
                    && grid[player.x][player.y - 1] == GRASS) {
                        lost = true;
                        grid[player.x][player.y - 1] = PLAYERUP;
                        grid[player.x][player.y] = GRASS;
                        player.y--;
                }
                // not lose in case of rock
                lost = player.y - 1 >= 0 
                            && grid[player.x][player.y - 1] == ROCK;
                break;
            case PLAYERLEFT:
                if(player.x - 1 >= 0
                    && grid[player.x - 1][player.y] == GRASS) {
                        lost = true;
                        grid[player.x - 1][player.y] = PLAYERLEFT;
                        grid[player.x][player.y] = GRASS;
                        player.x--;
                }
                lost = player.x - 1 >= 0 
                            && grid[player.x - 1][player.y] == ROCK;
                break;
            case PLAYERDOWN:
                if(player.y + 1 < grid.length 
                    && grid[player.x][player.y + 1] == GRASS) {
                        lost = true;
                        grid[player.x][player.y + 1] = PLAYERDOWN;
                        grid[player.x][player.y] = GRASS;
                        player.y++;
                }
                lost = player.y + 1 < grid.length 
                            && grid[player.x][player.y + 1] == ROCK;
                break;
            case PLAYERRIGHT:
                if(player.x + 1 < grid[0].length
                    && grid[player.x + 1][player.y] == GRASS) {
                        lost = true;
                        grid[player.x + 1][player.y] = PLAYERRIGHT;
                        grid[player.x][player.y] = GRASS;
                        player.x++;
                }
                lost = player.x + 1 < grid[0].length
                            && grid[player.x + 1][player.y] == ROCK;
                break;
        }
        return lost;
    } 

    /*
     * Returns true in case of valid input commands
     * not alone number, after if, for there is a number, if for end with end else followed by end
     */
    private static boolean checkValidity(int[] commands) {
        return false;
    }

    /*
     * Removes loops from input instructions
     * Unfolds loops returns arraylist
     */
    private static ArrayList<Integer> removeLoops(int[] init_commands) {
        ArrayList<Integer> commands = new ArrayList<>();
        for(int i = 0; i < init_commands.length; i++) {
            if(init_commands[i] != FOR) {
                commands.add(init_commands[i]);
            } else {
                int endPos = findEnd(new ArrayList(init_commands), index);        
                int times = init_commands[i + 1];
                for(int j = 0; j < times; j++) {
                    for(index = i + 2; index < endPos; index++) {
                        commands.add(init_commands[index]);
                    }
                }
                i = endPos;
            }
        }
        return commands;
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
