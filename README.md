# money-transfer

# Technologies:
	dropwizard
	swagger
# Run:
	mvn clean install
	java -jar ./target/money-transfer.jar server configuration.yml

# swagger link:
	http://localhost:8080/swagger

# APIs:

    POST    /account 
    GET     /account/{accountId}
    PUT     /account/{accountId}/deposit/{amount}
    PUT     /account/{accountId}/withdraw/{amount} 
    POST    /transaction/transfer (com.revoult.moneytransfer.controller.TransactionController)
    GET     /swagger
    GET     /swagger.{type:json|yaml} (io.swagger.jaxrs.listing.ApiListingResource)






