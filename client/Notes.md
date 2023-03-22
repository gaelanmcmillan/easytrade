Questions:
1. Token seems to store stuff about the user4 in base 64, then some hash.
	How should we verify the user, do we need to send all of info or just the hash
	"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2JieSIsImlhdCI6MTY3OTQ5Mz
	EwNCwiZXhwIjoxNjc5NDk0MDA0fQ.HWm8169RXnaPI61Sgl-FgIdbWPwEtGd5xaUsiQaM2Y0"
				    ^ This will seperate the base64 from the hash
General Notes:
1. sessionStorage.clear(), to clear login token without exiting page

