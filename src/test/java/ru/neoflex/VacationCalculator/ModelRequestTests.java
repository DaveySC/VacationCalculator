package ru.neoflex.VacationCalculator;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.neoflex.VacationCalculator.model.ModelRequest;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootTest
public class ModelRequestTests {

	@Test
	public void countDaysBetweenDatesTest() throws Exception {
		ModelRequest modelRequest = new ModelRequest();
		modelRequest.setTo(new SimpleDateFormat( "dd.MM.yyyy" ).parse( "28.12.2016" ));
		modelRequest.setFrom(new SimpleDateFormat( "dd.MM.yyyy" ).parse( "29.12.2016" ));

		Method countDaysBetweenDates =
				ModelRequest.class.getDeclaredMethod("countDaysBetweenDates", Date.class, Date.class);
		countDaysBetweenDates.setAccessible(true);
		long answerFromTest = (Long) countDaysBetweenDates.invoke(modelRequest, new Date(), new Date());
		Assertions.assertEquals( 0, answerFromTest);
	}


	@Test
	public void countVacationPayTest1() throws Exception {
		ModelRequest modelRequest = new ModelRequest();
		modelRequest.setSalary(100_000);
		modelRequest.setDays(28);

		Assertions.assertEquals(
				String.format("Ваши отпускные составляют : %.2f денежных единиц в человеческой валюте", 95563.14),
				modelRequest.calculateVacationPayAndGetTotalString()
		);
	}

	@Test
	public void countVacationPayTest2() throws Exception {
		ModelRequest modelRequest = new ModelRequest();
		modelRequest.setSalary(100_000);
		modelRequest.setDays(10);
		modelRequest.setFrom(new SimpleDateFormat( "dd.MM.yyyy" ).parse( "10.10.2022" ));
		modelRequest.setTo(new SimpleDateFormat( "dd.MM.yyyy" ).parse( "20.10.2022" ));

		Assertions.assertEquals(
				String.format("Ваши отпускные составляют : %.2f денежных единиц в человеческой валюте", 27303.75),
				modelRequest.calculateVacationPayAndGetTotalString()
		);
	}


}
