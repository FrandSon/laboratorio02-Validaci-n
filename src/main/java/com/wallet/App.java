/**
 * Implementación de la billetera inteligente con reglas de negocio específicas.
 */
public class SmartWallet {
  private double balance;
  private String userType; // "Standard" o "Premium"
  private boolean active;

  public SmartWallet(String userType) {
    this.balance = 0.0;
    this.userType = userType;
    this.active = true;
  }

  public boolean deposit(double amount) {
    if (amount <= 0)
      return false;

    // Regla: Límite para usuarios Standard
    if (userType.equalsIgnoreCase("Standard") && (balance + amount) > 5000) {
      return false;
    }

    double finalDeposit = amount;

    // Regla: Cashback del 1% si el monto es mayor a 100
    if (amount > 100) {
      finalDeposit += amount * 0.01;
    }

    balance += finalDeposit;
    return true;
  }

  public boolean withdraw(double amount) {
    if (amount <= 0 || amount > balance) {
      return false;
    }

    balance -= amount;

    // Regla: Si el saldo es exactamente 0, se inactiva
    if (balance == 0) {
      active = false;
    }

    return true;
  }

  // Getters necesarios para las pruebas
  public double getBalance() {
    return balance;
  }

  public boolean isActive() {
    return active;
  }

  public String getUserType() {
    return userType;
  }
}
