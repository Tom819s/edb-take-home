Feature: Redaction Matcher

  Scenario: Redact fields that match password regular expression
    * def inputData =
      """
      {
        "users": [
          {
            "name": "Portobello McSmith",
            "email": "p@mcsmith.com",
            "password": "mushrooms"
          },
          {
            "name": "Cucumber Daniels",
            "email": "c@daniels.com",
            "password": "crunchy"
          }
        ]
      }
      """

    * def expectedOutput =
      """
      {
        "users": [
          {
            "name": "Portobello McSmith",
            "email": "p@mcsmith.com",
            "password": "REDACTED"
          },
          {
            "name": "Cucumber Daniels",
            "email": "c@daniels.com",
            "password": "REDACTED"
          }
        ]
      }
      """

    Given url 'http://localhost:8080/'
    And request inputData
    When method post
    Then status 200

    * def firstPassword = response.users[0].password
    * def secondPassword = response.users[1].password
    * print 'First password field: ', firstPassword
    * print 'Second password field: ', secondPassword
    Then match response == expectedOutput