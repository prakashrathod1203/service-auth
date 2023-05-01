package in.om.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import in.om.component.Translator;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Log4j2
public class CommonUtils {
	
	private final static String DATE_FORMATE = "dd/MM/yyyy";
	private static ZoneId defaultZoneId = ZoneId.systemDefault();

	public static String objectToJsonString(Object object) {
		try {
			return objectMapper().writeValueAsString(object);
		} catch(IllegalArgumentException | JsonProcessingException e) {
			log.error("Error while create object to JSON String.", e);
		}
		return null;
	}

	public static <T> T objectToPojoConverter(Object object, Class<T> dto) {
		return objectMapper().convertValue(object, dto);
	}

	public static <S, T> List<T> objectToPojoConverter(List<S> source, Class<T> targetClass) {
		return source
				.stream()
				.map(element -> objectMapper().convertValue(element, targetClass))
				.collect(Collectors.toList());
	}

	private static ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false);
		return objectMapper;
	}

	public static LocalDateTime stringToDate(String stringDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATE);
		LocalDateTime dateTime = LocalDateTime.parse(stringDate, formatter);
		return dateTime;
	}
	
	public static LocalDate stringToLocalDate(String stringDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATE);
		LocalDate localDate = LocalDate.parse(stringDate, formatter);
		return localDate;
	}
	
	public static LocalDateTime timestampToLocalDateTime(long timestamp) {
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), 
		TimeZone.getDefault().toZoneId());
		LocalTime time = LocalTime.now();
		ldt = LocalDateTime.of(ldt.getYear(), ldt.getMonth(), ldt.getDayOfMonth(), time.getHour(), time.getMinute(), time.getSecond(), time.getNano());
		return ldt;
	}
	
	public static Date getSubtractedDate(int days) {
		LocalDate localDate = LocalDate.now();
		localDate = localDate.minusDays(days);
		return Date.from(localDate.atStartOfDay( ZoneId.systemDefault()).toInstant());
	}
	
	public static MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
	
	public static Date startTime(LocalDateTime localDateTime) {
		/*LocalDate localDate = localDateTime.toLocalDate();
		Date date = null;
		if(localDateTime.getHour() <= 12) {
			date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		} else {
			localDateTime = localDate.atTime(11, 59, 59);
			date = Date.from(localDateTime.atZone(defaultZoneId).toInstant());
		}*/
		return Date.from(localDateTime.atZone(defaultZoneId).toInstant());
	}

	public static Date endTime(LocalDateTime localDateTime) {
		/*LocalDate localDate = localDateTime.toLocalDate();
		Date date = null;
		if(localDateTime.getHour() <= 12) {
			localDateTime = localDate.atTime(11, 59, 59);
			date = Date.from(localDateTime.atZone(defaultZoneId).toInstant());
		} else {
			localDateTime = localDate.atTime(23, 59, 59);
			date = Date.from(localDateTime.atZone(defaultZoneId).toInstant());
		}*/
		return Date.from(localDateTime.plusMinutes(10).atZone(defaultZoneId).toInstant());
	}
	
	/*
	public static Date acceptedOnStringToDate(String stringDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ACCE_DATE_FORMATE);
		LocalDate localDate = LocalDate.parse(stringDate, formatter);
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		return date;
	}
	
	public static Date releasedOnStringToDate(String stringDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(REL_DATE_FORMATE);
		LocalDate localDate = LocalDate.parse(stringDate, formatter);
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		return date;
	}
	
	public static String dateToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMATE);
		return formatter.format(date);
	}
	
	public static boolean isMonthFirstDate(String stringDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATE);
		LocalDate localDate = LocalDate.parse(stringDate, formatter);
		LocalDate start = localDate.withDayOfMonth(1);
		if(localDate == start) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Date getPreviousDate(String stringDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ACCE_DATE_FORMATE);
		LocalDate localDate = LocalDate.parse(stringDate, formatter);
		localDate = localDate.minusDays(1);
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		return date;
	}
	
	public static Date getPreviousDate(Date date) {
		LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		ldt.minusDays(1);
		Date dateMinusOne = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		return dateMinusOne;
	}
	
	public static Date getLastDayOfMonth(String stringDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ACCE_DATE_FORMATE);
		LocalDate localDate = LocalDate.parse(stringDate, formatter);
		localDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		return date;
	}
	
	public static Date getFirstDayOfMonth(String stringDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ACCE_DATE_FORMATE);
		LocalDate localDate = LocalDate.parse(stringDate, formatter);
		localDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		return date;
	}
	
	public static long getDayDifference (Date date1, Date date2) {
		long diffInMillies = Math.abs(date1.getTime() - date2.getTime());
		return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}*/
}
