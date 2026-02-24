# Chain of Responsibility Design Pattern

## 📌 Module Objectives

- Learn how the Chain of Responsibility pattern allows for the **distribution of responsibilities** among objects, enabling multiple objects to handle a request.
- Explore how implementing this pattern can enhance **code maintainability** by promoting single responsibility for each object in the chain.
- Discover how the pattern facilitates **dynamic request handling** at runtime.

---

## 📖 Overview

The **Chain of Responsibility** is a behavioral design pattern that allows passing requests along a chain of potential handlers until one of them handles the request. Each handler in the chain decides either to **process the request** or **pass it on** to the next handler in the sequence.

---

## ✅ Benefits & ⚠️ Trade-offs

| | Description |
|---|---|
| ✅ **Decoupling** | Promotes loose coupling between the client and specific handlers, making code more adaptable and easier to maintain. |
| ✅ **Flexibility** | New handlers can be added dynamically without modifying existing code. |
| ✅ **Scalability** | New functionalities can be added without major code changes. |
| ⚠️ **Complexity** | Overly complex chains with numerous handlers can be difficult to manage and debug. |
| ⚠️ **Performance** | Traversing the chain can introduce additional overhead due to multiple method calls. |

---

## 🧩 Elements

| Element | Description |
|---|---|
| **Request** | The action or operation that needs to be performed (e.g., a message, data structure, or any information to be processed). |
| **Handler** | An object that can process the request. Each handler has a specific responsibility. |
| **Chain** | A series of handlers linked together. Each handler decides whether to process the request or pass it along. |
| **Client** | The object that initiates the request and sends it to the first handler in the chain. |
| **Concrete Handlers** | Specific implementations of the handler interface, each with its own processing logic. |
| **Context** | The environment in which the handlers operate, including any additional data needed for processing. |

---

## 💡 Problem & Implementation: BPI ATM Dispenser

We design an ATM system for **BPI (Bank of the Philippine Islands)** that dispenses cash in denominations of **₱1000**, **₱500**, and **₱100** bills using the Chain of Responsibility pattern.

---

### `Currency.java`

```java
public class Currency {
    private int amount;

    public Currency(int amt) {
        this.amount = amt;
    }

    public int getAmount() {
        return this.amount;
    }
}
```

---

### `DispenseChain.java` (Interface)

```java
public interface DispenseChain {
    void setNextChain(DispenseChain nextChain);
    void dispense(Currency currency);
}
```

---

### `ATMDispenseChain.java`

```java
public class ATMDispenseChain implements DispenseChain {
    private DispenseChain nextChain;

    public ATMDispenseChain() {
        // Initialize the chain
        this.nextChain = new Peso1000Dispenser();
        DispenseChain c2 = new Peso500Dispenser();
        DispenseChain c3 = new Peso100Dispenser();

        // Set the chain of responsibility
        nextChain.setNextChain(c2);
        c2.setNextChain(c3);
    }

    public void dispense(Currency currency) {
        nextChain.dispense(currency);
    }

    @Override
    public void setNextChain(DispenseChain nextChain) {
        this.nextChain = nextChain;
    }
}
```

---

### `Peso1000Dispenser.java`

```java
public class Peso1000Dispenser implements DispenseChain {
    private DispenseChain chain;

    @Override
    public void setNextChain(DispenseChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public void dispense(Currency cur) {
        if (cur.getAmount() >= 1000) {
            int num = cur.getAmount() / 1000;
            int remainder = cur.getAmount() % 1000;
            System.out.println("Dispensing " + num + " 1000 bills");
            if (remainder != 0) {
                this.chain.dispense(new Currency(remainder));
            }
        } else {
            this.chain.dispense(cur);
        }
    }
}
```

---

### `Peso500Dispenser.java`

```java
public class Peso500Dispenser implements DispenseChain {
    private DispenseChain chain;

    @Override
    public void setNextChain(DispenseChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public void dispense(Currency cur) {
        if (cur.getAmount() >= 500) {
            int num = cur.getAmount() / 500;
            int remainder = cur.getAmount() % 500;
            System.out.println("Dispensing " + num + " 500 bills");
            if (remainder != 0) {
                this.chain.dispense(new Currency(remainder));
            }
        } else {
            this.chain.dispense(cur);
        }
    }
}
```

---

### `Peso100Dispenser.java`

```java
public class Peso100Dispenser implements DispenseChain {
    private DispenseChain chain;

    @Override
    public void setNextChain(DispenseChain nextChain) {
        this.chain = nextChain;
    }

    @Override
    public void dispense(Currency cur) {
        if (cur.getAmount() >= 100) {
            int num = cur.getAmount() / 100;
            int remainder = cur.getAmount() % 100;
            System.out.println("Dispensing " + num + " 100 bills");
            if (remainder != 0) {
                this.chain.dispense(new Currency(remainder));
            }
        } else {
            this.chain.dispense(cur);
        }
    }
}
```

---

### `BPI_Atm.java` (Main / Client)

```java
public class BPI_Atm {
    public static void main(String[] args) {
        ATMDispenseChain atmDispenser = new ATMDispenseChain();
        int amount = 2970;

        if (amount % 10 != 0) {
            System.out.println("Amount should be in multiples of 100s.");
        } else {
            atmDispenser.dispense(new Currency(amount));
        }
    }
}
```

---

## 🔍 Pattern Elements in This Implementation

| Element | Class(es) |
|---|---|
| **Handler** | `Peso1000Dispenser`, `Peso500Dispenser`, `Peso100Dispenser` |
| **Chain** | `ATMDispenseChain` — links handlers via `setNextChain()` |
| **Request** | The `dispense()` method call with a `Currency` amount |
| **Client** | `BPI_Atm` — initiates the request |
| **Context** | `ATMDispenseChain` — manages the chain and ensures the request is passed along |

---

## 🏗️ Chain Flow Diagram

```
BPI_Atm
   │
   ▼
ATMDispenseChain
   │
   ▼
Peso1000Dispenser  ──▶  Peso500Dispenser  ──▶  Peso100Dispenser
```

Each dispenser handles what it can, then passes the **remainder** down the chain.
