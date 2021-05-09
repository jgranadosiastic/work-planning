package com.jgranados.workplanner.shifts.dto;

import com.jgranados.workplanner.shifts.enums.ShiftHour;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class NewShiftDTO {

    @NotNull
    private LocalDate shiftDate;

    @NotNull
    private ShiftHour shiftHour;
}
