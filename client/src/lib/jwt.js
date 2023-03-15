/**
 * A collection of JSON Web Token (JWT) utilities
 */

/**
 * Returns the payload object as extracted from the given token literal.
 * @param {*} tokenLiteral 
 * @returns 
 */
const extractPayload = (tokenLiteral) => {
  // JWT is of the form `header.payload.signature`
  const base64Payload = tokenLiteral.split('.')[1];
  console.log(`Base64 Payload: ${base64Payload}`)
  const jsonStringPayload = decodeURIComponent(window.atob(base64Payload))
  console.log(`As JSON String: ${jsonStringPayload}`)
  
  return JSON.parse(jsonStringPayload);
}

const JWT = {
  extractPayload
};

export default JWT;