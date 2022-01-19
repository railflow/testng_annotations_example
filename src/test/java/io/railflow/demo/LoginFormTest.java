package io.railflow.demo;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginFormTest {

	@Test
	public void login_correct_credentials() {
		System.out.println("login with correct credentials");
	}

	@Test
	public void login_error_incorrect_username() {
		Assert.fail("Error message was not shown");
	}

	@Test
	public void login_error_incorrect_password() {
		throw new RuntimeException("Unexpected exception");
	}

	@Test
	public void login_rememberMe_is_checked() {
		System.out.println("login with remember me set to true");
	}

	@Test(enabled = false)
	public void login_failed_service_unavailable() {
		System.out.println("login failed");
	}

}