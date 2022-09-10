Feature: CRUD of /posts endpoint

  Background:
    Given user creates a user

  Scenario Outline: Create a post in a /posts endpoint
    When user creates a post with title "<title>" and body "<body>"
    Then check if post is created with title "<title>" and body "<body>"

    Examples:
      | title                            | body                                                                                                                           |
      | DevX Title of the book Automated | Confugo copia tamisium. Volutabrum curvo cado. Cito tam consectetur. Acervus administratio temporibus. Reprehenderit assumenda |
