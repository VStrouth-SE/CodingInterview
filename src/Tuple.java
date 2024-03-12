import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Tuple {

		private String name;
		private String typeTime;
		private int duration;
		
		Tuple( String name, String typeTime, int duration)
		{
			this.name = name;
			this.typeTime = typeTime;
			this.duration = duration;
		}
		
		public static List<Tuple> readTxt(String path){
			
			List<Tuple> tuples = new ArrayList<>();
			try {
				File file = new File(path);
				Scanner scanner = new Scanner(file);
				
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] parts = line.split(",");
					String name = parts[0].replace("\"", "");
					String typeTime = parts[1].replace("\"", "");
					int duration = Integer.parseInt(parts[2]);
					
					Tuple myObject = new Tuple(name, typeTime, duration);
					tuples.add(myObject);
				}
				
				scanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			return tuples;
		}
	
	public static void calculateExclusiveTime (List<Tuple> tuples){
			//Map to keep the pairs name-duration
			Map<String,Integer> durations = new HashMap<>();
			
			Deque<Tuple> tuplesStack = new ArrayDeque<>();
			for ( Tuple tuple : tuples) {
				if (tuple.typeTime.equalsIgnoreCase("start")) {
					tuplesStack.push(tuple);
					if (!durations.containsKey(tuple.name))//if it's the first time the specific function is executed
						durations.put(tuple.name,0); //initialization of durations with zero for each function, because it refers to period of time and not timestamp
				} else {
					Tuple top = tuplesStack.pop();
					// so the tuple is the object of function's end, and top is the object of function's start
					int duration = durations.get(top.name) + (tuple.duration - top.duration + 1);
					durations.put(top.name, duration);
					// here we have to notice that each time we close one function and culculate its duration
					// we subtract this duration from the previous function. So by poping each function and subtract from the previous
					// we subtract all the durations of all the functions that have already been poped, recursively.
					// Until we reach the bottom which is the first executed function and the only remaining that has to finish.
					if (!tuplesStack.isEmpty()) {
						Tuple stackHead = tuplesStack.peek();
						duration = durations.get(stackHead.name) - (tuple.duration - top.duration + 1);
						durations.put(stackHead.name, duration);
					}
				}
			}
		
		durations.forEach((key, value) -> System.out.println("function " + key + " has exclusive duration: " + value));
	}
}

