/*
 * @(#)OrderStatus.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.observer;

/**
 * The steps an editing order goes through, from upload to delivery.
 *
 * @author hoa.tn
 * @since 1.0
 */
public enum OrderStatus {
  RECEIVED,
  IN_EDITING,
  QUALITY_CHECK,
  DELIVERED
}
