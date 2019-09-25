package kata.ex01;

import kata.ex01.model.HighwayDrive;
import kata.ex01.util.HolidayUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static kata.ex01.model.RouteType.RURAL;

/**
 * @author kawasima
 */
public class DiscountServiceImpl implements DiscountService {
    @Override
    public long calc(HighwayDrive drive) {

        LocalDateTime morningEnd, morningStart;
        if (drive.getEnteredAt().getHour() >= 9) {
            morningStart = LocalDateTime.of(
                    LocalDate.from(drive.getEnteredAt().plusDays(1)),
                    LocalTime.of(6, 0));
            morningEnd = LocalDateTime.of(
                    LocalDate.from(drive.getExitedAt().plusDays(1)),
                    LocalTime.of(9, 0));
        } else {
            morningStart = LocalDateTime.of(
                    LocalDate.from(drive.getEnteredAt()),
                    LocalTime.of(6, 0));
            morningEnd = LocalDateTime.of(
                    LocalDate.from(drive.getExitedAt()),
                    LocalTime.of(9, 0));
        }

        LocalDateTime eveningStart, eveningEnd;
        if (drive.getEnteredAt().getHour() >= 17) {
            eveningStart = LocalDateTime.of(
                    LocalDate.from(drive.getEnteredAt().plusDays(1)),
                    LocalTime.of(17, 0));
            eveningEnd = LocalDateTime.of(
                    LocalDate.from(drive.getExitedAt().plusDays(1)),
                    LocalTime.of(20, 0));
        } else {
            eveningStart = LocalDateTime.of(
                    LocalDate.from(drive.getEnteredAt()),
                    LocalTime.of(17, 0));
            eveningEnd = LocalDateTime.of(
                    LocalDate.from(drive.getExitedAt()),
                    LocalTime.of(20, 0));
        }

        LocalDateTime midnightStart, midnightEnd;
        if (drive.getEnteredAt().getHour() >= 0) {
            midnightStart = LocalDateTime.of(
                    LocalDate.from(drive.getEnteredAt().plusDays(1)),
                    LocalTime.of(0, 0));
            midnightEnd = LocalDateTime.of(
                    LocalDate.from(drive.getExitedAt().plusDays(1)),
                    LocalTime.of(4, 0));
        } else {
            midnightStart = LocalDateTime.of(
                    LocalDate.from(drive.getEnteredAt()),
                    LocalTime.of(0, 0));
            midnightEnd = LocalDateTime.of(
                    LocalDate.from(drive.getExitedAt()),
                    LocalTime.of(4, 0));
        }


        // 平日朝夕割引
        if (drive.getRouteType() == RURAL) {
            if (HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate()) || HolidayUtils.isHoliday(drive.getExitedAt().toLocalDate())) {
                return 30;
            }
            // 50%
            if (drive.getDriver().getCountPerMonth() >= 10) {
                return 50;
            }
        } else {
            // 深夜割引
            if (drive.getEnteredAt().isBefore(midnightEnd) && drive.getEnteredAt().isAfter(midnightStart)) {
                return 30;
            } else {
                return 0;
            }
        }
        return 0;
    }
}
