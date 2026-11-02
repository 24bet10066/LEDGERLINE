package com.ledgerline.controller;

import com.ledgerline.dto.LowStockItem;
import com.ledgerline.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers={PageController.class,InventoryRestController.class})
@AutoConfigureMockMvc(addFilters=false)
class LowStockNavigationTest {
 @Autowired MockMvc mvc;
 @MockBean InventoryService inventory;

 @Test void dashboardLinksToFilteredLowStockPage() throws Exception {
  when(inventory.dashboard()).thenReturn(Map.of("products",1L,"stock",BigDecimal.TEN,"value",BigDecimal.ONE,"low",1));
  mvc.perform(get("/dashboard")).andExpect(status().isOk()).andExpect(view().name("dashboard")).andExpect(model().attribute("low",1));
 }

 @Test void lowStockApiAcceptsSearchAndWarehouseFilters() throws Exception {
  when(inventory.lowStock("dock",2L)).thenReturn(List.of(new LowStockItem("LL-1","Dock","North",BigDecimal.ONE,BigDecimal.TEN,BigDecimal.valueOf(9))));
  mvc.perform(get("/api/inventory/low-stock").param("q","dock").param("warehouseId","2"))
    .andExpect(status().isOk()).andExpect(jsonPath("$[0].sku").value("LL-1")).andExpect(jsonPath("$[0].shortage").value(9));
  verify(inventory).lowStock("dock",2L);
 }

 @Test void purchaseReceivingPagePostsToTransactionalReceiveWorkflow() throws Exception {
  mvc.perform(post("/purchases/7/receive")
      .param("productId","11")
      .param("warehouseId","3")
      .param("locationId","5")
      .param("quantity","4.5"))
    .andExpect(status().is3xxRedirection())
    .andExpect(redirectedUrl("/purchases"));
  verify(inventory).receive(7L,11L,3L,5L,new BigDecimal("4.5"));
 }
}
