package ru.neoflex.VacationCalculator;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.text.DecimalFormat;


import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTests {
	private final DecimalFormat decimalFormat = new DecimalFormat("#.##");
	@Autowired
	private MockMvc mockMvc;

	private static MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

	@Test
	public void correctInput1() throws Exception {
		requestParams.add("salary", "30000");
		requestParams.add("days", "14");

		this.mockMvc.perform(get("/calculacte").params(requestParams))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(createTestString(14334.47))));

		requestParams.clear();
	}

	@Test
	public void correctInput2() throws Exception {
		requestParams.add("salary", "30000");
		requestParams.add("days", "14");
		requestParams.add("from", "10-10-2022");
		requestParams.add("to", "24-10-2022");

		this.mockMvc.perform(get("/calculacte").params(requestParams))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(createTestString(10238.91))));

		requestParams.clear();
	}

	@Test
	public void badInputSalary() throws Exception {
		requestParams.add("salary", "0");
		requestParams.add("days", "14");

		this.mockMvc.perform(get("/calculacte").params(requestParams))
				.andDo(print())
				.andExpect(status().isBadRequest());

		requestParams.clear();
	}

	@Test
	public void badInputDays() throws Exception {
		requestParams.add("salary", "12345");
		requestParams.add("days", "-14");

		this.mockMvc.perform(get("/calculacte").params(requestParams))
				.andDo(print())
				.andExpect(status().isBadRequest());

		requestParams.clear();
	}


	@Test
	public void badInputFromDate() throws Exception {
		requestParams.add("salary", "30000");
		requestParams.add("days", "14");
		requestParams.add("from", "21/23/1985");

		this.mockMvc.perform(get("/calculacte").params(requestParams))
				.andDo(print())
				.andExpect(status().isBadRequest());

		requestParams.clear();
	}


	@Test
	public void badInputDatesAndDays() throws Exception {
		requestParams.add("salary", "30000");
		requestParams.add("days", "15");
		requestParams.add("from", "10-10-2022");
		requestParams.add("to", "24-10-2022");

		this.mockMvc.perform(get("/calculacte").params(requestParams))
				.andDo(print())
				.andExpect(status().isBadRequest());

		requestParams.clear();
	}


	@Test
	public void badInputDates() throws Exception {
		requestParams.add("salary", "30000");
		requestParams.add("days", "20");
		requestParams.add("from", "30-10-2022");
		requestParams.add("to", "10-10-2022");

		this.mockMvc.perform(get("/calculacte").params(requestParams))
				.andDo(print())
				.andExpect(status().isBadRequest());

		requestParams.clear();
	}


	@Test
	public void badInputDate() throws Exception {
		requestParams.add("salary", "30000");
		requestParams.add("days", "14");
		requestParams.add("to", "21/23/1985");

		this.mockMvc.perform(get("/calculacte").params(requestParams))
				.andDo(print())
				.andExpect(status().isBadRequest());

		requestParams.clear();
	}
	private String createTestString(double testAnswer) {
		return  String.format("Ваши отпускные составляют : %s денежных единиц в человеческой валюте", decimalFormat.format(testAnswer));
	}
}
