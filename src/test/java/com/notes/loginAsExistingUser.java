package com.notes;

import org.testng.annotations.Test;

public class loginAsExistingUser  {
	@Test
	public void login() {
		BaseClass obj = new BaseClass();
		obj.createToken("yogibijapur@gmail.com", "admin@123");
	}
}
