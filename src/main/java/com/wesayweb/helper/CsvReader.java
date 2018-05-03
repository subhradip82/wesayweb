package com.wesayweb.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.wesayweb.application.WeSayWebApplication;
import com.wesayweb.model.SettingsCategory;
import com.wesayweb.model.Traits;

public class CsvReader {

	@Inject
	private static Logger logger;

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
				if (linecounter > 0) {
					Traits traitObj = new Traits();
					String[] trait = line.split(cvsSplitBy);
					traitObj.setTraitname(trait[0].trim());
					if (trait[1].trim().equalsIgnoreCase("y")) {
						traitObj.setActivestatus(1);
					} else {
						traitObj.setActivestatus(0);
					}
					traitObj.setTraittype(trait[2].trim().toLowerCase());
					traitObj.setTraitcreatedby(Long.valueOf(0));
					traitObj.setTraitidentifier(10000);
					traitObj.setApproveddate(new Date());
					traitObj.setTraituniqueid(WesayStringUtil.generateRandomNumber());
					returnList.add(traitObj);
				}
				linecounter++;
			}

		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return returnList;
	}
	
	public static List<SettingsCategory> getSettings() {
		List<SettingsCategory> returnList = new ArrayList<SettingsCategory>();
		ClassLoader classLoader = CsvReader.class.getClassLoader();
		File file = new File(classLoader.getResource("supported_stuff/user_settings.csv").getFile());
		BufferedReader br = null;
		int linecounter = 0;
		String line = "";
		String cvsSplitBy = ",";
		try {

			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				if (linecounter > 0) {
					SettingsCategory categoryObj = new SettingsCategory();
					String[] rows = line.split(cvsSplitBy);
					categoryObj.setCategoryname(rows[0].trim());
					categoryObj.setCategorydescription(rows[1].trim());
					categoryObj.setAllowedmultiplevalue(Integer.valueOf(rows[2].trim()));
					categoryObj.setDefaultvalue(Integer.valueOf(rows[3].trim()));
					categoryObj.setUniqueid(rows[4].trim());
					returnList.add(categoryObj);
				}
				linecounter++;
			}

		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return returnList;
	}
}