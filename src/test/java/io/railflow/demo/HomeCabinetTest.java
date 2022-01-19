package io.railflow.demo;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeCabinetTest {

	@Test
	public void change_password() {
		throw new RuntimeException("Unexpected exception");
	}

	@Test
	public void change_password_fail_on_incorrect_confirmation() {
		System.out.println("change password failed with incorrect confirmation");
	}

	@Test
	public void change_password_fail_on_incorrect_password() {
		System.out.println("change password fail with incorrect password");
	}

	@Test
	public void change_email() {
		System.out.println("Change email");
	}

	@Test
	public void change_email_fail_on_invalid_email() {
		Assert.fail("Email is empty");
	}
}