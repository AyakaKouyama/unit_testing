package pl.lodz.p.zzpj.testing.junit.tdd;

public class FizzBuzz {

    public String getFizzBuzzNumber(int number) throws FizzBuzzException{
        if(number < 0){
        	throw new FizzBuzzException("Number must be positive.");
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		StringBuilder result = new StringBuilder();
		if(number % 3 == 0){
			result.append("Fizz");
		}
		if(number % 5 == 0){
			result.append("Buzz");
		}
		if(number % 3 != 0 && number % 5 != 0){
			result.append(Integer.toString(number));
		}
        return result.toString();
    }
}
