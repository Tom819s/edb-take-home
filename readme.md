#EDB Take Home Assignment:

To Run Unit and Integration Tests:
`mvn test`

Build Command `mvn clean install`

To Override Default Port of `8080`: Add the following flag to command line argument: `--server.port=xxxx`

To Override Default Regular Expression for Redaction: Update application.properties or append `--regex={pattern}` to
start command

One can use the following command to send data to your listening port, to debug your
application

```
curl -X POST -H "Content-Type: application/json" --data '{"username":"xyz","password":"xyz"}'
http://localhost:8080/
```

## Deliverables and Time Investment

A well tested application written in Go or Java that opens a listening port, accepts JSON POST
HTTP requests, and emits valid scrubbed JSON to stdout.
Please share this application via a private repository on GitHub. The exercise should be
timeboxed to 2-3 hours max, and isn’t a pass/fail situation, but will be used for context in next
round interview discussion.