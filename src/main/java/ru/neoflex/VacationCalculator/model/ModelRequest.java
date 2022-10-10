package ru.neoflex.VacationCalculator.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.neoflex.VacationCalculator.exc.IncorrectDatesException;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModelRequest {

	@Min(value = 1, message = "the value of the average salary cannot be less than 1")
	private double salary;
	@Min(value = 1, message = "the number of vacation days cannot be less than 1")
	private int days;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date from;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date to;

	public String calculateVacationPayAndGetTotalString() {
		double averageDailySalary = salary / 29.3;
		double totalDailySalary = (to == null || from == null) ? calculateWithoutDates(averageDailySalary)
																: calculateWithDates(averageDailySalary);
		return String.format("Ваши отпускные составляют : %.2f денежных единиц в человеческой валюте", totalDailySalary);
	}

	private double calculateWithDates(double averageDailySalary) {
		System.out.println(countDaysBetweenDates(from, to));
		if (to.before(from)) throw new IncorrectDatesException("start date later than end date");
		if (countDaysBetweenDates(from, to) != days)
			throw new IncorrectDatesException("the number of days and the difference in days between the dates does not match");
		return calculatePay(averageDailySalary, days - countDaysOffNumber());
	}

	private long countDaysBetweenDates(Date from, Date to) {
		LocalDate fromL = LocalDate.ofInstant(from.toInstant(), ZoneId.systemDefault());
		LocalDate toL = LocalDate.ofInstant(to.toInstant(), ZoneId.systemDefault());
		return ChronoUnit.DAYS.between(fromL, toL);
	}

	private int countDaysOffNumber() {
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(from);

		Calendar endDate = Calendar.getInstance();
		endDate.setTime(to);

		int offDaysCounter = 0;

		while (startDate.getTimeInMillis() < endDate.getTimeInMillis()) {
			if (startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
					startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				offDaysCounter += 1;
			startDate.add(Calendar.DAY_OF_MONTH, 1);
		}
		return offDaysCounter;
	}

	private double calculateWithoutDates(double averageDailySalary) {
		return calculatePay(averageDailySalary, days);
	}

	private double calculatePay(double averageDailySalary, int days) {
		return averageDailySalary * days;
	}
}
