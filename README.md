# IACscan

IACscan is a tool developed for detecting Improper Access Control vulnerabilities within web applications.

The user manual below provides a detailed guide on how to use the tool.

## Setup

### Endpoints

- Endpoints represent URLs within the target application which will be scanned for vulnerabilities.
- Adding an Endpoint to the scan is done by providing the URL and the HTTP method to be used for the request.
- POST, PUT and PATCH methods support the addition of a request body and content type header.

### Tokens

- Tokens are used to authenticate requests to the target application via HTTP headers.
- Adding a token to the scan is done by providing the token type, value and a label for identification purposes.
- E.g. adding a user with `Name: Admin`, `Header: JWT` and `Value: asdf` will authenticate each request with HTTP header: `JWT: asdf`.
- IACscan adds an unauthenticated role by default.

### UDA Policy

- The User Data Access Policy outlines the access control rules for the target application.
- This is defined for each token and endpoint pair, where "true" indicates that the token should have access to the endpoint, and "false" indicates that the token should not have access.
- The policy can easily be changed by selecting the desired pair, which automatically toggles the value.

## Features

### Endpoint Detection

### Evaluate Endpoints

### Access Control Overview