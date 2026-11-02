package com.ledgerline.repository;
import com.ledgerline.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface SupplierRepository extends JpaRepository<Supplier,Long>{List<Supplier> findByActiveTrueOrderByName();}
