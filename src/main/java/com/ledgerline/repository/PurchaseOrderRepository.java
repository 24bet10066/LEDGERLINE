package com.ledgerline.repository;
import com.ledgerline.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Long>{List<PurchaseOrder> findAllByOrderByOrderedAtDesc();}
