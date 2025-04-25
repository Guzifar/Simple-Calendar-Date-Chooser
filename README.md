# Simple Calendar Date Chooser

A customizable Swing-based calendar date chooser component for Java applications. It supports both single date and date range selection, with extensive customization options for colors, fonts, and behavior.

## Features

- **Single and Range Selection Modes**: Choose between selecting a single date or a range of dates.
- **Customizable Appearance**:
  - Colors for headers, navigation bar, today’s date, selected dates, range start/end, range middle, and highlighted dates.
  - Foreground colors for various states (e.g., selected dates, today, range dates, highlighted dates).
  - Fonts for the month-year label, day headers, day buttons, and navigation buttons.
- **Behavior Customization**:
  - Option to disable past dates.
  - Option to disable Sundays.
  - Enable or disable selection of highlighted dates.
  - Show or hide the "Today" button and the mode toggle button (new in v1.1.0).
- **Month Selection Popup** (new in v1.1.0):
  - Click the month-year label to open a popup with a 4x3 grid of months (January to December).
  - Select a month to update the calendar while keeping the current year.
  - Customizable popup background, button background, button foreground, button hover background, and button font (new in v1.1.0).
- **Highlighted Dates**: Highlight specific dates with a custom color.
- **Date Format Customization**: Set a custom date format for displaying selected dates.
- **Event Listener**: Add a `DateSelectionListener` to react to date selection changes.
- **Right-Click to Clear**: Right-click any date to clear the current selection.
- **Navigation**:
  - Navigate months using previous/next buttons.
  - Jump to the current date with the "Today" button (if visible).
  - Toggle between single and range selection modes with the mode toggle button (if visible).

## Installation

1. **Download the JAR**:
   - Download the latest `SimpleCalendarDateChooser.jar` from the [Releases](https://github.com/Guzifar/Simple-Calendar-Date-Chooser/releases/tag/v1.1.0) page (latest version: v1.1.0).

2. **Add to Your Project**:
   - Add the JAR to your project’s build path in your IDE (e.g., Eclipse, IntelliJ IDEA).
   - For example, in Eclipse: Right-click your project > Properties > Java Build Path > Libraries > Add External JARs > Select the JAR.

3. **Ensure Dependencies**:
   - The component uses Java’s Swing library, which is included in the JDK. No additional dependencies are required.

## Usage

### Basic Setup

Here’s an example of how to use the `SimpleCalendarDateChooser` in your Swing application:

```java
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Calendar Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLayout(new BorderLayout());
            frame.setLocationRelativeTo(null);

            // Create the calendar with custom settings
            SimpleCalendarDateChooser calendar = new SimpleCalendarDateChooser(
                Color.BLUE, Color.CYAN, Color.LIGHT_GRAY,
                Color.DARK_GRAY, Color.BLUE, Color.BLACK, null,
                Color.BLACK, new Color(200, 255, 200), Color.ORANGE, Color.MAGENTA, null,
                Color.WHITE, Color.WHITE, Color.WHITE, Color.BLUE, Color.RED,
                Color.RED,
                new Font("Arial", Font.BOLD, 16),
                new Font("Arial", Font.PLAIN, 12),
                new Font("Arial", Font.PLAIN, 12),
                new Font("Segoe UI Symbol", Font.BOLD, 14),
                new Color(240, 240, 240), Color.WHITE, Color.BLACK, Color.CYAN,
                new Font("Arial", Font.BOLD, 14),
                true, true, true, true, true, SimpleCalendarDateChooser.SelectionMode.RANGE,
                "MMMM dd, yyyy"
            );

            // Customize colors
            calendar.setSelectedColor(Color.RED);
            calendar.setRangeStartEndColor(Color.GREEN);
            calendar.setRangeMiddleColor(new Color(150, 255, 150));
            calendar.setSelectedSingleForeground(Color.YELLOW);
            calendar.setRangeStartEndForeground(Color.BLACK);
            calendar.setRangeMiddleForeground(Color.DARK_GRAY);
            calendar.setTodaySingleForeground(Color.BLUE);
            calendar.setTodayRangeForeground(Color.RED);

            // Highlight specific dates
            List<LocalDate> datesToHighlight = new ArrayList<>();
            datesToHighlight.add(LocalDate.of(2025, 4, 25));
            datesToHighlight.add(LocalDate.of(2025, 4, 26));
            calendar.setHighlightedDates(datesToHighlight);

            // Add a button to get selected dates
            JButton getDatesButton = new JButton("Get Dates");
            getDatesButton.addActionListener(e -> {
                LocalDate todayDate = calendar.getTodayDate();
                Object selected = calendar.getSelectedDatesAsStrings();
                String currentFormat = calendar.getDateFormat();
                String message = "Today: " + todayDate.format(calendar.dateFormatter) +
                                "\nCurrent Format: " + currentFormat +
                                "\nSelected Dates: " + (selected instanceof String ? selected : selected.toString());
                JOptionPane.showMessageDialog(frame, message);
            });

            // Add a button to change the date format
            JButton changeFormatButton = new JButton("Change Format to yyyy-MM-dd");
            changeFormatButton.addActionListener(e -> {
                calendar.setDateFormat("yyyy-MM-dd");
                JOptionPane.showMessageDialog(frame, "Date format changed to: " + calendar.getDateFormat());
            });

            // Add a button to change the nav button hover color
            JButton changeHoverColorButton = new JButton("Change Hover Color to Cyan");
            changeHoverColorButton.addActionListener(e -> {
                calendar.setNavButtonHoverForeground(Color.CYAN);
                JOptionPane.showMessageDialog(frame, "Nav button hover color changed to Cyan");
            });

            // Add a button to change the highlight foreground color
            JButton changeHighlightFgButton = new JButton("Change Highlight FG to Purple");
            changeHighlightFgButton.addActionListener(e -> {
                calendar.setHighlightForeground(Color.MAGENTA);
                JOptionPane.showMessageDialog(frame, "Highlight foreground color changed to Purple");
            });

            // Add a button to toggle the Today button visibility
            JButton toggleTodayButton = new JButton("Toggle Today Button");
            toggleTodayButton.addActionListener(e -> {
                calendar.setShowTodayButton(!calendar.isShowTodayButton());
                JOptionPane.showMessageDialog(frame, "Today button visibility: " + (calendar.isShowTodayButton() ? "Shown" : "Hidden"));
            });

            // Add a button to toggle the mode toggle button visibility
            JButton toggleModeButton = new JButton("Toggle Mode Button");
            toggleModeButton.addActionListener(e -> {
                calendar.setShowToggleButton(!calendar.isShowToggleButton());
                JOptionPane.showMessageDialog(frame, "Mode toggle button visibility: " + (calendar.isShowToggleButton() ? "Shown" : "Hidden"));
            });

            // Add buttons to the frame
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(getDatesButton);
            buttonPanel.add(changeFormatButton);
            buttonPanel.add(changeHoverColorButton);
            buttonPanel.add(changeHighlightFgButton);
            buttonPanel.add(toggleTodayButton);
            buttonPanel.add(toggleModeButton);
            frame.add(buttonPanel, BorderLayout.SOUTH);

            frame.add(calendar, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}
```

### Screenshots

Below is a screenshot of the `SimpleCalendarDateChooser` in action.

![Default](https://i.imgur.com/P9veaoX.png)

![Month Selection](https://i.imgur.com/g8tVXBE.png))

![Demo](https://i.imgur.com/sgMmL8J.gif))

## Customization Options

### Colors
- `headerColor`: Color of the day-of-week headers.
- `highlightColor`: Background color for highlighted dates.
- `navBarColor`: Background color of the navigation bar.
- `monthYearForeground`: Foreground color of the month-year label.
- `headerForeground`: Foreground color of the day-of-week headers.
- `navButtonForeground`: Foreground color of navigation buttons.
- `navButtonHoverForeground`: Hover foreground color of navigation buttons.
- `dayButtonForeground`: Foreground color of day buttons.
- `todayColor`: Background color for today’s date.
- `selectedColor`: Background color for selected dates (single mode).
- `rangeStartEndColor`: Background color for the start and end of a range.
- `rangeMiddleColor`: Background color for dates in the middle of a range.
- `selectedSingleForeground`: Foreground color for selected dates (single mode).
- `rangeStartEndForeground`: Foreground color for the start and end of a range.
- `rangeMiddleForeground`: Foreground color for dates in the middle of a range.
- `todaySingleForeground`: Foreground color for today’s date (single mode).
- `todayRangeForeground`: Foreground color for today’s date (range mode).
- `highlightForeground`: Foreground color for highlighted dates.
- `monthPopupBackground`: Background color of the month selection popup (new in v1.1.0).
- `monthButtonBackground`: Background color of the month buttons in the popup (new in v1.1.0).
- `monthButtonForeground`: Foreground color of the month buttons in the popup (new in v1.1.0).
- `monthButtonHoverBackground`: Hover background color of the month buttons in the popup (new in v1.1.0).

### Fonts
- `monthYearFont`: Font for the month-year label.
- `headerFont`: Font for the day-of-week headers.
- `dayButtonFont`: Font for the day buttons.
- `navButtonFont`: Font for the navigation buttons.
- `monthButtonFont`: Font for the month buttons in the popup (new in v1.1.0).

### Behavior
- `disablePastDates`: Set to `true` to disable selection of past dates.
- `disableSundays`: Set to `true` to disable selection of Sundays.
- `enableHighlightedDateSelection`: Set to `true` to allow selection of highlighted dates.
- `showTodayButton`: Set to `true` to show the "Today" button, `false` to hide it (new in v1.1.0).
- `showToggleButton`: Set to `true` to show the mode toggle button, `false` to hide it (new in v1.1.0).
- `selectionMode`: Set to `SelectionMode.SINGLE` for single date selection or `SelectionMode.RANGE` for range selection.
- `dateFormatPattern`: Pattern for formatting selected dates (e.g., `"yyyy-MM-dd"`).

## Contributing

Contributions are welcome! Please fork the repository, make your changes, and submit a pull request. Ensure your code follows the existing style and includes appropriate documentation.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Releases

- **v1.1.0** (Latest): Added a month selection popup when clicking the month-year label, with customization options (background, button colors, font). Added options to show/hide the "Today" and mode toggle buttons. Added support for tooltips on the "Today" and mode toggle buttons, and updated font styling for navigation buttons.
- **v1.0.0**: Initial release with core functionality (single/range selection, customization, highlighted dates, etc.).

[Download the latest release](https://github.com/Guzifar/Simple-Calendar-Date-Chooser/releases/tag/v1.1.0)
