import java.io.*;
import java.net.*;
import org.json.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TocHw3 {
	public static void main(String[] args) {		
		String url = args[0];
		String TOWN = args[1];
		String ADDRESS = args[2];
		String YEAR = args[3];
		try {
			int MONTH = Integer.parseInt(YEAR) * 100;
			int total = 0;
			int count = 0;
			int avg,price,month;
			String address,town;


			TocHw3 hw3 = new TocHw3();
			hw3.getURLstr(url);
			JSONArray jsonRealPrice = new JSONArray(new JSONTokener(
					new FileReader(new File("URL.txt"))));

			for (int i = 0; i < jsonRealPrice.length(); i++) {
				JSONObject object = jsonRealPrice.getJSONObject(i);
				
				town = object.getString("鄉鎮市區");
				Pattern pattern = Pattern.compile(TOWN);
				Matcher matcher = pattern.matcher(town);
				if (matcher.find()) {
					address = object.getString("土地區段位置或建物區門牌");
					pattern = Pattern.compile(ADDRESS);
					matcher = pattern.matcher(".*"+address+".*");
					
					if (matcher.find()) {
						month = object.getInt("交易年月");
						price = object.getInt("總價元");
						
						if ((month - MONTH)>=0) {
					
							total = total + price;
							count++;
						}
					}
				}
				
	
				
			}
			avg = total / count;
			System.out.println(avg);
		} catch (FileNotFoundException ex) {
			System.out.println("Data File Not Found");
			ex.printStackTrace();
		} catch (JSONException ex) {
			System.out.println("JSON Exception");
		}
	}

	public void getURLstr(String strURL) {
		try {
			URL url_address = new URL(strURL);
			BufferedReader br = new BufferedReader(new InputStreamReader(url_address.openStream(), "UTF-8"));
			BufferedWriter bw = new BufferedWriter(new FileWriter("URL.txt",false));
			String oneLine = null;

			while ((oneLine = br.readLine()) != null) {
				bw.write(oneLine);
			}
			br.close();
			bw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}