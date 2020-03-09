import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Priyanka Wagh
 * 
 * ProblemStatementAnalysis:
 * 
 * Fan class has two cords:
 * cord-1 is used to change speed in between [speed0, speed1, speed2] speed range of 3.
 * cord-2 is used to change direction for fan in between: [Clockwise, Anticlockwise] 
 * 
 * Assumsions:
 * 1. Default fan speed at the start of program: speed0/Off
 * 2. Default direction for Fan at the start of program: Clockwise
 * 3. 'Exit' word will exit the whole program successfully
 * 
 * Cases of Live operation:
 * 1. When fan is on speed0/Off pull for DirectionCord should not change the fan direction
 * 2. Unless DirectionCord is pulled, fan should not change direction from present at all
 * 3. If SpeedCord and DirectionCord gets pulled together following steps will be 
 * 	  executed sequentially automatically: 
 * 			a. First SpeedCord.pull() process will be executed with next speed
 * 			b. Then DirectionCord.pull() method will be triggered to change direction
 * 4. When speed of cord is speed2, and if SpeedCord and DirectionCord gets pulled together:
 * 			a. First SpeedCord.pull() will be triggered to change speed to speed0/Off
 * 			b. Then DirectionCord.pull() method will be triggered but direction of fan should not be changed.
 * 
 */


/**
 * 
 * Test Cases to enter on console:
 * 
 * Happy Path Scenarios:[Note: for each of above scenario please set speed of fan at speed0 at the start]
 * 1. Only SpeedCord pulled inputs: [1, 1, 1, 1, 1, 1]
 * 	  Output For each single input: [speed1, speed2, Off, speed1, speed2, Off]
 * 
 * 3. Pull SpeedCord and then DirectionCord: [1, 2, 2, 1, 2, 1, 1, 1, 2]
 * 	  Expected Output : [speed1, Anticlockwise, Clockwise, speed2, Anticlockwise, Off, speed1, speed2, Clockwise]
 * 
 * 
 * Failure/Exception Scenarios:
 * 1. When Both Cords are pulled together: [12, 12, 21, 12, 21, 12]
 *    Output: [speed1 + Anticlockwise, speed2 + Clockwise, Off + Clockwise, speed1 + Anticlockwise, speed2 + Clockwise,
 *    		   Off + Clockwise]
 *    
 * 2. Only DirectionCord pulled at the begining when fan is on speed0 inputs: [2, 2, 2]
 * 	  Output for each single input: 'Fan is Off, so direction can't be changed!'
 * 
 * 3. Pull SpeedCord and then DirectionCord: [1, 2, 2, 1, 2, 1, 1, 1, 1, 2, 1, 2, 2]
 * 	  Expected Output : [speed1, Anticlockwise, Clockwise, speed2, Anticlockwise, Off, speed1, speed2,
 * 						 Off, 'Fan is Off, so direction can't be changed!', speed1, Clockwise] 
 * 
 * 4. [Note: Input is accepted at run time from user so someone can enter random inputs out of valid input range of [1, 2 or 12, 21]].
 * 	if input entered at keyboard is: [8, 4, 3, 4564564, A, g]
 *  Output for each: 'Invalid Input!\n Please enter valid input either as speed cord(Press 1) or direction cord(Press 2)!'
 * 
 */
public class Fan {
	public static void main(String[] args) {
		SpeedCord speedCord = new SpeedCord();
		DirectionCord directionCord = new DirectionCord();
		
		/* Messages to display on console at the beginning */
		System.out.println("Instruction:");
		System.out.println("1. Pull cord-1 to change speed(Press 1)");
		System.out.println("2. Pull cord-2 to change the direction(Press 2)");
		System.out.println("Exit. Press Exit to stop the fan program(Type Exit)");
		
		/* Take input as 1 or 2 or 12 or 21 from console at Run time as pulled cords of running fan */
		Scanner sc = new Scanner(System.in);
		String input;
		
		/*Code will be in running state until user types "Exit" at console*/
		while(!(input = sc.nextLine()).equals("Exit") )
		{		
			switch(input)
			{
				/* When user is pressing just enter key again and again, it won't be giving any acknowledgment */
				case "": break; 
				
				/* SpeedCord pulled to change speed */
				case "1": 
					speedCord.pull(); 
					break;
					
				/* 	DirectionCord pulled to change direction */
				case "2": 
					directionCord.pull(); 
					break;
					
				/* When both cords get pulled together */	
				case "12": 
				case "21": 
					speedCord.pull();
					directionCord.pull();
					break;
					
				/* Incase of wrong input entered */	
				default: 
					System.out.println("Invalid Input!\n Please enter valid input either as speed cord(Press 1) "
						+ "or direction cord(Press 2)!");
					break;
			}
		}
		System.out.println("Exited from the code");
	}
}


/**
 * Two cords has same pull method which those classes must implement as per need
 */
interface Cord {
	public void pull();
}


/**
 * SpeedCord class is used to change speed with respect to pull for it's cord each time in between 0-2 (0,1,2)
 */
class SpeedCord implements Cord {
	
	/* speed is a unmodifiableList which has constant three speeds and none can try to change it's value from other classes*/
	private final static List<String> speed = Collections.unmodifiableList(
		    new ArrayList<String>() {{
		        add("Off");
		        add("Speed1");
		        add("Speed2");
		    }});
	private static String currentSpeed; //currentSpeed holds static value of current speed
	
	public static String getCurrentSpeed() {
		return currentSpeed;
	}

	public SpeedCord() {
		currentSpeed = speed.get(0); //At the start of execution default value for fan speed is Off.
		System.out.println("Fan is off with speed: " + currentSpeed);
	}

	public void pull() {
		/* Update the current speed of fan after pulling cord to fit in range of [Off, speed1, speed2] */
		currentSpeed = speed.get((speed.indexOf(currentSpeed)+1)%3); 
		System.out.println("Speed of fan changed to: " + currentSpeed);
	}
}

/**
 * DirectionCord is class for switching fan in between two directions: front and back
 */
class DirectionCord implements Cord {
	/* direction is a unmodifiableList which has constant two directions and none can try to change it's value from other classes*/
	private final static List<String> direction = Collections.unmodifiableList(
		    new ArrayList<String>() {{
		        add("Clockwise");
		        add("Anticlockwise");
		    }});
	private static String currentDirection; //currentDirection holds static value of current speed
	
	public DirectionCord() {
		currentDirection = direction.get(0); //At the start of execution default value for fan direction is Clockwise.
		System.out.println("Default direction for fan is: " + currentDirection);
	}

	public void pull() {
		/* Direction of fan can be changed only if fan is not Off (i.e. speed should be either speed1/speed2)*/
		if(!SpeedCord.getCurrentSpeed().equals("Off")) {
		/* Update the current speed of fan after pulling cord to fit in range of [Off, speed1, speed2] */
		currentDirection = direction.get((((List<String>) direction).indexOf(currentDirection)+1)%2);
		System.out.println("Direction of fan changed to: " + currentDirection);
		}
		
		/* Direction of fan cannot be changed if fan is Off*/
		else {
			System.out.println("Fan is Off, so direction can't be changed!");
		}
	}
}

