package team14.arms.ui.components;

import com.vaadin.flow.component.textfield.TextField;
import org.apache.commons.lang3.StringUtils;

public class NumberField extends TextField {

    private static final int DEFAULT_VALUE = 0;

    private int value;

    public NumberField(int value) {
        super();

        setNumValue(value);
        setPattern("[0-9]*");
        setPreventInvalidInput(true);

        addChangeListener(event -> {
            String text = event.getSource().getValue();
            if (StringUtils.isNumeric(text)) {
                try {
                    setNumValue(Integer.parseInt(text));
                } catch (NumberFormatException e) {
                    // For errors, put the value back to the previous value.
                    setNumValue(value);
                }
            } else {
                setNumValue(DEFAULT_VALUE);
            }
        });
    }

    public NumberField() {
        this(DEFAULT_VALUE);
    }

    public void setNumValue(int value) {
        this.value = value;
        setValue(Integer.toString(value));
    }

    public int getNumValue() {
        return value;
    }
}

