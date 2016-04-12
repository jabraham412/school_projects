Feature: As an unregistered user
	I want to sign up for a new account with the site after entering valid information
	So that I can log in and use other features of the site

Scenario: sign up without entering any information.
	Given I am on the website https://m.facebook.com/r.php
	And I try to sign up without filling any field
	When I click sign up
	Then I should see "You must fill in all of the fields."

Scenario: sign up with an existing account
	Given I am on the website https://m.facebook.com/r.php
	And I input all the information correctly but with existing account
	When I click sign up
	Then I should see "It looks like you already have an account"

Scenario: sign up with password with only 4 characters
	Given I am on the website https://m.facebook.com/r.php
	And I input all the information correctly but with 0000 as password
	When I click sign up
	Then i should see "Your password must be at least 6 characters long. Please try another"

Scenario: sign up with incorrect email nor incorrect phone number.
	Given I am on the website https://m.facebook.com/r.php
	And I input all the information correctly but with abc as email or phone
	When I click sign up
	Then i should see "Please enter a valid email address or mobile number"

Scenario: sign up without filling First Name
	Given I am on the website https://m.facebook.com/r.php
	And I input all the information correctly but firstname
	When I click sign up
	Then i should see "You must in all of the fields"