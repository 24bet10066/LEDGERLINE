# LEDGERLINE Spring Boot viva guide

## What changed

The original application used servlet classes, `web.xml`, JSP pages, a manually created Hikari pool, and hand-written JDBC routing. LEDGERLINE now runs as one Spring Boot executable JAR. Spring creates the application context and manages web controllers, security, repositories, transactions, and configuration.

## Request flow

For a browser request:

```text
Thymeleaf Controller -> InventoryService -> Repository/JdbcTemplate -> MySQL
```

For an API request:

```text
REST Controller -> validated DTO -> InventoryService -> Repository/JdbcTemplate -> MySQL
```

Controllers do not calculate stock or write SQL business workflows. `InventoryService` is the transaction boundary.

## Important annotations

- `@SpringBootApplication` starts component scanning and auto-configuration.
- `@Controller` serves Thymeleaf pages.
- `@RestController` serves JSON operations under `/api`.
- `@Service` identifies the business layer.
- Spring Data interfaces are repository components.
- `@Entity`, `@Table`, `@Id`, and `@GeneratedValue` map Java objects to existing MySQL tables.
- `@Transactional` makes receipts, issues, transfers, purchase receiving, and sales atomic.
- `@Configuration` and `@Bean` configure security, password encoding, and initialization.
- `@RequestMapping`, `@GetMapping`, and `@PostMapping` define HTTP contracts.
- `@PathVariable`, `@RequestParam`, and `@RequestBody` bind request data.
- `@Valid` activates Bean Validation on DTOs.
- `@RestControllerAdvice` converts business failures into clear HTTP responses.
- `@PreAuthorize` protects product creation and purchasing operations.

## Entities and relationships

- `User` stores email, BCrypt password hash, role, and active state.
- `Product` references one `Category` and one `Supplier`.
- `Warehouse` owns `Location` records.
- `PurchaseOrder` references its supplier and destination warehouse.
- `StockMovement` references product, warehouse, location, and operator.
- `stock_balance` stores quantity at product/warehouse/location granularity.

The purchase-order table never changes stock. A receipt writes a positive `RECEIPT` movement and updates the balance. Issues, sales, and damage write negative movement quantities. Transfers write one negative source movement and one positive destination movement in the same transaction.

## Demonstration workflow

1. Sign in as the configured administrator.
2. Open Overview to see live MySQL statistics.
3. Use Products to create a catalog item.
4. Create and approve a purchase order.
5. Receive all or part of the order through the API or inventory flow.
6. Confirm the ledger and balance changed only after receipt.
7. Post an issue, sale, return, adjustment, or damage movement.
8. Use the clickable low-stock card and open the stock ledger.
9. Download the CSV export.

## One-time demonstration initialization

Initialization checks the `DEMO_INITIALIZED` audit event, not whether tables are empty. This prevents deleted records from being recreated on restart. Existing records remain in place during the Spring migration.

## Running tests

```bash
mvn clean test package
```

The test suite covers inventory arithmetic, issue/receipt behavior, transfer balancing, and return compensation. The live verification additionally exercises Spring login, protected MVC routes, REST dashboard/product endpoints, and a persisted transactional receipt against MySQL 8.4.
