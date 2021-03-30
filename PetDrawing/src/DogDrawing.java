
public class DogDrawing {

	public static void main(String[] args) {
		String line = "oooooooooooooooooooooooooooooo";
		double decimal = 8.88888888;
		boolean bool = true;
		int whole = 8;
		System.out.println(
				String.format("%17.8s "
						+ "\n%8.5s %14.5s"
						+ "\n%3d %2.1s   %.0f      %.0f %3.1s %2d"
						+ "\n%2d %3.1s %14.1s %3d"
						+ "\n%3d %2.1s     %b %5.1s %2d"
						+ "\n%7.2f%.1s %11.1s%.2f"
						+ "\n%17.8s", 
						line, 
						line, line, 
						whole, line, decimal, decimal, line, whole, 
						whole, line, line, whole,
						whole, line, true, line, whole, 
						decimal, line, line, decimal,
						line));
		System.out.println(String.format("\n %s", "This is supposed to be a dog"));
	}

}
