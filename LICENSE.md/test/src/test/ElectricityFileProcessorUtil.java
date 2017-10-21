package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElectricityFileProcessorUtil {
	public static String getCurrentDir(){
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		System.out.println("File is present at the location ... " + s+"/"+ElectricityConstants.FILE_NAME);
		return s;
	}
	
	
	public static  List<String> readFile(String filename) {
	  List<String> records = new ArrayList<String>();
	  try
	  {
	    BufferedReader reader = new BufferedReader(new FileReader(filename));
	    String line;
	    while ((line = reader.readLine()) != null)
	    {
	      records.add(line);
	      System.out.println("asdad...."+line);
	    }
	    reader.close();
	    getCurrentDir();
	    return records;
	  } catch (Exception e) {
	    File file = new File(filename);
	    try {
			file.createNewFile();
			List<String> lines = Arrays.asList("//sample  month of year|room1_units|room1_charge|room2_units|room2_charge...|total_units|total_charge");
			Path path = Paths.get(ElectricityConstants.FILE_NAME);
			Files.write(path, lines, Charset.forName("UTF-8"));
			//Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
			System.out.println("File is created");
			getCurrentDir();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("An error has occrueed while reading the file");
		}
        return null;
	  }
	}
	
	public static int searchForEntry(String previousMonth) throws IOException {
		String entries = readFile(ElectricityConstants.FILE_NAME).get(0);
		String[] entry = entries.split(ElectricityConstants.SPLITTER);
		boolean found = false;
		int i = 0;
		for(i=0 ; i<entry.length;i++){
			if(entry[i].equals(previousMonth)) {
				found=true;
				break;
			}
		}
		if(!found){
			return -1;
		}
			return i+2;
	}
	
	public static float[] processUnitsForLineNumber(int line_number){
		String line = null;
		try {
			line = Files.readAllLines(Paths.get(ElectricityConstants.FILE_NAME)).get(line_number);
		} catch (IOException e) {
			System.out.println("exception while processing the content for the line number");
			e.printStackTrace();
		}
		float[] units = null;
		if(null != line && !line.equals("")){
		String[] contentOfLine = line.split(ElectricityConstants.SPLITTER);
		units = new float[ElectricityConstants.NO_OF_ROOMS];
		int j=0;
		for(int i=1 ;i<= ElectricityConstants.NO_OF_ROOMS ; i=i+2){
			units[j] = Float.parseFloat(contentOfLine[i]);
			j++;
		}
		}
		return units;
	}
	
//	public static void writeOutputToFile(String monthOfYear, float[] units, float[] charge){
//		BufferedWriter bw = null;
//		FileWriter fw = null;
//		try {
//			File file = new File(ElectricityConstants.FILE_NAME);
//			// true = append file
//			fw = new FileWriter(file.getAbsoluteFile(), true);
//			bw = new BufferedWriter(fw);
//			bw.write(fileStringConstructor(monthOfYear,units,charge));
//			System.out.println("writing at the end is done ...");
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (bw != null)
//					bw.close();
//				if (fw != null)
//					fw.close();
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
//	}
	
	public static void writeOutputToFile(Month month, String year, float[] units, float[] charge, float totalAmount, float totalUnits) throws Exception{
		Path path = Paths.get(ElectricityConstants.FILE_NAME);
	    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
	    String meta = lines.get(0);
	    if(meta.contains("sample")){
	    	meta = month.toString()+year;
	    } else {
	    meta = meta+"|"+month.toString()+year;
	    }
	    lines.add(0, meta);
	    lines.add(fileStringConstructor(month.toString(), year,units,charge, totalAmount, totalUnits));
	    Files.write(path, lines, StandardCharsets.UTF_8);
	}
	
	private static String fileStringConstructor(String month, String year, float[] units, float[] charge, float totalAmount, float totalUnits){
		StringBuilder builder = new StringBuilder();
		builder.append(month).append(" of ").append(year).append("|");
		for(int i =0 ; i<units.length ;i++) {
			builder.append(units[i]).append("|").append(charge[i]).append("|");
		}
		builder.append(totalAmount).append("|").append(totalUnits);
		return builder.toString();
	}
}
