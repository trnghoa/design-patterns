/*
 * @(#)Adjustment.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.command;

import java.math.BigDecimal;

/**
 * One line taken off the order, e.g. "voucher WELCOME20" for 20.00 EUR.
 *
 * @author hoa.tn
 * @since 1.0
 */
public record Adjustment(String label, BigDecimal amount) {
}
