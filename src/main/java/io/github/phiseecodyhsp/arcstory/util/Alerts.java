package io.github.phiseecodyhsp.arcstory.util;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Alerts {

    /**
     * 创建一个异常提示窗口, 会将传入的异常消息与堆栈展示出来, 并阻塞主线程运行.
     *
     * @param exception 要提示的异常
     */
    public static void alertException(Exception exception) {
        // 换用 slf4j?
        exception.printStackTrace();

        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        String stackTrace = writer.toString();

        Platform.runLater(() -> showAlert(exception.getMessage(), stackTrace));
    }

    private static void showAlert(String message, String stackTrace) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("错误");
        alert.setHeaderText("程序抛出异常");
        alert.setContentText(message);

        TextArea textArea = new TextArea(stackTrace);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        textArea.setPrefColumnCount(50);
        textArea.setPrefRowCount(15);

        alert.getDialogPane().setExpandableContent(textArea);
        alert.getDialogPane().setExpanded(false);

        alert.showAndWait();
    }
}
