package com.wesayweb.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.wesayweb.model.ContactList;
import com.wesayweb.request.model.ContactRequestModel;
import com.wesayweb.request.model.PhoneNumberModel;

public class JsonParser {

	public static void main(String[] args) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		List<ContactRequestModel> contactList = new ArrayList<ContactRequestModel>();
		Gson gson = new Gson();
		File jsonFile = Paths.get("C:\\Apps\\contact.json").toFile();
		JsonArray jsonObject = gson.fromJson(new FileReader(jsonFile), JsonArray.class);
		for (int counter = 0; counter < jsonObject.size(); counter++) {
			ContactRequestModel saltPojo = gson.fromJson(
					jsonObject.get(counter).getAsJsonObject().get("_objectInstance"), ContactRequestModel.class);
					contactList.add(saltPojo);
		}
		for(ContactRequestModel contactModel : contactList) 
		{
			for(PhoneNumberModel phoneObj : contactModel.getPhoneNumbers())
			{
				ContactList contactListObj = ContactList.builder().build();
				contactListObj.setFullname(contactModel.getDisplayName());
				contactListObj.setMobilenumber(phoneObj.getValue());
				
			}
	}
}
}
