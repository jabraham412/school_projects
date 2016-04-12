Feature: As a registered and logged in Facebook user
	I want to be able to add personal information to the “About” section
	So that I can reflect any updates or add new personal information

Scenario: Add an address
	Given that I am logged into my facebook account
	And I entered an address under “Address in the “About” section
	When I click on “save changes”
	Then an entry with that information should be added to the “About” section

Scenario: Add a favorite quote
	Given that I am logged into my facebook account
	And I entered a favorite quote under “Farvorite Quote” in the “About” section
	When I click on “save changes”
	Then an entry with that information should be added to the “About” section

Scenario: Add a bio
	Given that I am logged into my facebook account
	And I entered a new bio under “About You” in the “About” section
	When I click on “save changes”
	Then an entry with that information should be added to the “About” section

Scenario: Add a relationship status
	Given that I am logged into my facebook account
	And I entered a new relationship status under “Relationship Status” in the “About” section
	When I click on “save changes”
	Then an entry with that information should be added to the “About” section

Scenario: Add a mobile phone number
	Given that I am logged into my facebook account
	And I entered a new number under “Add a mobile phone” in the “About” section
	When I click on “save changes”
	Then an entry with that information should be added to the “About” section