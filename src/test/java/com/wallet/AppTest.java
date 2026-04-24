import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase SmartWallet.
 * Cubre caminos felices, límites y casos de error.
 */
class SmartWalletTest {
  private SmartWallet wallet;

  @BeforeEach
  void setUp() {
    // Inicialización antes de cada test
    wallet = new SmartWallet("Standard");
  }

  @Test
  void testDepositValidoSinCashback() {
    assertTrue(wallet.deposit(50.0));
    assertEquals(50.0, wallet.getBalance());
  }

  @Test
  void testDepositConCashback() {
    // Depósito > 100 genera 1% extra (1.01 de cashback)
    wallet.deposit(101.0);
    assertEquals(102.01, wallet.getBalance(), 0.001);
  }

  @Test
  void testDepositLimiteStandard() {
    // Intentar superar el límite de 5000 para Standard
    assertFalse(wallet.deposit(5001.0));
  }

  @Test
  void testDepositExactamenteCienSinCashback() {
    // La regla dice mayor a 100, por lo que 100 no tiene cashback
    wallet.deposit(100.0);
    assertEquals(100.0, wallet.getBalance());
  }

  @Test
  void testWithdrawValido() {
    wallet.deposit(200.0);
    assertTrue(wallet.withdraw(50.0));
    // 200 + 2 de cashback - 50 = 152
    assertEquals(152.0, wallet.getBalance());
  }

  @Test
  void testWithdrawInactivaCuenta() {
    wallet.deposit(100.0);
    wallet.withdraw(100.0);
    assertEquals(0.0, wallet.getBalance());
    assertFalse(wallet.isActive(), "La cuenta debería estar inactiva al llegar a 0");
  }

  @Test
  void testErrorDepositNegativo() {
    assertFalse(wallet.deposit(-10.0));
  }

  @Test
  void testErrorWithdrawSaldoInsuficiente() {
    wallet.deposit(50.0);
    assertFalse(wallet.withdraw(60.0));
  }
}
