package com.ledgerline.repository;
import com.ledgerline.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProductRepository extends JpaRepository<Product,Long>{List<Product> findByActiveTrueAndNameContainingIgnoreCaseOrActiveTrueAndSkuContainingIgnoreCase(String name,String sku);}
