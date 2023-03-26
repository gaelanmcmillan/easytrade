curl -i -X OPTIONS -H "Origin: http://localhost:3000" \
    -H 'Access-Control-Request-Method: GET' \
    -H 'Access-Control-Request-Headers: Content-Type, Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2JieSIsImlhdCI6MTY3OTc5MTM1OCwiZXhwIjoxNjc5NzkyMjU4fQ.WOlrqR3pKqbxFbt11Hv29vZ1Qugk0RBA8ZgTZYyx4po' \
    "http://localhost:8080/api/v1/demo"
