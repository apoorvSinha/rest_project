#Feature: Validating place APIs
#
#  Scenario Outline: verify if place is being successfully added
#    Given add place API payload with "<name>" "<language>"  "<address>"
#    When user calls "addPlaceAPI" with "POST" http request
#    Then the API call is success with status code 200
#    And "status" should in response body is "OK"
#    And "scope" should in response body is "APP"
#
#    Examples:
#      | name    | language | address            |
#      | AAhouse | English  | World cross center |
#      | BcHouse | Hindi    | Old york centre    |
