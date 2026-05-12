package edu.ntnu.idatt2003.group14.ui.app;

import javafx.scene.Parent;

/**
 * Represents a view in the application.
 *
 * <p>All views must expose a root {@link Parent} node which can be
 * placed in the scene graph by the {@link AppRouter}.</p>
 */
public interface View {

  /**
   * Returns the root node of this view.
   *
   * @return the root {@link Parent} node
   */
  Parent getRoot();
}
