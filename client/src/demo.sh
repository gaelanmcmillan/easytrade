curl -v -X PATCH 'http://localhost:8080/api/v1/stock/buy' -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhIiwiaWF0IjoxNjc5ODY2ODA2LCJleHAiOjE2Nzk4Njc3MDZ9.O_nrEdnFL9G8OWpqPa0b9JqlnbP_beqzz8wyfQi-1rQ' -d '{
    "symbol": "AAPL"
    "quantity": 15
}'
