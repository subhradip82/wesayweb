1. Register Via Mobile Number
	Pattern 	: /api/mobileregistration/
	Method  	: POST    
	Type    	: JSON
	Parameters 	: {
					"emailaddress":"xxxxx@xxx.com",
					"countrycode":"+91",
					"mobilenumber":000000000,
					"dateofbirth":"05-12-2018",
					"gender"	 : "male"
					}
2. Register Via Email
	Pattern 	: /api/emailregistration/
	Method  	: POST    
	Type    	: JSON
	Parameters 	: {
					"emailaddress":"xxxxx@xxx.com",
					"countrycode":"+91",
					"mobilenumber":000000000,
					"dateofbirth":"05-12-2018",
					"gender"	 : "male"
					}
					
3. Check if Mobile number already exists
	Pattern 	: /api/validatemobile/
	Method  	: POST    
	Type    	: JSON
	Parameters 	: {
					"countrycode":"+91",
					"mobilenumber":000000000
				}
					
4. Check if email already exists
	Pattern 	: /api/validateemail/
	Method  	: POST    
	Type    	: JSON
	Parameters 	: {
					"emailaddress":"xxxxx@xxx.com"
					
					}