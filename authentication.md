## API Header required

| Header Name | Header Value | Description |
| --- | --- | --- |
| Authorization | AimMatic ***SecurePlaceApiKey**:**Signature*** | The Authentication Header where backend use to validate the request. See below how to calculate signature. |
| Content-Type | Example: **application/json** | The content type of the resource in case the request content in the body. |
| Content-MD5 | Example: 8746a90fbd71C0f73be4b1196340c426 | The base64 encoded 128-bit MD5 digest of the message according to [RFC 1864](https://www.ietf.org/rfc/rfc1864.txt). |
| Date | Example: Mon, 02 Jan 2006 15:04:05 GMT | The current date and time according to the requester and following [RFC 1123](http://tools.ietf.org/html/rfc1123) |
| X-PlaceNext-Date | Example: Mon, 02 Jan 2006 15:04:05 GMT | The current date and time according to the requester and following [RFC 1123](http://tools.ietf.org/html/rfc1123) |

## Calculate Authenticate Signature

To Calculate API signature which then validate in backend follow the formula below:

Additional link:
1. [Base64](https://tools.ietf.org/html/rfc4648)
2. [HMAC-SHA256](https://tools.ietf.org/html/rfc4634)

**Note**: we will be using Base64 standard encoding/decoding which mean by default
encoder will append the character padding and decoder will remove padding

```
Authorization = "AimMatic" + " " + SecurePlaceApiKey + ":" + Signature

Signature = Base64(HMAC-SHA256(SecurePlaceApiSecretKey, dataToSign))

dataToSign = Content-MD5 + "\n" +
                 Content-Type + "\n" +
                 Date + "\n"
                 headerConcat + "\n"
                 url
```

### headerConcat

headerConcat is the concatenate of all header with the pair key value.

**Rule:**

| Order | Rule | Sample data | Description |
| --- | --- | --- | --- |
| 1 | all key is lower case | X-Placenext-Date | The header X-Placenext-Date should transform into x-placenext-date |
| 2 | merge identical key | X-Placenext-A: 123, X-Placenext-A: 456 | The two header of X-SecurePlace-A should merge into X-Placenext-A: 123,456. Of course, rule number 1 is also apply. |
| 3 | remove all space round colon | X-Placenext-A: 123 | The whole string should transform into X-Placenext-A:123 and rule number 1 and number 2 also apply |
| 4 | all header key sorted | | concatenate the header with key alphabetic order from a to b |


Example: Below is available header in the request of course it exclude **Authorization**

- X-Placenext-Date: Mon, 02 Jan 2006 15:04:05 GMT
- X-Placenext-A: abc
- X-Placenext-B: 123

Then headerConcat is:

```
headerConcat = "x-placenext-date:Mon, 02 Jan 2006 15:04:05 GMT" + "x-placenext-a:abc" + "x-placenext-b:123"
```

### URL

The value of the url is:

```
url = "https://" + host + "/" + apiEndpoint + query
query = "?" + queryKey + "=" + queryValue
```

Example: Url of import data endpoint should be

`url = "https://api.aimmaitc.com/v1/import/data"`

Note: because endpoint does not have query parameter that query value is empty.
