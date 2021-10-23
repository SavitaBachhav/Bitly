package com.qa.rest.tests;

import static io.restassured.RestAssured.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class GetCallBDD {
	
	//Positive Test:GET /groups/{group_guid}

	@Test(priority=0)	
	public void get_Groups(){

		String response =given().log().all().
				header("Authorization","Bearer e9023949a940f7093b5b6ce6769b58b02de5bae8").
				when().
				get("https://api-ssl.bitly.com/v4/groups/BlaljVOEOhe/bitlinks").
				then().
				assertThat().
				statusCode(200).
				and().
				body("links.id",hasSize(4)).
				header("Content-Type",equalTo("application/json")).extract().response().asString();

		System.out.println(response);

	}
	//Positive Test:GET /groups/{group_guid}/bitlinks/{sort}
	//Can add more testcases for different unit values (day,hour,minute,week)
	@Test(priority=1)
	public void get_Groups_Sort(){

		String response_sort =given().log().all().
				header("Authorization","Bearer e9023949a940f7093b5b6ce6769b58b02de5bae8").
				when().
				get("https://api-ssl.bitly.com/v4/groups/BlaljVOEOhe/bitlinks/clicks?unit=month&unit_reference=2021-10-02T15:04:05-0700").
				then().
				assertThat().
				statusCode(200).
				and().
				header("Content-Type",equalTo("application/json")).extract().response().asString();

		System.out.println(response_sort);


	}
	//POST /bitlinks
	@Test(priority=2)
	public void post_CreateBitlink(){

		String response1 =given().log().all().

				header("Authorization","Bearer e9023949a940f7093b5b6ce6769b58b02de5bae8").
				header("Content-Type","application/json")
				.body("{\r\n" + 
						"    \"long_url\": \"https://www.youtube.com/watch?v=W0eqwUU15Eg\",\r\n" + 
						"    \"domain\": \"bit.ly\",\r\n" + 
						"    \"group_guid\": \"Blak9TAfuh3\",\r\n" + 
						"    \"title\": \"Agile\",\r\n" + 
						"    \"tags\": [],\r\n" + 
						"    \"deeplinks\": []\r\n" + 
						"}").when().post("https://api-ssl.bitly.com/v4/bitlinks/")
				.then().
				assertThat().
				statusCode(201).extract().response().asString();
		System.out.println(response1);

		JsonPath js = new JsonPath(response1);
		String title = js.getString("title");
		System.out.println(title);
		Assert.assertEquals(title, "HTTP CLIENT");
	}
	
	//Negative Test:GET /groups/{group_guid} - Size - Quantity of the items testcase with invalid size
	//We can write more testcases here for Boundry Value analysis
	@Test(enabled = false)	
	public void Get_InvalidGroupSize(){

		String response =given().log().all().
				header("Authorization","Bearer e9023949a940f7093b5b6ce6769b58b02de5bae8").
				when().
				get("https://api-ssl.bitly.com/v4/groups/BlaljVOEOhe/bitlinks").
				then().
				assertThat().
				statusCode(200).
				and().
				body("links.id",hasSize(51)).
				header("Content-Type",equalTo("application/json")).extract().response().asString();

		System.out.println(response);

	}
	
	//Negative Test:GET /groups/{group_guid} - Test for invalid GroupId
	@Test(enabled = false)	
	public void Gopid_Validation(){

		String response =given().log().all().
				header("Authorization","Bearer e9023949a940f7093b5b6ce6769b58b02de5bae8").
				when().
				get("https://api-ssl.bitly.com/v4/groups/BlaljVOEOhesavi/bitlinks").
				then().
				assertThat().
				statusCode(200).
				and().
				body("links.id",hasSize(4)).
				header("Content-Type",equalTo("application/json")).extract().response().asString();

		System.out.println(response);

	}
	
	//Negative Test:GET /groups/{group_guid}/bitlinks/{sort} - Invalid value of Unit like second
	@Test(enabled = false)
	public void invalidUnit(){

		String response_sort =given().log().all().
				header("Authorization","Bearer e9023949a940f7093b5b6ce6769b58b02de5bae8").
				when().
				get("https://api-ssl.bitly.com/v4/groups/BlaljVOEOhe/bitlinks/clicks?unit=Second&unit_reference=2021-10-02T15:04:05-0700").
				then().
				assertThat().
				statusCode(200).
				and().
				header("Content-Type",equalTo("application/json")).extract().response().asString();

		System.out.println(response_sort);


	}
}
