import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import au.com.bytecode.opencsv.CSVReader;

public class CS245A2 {

	public static void main(String[] args) throws Exception {
		//opens a scanner
		Scanner scanner = new Scanner(System.in);

		System.out.println("Please enter the full name of an actor: ");
		String actor1 = scanner.nextLine();
		System.out.println("Please enter the full name of another actor: ");
		String actor2 = scanner.nextLine();
		// if actor 1 doesn't exist, this creates a "flag" int
		int actor1Exists = 0;
		// if actor 2 doesn't exist, create a "flag" int
		int actor2Exists = 0;
		//closes the scanner
		scanner.close();

		//Create a graph of size 20,000
		BaconGraph graph = new BaconGraph(20000);

		//Initialize the JSON file parser
		JSONParser parser = new JSONParser();

		//Set the CSV file reader to null
		CSVReader movieCreditFile = null;

		//try catch exception
		try {
			//this reads the CVS file
			movieCreditFile = new CSVReader(new FileReader("tmdb_5000_credits.csv"));
			String movieTitle;
			//this is the array for pairs of actors
			String[] actors = new String[2];
			String[] next;
			JSONObject a;
			int titleCount, i, j, movieCount;
			// skips the first line
			int counter = 0;

			while ((next = movieCreditFile.readNext()) != null) // reads file line by line
			{
				try {
					// skip the header line
					if (counter == 0) {
						counter++;
						continue;
					}
					movieTitle = next[1];
					Object Object = parser.parse(next[2]);
					JSONArray jsonArray = (JSONArray) Object;

					// Parsing JSON file

					List<String> myList = new ArrayList<String>();
					i = 0;
					while (i < jsonArray.size()) {
						a = (JSONObject) jsonArray.get(i);
						myList.add((String) a.get("Name")); // name in the cast field
						i++;
					}

					// this checks if the actors names exist
					boolean actor1bool = myList.contains(actor1);
					boolean actor2bool = myList.contains(actor2);
					if(actor1bool){
						actor1Exists = 1;
					}
					if(actor2bool){
						actor2Exists = 1;
					}

					String[] actorNames = new String[myList.size()]; //this is a list of the actors' names
					myList.toArray(actorNames); // this will convert the list to an array

					// splits the array that was made into movieTitle, actor1, & actor2
					titleCount = 1;
					for (movieCount = 0; movieCount < (jsonArray.size() - titleCount); movieCount++) {
						for (j = titleCount; j < jsonArray.size(); j++) {
							actors[0] = actorNames[movieCount];
							actors[1] = actorNames[j];
							graph.createEdge(actors[0].trim(), actors[1].trim(), movieTitle); // fill in the graph
						}
						titleCount = titleCount + 1;
					}

					// throw exception if the line in the file is corrupted
				} catch (Exception e) {
					throw new Exception("An error occurred!");
				}

			}
			movieCreditFile.close(); //this closes the movie file

			//throws an exception if an error occurs while trying to read the file
		} catch (IOException e) {
			e.printStackTrace();
		}

		// checks if actor1 exists in the file & if not, sets the flag value to 0
		if (actor1Exists == 0) {
			System.out.println("This actor does not exist.");
			return;
		}
		// checks if actor2 exists in the file & if not, sets the flag value to 0
		if (actor2Exists == 0) {
			System.out.println("This actor does not exist.");
			return;
		}

		int vertexTracker = graph.BFS(actor1, actor2);
		int previousTracker;
		String compare = "";
		//if -1, there is no connection
		if (vertexTracker == -1)
			System.out.println("No connection exists");
		else {
			// print the existing path
			System.out.println("There is a path between " + actor1 + " and " + actor2 + ": " + actor1 + " --> " + actor2);
		}
	}
}
