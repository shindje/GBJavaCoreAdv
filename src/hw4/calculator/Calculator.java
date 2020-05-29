package hw4.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Calculator {
    @FXML
    public TextField fullTextField;
    @FXML
    public TextField textField;
    @FXML
    public TextField memoField;
    @FXML
    public TextArea history;

    final int MAX_TEXT_LENGTH = 10;
    private Double result = null;
    private enum ActionType {DIGIT, OPERATION, FINISH};
    private ActionType lastActionType = null;
    private Operation lastOperaion = null;

    public void delClick(ActionEvent actionEvent) {
        if (lastActionType == ActionType.DIGIT) {
            String text = textField.getText();
            if (text.length() == 1) {
                text = "0";
            } else {
                text = text.substring(0, text.length() - 1);
            }

            textField.setText(text);
        }
    }

    public void divXClick(ActionEvent actionEvent) {
        Double d = Double.parseDouble(textField.getText());
        textField.setText(convertDouble(1/d));
        lastActionType = ActionType.OPERATION;
    }

    public void squareClick(ActionEvent actionEvent) {
        Double d = Double.parseDouble(textField.getText());
        textField.setText(convertDouble(d*d));
        lastActionType = ActionType.OPERATION;
    }

    public void rootClick(ActionEvent actionEvent) {
        Double d = Double.parseDouble(textField.getText());
        if (d < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка вычисления");
            alert.setHeaderText("Нельзя извлекать корень из отрицательного числа");
            alert.showAndWait();
        } else {
            textField.setText(convertDouble(Math.sqrt(d)));
            lastActionType = ActionType.OPERATION;
        }
    }

    public void plusMinusClick(ActionEvent actionEvent) {
        Double d = Double.parseDouble(textField.getText());
        textField.setText(convertDouble(d*-1));
    }

    public void divideClick(ActionEvent actionEvent) {
        Operation operation = new Operation("/") {
            @Override
            Double doAction() {
                if (Double.parseDouble(textField.getText()) == 0) {
                    throw new IllegalArgumentException("Делить на ноль нельзя!");
                } else {
                    return result / Double.parseDouble(textField.getText());
                }
            }
        };
        operation.actionPerform();
    }

    public void multiplyClick(ActionEvent actionEvent) {
        Operation operation = new Operation("*") {
            @Override
            Double doAction() {
                return result * Double.parseDouble(textField.getText());
            }
        };
        operation.actionPerform();
    }

    public void minusClick(ActionEvent actionEvent) {
        Operation operation = new Operation("-") {
            @Override
            Double doAction() {
                return result - Double.parseDouble(textField.getText());
            }
        };
        operation.actionPerform();
    }

    public void plusClick(ActionEvent actionEvent) {
        Operation operation = new Operation("+") {
            @Override
            Double doAction() {
                return result + Double.parseDouble(textField.getText());
            }
        };
        operation.actionPerform();
    }

    public void dotClick(ActionEvent actionEvent) {
        String text = textField.getText();
        if (lastActionType != ActionType.FINISH && text.indexOf(".") == -1) {
            text += ".";
            textField.setText(text);
        }
    }

    public void equalsClick(ActionEvent actionEvent) {
        Operation operation = new Operation("=") {
            @Override
            Double doAction() {
                return result;
            }

            void onActionPerformed() {
                lastOperaion = null;
                lastActionType = ActionType.FINISH;
                history.appendText(fullTextField.getText() + "  " + convertDouble(result) + "\n");
            }
        };
        operation.actionPerform();
    }

    public void clearHistory(ActionEvent actionEvent) {
        history.setText("");
    }

    public void numberClick(ActionEvent actionEvent) {
        String text = textField.getText();
        if (text.equals("0") || lastActionType == ActionType.OPERATION || lastActionType == ActionType.FINISH) {
            text = "";
        }
        if (text.length() < MAX_TEXT_LENGTH) {
            text += ((NumberButton)actionEvent.getSource()).getNumber();
        }
        textField.setText(text);
        lastActionType = ActionType.DIGIT;
    }

    public void mcClick(ActionEvent actionEvent) {
        memoField.setText("");
    }

    public void mrClick(ActionEvent actionEvent) {
        String memo = memoField.getText();
        if (!memo.equals("")) {
            textField.setText(memo);
        }
        lastActionType = ActionType.OPERATION;
    }

    public void mPlusClick(ActionEvent actionEvent) {
        String memo = memoField.getText();
        if (memo.equals("")) {
            memo = "0";
        }
        Double d = Double.parseDouble(memo) + Double.parseDouble(textField.getText());
        memoField.setText(convertDouble(d));
        lastActionType = ActionType.OPERATION;
    }

    public void mMinusClick(ActionEvent actionEvent) {
        String memo = memoField.getText();
        if (memo.equals("")) {
            memo = "0";
        }
        Double d = Double.parseDouble(memo) - Double.parseDouble(textField.getText());
        memoField.setText(convertDouble(d));
        lastActionType = ActionType.OPERATION;
    }

    public void percentClick(ActionEvent actionEvent) {
        Double d = Double.parseDouble(textField.getText());
        textField.setText(convertDouble(result * d / 100));
        lastActionType = ActionType.OPERATION;
    }

    public void ceClick(ActionEvent actionEvent) {
        textField.setText("0");
    }

    public void cClick(ActionEvent actionEvent) {
        lastActionType = ActionType.FINISH;
        lastOperaion = null;
        result = null;
        fullTextField.setText("");
        textField.setText("0");
    }



    static String convertDouble(Double d) {
        if (d.doubleValue() == d.intValue()) {
            return "" + d.intValue();
        } else {
            return d.toString();
        }
    }

    abstract class Operation {
        private String actionText;

        Operation (String actionText) {
            this.actionText = actionText;
        }

        public void actionPerform() {
            String text = textField.getText();
            String fullText = fullTextField.getText();
            try {
                if (lastOperaion != null) {
                    result = lastOperaion.doAction();
                    textField.setText(convertDouble(result));
                } else {
                    result = Double.parseDouble(text);
                    fullText = "";
                }
                fullText += "  " + text;
                fullText += "  " + actionText;
                fullTextField.setText(fullText);

                lastActionType = ActionType.OPERATION;
                lastOperaion = this;

                onActionPerformed();
            } catch (IllegalArgumentException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка вычисления");
                alert.setHeaderText(ex.getMessage());
                alert.showAndWait();
            }
        }

        void onActionPerformed() {};

        abstract Double doAction();
    };
}

