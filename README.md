# Simple Calendar Date Chooser

## Description
The **Simple Calendar Date Chooser** is a customizable Java Swing component (`JPanel`) for selecting dates. It provides a clean and intuitive calendar interface to pick single dates or date ranges, with extensive options to customize colors, fonts, behavior, and date formatting. Ideal for applications needing a date picker, it integrates seamlessly with NetBeans UI Builder, allowing easy customization via the Properties window.

## Features
- **Selection Modes**: Choose single dates (`SINGLE`) or a date range (`RANGE`).
- **Customizable Date Format**:
  - Default format: `MMMM dd, yyyy` (e.g., "April 25, 2025").
  - Set custom formats (e.g., `yyyy-MM-dd`, `dd MMM yyyy`) using `setDateFormat(String pattern)`.
  - Retrieve the current format with `getDateFormat()`.
- **Customizable Colors**:
  - Backgrounds: Single selection, range start/end, range middle, today.
  - Foregrounds: Single selection, range start/end, range middle, today (separate for single/range modes).
  - Colors for headers, navigation buttons, and highlighted dates.
- **Customizable Fonts**: For month/year label, day headers, day buttons, and navigation buttons.
- **Behavior Options**:
  - Disable past dates or Sundays.
  - Highlight specific dates (e.g., holidays) with a custom color.
  - Right-click to clear selections.
- **Navigation**: Switch months with arrow buttons (includes hover effects for better UX).
- **NetBeans Support**: Edit properties directly in the NetBeans Properties window for quick customization.

![Calendar Appearance](https://i.imgur.com/ZqAV4ar.png "Simple Calendar Date Chooser in RANGE mode")

## Example Usage

### 1. Using in NetBeans
1. **Add to Project**:
   - Compile `SimpleCalendarDateChooser.java and DateSelectionListener.java` and add it to the NetBeans Palette (`Tools > Palette > Swing/AWT Components`).
2. **Add to Form**:
   - Drag `SimpleCalendarDateChooser` onto a JFrame Form.
3. **Customize**:
   - In the Properties window, set:
     - `selectionMode` to `RANGE`
     - `selectedColor` to red
     - `rangeStartEndColor` to green
     - `selectedSingleForeground` to yellow
     - `dateFormatPattern` to `dd MMM yyyy` (e.g., "25 Apr 2025")

   Below are screenshots of the Properties window showing the customization options for the `SimpleCalendarDateChooser`:

   ![Properties Window 1](https://i.imgur.com/UATibcK.png "Properties Window - Basic Settings")
   
   ![Properties Window 2](https://i.imgur.com/NkwVlyA.png "Properties Window - Color Settings")

### 2. Programmatic Usage
```java
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

            // Create SimpleCalendarDateChooser
            SimpleCalendarDateChooser calendar = new SimpleCalendarDateChooser();
            calendar.setSelectionMode(SimpleCalendarDateChooser.SelectionMode.RANGE);
            calendar.setSelectedColor(Color.RED);
            calendar.setRangeStartEndColor(Color.GREEN);
            calendar.setRangeMiddleColor(new Color(150, 255, 150));
            calendar.setSelectedSingleForeground(Color.YELLOW);
            calendar.setRangeStartEndForeground(Color.BLACK);
            calendar.setTodaySingleForeground(Color.BLUE);
            calendar.setDateFormat("dd MMM yyyy"); // Custom date format

            // Highlight dates
            List<LocalDate> datesToHighlight = new ArrayList<>();
            datesToHighlight.add(LocalDate.of(2025, 4, 25));
            calendar.setHighlightedDates(datesToHighlight);

            // Button to show selected dates and format
            JButton getDatesButton = new JButton("Get Dates");
            getDatesButton.addActionListener(e -> {
                Object selected = calendar.getSelectedDatesAsStrings();
                String currentFormat = calendar.getDateFormat();
                String message = "Current Format: " + currentFormat +
                                 "\nSelected: " + (selected instanceof String ? selected : selected.toString());
                JOptionPane.showMessageDialog(frame, message);
            });

            // Button to change date format
            JButton changeFormatButton = new JButton("Change Format to yyyy-MM-dd");
            changeFormatButton.addActionListener(e -> {
                calendar.setDateFormat("yyyy-MM-dd");
                JOptionPane.showMessageDialog(frame, "Date format changed to: " + calendar.getDateFormat());
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(getDatesButton);
            buttonPanel.add(changeFormatButton);
            frame.add(buttonPanel, BorderLayout.SOUTH);

            frame.add(calendar, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}
```

### 3. Testing
- **Single Mode**: Set `selectionMode` to `SINGLE`, click a date—it turns red with yellow text (e.g., "25 Apr 2025" with the custom format).
- **Range Mode**: Set `selectionMode` to `RANGE`, select two dates—start/end turn green with black text, middle dates are light green (e.g., ["25 Apr 2025", "26 Apr 2025"]).
- **Date Format Change**: Click "Change Format to yyyy-MM-dd", then select dates—they’ll display as "2025-04-25".
- **Clear Selection**: Right-click to clear selected dates.

![Calendar Interaction](https://i.imgur.com/CaJI9AD.gif "Selecting a range and clearing selection")
