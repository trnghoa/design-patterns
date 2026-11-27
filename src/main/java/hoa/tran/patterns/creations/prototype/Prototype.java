/*
 * @(#)Prototype.java
 *
 * Copyright 2026 by esoft systems (tm).
 * All rights reserved.
 */
package hoa.tran.patterns.creations.prototype;

/**
 * Something that can make a copy of itself. The caller gets a new, independent object
 * without knowing its concrete class or how it was set up.
 *
 * <p>This is used instead of Java's {@code Cloneable}, which is hard to use correctly:
 * {@code Object.clone()} makes a shallow copy and throws a checked exception.
 *
 * @author hoa.tn
 * @since 1.0
 */
public interface Prototype<T> {

  /** Returns a deep copy: changing the copy must never change the original. */
  T copy();
}
