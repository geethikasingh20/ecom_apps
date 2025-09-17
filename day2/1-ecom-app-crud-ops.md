# I. Java & Maven Development Setup (VS Code)

## 1. Install Java Development Kit (JDK)
1. Download the latest JDK from [Adoptium](https://adoptium.net/) or [Oracle](https://www.oracle.com/java/technologies/downloads/).
2. Run the installer and follow the instructions.
3. Set the `JAVA_HOME` environment variable:
   - Windows: Search "Environment Variables" > Add new system variable `JAVA_HOME` pointing to your JDK folder (e.g., `C:\Program Files\Eclipse Adoptium\jdk-XX`).
   - Add JDK `bin` folder to your `PATH` variable.
4. Verify installation:
    ```powershell
    java -version
    ```

## 2. Install Maven
1. Download Maven from [here](https://maven.apache.org/download.cgi).
2. Extract the archive to a folder (e.g., `C:\Program Files\Apache\maven`).
3. Add the Maven `bin` folder to your `PATH` environment variable.
4. Verify installation:
    ```powershell
    mvn -version
    ```

## Java App Development IDE 
### 3a. Install "Spring Tools for Eclipse (Windows)"
* We will use STS (Spring Tool Suite) for Java App Development
1. Download and Install [Spring Tools for Eclipse (Windows)](https://spring.io/tools)
2. Let us start to use STS for Spring Boot App Development.

OR

### 3b. Install Visual Studio Code (VS Code)
* We will use VSC (Visual Studio Code) for React App Development
* And VSC will abe used optionally for Java Development as well.
1. Download and install [VS Code](https://code.visualstudio.com/).
2. Install `Thunder Client` VSC extension to test Java App's APIs
3. Open VS Code and install the following extensions:
   - [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
   - [Maven for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-maven)


## 4. Create a Java/Maven Project
1. Use [Spring Initializr](https://start.spring.io/) to generate a Spring Boot project (select Maven as build tool).
2. Download and extract the project.
3. Open the project folder in VS Code.

## 5. Run the Application
1. Open a `command prompt` or `terminal in VS Code`.
2. Run:
    ```powershell
    mvn spring-boot:run
    ```

## 6. Useful Links
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Java Documentation](https://docs.oracle.com/en/java/)

```
```

# II.Spring Boot CRUD Operations with MongoDB

## 1.  Create Spring Boot Project
Use [Spring Initializr](https://start.spring.io/) with:
- Dependencies: Spring Web, Spring Data MongoDB

**Detailed To Be filled in the `Spring Initializr`**
1. Project : Maven
2. Language : Java 
3. Spring Boot : 3.5.5.
4. Project meta data 
   * Group : com.mahesh
   * Artifact : ecomsystem
   * Name : ecomsystem 
   * Description : ecommerce system with admin and customer apps 
   * Package Name: com.mahesh.ecomsystem
   * Packaging : jar
   * Java : 21
5. Dependencies: Spring Web (WEB), Spring Data MongoDB (NOSQL) 

#### Here’s an explanation of each attribute in a Spring Boot project setup:
- **Group**: The base package or organization name (e.g., `com.mahesh`). Used for package naming and Maven coordinates.
- **Artifact**: The project’s unique name (e.g., `ecomsystem`). It becomes the name of the built JAR file.
- **Name**: The display name of the project (e.g., `ecomsystem`). Used in documentation and metadata.
- **Description**: A short summary of the project’s purpose (e.g., `ecommerce system with admin and customer apps`).
- **Package Name**: The root Java package for your code (e.g., `com.mahesh.ecomsystem`). All classes are placed under this package.
- **Packaging**: The output format, usually `jar` (Java ARchive) or `war` (Web ARchive).
- **Java**: The Java version to use (e.g., 21).

These attributes help organize, build, and identify your Spring Boot application.

#### Here are different examples of the Spring Initializr attributes for various project types:

**1. E-commerce Application**
- Group: `com.shoponline`
- Artifact: `ecommerce-app`
- Name: `ecommerce-app`
- Description: `Online shopping platform with cart and payment features`
- Package Name: `com.shoponline.ecommerce`
- Packaging: `jar`
- Java: `21`

**2. School Management System**
- Group: `org.school`
- Artifact: `school-mgmt`
- Name: `school-mgmt`
- Description: `Manages students, teachers, and classes`
- Package Name: `org.school.management`
- Packaging: `jar`
- Java: `17`


**3. Blog Platform**
- Group: `net.blogsite`
- Artifact: `blog-platform`
- Name: `blog-platform`
- Description: `A platform for creating and sharing blog posts`
- Package Name: `net.blogsite.platform`
- Packaging: `jar`
- Java: `21`


**4. Inventory Tracking System**
- Group: `com.inventory`
- Artifact: `inventory-tracker`
- Name: `inventory-tracker`
- Description: `Tracks products and stock levels`
- Package Name: `com.inventory.tracker`
- Packaging: `jar`
- Java: `17`


**5. Employee Directory**
- Group: `io.company`
- Artifact: `employee-directory`
- Name: `employee-directory`
- Description: `Directory of company employees with search features`
- Package Name: `io.company.directory`
- Packaging: `jar`
- Java: `21`

You can adjust these attributes to fit your project’s domain, organization, and purpose.


#### Here’s a brief explanation of each Spring Boot dependency:

- Spring Web (WEB):  
  Adds support for building web applications, including REST APIs, using Spring MVC. It provides controllers, routing, and HTTP handling.

- Spring Data MongoDB (NOSQL):  
  Enables integration with MongoDB databases. It simplifies data access, repository creation, and CRUD operations for MongoDB.

- spring-boot-starter-actuator:  
  Adds production-ready features to your app, such as health checks, metrics, monitoring, and management endpoints (e.g., /actuator/health, /actuator/metrics).

These dependencies help you build, connect, and monitor Spring Boot applications efficiently.

Or

```bash
mvn archetype:generate -DgroupId=com.example.demo -DartifactId=mongodb-crud -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```
 

### Add MongoDB Dependency (if not present)
```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

### Configure MongoDB Connection
```.properties
# src/main/resources/application.properties
spring.data.mongodb.uri=${MONGO_URI}
server.port=${PORT:8080}
```
* MONGO_URI = mongodb+srv://<username>:<password>@<cluster-url>/<dbName>?retryWrites=true&w=majority, is stored in env variable.
* `${MONGO_URI}` will be fetched from environment variables at runtime.
* No secrets are stored in the source code.
* ${PORT:8080} → means get the value of the environment variable PORT.
  * If PORT exists → use that value.
  * If PORT is not set → use 8080 as the default.
* **for local computer mongodb**
  * spring.data.mongodb.uri=${MONGO_URI} 
  * MONGO_URI = mongodb://localhost:27017/<dbName>, is stroed in env variable.
  * No secrets are stored in the source code.

### **Add Environment Variables Locally for Testing**
Set Environment Variables in Windows
* Press Win + S → search “Environment Variables” → click “Edit the system environment variables”.
* In System Properties → Environment Variables…
* Under User variables (for current user) or System variables (for all users), click New…
```
Variable name: MONGO_URI
Value: mongodb+srv://<username>:<password>@<cluster-url>/<dbName>?retryWrites=true&w=majority
```
* Click OK to save all.
* ⚠️ No spaces in names, make sure values are correct.

#### Restart Command Prompt
* Close `Command Prompt` completely.
* Reopen it, and open the `Command Prompt`.
* This ensures the `Command Prompt` loads the new environment variables.

#### Verify Environment Variables
* In `Command Prompt`, check your variables:
  * $> echo %MONGO_URI%
* You should see the values you set.
* If not, double-check the step `Add Environment Variables Locally for Testing`.

### Setup in Command Prompt
* open the `Command Prompt` 
```bash
mvn clean install
```

#### Here’s what each Maven command does in a Spring Boot project:

- mvn clean install  
  Cleans previous build files and compiles the project, runs tests, and packages the application (usually as a JAR or WAR). The result is placed in the `target` directory.

- mvn compile  
  Compiles the source code of the project (Java files to class files) but does not package or run tests.

- mvn spring-boot:run  
  Runs your Spring Boot application directly from the source code using Maven, without needing to build a JAR first. This is useful for development and testing.

These commands are commonly used to build, test, and run Java/Spring Boot applications using Maven.

* **Note**: If your app code is showing the syntax errors, restart your project visual studio code instance.


## 2. Code Development

### Create Model
```java
package com.mahesh.ecomsystem;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {
	@Id
    private String id;
    private String name;
    private String description;
    private String category;
    private String tags;
    private float price;
    private int stock;
    // setters getters constructors toString equals
}
```

### Create Repository
```java
package com.mahesh.ecomsystem;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
	
}
```

### Create Controller
```java
package com.mahesh.ecomsystem;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/products")
public class ProductController {
	@Autowired
	private ProductRepository repo;
	@PostMapping
	public Product create(@RequestBody Product product) {
		product.setId(null);
		Product savedProduct = repo.save(product);		
		return savedProduct;
	}
	@GetMapping
	public List<Product> findAll() {
		List<Product> products = repo.findAll();
		return products;
	}
	@GetMapping(path="/{id}")
	public Product findById(@PathVariable String id) {
		Product product = repo.findById(id).get();
		return product;
	}
	@PutMapping(path="/{id}")
	public Product update(@PathVariable String id, @RequestBody Product product) {
		Product oldProduct = repo.findById(id).get();
		oldProduct.setName(product.getName());
		oldProduct.setDescription(product.getDescription());
		oldProduct.setCategory(product.getCategory());
		oldProduct.setTags(product.getTags());
		oldProduct.setStock(product.getStock());
		oldProduct.setPrice(product.getPrice());
		//
		Product updatedProduct = repo.save(oldProduct);
		return updatedProduct;
	}
	@DeleteMapping(path="{id}")
	public boolean delete(@PathVariable String id) { 
		Optional<Product> optionalProduct = repo.findById(id);
		if(optionalProduct.isEmpty()) {
			return false;
		}
		repo.deleteById(id);
		return true;
	}
}
```

### Run Application
```bash
mvn spring-boot:run

java -jar target\app-xxx.jar
```

## 3. Test Endpoints
Use `⚡ VSC Thunder Client` or `Postman` or `curl` to test CRUD operations at `http://localhost:8080/products`.


**URL End Points**
```
Method + URL                                                         
Verb   + Noun

# Operation: find All Products 
GET     http://localhost:8080/products        

# Operation: create Product                      
POST    http://localhost:8080/products                               
REQUEST BODY: 
{ "name": "Laptop Pro 17", "description": "High performance laptop", "category": "Electronics", 
"price": 1200, "stock": 10, "tags": "laptop, tech,computer" }

# Operation: find Product By Id=68b15920505c81a9a05552e9  
GET     http://localhost:8080/products/68b15920505c81a9a05552e9 

# Operation: update Product By Id=68b15920505c81a9a05552e9    
PUT     http://localhost:8080/products/68b15920505c81a9a05552e9     
REQUEST BODY: 
{ "name": "Laptop Pro 17", "description": "High performance laptop", "category": "Electronics", 
"price": 1200, "stock": 10, "tags": "laptop, tech,computer" }

# Operation:  delete Product By Id=68b15920505c81a9a05552e9
DELETE  http://localhost:8080/products/68b15920505c81a9a05552e9    
```

### To debug the application (attch the below in properties file) and rerun the app
* in application.proiperties
```
...
# Show request mapping and controller method handling
logging.level.org.springframework.web=DEBUG

# (Optional) If you also want to see request details (payload, headers)
logging.level.org.springframework.web.filter.CommonsRequestLoggingFilter=DEBUG
```