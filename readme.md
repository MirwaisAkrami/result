## Using Result Class as a Response Wrapper in a Java project.


### Introduction
The Result class is a versatile response wrapper designed to handle various outcomes in a Java based application. It encapsulates the result of an operation, providing a consistent way to represent success, errors, validation issues, or other status conditions.

In This guide I am using spring boot to demonstrate the usage of `Result`, but it can be used with any Java based project.

### Getting Started
To use the `Result` class in your Spring Boot project, follow these steps:

#### Step 1: Dependency
Ensure that you have the `Result` class in your project. You can either copy the class directly into your codebase or create a separate module or library containing it.

#### Step 2: Integration
The integration involves using the `Result` class to wrap the responses of your Spring MVC controllers or service methods.

Using the `Result` class in the `service` layer is a good practice for encapsulating the outcome of operations. Below is an example of how you can integrate the `Result` class into your service layer in a Spring Boot project.


```
package com.yourpackage.service;

import com.result.Result;
import com.result.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YourService {

    // Inject dependencies as needed
    // @Autowired
    // private YourRepository yourRepository;

    public Result<YourDataType> findById(Long id) {
        // Your logic to fetch data from a repository or perform any operation

        YourDataType data = /* ... */;

        if (data != null) {
            // Operation successful
            return Result.success(data, "Data found successfully");
        } else {
            // Data not found
            return Result.notFound("Data not found for id: " + id);
        }
    }

    public Result<Void> create(YourDataType requestData) {
        // Your logic to create a new record

        try {
            // Perform the operation

            // If successful, return success result
            return Result.success("Record created successfully");
        } catch (Exception e) {
            // Log the exception

            // If an error occurs, return an error result
            return Result.error("Failed to create record");
        }
    }

    // Add more service methods as needed
}

```

In this example, the service methods return Result objects, encapsulating the success or failure of the operations. The Result class is used to communicate the outcome along with optional messages or data.

Your Spring MVC controller can then call these service methods and handle the Result objects accordingly:

```
@RestController
@RequestMapping("/api/yourresource")
public class YourController {

    @Autowired
    private YourService yourService;

    @GetMapping("/{id}")
    public ResponseEntity<Result<YourDataType>> getById(@PathVariable Long id) {
        Result<YourDataType> result = yourService.findById(id);

        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    @PostMapping("/create")
    public ResponseEntity<Result<Void>> create(@RequestBody YourDataType requestData) {
        Result<Void> result = yourService.create(requestData);
        return ResponseEntity.status(result.getStatus().value()).body(result);
    }

    // Add more controller methods as needed
}

```

###  Using `Result` library with the `java-fluent-validator` library for fluent validation


If you want to integrate the `Result` library with the `java-fluent-validator` library for fluent validation error collection, you can enhance the Result class with a method to handle these errors.

https://github.com/mvallim/java-fluent-validator

```
import br.com.fluentvalidator.context.Error;
...

// This method is used to integrate the fluent validation error collection
    public static <T> Result<T> invalid(Collection<Error> errors) {
        List<ValidationError> validationErrors = new ArrayList<>();
        for (Error error : errors) {
            ValidationError validationError = new ValidationError();
            validationError.setErrorCode(error.getCode());
            validationError.setErrorMessage(error.getMessage());
            validationError.setIdentifier(error.getField());
            validationErrors.add(validationError);
        }

        Result<T> result = new Result<>(ResultStatus.INVALID);
        result.validationErrors = validationErrors;
        return result;
    }
```

Here's an example of how to use it in your service layer:

```
public class YourService {

    @Autowired
    private YourEntityCreateValidator yourEntityCreateValidator;

    public Result<EntityDetailDto> create(EntityDataDto data) {
        // Use the fluent validator to perform validation
        ValidationResult validationResult = yourEntityCreateValidator.validate(data);

        if(!validationResult.isValid()) {
            return Result.invalid(validationResult.getErrors());
        }

        // ... rest of the code
    }

    // ...
}
```



