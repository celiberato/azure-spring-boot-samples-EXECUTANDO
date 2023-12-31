---
page_type: sample
languages:
- java
products:
- azure-database-postgresql
name: Store and retrieve data in Azure Database for PostgreSQL in Spring Boot Application
description: This example demonstrates how to use passwordless connections to store and retrieve data in Azure Database for PostgreSQL in a Spring Boot application.
---

# Store and retrieve data in Azure Database for PostgreSQL in Spring Boot Application

This code sample demonstrates how to use [passwordless connections](https://learn.microsoft.com/azure/developer/intro/passwordless-overview) to store and retrieve data in [Azure Database for PostgreSQL](https://azure.microsoft.com/products/postgresql/) using the [Spring Cloud Azure Starter JDBC PostgreSQL](https://learn.microsoft.com/azure/developer/java/spring-framework/spring-cloud-azure?tabs=maven#postgresql-support).

## What You Will Build

You will build an application to store and retrieve data in Azure Database for PostgreSQL.

## What You Need

- [An Azure subscription](https://azure.microsoft.com/free/)
- [Terraform](https://www.terraform.io/)
- [Azure CLI](https://docs.microsoft.com/cli/azure/install-azure-cli)
- [JDK8](https://www.oracle.com/java/technologies/downloads/) or later
- [Maven](https://maven.apache.org/)
- [cURL](https://curl.se/) or a similar HTTP utility to test functionality.
- [PostgreSQL command line client](https://www.postgresql.org/download/)
- You can also import the code straight into your IDE:
    - [IntelliJ IDEA](https://www.jetbrains.com/idea/download)

## Provision Azure Resources Required to Run This Sample
This sample will create Azure resources using Terraform. 

### Authenticate Using the Azure CLI
Terraform must authenticate to Azure to create infrastructure.

In your terminal, use the Azure CLI tool to setup your account permissions locally.

```shell
az login
```

Your browser window will open and you will be prompted to enter your Azure login credentials. After successful authentication, your terminal will display your subscription information. You do not need to save this output as it is saved in your system for Terraform to use.

```shell
You have logged in. Now let us find all the subscriptions to which you have access...

[
  {
    "cloudName": "AzureCloud",
    "homeTenantId": "home-Tenant-Id",
    "id": "subscription-id",
    "isDefault": true,
    "managedByTenants": [],
    "name": "Subscription-Name",
    "state": "Enabled",
    "tenantId": "0envbwi39-TenantId",
    "user": {
      "name": "your-username@domain.com",
      "type": "user"
    }
  }
]
```

If you have more than one subscription, specify the subscription-id you want to use with command below:
```shell
az account set --subscription <your-subscription-id>
```

### Provision the Resources

After login Azure CLI with your account, now you can use the terraform script to create Azure Resources.

| Run with Bash                                      |  Run with Powershell                              |
|----------------------------------------------------|---------------------------------------------------|
| `terraform -chdir=./terraform init`                |  `terraform -chdir=terraform init`                |
| `terraform -chdir=./terraform apply -auto-approve` |  `terraform -chdir=terraform apply -auto-approve` |

It may take a few minutes to run the script. After successful running, you will see prompt information like below:

```shell

random_password.password: Creating...
azurecaf_name.postgresql_server_name: Creating...
azurecaf_name.resource_group: Creating...
...
random_password.password: Creation complete after 0s [id=none]
azurerm_resource_group.main: Creating...
...
azurerm_postgresql_flexible_server.postgresql_server: Creating...
...
azurerm_postgresql_flexible_server_active_directory_administrator.current_aad_user_admin: Creating...
azurerm_postgresql_flexible_server_firewall_rule.firewall_clientip: Creating...
azurerm_postgresql_flexible_server_database.database: Creating...
...
Apply complete! Resources: 8 added, 0 changed, 0 destroyed.

```

You can go to [Azure portal](https://ms.portal.azure.com/) in your web browser to check the resources you created.

### Create a PostgreSQL non-admin user and grant permission

Run the following command to create a non-admin user by the Azure Cli login user:

| Run with Bash                                 | Run with Powershell                   |
|-----------------------------------------------|---------------------------------------|
| `source ./terraform/create_non_admin_user.sh` | `terraform\create_non_admin_user.ps1` |

### Export Output to Your Local Environment
Running the command below to export environment values:

| Run with Bash                     | Run with Powershell       |
|-----------------------------------|---------------------------|
| `source ./terraform/setup_env.sh` | `terraform\setup_env.ps1` |

If you want to run the sample in debug mode, you can save the output value.

```shell
AZ_DATABASE_SERVER_NAME=...
AZ_DATABASE_NAME=...
AZ_POSTGRESQL_AD_NON_ADMIN_USERNAME=...

```

## Run Locally

### Run the sample with Maven

In your terminal, run `mvn clean spring-boot:run`.

```shell
mvn clean spring-boot:run
```

### Run the sample in IDEs

Replace the placeholders in the sample's `application.yaml` file, run the application.

### Verify This Sample

1.1 Create a new "todo" item in the database.

```bash
curl --header "Content-Type: application/json" \
    --request POST \
    --data '{"description":"configuration","details":"congratulations, you have set up JDBC correctly!","done": "true"}' \
    http://127.0.0.1:8080
``````

1.2 Retrieve the data by using a new cURL request as follows.

```shell
curl http://127.0.0.1:8080
```

Will return the list of "todo" items, including the item you've created, as follows:

```json
[{"id":1,"description":"configuration","details":"congratulations, you have set up correctly!","done":true}]
```

## Run on Azure hosting environment

This sample can only run in local, if you want run in Azure hosting environment, please refer this [docs](https://learn.microsoft.com/azure/developer/java/spring-framework/migrate-postgresql-to-passwordless-connection?tabs=sign-in-azure-cli%2Cjava%2Capp-service%2Cassign-role-service-connector#4-configure-the-azure-hosting-environment).

## Clean Up Resources
After running the sample, if you don't want to run the sample anymore, remember to destroy the Azure resources you created to avoid unnecessary billing.

The terraform destroy command terminates resources managed by your Terraform project.   
To destroy the resources you created.

| Run with Bash                                                                                            | Run with Powershell                                                                                    |
|----------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|
| `az group delete --resource-group $(terraform -chdir=./terraform output -raw resource_group_name) --yes` | `az group delete --resource-group $(terraform -chdir=terraform output -raw resource_group_name) --yes` |

## Deploy to Azure Spring Apps

Now that you have the Spring Boot application running locally, it's time to move it to production. [Azure Spring Apps](https://learn.microsoft.com/azure/spring-apps/overview) makes it easy to deploy Spring Boot applications to Azure without any code changes. The service manages the infrastructure of Spring applications so developers can focus on their code. Azure Spring Apps provides lifecycle management using comprehensive monitoring and diagnostics, configuration management, service discovery, CI/CD integration, blue-green deployments, and more. To deploy your application to Azure Spring Apps, see [Deploy your first application to Azure Spring Apps](https://learn.microsoft.com/azure/spring-apps/quickstart?tabs=Azure-CLI).
