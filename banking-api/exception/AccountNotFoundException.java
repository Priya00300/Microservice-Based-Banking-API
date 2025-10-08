// AccountNotFoundException.java
package com.banking.bankingapi.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}

// InsufficientFundsException.java
package com.banking.bankingapi.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// InvalidAccountStateException.java
package com.banking.bankingapi.exception;

public class InvalidAccountStateException extends RuntimeException {
    public InvalidAccountStateException(String message) {
        super(message);
    }
}

// DuplicateAccountException.java
package com.banking.bankingapi.exception;

public class DuplicateAccountException extends RuntimeException {
    public DuplicateAccountException(String message) {
        super(message);
    }
}

// InvalidTransactionException.java
package com.banking.bankingapi.exception;

public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String message) {
        super(message);
    }
}