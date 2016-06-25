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
}
