# *EasyTrade*

Paper Money Stock Market System

Buy, sell, and view stocks in a low-pressure, paper money environment.

# Server
The backend server is is a Spring Boot application which follows the M(V)C architecture. *V* is parenthesized because the View is really going to be powered by our external frontend client.
Authentication is implemented with Spring Security and the [JSON Web Token Protocol](https://jwt.io/).
## Featues:
### API
- [x] Signup with first name, last name, email and password
- [x] Login with email and password
- [x] User Model with a "USER" role.
- [ ] "ADMIN" role with advanced permissions.
- [x] Public, no-authorization-required endpoints for Signup, Login and Logout
- [x] Secured endpoints, requiring a valid JWT to access resources.
