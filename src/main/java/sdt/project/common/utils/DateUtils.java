package sdt.project.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    /**
     * System default zone (Asia/Ho_Chi_Minh)
     */
    public static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    /**
     * Common formatters
     */
    public static final DateTimeFormatter DATE =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final DateTimeFormatter DATE_TIME =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter ISO_DATE_TIME =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private DateUtils() {
        // Utility class
    }

    /* =====================================================
     * NOW
     * ===================================================== */

    public static LocalDate today() {
        return LocalDate.now(DEFAULT_ZONE);
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(DEFAULT_ZONE);
    }

    public static Instant nowInstant() {
        return Instant.now();
    }

    /* =====================================================
     * ADD / SUBTRACT
     * ===================================================== */

    public static LocalDateTime plusMinutes(long minutes) {
        return now().plusMinutes(minutes);
    }

    public static LocalDateTime plusHours(long hours) {
        return now().plusHours(hours);
    }

    public static LocalDateTime plusDays(long days) {
        return now().plusDays(days);
    }

    public static LocalDateTime plusMonths(long months) {
        return now().plusMonths(months);
    }

    public static LocalDateTime minusMinutes(long minutes) {
        return now().minusMinutes(minutes);
    }

    /* =====================================================
     * CONVERT
     * ===================================================== */

    public static LocalDateTime toLocalDateTime(Instant instant) {
        return instant == null ? null :
                LocalDateTime.ofInstant(instant, DEFAULT_ZONE);
    }

    public static Instant toInstant(LocalDateTime dateTime) {
        return dateTime == null ? null :
                dateTime.atZone(DEFAULT_ZONE).toInstant();
    }

    public static LocalDate toLocalDate(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.toLocalDate();
    }

    /* =====================================================
     * FORMAT
     * ===================================================== */

    public static String format(LocalDate date) {
        return date == null ? null : date.format(DATE);
    }

    public static String format(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(DATE_TIME);
    }

    public static String formatIso(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(ISO_DATE_TIME);
    }

    /* =====================================================
     * PARSE
     * ===================================================== */

    public static LocalDate parseDate(String value) {
        return value == null ? null : LocalDate.parse(value, DATE);
    }

    public static LocalDateTime parseDateTime(String value) {
        return value == null ? null : LocalDateTime.parse(value, DATE_TIME);
    }

    /* =====================================================
     * COMPARE
     * ===================================================== */

    public static boolean isExpired(LocalDateTime expiredAt) {
        return expiredAt != null && expiredAt.isBefore(now());
    }

    public static boolean isBetween(
            LocalDateTime target,
            LocalDateTime from,
            LocalDateTime to
    ) {
        return target != null &&
                !target.isBefore(from) &&
                !target.isAfter(to);
    }
}

