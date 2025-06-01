## Handling `/.well-known/jwks.json` for JWT Verification

## Overview

* When a Resource Server needs to verify a JWT (JSON Web Token), it relies on a set of public keys provided by the identity provider (or authorization server).
* This process involves fetching a JWKS (JSON Web Key Set) from a specified endpoint, using it to match and construct the appropriate public key, and then verifying the JWT signature.

## 1. Issuer Provides a JWKS Endpoint

The identity provider or authorization server exposes a JWKS endpoint at a URL such as `https://your-issuer/.well-known/jwks.json`. This endpoint provides a JSON object containing public keys used for signing JWTs.

### Example JWKS Entry

```json
{
  "keys": [
    {
      "kid": "abc123",
      "kty": "RSA",
      "alg": "RS256",
      "n": "base64urlencodedModulus",
      "e": "base64urlencodedExponent"
    }
  ]
}
```

## 2. Resource Server Fetches JWKS
```yml
spring:
  security:
    oauth2:
      resource-server:
        jwt:
          issuer-uri: http://localhost:8083/api/auth
          jwk-set-uri: http://localhost:8083/api/auth/.well-known/jwks.json
```
The Resource Server fetches the JWKS from this endpoint. This is typically done once at startup or periodically to ensure the JWKS is up-to-date.

## 3. Key Matching
* When a JWT is received, extract the kid (key ID) from the JWT header.
```json
{
  "alg": "RS256",
  "typ": "JWT",
  "kid": "abc123"
}
```
* Locate the corresponding key in the JWKS using the kid.
```json
{
  "keys": [
    {
      "kid": "abc123",
      "kty": "RSA",
      "alg": "RS256",
      "n": "oahUIeodkT_rjAk74N...",
      "e": "AQAB"
    },
    {
      "kid": "def456",
      "kty": "RSA",
      "alg": "RS256",
      "n": "0Qb0qz9ciQBkLkIjY0...",
      "e": "AQAB"
    }
  ]
}
```

## 4. Construct the Public Key
For RSA Keys
* Extract n (modulus) and e (exponent) from the JWKS.
* Decode n and e from Base64URL encoding.
* Construct the RSA public key.

## 5. Verify the JWT Signature
Use the constructed public key to verify the JWT’s signature.


## JWT Validation Flow Hierarchy

1. **SecurityConfig**
    - Configures the security filter chain and sets up the OAuth2 resource server to handle JWT validation.

    - Delegates to:

2. **BearerTokenAuthenticationFilter**
    - Extracts the Bearer token (JWT) from the `Authorization` header of incoming HTTP requests.

    - Delegates to:

3. **JwtAuthenticationProvider**
    - Uses the `JwtDecoder` to validate the JWT token.
    - If the token is valid, it creates a `JwtAuthenticationToken` which holds the authentication information.

    - Utilizes:

   3.1 **JwtDecoder** (Interface)
    - Responsible for decoding and verifying the JWT token.

    - Common Implementation:

    - **NimbusJwtDecoder**
        - The default implementation provided by Spring Security for JWT validation.
        - Fetches the public key from JWKS (JSON Web Key Set) if configured and uses it to verify the JWT's signature.

5. **JwtAuthenticationToken**
    - Represents the authenticated user after successful JWT validation.
    - Populated with the claims extracted from the JWT, including roles and other authorities.

6. **SecurityContextHolder**
- After the `JwtAuthenticationToken` is created and holds the authenticated user's information, the authentication is set in the `SecurityContextHolder`. 
- This typically happens in the `BearerTokenAuthenticationFilter`, which calls `SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);` after successful validation by the `JwtAuthenticationProvider`.