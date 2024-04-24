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

- The Endpoint Detection feature allows the user to detect new potentially vulnerable endpoints within the target application.
- The user is prompted to select a token for the detection process, which will be used to authenticate requests.
- The user can select which HTTP methods to scan for by selecting them from the list.
- Starting the process will highlight potentially active endpoints in red.
- Selecting an individual request will display the raw HTTP request and response in the side panel.
- Detected endpoints can be added to the "Endpoints" list.

### Evaluate Endpoints

- The Evaluate Endpoints page allows the user to evaluate the access control policy for each token and endpoint pair.
- Starting the evaluation process will populate the table with all communication between IACscan and the target.
- Selecting individual requests will display the raw HTTP request and response in the side panel.

### Access Control Overview

- The Access Control Overview page compares the UDA policy with the HTTP responses for each request.
- Requests which match the policy are displayed in green, while potentially vulnerable requests are displayed in orange.
- Requests which indicate an IAC vulnerable endpoint are displayed in red.
- Selecting individual requests will display the raw HTTP request and response in the side panel.