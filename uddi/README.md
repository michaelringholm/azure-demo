# Convert Eclipse project to a faceted project and add the Dynamic Web Module facet to enable deployment to Azure from within eclipse. 

# When running locally with Tomcat this page will be accessible via http://localhost:8080/stock-prices.

# When running locally via Jetty this page will be accessible via http://localhost:8080/.

# Controllers has been setup to listen for request with the .ctl extension.

# Call upsert remotely
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"name":"Sports Results", "domain":"Sports", "subDomain":"Statistics", "endpoint":"http://sports-service.azure.com"}' https://service-registry-dot-stelinno-dev.appspot.com/upsert