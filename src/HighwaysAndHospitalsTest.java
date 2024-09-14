/**
 * Highways & Hospitals
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *
 * To use this test file, run either the entire thing or individual tests (one at a time).
 * There are five test cases, each of which will load data from [test number].txt, which is in the
 * test_files directory.
 *
 */
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

public class HighwaysAndHospitalsTest {

    private final HighwaysAndHospitals studentSolution = new HighwaysAndHospitals();
    private int n;
    private int m;
    private int hospitalCost;
    private int hightwayCost;
    private int[][] cities;

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testCorrectSmall() {
        setTestData(0);
    }
    @Timeout(value = 60, unit = TimeUnit.SECONDS)
    @Test
    public void testCorrectLarge() {
        setTestData(10);
    }

    @Test
    @Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
    public void testEfficientSmall() {
        setTestData(1);
    }

    @Test
    @Timeout(value = 1100, unit = TimeUnit.MILLISECONDS)
    public void testEfficientLarge() {
        setTestData(10);
    }

    @Test
    @Timeout(value = 200, unit = TimeUnit.MILLISECONDS)
    public void testEfficientAlsoLarge() {
        setTestData(7);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    public void testVeryLarge() {
        setTestData(9);
    }

    private void setTestData(int testNumber) {
        // Open files
        try {
            BufferedReader testReader = new BufferedReader(new FileReader("test_files/" + testNumber + ".txt"));
            BufferedReader answerReader = new BufferedReader(new FileReader("test_files/" + testNumber + "_answers.txt"));

            // Get the number of tests in the file
            int numTests = Integer.parseInt(testReader.readLine());

            // Read in the data for each test, then run.
            for (int i = 0; i < numTests; i++)
            {
                long answerCost = Long.parseLong(answerReader.readLine());
                loadTest(testReader);
                assertEquals((long)answerCost, (long)studentSolution.cost(n, hospitalCost, hightwayCost, cities),
                        "Test " + testNumber + " failed: should return " + answerCost);
            }
        } catch (IOException e) {
            System.out.println("Error opening test file #" + testNumber);
            e.printStackTrace();
        }
    }

    private void loadTest(BufferedReader br) {
        String line;
        try {
            line = br.readLine();
            String[] parts = line.trim().split("\\s+");

            // Update instance variables with test data
            n = Integer.parseInt(parts[0]);
            m = Integer.parseInt(parts[1]);
            hospitalCost = Integer.parseInt(parts[2]);
            hightwayCost = Integer.parseInt(parts[3]);
            cities = new int[m][2];

            for (int i = 0; i < m; i++) {
                line = br.readLine();
                parts = line.trim().split("\\s+");
                cities[i][0] = Integer.parseInt(parts[0]);
                cities[i][1] = Integer.parseInt(parts[1]);
            }
        } catch (IOException e) {
            System.out.println("Error opening test file");
            e.printStackTrace();
        }
    }
}
