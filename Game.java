/**
 *  This class is the main class of the "World of Zuul" application.
 *  "World of Zuul" is a very simple, text based adventure game.  Users
 *  can walk around some scenery. That's all. It should really be extended
 *  to make it more interesting!
 *
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class Game
{
    private Parser parser;
    private Player player;
    boolean finished = false;

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {	
    	player = new Player();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, atrium, overseerOffice, mainEntrance, upperLevel, adminZone, escapeRoom, classroom, medicalCenter, lowerLevel, cafeteria, residential, reactorRoom, overseerQuarters, centralWasteland, southernWasteland, easternWasteland, city, northernWasteland, westernWasteland, doctor ;

        // create the rooms
        outside = new Room("outside the main entrance of the abandoned vault, the Wasteland is behind you");
        mainEntrance = new Room("inside the main entrance of the vault");
        atrium = new Room("in the vaults atrium");
        upperLevel = new Room("on the upper levels standing on the bridge crossing the atrium");
        adminZone = new Room("in the adminstration zone of the vault");
        overseerQuarters = new Room("in the overseers quarters you decide to make it your room");
        overseerOffice = new Room("in the overseers office and look down on the vaults atrium");
        escapeRoom = new Room("in the hidden escape tunnel under the overseers desk");
        classroom = new Room("inside the vaults old classroom");
        medicalCenter = new Room("in the vaults medical center, you find enough medical suplies to keep you going until you find a doctor in the wasteland");
        lowerLevel = new Room("in the lower levels of the vault");
        cafeteria = new Room("in the old cafeteria");
        residential = new Room("in the residential block, you find an old vault 73 jumpsuit and put it on");
        reactorRoom = new Room("in the old reactor room, you try to turn the reactor on and have returned power back to the vault. It may be possible to start a colony now");
        centralWasteland = new Room("CENTRAL WASTELAND");
        southernWasteland = new Room("SOUTHERN WASTELAND");
        easternWasteland = new Room("EASTERB WASTELAND");
        city = new Room("GENERIC CITY");
        northernWasteland = new Room("NORTHERN WASTELAND");
        westernWasteland = new Room("WESTERN WASTELAND");
        doctor = new Room("in the doctors office /n you have reached your final destination");
        

        // initialise room exits
        outside.setExit("north", mainEntrance );
        mainEntrance.setExit("south", outside);
        mainEntrance.setExit("down", atrium);
        atrium.setExit("up", upperLevel);
        atrium.setExit("down", lowerLevel);
        atrium.setExit("entrance", mainEntrance);
        upperLevel.setExit("north", medicalCenter );
        upperLevel.setExit("west", adminZone );
        upperLevel.setExit("east", classroom);
        medicalCenter.setExit("south", upperLevel);
        classroom.setExit("west", upperLevel);
        adminZone.setExit("east", upperLevel);
        adminZone.setExit("north", overseerQuarters);
        adminZone.setExit("west", overseerOffice);
        overseerQuarters.setExit("south", adminZone);
        overseerOffice.setExit("east", adminZone);
        overseerOffice.setExit("down", escapeRoom);
        escapeRoom.setExit("up", overseerOffice);
        escapeRoom.setExit("north", mainEntrance);
        lowerLevel.setExit("up", atrium);
        lowerLevel.setExit("west", reactorRoom);
        lowerLevel.setExit("north", residential);
        lowerLevel.setExit("east", cafeteria);
        reactorRoom.setExit("east", lowerLevel);
        cafeteria.setExit("west", lowerLevel);
        residential.setExit("south", lowerLevel);
        outside.setExit("south", centralWasteland);
        centralWasteland.setExit("north", outside);
        centralWasteland.setExit("east", easternWasteland);
        easternWasteland.setExit("west", centralWasteland);
        easternWasteland.setExit("east", city);
        city.setExit("west", easternWasteland);
        city.setExit("east", doctor);
        centralWasteland.setExit("south", southernWasteland);
        southernWasteland.setExit("north", centralWasteland);
        southernWasteland.setExit("west", westernWasteland);
        westernWasteland.setExit("east", southernWasteland);
        



        player.setCurrentRoom(outside);  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play()
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        
        System.out.println("Welcome to Fallout TA!");
        System.out.println("Fallout TA is a text adventure game where you exlore the deserted vault 73 .");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("You are lost. You are wounded. You wander the wasteland");
        System.out.println("and stumble at an abandoned vault.");
        System.out.println();
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("look"))
            look(command);
        else if (commandWord.equals("quit"))
            wantToQuit = quit(command);
        else if (commandWord.equals("health")){
        	System.out.println(player.getHealth());
        }
        else if (commandWord.equals("heal")){
        	player.setHealth(50);
        	System.out.println("You jam a stimpack in your wounds to heal them");
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp()
    {
        System.out.println("You are lost. You are wounded. You wander the wasteland");
        System.out.println("and stumble at an abandoned vault.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null)
            System.out.println("There is no door!");
        else {
            player.setCurrentRoom(nextRoom);
            player.damage(10);
            player.isAlive();
            if (!player.isAlive()) {
            	finished = true;
            }
            System.out.println(player.getCurrentRoom().getLongDescription());
        }
    }

    private void look(Command command) {
    	if(!command.hasSecondWord()) {
    		System.out.println(player.getCurrentRoom().getLongDescription());
    		return;
    	}
    }
    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else
            return true;  // signal that we want to quit
    }

    public static void main(String[] args)
    {
        Game game = new Game();
        game.play();
    }
}
