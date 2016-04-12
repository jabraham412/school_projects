Feature: As a registered Facebook user
	I want to be able to sign in after entering my e-mail and password
	In order to view my newsfeed
	
Scenario: Log in with incorrect valid e-mail address and incorrect password
	Given I am on the log in page https://www.facebook.com/
	And I input an incorrect valid email address and incorrect password
	When I click log in
	Then I should see "Incorrect Email"

Scenario: Log in with correct e-mail address and password
	Given I am on the log in page https://www.facebook.com/
	And I input the correct username and password
	When I click log in
	Then I should see "Welcome"

Scenario: Log in with incorrect valid e-mail address and correct password
	Given I am on the log in page https://www.facebook.com/
	And I input a correct valid email address and an incorrect password
	When I click log in
	Then I should see "Incorrect Email"

Scenario: Log in with incorrect password and correct e-mail address
	Given I am on the log in page https://www.facebook.com/
	And input an correct e-mail address and an incorrect password
	When I click log in
	Then I should see "The password you entered is incorrect"

Scenario: Log in with invalid email address
	Given I am on the log in page https://www.facebook.com/
	And I input an invalid unregistered e-mail address
	When I click log in
	Then I should see "Please Confirm Password"