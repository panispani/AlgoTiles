public class Simulate {
    
    private class Point {
        int x;
        int y;
    }

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

    private static ArrayList<Integer> removeLoops(int[] init_commands) {
        return null; 
    }
    
    /*
     * Finds position of player in grid
     * Assumes only one player is around
     */
    private static Point findPlayer(int[][] grid) {
        Point player;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == PLAYER) {
                    player.x = i;
                    player.y = j;
                    return;
                }
            }
        } 
        System.out.println("Error in finding player");
        return player;
    }
}
