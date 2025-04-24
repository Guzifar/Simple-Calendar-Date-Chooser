# Simple-Calendar-Date-Chooser

The Simple Calendar Date Chooser is a customizable Java Swing component (JPanel) for selecting dates. It offers a clean calendar interface to pick single dates or date ranges, with options to customize colors, fonts, and behavior. Perfect for apps needing a date picker, it integrates seamlessly with NetBeans 8.2's UI Builder for easy customization via the Properties window.
Features

Selection Modes: Choose single dates (SINGLE) or a date range (RANGE).
Customizable Colors:
Backgrounds: Single selection, range start/end, range middle, today.
Foregrounds: Single selection, range start/end, range middle, today (separate for single/range modes).
Colors for headers, navigation, and highlighted dates.


Customizable Fonts: For month/year, day headers, day buttons, and navigation buttons.
Behavior Options:
Disable past dates or Sundays.
Highlight specific dates (e.g., holidays).
Right-click to clear selections.


Navigation: Switch months with arrow buttons (with hover effects).
NetBeans Support: Edit properties in NetBeans 8.2's Properties window.


Example Usage
1. Using in NetBeans

Add to Project:

Compile CalendarPanel.java and add it to the NetBeans Palette (Tools > Palette > Swing/AWT Components).


Add to Form:

Drag CalendarPanel onto a JFrame Form.


Customize:

In the Properties window, set:
selectionMode to RANGE
selectedColor to red
rangeStartEndColor to green
selectedSingleForeground to yellow



Below are screenshots of the Properties window showing the customization options for the CalendarPanel:



2. Programmatic Usage
```
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Calendar Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLayout(new BorderLayout());
            frame.setLocationRelativeTo(null);

            // Create CalendarPanel
            CalendarPanel calendar = new CalendarPanel();
            calendar.setSelectionMode(CalendarPanel.SelectionMode.RANGE);
            calendar.setSelectedColor(Color.RED);
            calendar.setRangeStartEndColor(Color.GREEN);
            calendar.setRangeMiddleColor(new Color(150, 255, 150));
            calendar.setSelectedSingleForeground(Color.YELLOW);
            calendar.setRangeStartEndForeground(Color.BLACK);
            calendar.setTodaySingleForeground(Color.BLUE);

            // Highlight dates
            var datesToHighlight = new ArrayList<LocalDate>();
            datesToHighlight.add(LocalDate.of(2025, 4, 25));
            calendar.setHighlightedDates(datesToHighlight);

            // Button to show selected dates
            JButton getDatesButton = new JButton("Get Dates");
            getDatesButton.addActionListener(e -> {
                String message = "Selected: " + calendar.getSelectedDates();
                JOptionPane.showMessageDialog(frame, message);
            });
            frame.add(getDatesButton, BorderLayout.SOUTH);

            frame.add(calendar, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}
```
3. Testing

Single Mode: Set selectionMode to SINGLE, click a date—it turns red with yellow text.
Range Mode: Set selectionMode to RANGE, select two dates—start/end turn green with black text, middle dates are light green.
Clear Selection: Right-click to clear selected dates.


