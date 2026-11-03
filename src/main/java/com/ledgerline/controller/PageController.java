package com.ledgerline.controller;
import com.ledgerline.service.InventoryService;
import com.ledgerline.dto.ProductRequest;
import com.ledgerline.exception.BusinessException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import java.math.BigDecimal;
import java.util.stream.Collectors;
@Controller @RequiredArgsConstructor
public class PageController {
 private final InventoryService inventory;
 @GetMapping("/login") String login(){return "login";}
 @GetMapping({"/","/dashboard"}) String dashboard(Model m){m.addAllAttributes(inventory.dashboard());return "dashboard";}
 @GetMapping("/products") String products(@RequestParam(required=false) String q,Model m){return productsPage(q,m);}
 @GetMapping("/inventory") String inventory(@RequestParam(required=false,defaultValue="false") boolean lowStock,@RequestParam(required=false) String q,@RequestParam(required=false) Long warehouseId,Model m){m.addAttribute("products",inventory.products(q));m.addAttribute("lowRows",inventory.lowStock(q,warehouseId));m.addAttribute("lowStock",lowStock);m.addAttribute("search",q);m.addAttribute("selectedWarehouse",warehouseId);m.addAttribute("warehouses",inventory.warehouses());m.addAttribute("locations",inventory.locations());return "inventory";}
 @GetMapping("/purchases") String purchases(Model m){m.addAttribute("purchases",inventory.purchaseRows());m.addAttribute("suppliers",inventory.suppliers());m.addAttribute("warehouses",inventory.warehouses());m.addAttribute("products",inventory.products(null));m.addAttribute("locations",inventory.locations());return "purchases";}
 @GetMapping("/ledger") String ledger(Model m){m.addAttribute("rows",inventory.ledger());return "ledger";}
 @GetMapping("/report") String report(Model m){m.addAttribute("rows",inventory.ledger());return "report";}
 @GetMapping("/export.csv")@ResponseBody String export(){return inventory.csv();}
 @PostMapping("/products") String createProduct(@Valid @ModelAttribute("productRequest") ProductRequest request,BindingResult bindingResult,Model m){if(bindingResult.hasErrors()){m.addAttribute("productError",validationMessage(bindingResult));return productsPage(null,m);}try{inventory.create(request);return "redirect:/products";}catch(BusinessException e){m.addAttribute("productError",e.getMessage());return productsPage(null,m);}}
 @PostMapping("/inventory") String postStock(@RequestParam Long productId,@RequestParam Long warehouseId,@RequestParam Long locationId,@RequestParam BigDecimal quantity,@RequestParam String type,@RequestParam String reference,@RequestParam String reason){inventory.stock(new com.ledgerline.dto.StockRequest(productId,warehouseId,locationId,quantity,type,reference,reason));return "redirect:/ledger";}
 @PostMapping("/purchases") String createPurchase(@RequestParam String orderNumber,@RequestParam Long supplierId,@RequestParam Long warehouseId,@RequestParam Long productId,@RequestParam BigDecimal quantity,@RequestParam BigDecimal cost){inventory.createPurchase(new com.ledgerline.dto.PurchaseRequest(orderNumber,supplierId,warehouseId,productId,quantity,cost));return "redirect:/purchases";}
 @PostMapping("/purchases/{id}/approve") String approve(@PathVariable Long id){inventory.approve(id);return "redirect:/purchases";}
 @PostMapping("/purchases/{id}/receive") String receive(@PathVariable Long id,@RequestParam Long productId,@RequestParam Long warehouseId,@RequestParam Long locationId,@RequestParam BigDecimal quantity){inventory.receive(id,productId,warehouseId,locationId,quantity);return "redirect:/purchases";}
 private String productsPage(String q,Model m){m.addAttribute("products",inventory.products(q));m.addAttribute("categories",inventory.categories());m.addAttribute("suppliers",inventory.suppliers());return "products";}
 private String validationMessage(BindingResult result){return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));}
}
