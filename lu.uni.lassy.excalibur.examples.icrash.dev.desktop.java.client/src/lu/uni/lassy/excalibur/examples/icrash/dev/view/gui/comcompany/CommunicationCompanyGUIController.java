/*******************************************************************************
 * Copyright (c) 2014-2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *     Christophe Kamphaus - Remote implementation of Actors
 *     Thomas Mortimer - Updated client to MVC and added new design patterns
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.comcompany;

import java.net.URL;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.ComCompanyController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectActorException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectFormatException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.InvalidHumanKindException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.StringToNumberException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtHumanKind; 
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.ICrashUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Message;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractGUIController;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.HasTables;
/*
 * This is the import section to be replaced by modifications in the CommunicationCompanyGUI.fxml document from the sample skeleton controller
 */
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;

/*
 * This is the end of the import section to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
 */

/**
 * The Class CommunicationCompanyGUIController, used for controlling the GUI for the communication company.
 * The GUI is an example of how the system might work, but normally the would create an alert by a separate API that has access to the server.
 */
public class CommunicationCompanyGUIController extends AbstractGUIController implements HasTables {
	/*
	* This section of controls and methods is to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
	* When replacing, remember to reassign the correct methods to the button event methods and set the correct types for the tableviews
	*/

	/** The scroll pane of which to insert the controls for sending an alert to the system */
    @FXML
    private ScrollPane scrllpnSendAlert;

    /** The tableview of all the dispatched messages sent to each communication company. */
    @FXML
    private TableView<Message> tblvwDispatchedMessages;
	/*
    * These are other classes accessed by this controller
    */
	
	/** The comcompany controller that is used to create alerts or get communication company information. */
	ComCompanyController comcompanyController;
	
	/*
	* Other things created for this controller
	*/
	
	//Nothing here....
	
	/*
	 * Methods used within the GUI
	 */	
	
	/**
	 * Creates the alert form inside the Anchor pane. Since controls are being generated by other places, this seemed simpler than manually adding the controls myself via scenebuilder.
	 */
	private void createAlertForm(){
		int width = 250;
		Label lblPersonType = new Label("Type of person:");
		ListView<EtHumanKind> lstvwPersonType = new ListView<EtHumanKind>();
		lstvwPersonType.setMinWidth(width);
		lstvwPersonType.setMaxWidth(width);
		lstvwPersonType.setMaxHeight(50);
		lstvwPersonType.setItems( FXCollections.observableArrayList( EtHumanKind.values()));
		lstvwPersonType.setOnEditCommit(new EventHandler<ListView.EditEvent<EtHumanKind>>() {
			@Override
			public void handle(EditEvent<EtHumanKind> event) {
				if (lstvwPersonType.getSelectionModel().getSelectedItems().size() > 1){
					lstvwPersonType.getSelectionModel().clearSelection();
					lstvwPersonType.getSelectionModel().select(event.getNewValue());
				}
			}
		});
		
		Label lblCrisisType = new Label("Type of crisis:");
		ListView<EtCrisisType> lstvwCrisisType = new ListView<EtCrisisType>();
		lstvwCrisisType.setMinWidth(width);
		lstvwCrisisType.setMaxWidth(width);
		lstvwCrisisType.setMaxHeight(50);
		lstvwCrisisType.setItems( FXCollections.observableArrayList( EtCrisisType.values()));
		lstvwCrisisType.setOnEditCommit(new EventHandler<ListView.EditEvent<EtCrisisType>>() {
			@Override
			public void handle(EditEvent<EtCrisisType> event) {
				if (lstvwCrisisType.getSelectionModel().getSelectedItems().size() > 1){
					lstvwCrisisType.getSelectionModel().clearSelection();
					lstvwCrisisType.getSelectionModel().select(event.getNewValue());
				}
			}
		});
		DatePicker dtpckr = getDatePicker(ICrashUtils.getCurrentDate(), width);
		DtTime aDtTime = ICrashUtils.getCurrentTime();
		TextField txtfldCurrentSetHour = new TextField();
		TextField txtfldCurrentSetMinute = new TextField();
		TextField txtfldCurrentSetSecond = new TextField();
		Slider sldrHourPicker = getSlider(width/2, SliderType.Hour, txtfldCurrentSetHour, aDtTime);
		Slider sldrMinutePicker = getSlider(width/2, SliderType.Minute, txtfldCurrentSetMinute, aDtTime);
		Slider sldrSecondPicker = getSlider(width/2, SliderType.Second, txtfldCurrentSetSecond, aDtTime);
		Label lblDate = new Label("Date:");
		Label lblTimeHour = new Label("Hour:");
		Label lblTimeMinute = new Label("Minute:");
		Label lblTimeSecond = new Label("Second:");
		TextField txtfldPhone = new TextField();
		txtfldPhone.setMinWidth(width);
		txtfldPhone.setMaxWidth(width);
		txtfldPhone.setPromptText("Enter a phone number");
		TextField txtfldLongitude = new TextField();
		txtfldLongitude.setMinWidth(width);
		txtfldLongitude.setMaxWidth(width);
		txtfldLongitude.setPromptText("Enter a longitude");
		TextField txtfldLatitude = new TextField();
		txtfldLatitude.setMinWidth(width);
		txtfldLatitude.setMaxWidth(width);
		txtfldLatitude.setPromptText("Enter a latitude");
		TextArea txtarComment = new TextArea();
		txtarComment.setMinWidth(width);
		txtarComment.setMaxWidth(width);
		txtarComment.setPromptText("Enter a comment");
		Button bttnOk = new Button("Send alert");
		bttnOk.setDefaultButton(true);
		Button bttnClear = new Button("Reset form");
		GridPane grdpn = new GridPane();
		grdpn.add(lblPersonType, 1, 1);
		grdpn.add(lblCrisisType, 4, 1);
		grdpn.add(lstvwCrisisType, 4, 2, 2, 1);
		grdpn.add(lstvwPersonType, 1, 2, 2, 1);
		grdpn.add(lblDate, 1, 3);
		grdpn.add(dtpckr, 1, 4, 2, 1);
		grdpn.add(txtfldPhone, 1, 5, 2, 1);
		grdpn.add(txtfldLatitude, 1, 6, 2, 1);
		grdpn.add(txtfldLongitude, 1, 7, 2, 1);
		grdpn.add(txtarComment, 1, 8, 2, 1);
		grdpn.add(lblTimeHour, 1, 9);
		grdpn.add(sldrHourPicker, 1, 10);
		grdpn.add(txtfldCurrentSetHour, 2, 10);
		grdpn.add(lblTimeMinute, 1, 11);
		grdpn.add(sldrMinutePicker, 1, 12);
		grdpn.add(txtfldCurrentSetMinute, 2, 12);
		grdpn.add(lblTimeSecond, 1, 13);
		grdpn.add(sldrSecondPicker, 1, 14);
		grdpn.add(txtfldCurrentSetSecond, 2, 14);
		grdpn.add(bttnOk, 1, 15);
		grdpn.add(bttnClear, 2, 15);
		bttnOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (checkIfAllDialogHasBeenFilledIn(grdpn)){
					if (checkDataAndSend(Math.floor(sldrHourPicker.getValue()), Math.floor(sldrMinutePicker.getValue()), Math.floor(sldrSecondPicker.getValue()),
							dtpckr.getValue().getYear(), dtpckr.getValue().getMonthValue(), dtpckr.getValue().getDayOfMonth(),
							lstvwPersonType.getSelectionModel().getSelectedItem(), txtfldPhone.getText(),
							txtfldLatitude.getText(), txtfldLongitude.getText(),
							txtarComment.getText(),lstvwCrisisType.getSelectionModel().getSelectedItem()).getValue())
						resetForm(grdpn);
					else
						showWarningMessage("Error", "Unable to create alert");
				}
				else
					showWarningNoDataEntered();
			}
		}); 
		bttnClear.addEventFilter(ActionEvent.ACTION, event-> resetForm(grdpn));
		scrllpnSendAlert.setContent(grdpn);
	}
	
	/**
	 * Checks the data is OK and if so will send it.
	 *
	 * @param hour The hour on the clock when the accident happened
	 * @param minute The minute on the clock when the accident happened
	 * @param second The second on the clock when the accident happened
	 * @param year The year of the accident
	 * @param month The month of the accident
	 * @param day The day of the month of the accident
	 * @param humanKind The type of human reporting the accident
	 * @param phoneNumber The phone number of the human who is reporting the accident
	 * @param latitude The latitude of the accident
	 * @param longitude The longitude of the accident
	 * @param comment The message sent by the human about the accident
	 * @return The success of the method
	 */
	public PtBoolean checkDataAndSend(double hour, double minute, double second, int year, int month, int day, EtHumanKind humanKind, String phoneNumber, String latitude, String longitude, String comment, EtCrisisType aEtCrisisType){
		try {
			return comcompanyController.oeAlert(humanKind, year, month, day, (int)hour, (int)minute, (int)second, phoneNumber, latitude, longitude, comment, aEtCrisisType);
		} catch (ServerOfflineException | InvalidHumanKindException | ServerNotBoundException e) {
			showExceptionErrorMessage(e);
		} catch (IncorrectFormatException e) {
			showWarningIncorrectInformationEntered(e);
		} catch (StringToNumberException e){
			showWarningIncorrectData(e.getMessage());
		}
		return new PtBoolean(false);
	}
	
	/**
	 * Resets all the controls on the form for sending alert to blank (Or similar).
	 *
	 * @param grdPn The grid pane containing all the controls to blank
	 */
	public void resetForm(GridPane grdPn){
		for(Node n: grdPn.getChildren()){
			if (n instanceof TextField)
				((TextField)n).setText("");
			else if (n instanceof ComboBox)
				((ComboBox<?>)n).setValue(null);
			else if (n instanceof TextArea)
				((TextArea)n).setText("");
			else if (n instanceof DatePicker)
				((DatePicker)n).setValue(Calendar.getInstance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			
			
		}
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.HasTables#setUpTables()
	 */
	@Override
	public
	void setUpTables() {
		setUpMessageTables(tblvwDispatchedMessages);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractGUIController#closeForm()
	 */
	@Override
	public void closeForm() {
		try {
			comcompanyController.removeAllListeners();
		} catch (ServerOfflineException | ServerNotBoundException e) {
			showExceptionErrorMessage(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createAlertForm();
		setUpTables();
	}

	@Override
	public PtBoolean setActor(JIntIsActor actor) {
		try {
			if (actor instanceof ActComCompany){
				comcompanyController = new ComCompanyController((ActComCompany)actor);
				try {
					comcompanyController.getAuth().listOfMessages.addListener(new ListChangeListener<Message>() {
						@Override
						public void onChanged(ListChangeListener.Change<? extends Message> c) {
							addMessageToTableView(tblvwDispatchedMessages, c.getList());
						}
					});
				} catch (Exception e) {
					showExceptionErrorMessage(e);
				}
			}
			else
				throw new IncorrectActorException(actor, ActComCompany.class);
		} catch (ServerOfflineException | ServerNotBoundException | IncorrectActorException e) {
			showExceptionErrorMessage(e);
			return new PtBoolean(false);
		}
		return new PtBoolean(true);
	}
}
