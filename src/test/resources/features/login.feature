Feature: SauceDemo login and shopping cart

  @smoke
  Scenario: A valid user can log in
    Given the SauceDemo login page is open
    When I log in with username "standard_user" and password "secret_sauce"
    Then the products page is displayed

  @smoke
  Scenario Outline: Invalid credentials show an error
    Given the SauceDemo login page is open
    When I log in with username "<username>" and password "<password>"
    Then the login error contains "Username and password do not match any user in this service"

    Examples:
      | username      | password     |
      | standard_user | wrong_password |
      | unknown_user  | secret_sauce |

  @regression
  Scenario: A user can add an item to the cart
    Given I am logged in as "standard_user"
    When I add "Sauce Labs Backpack" to the cart
    Then the cart contains "Sauce Labs Backpack"