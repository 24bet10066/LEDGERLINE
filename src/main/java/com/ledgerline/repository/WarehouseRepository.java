package com.ledgerline.repository;
import com.ledgerline.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface WarehouseRepository extends JpaRepository<Warehouse,Long>{List<Warehouse> findByActiveTrueOrderByName();}
