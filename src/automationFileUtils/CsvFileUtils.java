package automationFileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class CsvFileUtils {

	Logger log = Logger.getLogger(CsvFileUtils.class);

	public void readcsvfile(String path, String filename, String delimiter) {
		// path
		path = "C:/Users/abhishek.verma02/Desktop/TEXT FILES ALL/";
		filename = "Book2.csv";
		delimiter = ",";

		String csvfilepath = path + File.separator + filename;
		System.out.println(csvfilepath);
		log.info(csvfilepath);
		BufferedReader br = null;
		String line = null;

		try {
			br = new BufferedReader(new FileReader(csvfilepath));
			while ((line = br.readLine()) != null) {
				String[] detail = line.split(delimiter);
				for (int i = 0; i < detail.length; i++) {
					System.out.print("Value[" + i + "]::" + detail[i] + " ");
					log.info("Value[" + i + "]::" + detail[i] + " ");
				}
				System.out.println();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void devidecsv(String path, String filename, int parts) {
		path = "C:/Users/abhishek.verma02/Desktop/TEXT FILES ALL";
		filename = "Book2.csv";

		String csvfilepath = path + File.separator + filename;
		System.out.println(csvfilepath);
		log.info(csvfilepath);
		BufferedReader br = null;
		String line = null;
		int count = 0;
		try {
			FileReader fr = new FileReader(csvfilepath);
			br = new BufferedReader(fr);
			while (br.readLine() != null) {
				count++;
			}
			System.out.println(count);
			log.info(count);
			ArrayList<Integer> pt = devideNoToNthTime(count, parts);

			// line = null;
			count = 0;
			int files = 1;
			int a;
			br = null;
			int length = pt.size();
			File f = null;
			// creating new folder
			String newpath = path + File.separator + "Output";
			f = new File(newpath);
			if (f.exists()) {
				f.delete();
			} else {
				if (!f.exists())
					f.mkdir();
			}
			f = null;
			File f2=null;
			f = new File(newpath + File.separator + "file-" + files + ".csv");
			System.out.println(f.createNewFile());
			FileWriter writer = new FileWriter(newpath + File.separator
					+ "file-" + files + ".csv");
			FileReader fr1 = new FileReader(csvfilepath);
			br = new BufferedReader(fr1);
			while ((line = br.readLine()) != null) {
				System.out.println("file" + files + "---" + line + "-----"
						+ count);
				a = pt.get(length - 1);
				if (a != count) {
					count++;
					writer.write(line);
					writer.write("\n");
				} else {
					Thread.sleep(4000);
					length = length - 1;
					count = 0;
					files++;
					f2 = new File(newpath + File.separator + "file-" + files
							+ ".csv");
					System.out.println(f2.createNewFile());
					writer = new FileWriter(newpath + File.separator + "file-"
							+ files + ".csv");
					writer.write(line);
				}

			}
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Integer> devideNoToNthTime(int number, int parts) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		int temp = parts;
		{
			if (number % temp != 0) {

				a.add(number / parts + number % parts);
				temp--;
			}
			for (int i = temp; i > 0; i--) {
				a.add(number / parts);
			}
		}
		System.out.println(a);
		return a;
	}

}
