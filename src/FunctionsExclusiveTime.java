import java.util.List;

public class FunctionsExclusiveTime {
	
	/**
	 * By following the logic of concurrent execution of multiple threads in an application:
	 * 1. Each start has an end and starts precede the corresponding ends timestamps
	 * 2. Starts are ordered, that means that each subfunction starts later and it is listed after its parent start
	 * 3. A parent cannot end if the subfunctions are still running
	 * 4. Multiple functions cannot start at the same time
	 * 5. Multiple functions cannot end at the same time
	 * 6. The end of one function cannot be same with the start of another function
	 * 7. One function can call itself, that means recursive call
	 * 8. After the complete execution of one function it could start again later
	 */

	public static void main(String[] args) {
		String path = "C:\\Projects\\CodingInterview\\src\\tuples4.txt";
		
		List<Tuple> tuples = Tuple.readTxt(path);
		
		Tuple.calculateExclusiveTime(tuples);
	}
	
}