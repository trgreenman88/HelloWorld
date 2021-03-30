package Math;

import java.util.Scanner;

public class Exercise1 {
	
	private static Scanner scan = new Scanner(System.in);
	
	public static int GenerateNumber() {
		int randomNumber = (int)Math.floor(Math.random()*10)+1;
		return randomNumber;
	}
	
	private static int getInput(String prompt) {
		System.out.println(prompt);
		//one word scan.next();
		int result = scan.nextInt();
		return result;
	}
	
	private static void GuessNumber(int randomNumber) {
		int guess = 0;
		while (guess != randomNumber) {
			guess = getInput("Guess a number from 1 to 10");
			if (guess > 10 || guess < 1) {
				System.out.println("Guess is out of range");
			} else if (guess > randomNumber) {
				System.out.println("Guess is too high");
			} else if (guess < randomNumber) {
				System.out.println("Guess is too low");
			} else if (guess == randomNumber) {
				System.out.println("You Win!");
			} 
		}
	}
	
	public static void main(String[] args) {
		
		int randNumber = GenerateNumber();
		System.out.println(randNumber);
		GuessNumber(randNumber);	

	}

}
