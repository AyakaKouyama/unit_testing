package pl.lodz.p.zzpj.testing.junit.tdd;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("FizzBuzz class test")
public class FizBuzzTest {

	private FizzBuzz fizBuzz;

	@BeforeAll
	public void initialize() {
		fizBuzz = new FizzBuzz();
		System.out.println("FizzBuzz test class start.");
	}

	@BeforeEach
	public void executeBeforeEach() {
		System.out.println("Test start.");
	}

	@AfterEach
	public void printAfterTest() {
		System.out.println("Test completed.");
	}

	@AfterAll
	public void printAfterAll() {
		System.out.println("All tests completed.");
	}


	@Test
	public void testGetFizzNumber() {

		assertAll(() -> assertEquals("Fizz", fizBuzz.getFizzBuzzNumber(3)),
				() -> assertEquals("Fizz", fizBuzz.getFizzBuzzNumber(12)),
				() -> assertEquals("Fizz", fizBuzz.getFizzBuzzNumber(27)),
				() -> assertEquals("Fizz", fizBuzz.getFizzBuzzNumber(9)),

				() -> assertEquals("Buzz", fizBuzz.getFizzBuzzNumber(50)),
				() -> assertEquals("Buzz", fizBuzz.getFizzBuzzNumber(520)),
				() -> assertEquals("Buzz", fizBuzz.getFizzBuzzNumber(125)),
				() -> assertEquals("Buzz", fizBuzz.getFizzBuzzNumber(5)),

				() -> assertEquals("FizzBuzz", fizBuzz.getFizzBuzzNumber(15)),
				() -> assertEquals("FizzBuzz", fizBuzz.getFizzBuzzNumber(30)),
				() -> assertEquals("FizzBuzz", fizBuzz.getFizzBuzzNumber(60)),
				() -> assertEquals("FizzBuzz", fizBuzz.getFizzBuzzNumber(120)),

				() -> assertEquals("2", fizBuzz.getFizzBuzzNumber(2)),
				() -> assertEquals("13", fizBuzz.getFizzBuzzNumber(13)),
				() -> assertEquals("17", fizBuzz.getFizzBuzzNumber(17)),
				() -> assertEquals("37", fizBuzz.getFizzBuzzNumber(37))
		);
	}

	@Test
	public void testFizzBuzzThrowsException() {
		assertAll(() -> assertThrows(FizzBuzzException.class, () -> fizBuzz.getFizzBuzzNumber(-4)),
				() -> assertThrows(FizzBuzzException.class, () -> fizBuzz.getFizzBuzzNumber(-5)));

	}

	@Test
	public void testFizzBuzzTimeout(){
		assertTimeout(Duration.ofSeconds(3), () -> fizBuzz.getFizzBuzzNumber(5));
	}

	@Test
	public void assumeThatNumberIsFizz() throws FizzBuzzException {
		assumeTrue(fizBuzz.getFizzBuzzNumber(3).equals("Fizz"));
	}

	@Test
	public void assumeThatNumberIsNotBuzz() throws FizzBuzzException{
		assumeFalse(fizBuzz.getFizzBuzzNumber(12).equals("Buzz"));
	}

	@Test
	public void assumeThatNumberContainsFizz() throws FizzBuzzException {
		assumeTrue(fizBuzz.getFizzBuzzNumber(15).contains("Fizz"));
	}

	@Test
	public void assumeThatModulo3IsBuzz(){
		for(int i = 0; i<100; i++){
			final int iterator = i;
			assumingThat((i % 3 == 0) && (i % 5 != 0), () -> assertEquals("Fizz", fizBuzz.getFizzBuzzNumber(iterator)));
		}
	}

	@Nested
	@DisplayName("Nested class for parametrized tests")
	public class ParametrizedTests{

		@Tag("Parametrized")
		@ParameterizedTest
		@ValueSource(ints = {3, 6, 9, 12})
		public void valueSourceParameterTest(int value) throws FizzBuzzException {
			assertEquals("Fizz", fizBuzz.getFizzBuzzNumber(value));
		}


		@ParameterizedTest
		@CsvSource({"3, 5, 15", "6, 10, 30"})
		public void csvSourceParameterTest(int numberOne, int numberTwo, int numberThree) throws FizzBuzzException {
			assertEquals("Fizz", fizBuzz.getFizzBuzzNumber(numberOne));
			assertEquals("Buzz", fizBuzz.getFizzBuzzNumber(numberTwo));
			assertEquals("FizzBuzz", fizBuzz.getFizzBuzzNumber(numberThree));
		}

		@Tag("Parametrized")
		@ParameterizedTest
		@CsvFileSource(resources = "/numbers.csv")
		public void csvFileSourceParameterTest(int numberOne, int numberTwo, int numberThree) throws FizzBuzzException {
			assertEquals("Fizz", fizBuzz.getFizzBuzzNumber(numberOne));
			assertEquals("Buzz", fizBuzz.getFizzBuzzNumber(numberTwo));
			assertEquals("FizzBuzz", fizBuzz.getFizzBuzzNumber(numberThree));
		}

	}

	@Tag("Parametrized")
	@ParameterizedTest
	@MethodSource("getNumbers")
	public void methodSourceParameterTest(int number) throws FizzBuzzException {
		assertEquals("Fizz", fizBuzz.getFizzBuzzNumber(number));
	}

	private static Stream<Integer> getNumbers(){
		return Stream.of(3, 6, 12);
	}

	@Tag("Not implemented")
	@Disabled
	@Test
	public void disabledTest() throws FizzBuzzException {
	}

	@EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_11})
	@Test
	public void enabledOnSpecificJRE() throws FizzBuzzException {
		assertEquals("Buzz", fizBuzz.getFizzBuzzNumber(5));
	}

	@EnabledOnOs({OS.WINDOWS})
	@Test
	public void enabledOnlyOnWindows() throws FizzBuzzException {
		assertEquals("FizzBuzz", fizBuzz.getFizzBuzzNumber(15));
	}

}
