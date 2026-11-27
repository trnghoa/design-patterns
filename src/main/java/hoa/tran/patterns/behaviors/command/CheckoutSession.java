/*
 * @(#)CheckoutSession.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.behaviors.command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * The invoker. It runs commands without knowing what they do, and keeps them so they can be
 * undone and redone. It also keeps an audit log of every action.
 *
 * @author hoa.tn
 * @since 1.0
 */
public class CheckoutSession {

  private final Deque<OrderCommand> undoStack = new ArrayDeque<>();
  private final Deque<OrderCommand> redoStack = new ArrayDeque<>();
  private final List<String> auditLog = new ArrayList<>();

  /**
   * Runs the command and remembers it. If the command refuses (throws), nothing is
   * remembered, and the exception reaches the caller.
   */
  public void apply(OrderCommand command) {
    command.execute();
    undoStack.push(command);
    redoStack.clear();
    auditLog.add("APPLY " + command.description());
  }

  /** Undoes the most recent command. Returns {@code false} if there is nothing to undo. */
  public boolean undo() {
    OrderCommand command = undoStack.poll();
    if (command == null) {
      return false;
    }
    command.undo();
    redoStack.push(command);
    auditLog.add("UNDO  " + command.description());
    return true;
  }

  /** Re-applies the most recently undone command. Returns {@code false} if there is none. */
  public boolean redo() {
    OrderCommand command = redoStack.poll();
    if (command == null) {
      return false;
    }
    command.execute();
    undoStack.push(command);
    auditLog.add("REDO  " + command.description());
    return true;
  }

  public List<String> getAuditLog() {
    return List.copyOf(auditLog);
  }
}
