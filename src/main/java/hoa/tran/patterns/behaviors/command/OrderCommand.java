/*
 * @(#)OrderCommand.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.command;

/**
 * The command. One action on an order, packed into an object. Because it is an object, it
 * can be stored, logged, run later and undone.
 *
 * @author hoa.tn
 * @since 1.0
 */
public interface OrderCommand {

  /** Performs the action. */
  void execute();

  /** Reverses exactly what {@link #execute()} did. */
  void undo();

  /** Short text for the audit log, e.g. "voucher WELCOME20". */
  String description();
}
