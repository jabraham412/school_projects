Feature: As a registered and logged in Facebook user
	I want be able to receive notifications
	So that I know if something happened on my account

Scenario: Receive a notification when a user sent me a friend request
	Given I am logged into my facebook account
	And I can see my newsfeed
	When a user sends me a friend request
	Then a red counter should appear at the top of the page over the users icon
	Then the friend request is shown when the icon is clicked

Scenario: Receive a notification when a message has been sent to my inbox
	Given I am logged into my facebook account
	And I can see my newsfeed
	When a user send me a message to my inbox
	Then a red counter should appear at the top of the page over the dialog icon
	Then the message is shown when the icon is clicked
	
Scenario: Receive a notification when a post has been shared on my wall
	Given I am logged into my facebook account
	And I can see my newsfeed
	When a user shares a post on my wall
	Then a red counter should appear at the top of the page over the globe icon
	Then the notification is shown when the icon is clicked
	
Scenario: Receive a notification when one of my shared posts has been liked by a user
	Given I am logged into my facebook account
	And I can see my newsfeed
	When a user likes a post that I previously shared
	Then a red counter should appear at the top of the page over the globe icon
	Then the notification is shown when the icon is clicked
	
Scenario: Receive a notification when one of my pictures has been liked by a user
	Given I am logged into my facebook account
	And I can see my newsfeed
	When a user likes a picture that I previously posted
	Then a red counter should appear at the top of the page over the globe icon
	Then the notification is shown when the icon is clicked