package main.views;

import javafx.scene.control.ListCell;
import main.preprocess.PreprocessorOperation;

public class OperationListCell extends ListCell<PreprocessorOperation> {

    private OperationView operationView;

    @Override
    protected void updateItem(PreprocessorOperation operation, boolean empty) {
        super.updateItem(operation, empty);

        if (operation != null) {

            if (operationView == null) {
                operationView = new OperationView();
            }

            operationView.setText(operation.getType().getName());
            setGraphic(operationView.getContainer());
        }
    }
}
