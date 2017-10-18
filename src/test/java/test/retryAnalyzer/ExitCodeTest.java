package test.retryAnalyzer;

import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.annotations.Test;
import test.SimpleBaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ExitCodeTest extends SimpleBaseTest {
  @Test
  public void exitsWithZeroOnSuccess() {
    TestNG tng = create(ImmediateSuccess.class);
    tng.run();
    assertEquals(tng.getStatus(), 0);
  }

  @Test
  public void exitsWithNonzeroOnFailure() {
    TestNG tng = create(PersistentFailure.class);
    tng.run();
    assertTrue(tng.getStatus() != 0);
  }

  @Test
  public void exitsWithZeroAfterSuccessfulRetry() {
    TestNG tng = create(EventualSuccess.class);
    tng.addListener((ITestNGListener) new TestResultPruner());
    tng.run();
    assertEquals(tng.getStatus(), 0);
  }

  @Test(description = "GITHUB-217")
  public void exitWithNonzeroOnSkips() {
    TestNG tng = create(Issue217TestClassSample.class);
    tng.run();
    assertEquals(tng.getStatus(), 5);
  }

  @Test(description = "GITHUB-217")
  public void exitWithNonzeroOnSkips1() {
    TestNG tng = create(Issue217TestClassSampleWithOneDataProvider.class);
    tng.run();
    assertEquals(tng.getStatus(), 4);
  }

}
