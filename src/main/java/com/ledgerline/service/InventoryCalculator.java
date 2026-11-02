package com.ledgerline.service;
import java.math.BigDecimal;
public final class InventoryCalculator {
 private InventoryCalculator(){}
 public static BigDecimal after(BigDecimal current, BigDecimal delta) {
  BigDecimal result=current.add(delta);
  if(result.signum()<0) throw new IllegalArgumentException("Insufficient stock");
  return result;
 }
}
