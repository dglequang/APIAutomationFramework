
Feature: Get weather of a city by using city name
  Description: Given a city name, get a weather of that city

  Background:
  @regression1
  Scenario Outline: Search weather with multiple cities
    Given A city name: "<CityName>"
    When  Use GET method with city name
    Then  Response code is "200"
    And Response body is correct

    Examples:
      |CityName|
      |London  |
      |Seoul   |

  @regression2
  Scenario: Search weather in your city
    Given A city name: "London"
    When  Use GET method with city name
    Then  Response code is "200"
    And Response body is correct as table
      |cod|name   |country|longitude|latitude|
      |200|London |GB     |-0.1257  |51.5085 |






