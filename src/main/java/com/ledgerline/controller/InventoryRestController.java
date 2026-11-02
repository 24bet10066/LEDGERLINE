package com.ledgerline.controller;
import com.ledgerline.dto.*;
import com.ledgerline.entity.*;
import com.ledgerline.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;
@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class InventoryRestController {
 private final InventoryService inventory;
 @GetMapping("/dashboard") Map<String,Object> dashboard(){return inventory.dashboard();}
 @GetMapping("/products") List<Product> products(@RequestParam(required=false)String q){return inventory.products(q);}
 @PostMapping("/products") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") ResponseEntity<Product> create(@Valid @RequestBody ProductRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(inventory.create(r));}
 @GetMapping("/ledger") List<Map<String,Object>> ledger(){return inventory.ledger();}
 @GetMapping("/inventory/low-stock") List<LowStockItem> lowStock(@RequestParam(required=false) String q,@RequestParam(required=false) Long warehouseId){return inventory.lowStock(q,warehouseId);}
 @PostMapping("/stock") void stock(@Valid @RequestBody StockRequest r){inventory.stock(r);}
 @PostMapping("/transfers") void transfer(@Valid @RequestBody TransferRequest r){inventory.transfer(r);}
 @PostMapping("/purchases") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") PurchaseOrder purchase(@Valid @RequestBody PurchaseRequest r){return inventory.createPurchase(r);}
 @PostMapping("/purchases/{id}/approve") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") void approve(@PathVariable Long id){inventory.approve(id);}
 @PostMapping("/purchases/{id}/receive") void receive(@PathVariable Long id,@RequestParam Long productId,@RequestParam Long warehouseId,@RequestParam Long locationId,@RequestParam BigDecimal quantity){inventory.receive(id,productId,warehouseId,locationId,quantity);}
}
