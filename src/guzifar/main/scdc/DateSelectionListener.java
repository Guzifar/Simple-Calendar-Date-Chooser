/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guzifar.main.scdc;

/**
 *
 * @author Fern
 */
import java.time.LocalDate;
import java.util.List;

public interface DateSelectionListener {
    void datesSelected(List<LocalDate> selectedDates);
}
