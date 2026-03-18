package edu.ntnu.idatt2003.group14.model;

/**
 * Enumerator for the player status.
 *
 * <p>The different player statuses:
 * <ul>
 *   <li>Novice: no qualifier </li>
 *   <li>Investor: player has traded during 10 weeks and increased
 *   their net worth with at least 20%.</li>
 *   <li>Speculator: player has traded during 20 weeks and increased
 *   their net worth with at least 100%</li>
 * </ul>
 *
 * @author Elias Haugsbakk
 * @since 0.0.1
 */
public enum PlayerStatus {
  NOVICE,
  INVESTOR,
  SPECULATOR
}
