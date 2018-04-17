package com.wesayweb.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.wesayweb.model.Traits;

public  class CsvReader {

	public static List<Traits> getTraits() {
		List<Traits> returnList = new ArrayList<Traits>();
		ClassLoader classLoader = CsvReader.class.getClassLoader();
		File file = new File(classLoader.getResource("supported_stuff/traitlist.csv").getFile());
		BufferedReader br = null;
		int linecounter = 0;
		String line = "";
		String cvsSplitBy = ",";
		try {

			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				if(linecounter > 0) {
				Traits traitObj = new Traits();
				String[] trait = line.split(cvsSplitBy);
				traitObj.setTrait_name(trait[0].trim());
				if(trait[1].trim().equalsIgnoreCase("y")) {
					traitObj.setIs_active(1);
				}
				else
				{
					traitObj.setIs_active(0);
				}
				returnList.add(traitObj);
				}
				linecounter++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return returnList;
	}
}
