# III.Spring Boot - Fill Gap - By Seperation of Concerns, Exception Handling, Logging and Unit Test
## 1. **We will have `separation of concerns`**
   * All logic is in the controller. 
   * In enterprise code, you normally have:
     * `Controller` → Handles HTTP requests/responses only.
     * `Service` → Contains business logic.
     * `Repository` → Talks to DB.
   * This is called `separation of concerns`.
## 2. Define Exceptions for `Product Service`
### **No error handling**
   * `.get()` on `Optional` → throws `NoSuchElementException` if product doesn’t exist.
   * No custom exceptions → clients get ugly stack traces instead of proper error responses.

### Fix to define custom exceptions
**ResourceNotFoundException.java**
```
package com.mahesh.ecomsystem;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg) { super(msg); }
}

```

**BusinessException.java**
```
package com.mahesh.ecomsystem;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BusinessException extends RuntimeException {
    public BusinessException(String msg) { super(msg); }
}
```

**GlobalExceptionHandler.java**
```
package com.mahesh.ecomsystem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusiness(BusinessException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
```
## 3. Product Service + Attach Business Rules in `Create Product` operation + Handles Exception + Logging
### **No validation / business rules**
   * We accept any product JSON, even invalid (`price = -100`, `stock = -50`, missing `name`, etc.).

### **No logging**
   * We don’t log requests/responses or important lifecycle events (e.g., product created, deleted).

### Fix to have validations, log informations and errors, handle exceptions
**ProductService.java**
```
package com.mahesh.ecomsystem;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private ProductRepository repo;
	
	public Product create(Product product) {
		log.info("Creating product: {}", product.getName());
		product.setId(null);
		validate(product);
		Product savedProduct = repo.save(product);		
		return savedProduct;
	}
	public List<Product> findAll() {
		log.info("Finding All Products");
		List<Product> products = repo.findAll();
		return products;
	}
	public Product findById(String id) {
		log.info("Finding Product By id {}", id);
		Optional<Product> optionalProduct = repo.findById(id);
		if(optionalProduct.isEmpty()) {
			log.error("Attempted to find non-existing product id: " + id);
			throw new ResourceNotFoundException("Product not found: " + id);
		}
		
		return optionalProduct.get();
	}
	public Product update(String id, Product product) {
		Optional<Product> optionalProduct = repo.findById(id);
		if(optionalProduct.isEmpty()) {
			log.error("Attempted to update non-existing product id: " + id);
			throw new ResourceNotFoundException("Product not found: " + id);
		}
		log.info("Updating Product By id {}", id);
		Product oldProduct = optionalProduct.get();
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
	public boolean delete(String id) { 
		Optional<Product> optionalProduct = repo.findById(id);
		if(optionalProduct.isEmpty()) {
			log.error("Attempted to delete non-existing product id: " + id);
			throw new ResourceNotFoundException("Product not found: " + id);
		}
		log.info("Deleting Product By id {}", id);
		repo.deleteById(id);
		return true;
	}
	
	private void validate(Product product) {
        if (product.getPrice() < 0) { 
        	log.error("Price cannot be negative");
        	throw new BusinessException("Price cannot be negative");
        }
        if (product.getStock() < 0) { 
        	log.error("Stock cannot be negative");
        	throw new BusinessException("Stock cannot be negative");
        }
    }
}
```

## 4. Use Product Service in the Prdouct Controller 
* Use `ResponseEntity` in Controller
**ProductController.java**
```
package com.mahesh.ecomsystem;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	private ProductService service;
	@PostMapping
	public ResponseEntity<Product> create(@RequestBody Product product) {
		Product savedProduct = service.create(product);		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
	}
	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		List<Product> products = service.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(products);
	}
	@GetMapping(path="/{id}")
	public ResponseEntity<Product> findById(@PathVariable String id) {
		Product product = service.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}
	@PutMapping(path="/{id}")
	public ResponseEntity<Product> update(@PathVariable String id, @RequestBody Product product) {
		Product updatedProduct = service.update(id, product);
		return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
	}
	@DeleteMapping(path="{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) { 
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
```
### **Unit/Integration Tests**
* Before
   * No test coverage (controller, repository, business rules).
   * No mock testing (`@WebMvcTest`, `@DataMongoTest`).
* We may do
  * **Repository Tests** with `@DataMongoTest`.
  * **Controller Tests** with `@WebMvcTest`.
  * **Service Tests** with `Mockito`.
#### Example Controller Test:
```
package com.mahesh.ecomsystem;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    void shouldReturnAllProducts() throws Exception {
        when(service.findAll()).thenReturn(List.of(new Product("1", "Laptop", "Desc", "Cat", "tag", 1000, 5)));
        mockMvc.perform(get("/products"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].name").value("Laptop"));
    }
}
```

✅ Run the Tests
You have multiple ways:

🔹 From Eclipse / IntelliJ
Right-click on the test class (e.g., ProductControllerTest.java) → Run As → JUnit Test

🔹 From Maven
mvn test

### Build, Run and API Test
```
mvn clean
mvn install
mvn spring-boot:run
```
* Test the App API End Points using `VSC Thunder Client` 


## 5. 🔎 Filled Gaps in our current controller
* We handle exceptions properly.
* We enforce business rules & validation.
* We get structured logging.
* We follow enterprise layering (Controller → Service → Repository).
