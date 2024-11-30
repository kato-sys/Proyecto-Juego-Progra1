import java.util.Random;
import java.util.Scanner;

class Node {
    String roomId;
    Node[] neighbors; // Fixed-size array for 4 directions: [0] = NORTH, [1] = SOUTH, [2] = EAST, [3] = WEST
    int[][] matrix; // 10x10 grid for the room
    Random random = new Random();

    public Node(String roomId) {
        this.roomId = roomId;
        this.neighbors = new Node[4];
        this.matrix = new int[10][10];
        initializeMatrix();
    }

    private void initializeMatrix() {
        // Fill the matrix with empty spaces (0)
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public void generateDoors(int previousDirection) {
        // Ensure the door from where the player came is present
        if (previousDirection != -1) {
            placeDoor(oppositeDirection(previousDirection));
        }

        // Generate random number of additional doors (1 to 3)
        int numberOfAdditionalDoors = random.nextInt(3) + 1;

        for (int i = 0; i < 4; i++) {
            if (i != previousDirection && neighbors[i] == null && numberOfAdditionalDoors > 0) {
                placeDoor(i);
                numberOfAdditionalDoors--;
            }
        }
    }

    private void placeDoor(int direction) {
        // Place a door (1) in the matrix at the edges
        switch (direction) {
            case 0: // NORTH
                matrix[0][random.nextInt(10)] = 1; // Anywhere along the top row
                break;
            case 1: // SOUTH
                matrix[9][random.nextInt(10)] = 1; // Anywhere along the bottom row
                break;
            case 2: // EAST
                matrix[random.nextInt(10)][9] = 1; // Anywhere along the rightmost column
                break;
            case 3: // WEST
                matrix[random.nextInt(10)][0] = 1; // Anywhere along the leftmost column
                break;
        }
    }

    public void connect(Node other, int direction) {
        neighbors[direction] = other;
        other.neighbors[oppositeDirection(direction)] = this;
    }

    private int oppositeDirection(int direction) {
        switch (direction) {
            case 0: return 1; // NORTH -> SOUTH
            case 1: return 0; // SOUTH -> NORTH
            case 2: return 3; // EAST -> WEST
            case 3: return 2; // WEST -> EAST
            default: return -1;
        }
    }
}

class Player {
    int x, y; // Position in the current room

    public Player() {
        x = 5;
        y = 5; // Start in the center of the room
    }

    // Move player and return true if they step on a door
    public boolean move(String direction, Node currentRoom) {
        int newX = x, newY = y;
        switch (direction.toUpperCase()) {
            case "W": newX--; break; // Move up
            case "S": newX++; break; // Move down
            case "A": newY--; break; // Move left
            case "D": newY++; break; // Move right
            default:
                System.out.println("Invalid move!");
                return false;
        }

        if (newX >= 0 && newX < 10 && newY >= 0 && newY < 10) {
            x = newX;
            y = newY;

            // Check if stepping on a door
            if (currentRoom.matrix[x][y] == 1) {
                return true; // Player moved through a door
            }
        }
        return false;
    }
}

class DungeonGraph {
    Node root; // Starting room
    Node currentRoom;
    Player player;
    int roomCounter = 0;
    Random random = new Random();

    public DungeonGraph() {
        root = new Node("Room_" + roomCounter++);
        currentRoom = root;
        player = new Player();
        root.generateDoors(-1); // First room doesn't have a previous direction
    }

    // Generate a new room when the player goes through a door
    private Node createRoom(int direction) {
        Node newRoom = new Node("Room_" + roomCounter++);
        currentRoom.connect(newRoom, direction);
        newRoom.generateDoors(direction);
        return newRoom;
    }

    // Move to a connected room or create a new one if it doesn't exist
    public void moveToRoom(int direction) {
        if (currentRoom.neighbors[direction] == null) {
            // Create a new room if no room exists in the given direction
            currentRoom.neighbors[direction] = createRoom(direction);
        }
        currentRoom = currentRoom.neighbors[direction];
        // Place player at the opposite door in the new room
        player.x = (direction == 0) ? 9 : (direction == 1) ? 0 : random.nextInt(10);
        player.y = (direction == 2) ? 0 : (direction == 3) ? 9 : random.nextInt(10);

        System.out.println("You have entered " + currentRoom.roomId);
    }

    // Display the current room
    public void displayCurrentRoom() {
        System.out.println("You are in " + currentRoom.roomId);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == player.x && j == player.y) {
                    System.out.print("P "); // Player position
                } else if (currentRoom.matrix[i][j] == 1) {
                    System.out.print("/ "); // Door
                } else {
                    System.out.print(". "); // Empty space
                }
            }
            System.out.println();
        }
    }
}

public class DungeonGame {
    public static void main(String[] args) {
        DungeonGraph dungeon = new DungeonGraph();
        Scanner scanner = new Scanner(System.in);
        boolean gameRunning = true;

        System.out.println("Welcome to the Infinite Dungeon!");
        System.out.println("Use W/A/S/D to move around the room.");
        System.out.println("Step on a door (/) to enter a new room.");
        System.out.println("Type EXIT to quit the game.");

        while (gameRunning) {
            dungeon.displayCurrentRoom();
            System.out.print("Enter command: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("EXIT")) {
                gameRunning = false;
            } else {
                boolean movedThroughDoor = dungeon.player.move(input, dungeon.currentRoom);
                if (movedThroughDoor) {
                    // Determine which door was used
                    if (dungeon.player.x == 0) {
                        dungeon.moveToRoom(0); // NORTH
                    } else if (dungeon.player.x == 9) {
                        dungeon.moveToRoom(1); // SOUTH
                    } else if (dungeon.player.y == 9) {
                        dungeon.moveToRoom(2); // EAST
                    } else if (dungeon.player.y == 0) {
                        dungeon.moveToRoom(3); // WEST
                    }
                }
            }
        }

        scanner.close();
        System.out.println("Thanks for playing!");
    }
}
