import javax.swing.*;

public class ConfigPanel extends JPanel {
    final MainFrame frame;
    JLabel sidesLabel; // we’re drawing regular polygons
    JSpinner sidesField; // number of sides
    JComboBox colorCombo; // the color of the shape
    JLabel sizeLabel;
    JSpinner sizeField;

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        //create the sides label and the sides spinner
        sidesLabel = new JLabel("Number of sides:");
        sidesField = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        sidesField.setValue(6); //default number of sides

        //create the colorCombo, containing the values: Random and Black
        String[] colors = {"Random", "Black"};
        colorCombo = new JComboBox(colors);
        colorCombo.setSelectedIndex(0);

        //create the size label and the size spinner
        sizeLabel = new JLabel("Size:");
        sizeField = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        sizeField.setValue(6); //default number of sides

        add(sidesLabel); //JPanel uses FlowLayout by default
        add(sidesField);
        add(colorCombo);
        add(sizeLabel);
        add(sizeField);
    }

    public int getNumberOfSides() {
        return (Integer) sidesField.getValue();
    }
}
