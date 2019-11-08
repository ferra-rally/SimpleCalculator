/**
 * @author ferra-rally
 * 
 */
package ferrarally;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class Calculator2 extends Application {
	private int n = 18;
	private int buttonWidth = 50;
	private int buttonHeight = 50;
	// variables for the calculation
	private String und = "UNDEFINED";
	private String a;
	private String b;
	private String op;
	private int point = 0;	// 1 if a point is present in the number
	private String[] pastOp = { "0", " ", " ", " " };
	private int repOp = 0; 
	//
	Label calcLabel;
	GridPane buttonGrid = new GridPane();
	Button[] buttonArray = new Button[n];	// array of buttons
	String[] buttonLabels = { "1", "2", "3", "+", "4", "5", "6", "-", "7", "8", "9", "X", "+/-", "0", ".", "/", "=",
			"C" };	// button labels

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) throws Exception {
		// creating buttons

		int x = 0, y = 0;

		for (int i = 0; i < n - 2; i++) {
			buttonArray[i] = new Button(buttonLabels[i]);
			buttonArray[i].setMinWidth(buttonWidth);
			buttonArray[i].setMinHeight(buttonHeight);
			GridPane.setConstraints(buttonArray[i], x, y);
			buttonGrid.getChildren().add(buttonArray[i]);
			x++;
			if (x == 4) {
				x = 0;
				y++;
			}
		}

		buttonArray[16] = new Button(buttonLabels[16]);
		buttonArray[16].setMinWidth(2 * buttonWidth);
		buttonArray[16].setMinHeight(buttonHeight);

		buttonArray[17] = new Button(buttonLabels[17]);
		buttonArray[17].setMinWidth(2 * buttonWidth);
		buttonArray[17].setMinHeight(buttonHeight);

		// creating main label
		calcLabel = new Label("0");
		calcLabel.setMinHeight(50);
		calcLabel.setMinWidth(200);
		calcLabel.setAlignment(Pos.CENTER_RIGHT);

		// setting up scene
		stage.setTitle("Calc");
		VBox root = new VBox();
		HBox base = new HBox();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		this.setButton();

		base.getChildren().addAll(buttonArray[16], buttonArray[17]);
		root.getChildren().addAll(calcLabel, buttonGrid, base);
		stage.show();
	}

	private void setButton() {
		int i;
		for (i = 0; i < n; i++) {
			final int x = i;
			if (buttonLabels[x].equals("=")) {
				buttonArray[x].setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						String result = "";
						b = calcLabel.getText();

						if (pastOp[0].equals("1")) {
							a = pastOp[1];
							op = pastOp[2];
							b = pastOp[3];
						}

						if (a != und && b != und) {
							if (op.equals("+")) {
								result = Float.toString(Float.parseFloat(a) + Float.parseFloat(b));
							} else if (op == "-") {
								result = Float.toString(Float.parseFloat(a) - Float.parseFloat(b));
							} else if (op.equals("X")) {
								result = Float.toString(Float.parseFloat(a) * Float.parseFloat(b));
							} else if (op.equals("/")) {
								float ter2 = Float.parseFloat(b);
								if (ter2 == 0) {
									result = und;
								} else {
									result = Float.toString(Float.parseFloat(a) / ter2);
								}
							} else {
								result = calcLabel.getText();
							}
							pastOp[0] = "1";
							pastOp[1] = result;
							pastOp[2] = op;
							pastOp[3] = b;
							point = 1;
							repOp = 0;
							calcLabel.setText(result);
						}
					}

				});

			} else if (buttonLabels[x].equals("+") || buttonLabels[x].equals("X") || buttonLabels[x].equals("-")
					|| buttonLabels[x].equals("/")) {
				buttonArray[x].setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						String temp;
						temp = calcLabel.getText();
						if (temp != und) {
							point = 0;
							op = buttonLabels[x];
							pastOp[0] = "0";
							if (repOp == 1) {
								buttonArray[16].fire();
								a = calcLabel.getText();
							} else {
								a = temp;
								calcLabel.setText("0");
							}
							repOp = 1;
						}
					}
				});
			} else if (buttonLabels[x].equals("C")) {
				buttonArray[x].setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						op = "";
						a = "";
						b = "";
						point = 0;
						pastOp[0] = "0";
						repOp = 0;
						calcLabel.setText("0");
					}
				});
			} else if (buttonLabels[x].equals("+/-")) {
				buttonArray[x].setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						String text = calcLabel.getText();
						String temp;
						String[] part;
						if (text != und) {
							if (text != "0") {
								if (text.charAt(0) == '-') {
									part = text.split("-");
									temp = part[1];
									calcLabel.setText(temp);
								} else {
									calcLabel.setText("-" + text);
								}
							}
						}
					}
				});
			} else if (buttonLabels[x] == ".") {
				buttonArray[x].setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						String text = calcLabel.getText();
						if (text != "UNDEFINED") {
							if (point == 0) {
								calcLabel.setText(text + buttonLabels[x]);
								point = 1;
							}
						}
					}
				});
			} else {
				buttonArray[x].setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						String text = calcLabel.getText();
						
							if (text == "0" || pastOp[0] == "1" || text == "UNDEFINED") {
								point = 0;
								calcLabel.setText(buttonLabels[x]);
							} else {
								calcLabel.setText(text + buttonLabels[x]);
							}
							pastOp[0] = "0";
						
					}
				});

			}
		}
	}

}
