import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class assn3checker
{
	public static void main ( String args [])
	{
		BufferedReader br = null;
		RoutingMapTree r = new RoutingMapTree();

		try {
			String actionString;
			br = new BufferedReader(new FileReader("actions.txt"));

			while ((actionString = br.readLine()) != null)
			{
				String str = r.performAction(actionString);
				if(!str.equals("")) System.out.println(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
}
