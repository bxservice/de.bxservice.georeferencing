/**********************************************************************
 * This file is part of iDempiere ERP Open Source                      *
 * http://www.idempiere.org                                            *
 *                                                                     *
 * Copyright (C) Contributors                                          *
 *                                                                     *
 * This program is free software; you can redistribute it and/or       *
 * modify it under the terms of the GNU General Public License         *
 * as published by the Free Software Foundation; either version 2      *
 * of the License, or (at your option) any later version.              *
 *                                                                     *
 * This program is distributed in the hope that it will be useful,     *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of      *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
 * GNU General Public License for more details.                        *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with this program; if not, write to the Free Software         *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
 * MA 02110-1301, USA.                                                 *
 *                                                                     *
 * Contributors:                                                       *
 * - Diego Ruiz - BX Service GmbH								      *
 **********************************************************************/
package de.bxservice.georeferencing.webui.form;

import java.util.List;

import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.theme.ThemeManager;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.ValueNamePair;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.North;
import org.zkoss.zul.South;

import de.bxservice.georeferencing.model.MBXSGeoreferencing;
import de.bxservice.georeferencing.tools.OsgiUtil;
import de.bxservice.georeferencing.webui.apps.form.GeoReferencing;


public class WGeoreferencingMap extends GeoReferencing implements IFormController, EventListener<Event> {

	public static final String GEOMAP_FORM_UU = "'c597a78d-5ee9-424f-a9f7-b90458b0c128'";

	/** General UI **/
	private CustomForm mainForm = new CustomForm();
	private Borderlayout	mainLayout	= new Borderlayout();
	
	/** North configuration panel **/
	private Label lMaps = new Label();
	private Label lMapProvider = new Label();
	private Button bRefresh = new Button();
	private Hbox northPanelHbox;
	private Listbox mapsCombobox = ListboxFactory.newDropdownListbox();
	private Listbox mapsProviderCombobox = ListboxFactory.newDropdownListbox();
	private String mapProviderName = null;
	/** South content panel **/
	private Iframe markersMap;
	
	int BXS_Georeferencing_ID = -1;

	public WGeoreferencingMap() {
		super();
		initForm();
	}

	protected void initForm() {
		try {
			windowNo = SessionManager.getAppDesktop().registerWindow(this);
			dynList();
			jbInit();
		} catch (Exception ex){}
	}

	/**
	 *  Initialize List of existing maps
	 */
	private void dynList() {
		//	Fill List
		for (KeyNamePair mapConfigurations : getGeoMapList())
			mapsCombobox.addItem(mapConfigurations);

		mapsCombobox.addEventListener(Events.ON_SELECT, this);
		
		List<String> mapProviderNames = OsgiUtil.getMapProviders();
		mapsProviderCombobox.addEventListener(Events.ON_SELECT, this);
		for (String mapProviderName : mapProviderNames) {
			mapsProviderCombobox.addItem(new ValueNamePair(mapProviderName, mapProviderName));
		}
		
		if (mapProviderNames.size() > 0) {
			mapsProviderCombobox.setSelectedIndex(0);
			mapProviderName = mapProviderNames.get(0);
		}
		
		lMapProvider.setVisible(mapProviderNames.size() > 1);
		mapsProviderCombobox.setVisible(mapProviderNames.size() > 1);
			
	}   //  dynList

	/**
	 * 	Static init
	 *	@throws Exception
	 */
	private void jbInit() throws Exception {
		mainForm.setClosable(true);
		mainForm.setMaximizable(true);
		mainForm.setWidth("100%");
		mainForm.setHeight("100%");
		mainForm.appendChild (mainLayout);
		mainForm.setBorder("normal");
		
		//North Panel
		
		lMaps.setText(Msg.translate(Env.getCtx(), "Map"));
		lMaps.setStyle("vertical-align: middle;");
		lMapProvider.setText(Msg.translate(Env.getCtx(), "Map Provider"));
		lMapProvider.setStyle("vertical-align: middle;");
		if (ThemeManager.isUseFontIconForImage())
			bRefresh.setIconSclass("z-icon-Refresh");
		else
			bRefresh.setImage(ThemeManager.getThemeResource("images/Refresh16.png"));
		bRefresh.setTooltiptext(Msg.getMsg(Env.getCtx(), "Refresh"));
		bRefresh.addEventListener(Events.ON_CLICK, this);

		northPanelHbox = new Hbox();
		northPanelHbox.appendChild(lMapProvider);
		northPanelHbox.appendChild(mapsProviderCombobox);
		northPanelHbox.appendChild(lMaps.rightAlign());
		northPanelHbox.appendChild(mapsCombobox);			
		northPanelHbox.appendChild(bRefresh);
		northPanelHbox.setHflex("1");
		northPanelHbox.setStyle("padding: 5px; border-style: double; border-width: 0px 0px 1px; border-color: black;");
		
		North north = new North();
		north.setSize("5%");
		LayoutUtils.addSclass("tab-editor-form-north-panel", north);
		north.appendChild(northPanelHbox);
		mainLayout.appendChild(north);

		//South Panel
		markersMap = new Iframe();
		markersMap.setWidth("100%");
		markersMap.setHeight("100%");
		South south = new South();
		LayoutUtils.addSclass("tab-editor-form-center-panel", south);
		south.setSize("95%");
		south.appendChild(markersMap);

		mainLayout.appendChild(south);
	}	//	jbInit

	public void renderMap() {
		markersMap = new Iframe();

		if (BXS_Georeferencing_ID != -1) {
			setMap(BXS_Georeferencing_ID);
			setContent(getHTMLCode(mapProviderName));
		}
	}

	private void refresh() {
		setMap(BXS_Georeferencing_ID);
		SessionManager.getAppDesktop().updateHelpTooltip(null,null,null,null);
		repaintMap();
	}
	
	private void repaintMap() {
		mainLayout.getSouth().removeChild(markersMap);
		renderMap();
		mainLayout.getSouth().appendChild(markersMap);
	}
	
	@Override
	public void onEvent(Event event) throws Exception {
		if (Events.ON_SELECT.equals(event.getName()) && event.getTarget() == mapsCombobox) {
			if (mapsCombobox.getSelectedIndex() != -1) {
				KeyNamePair geoMap = null;
				BXS_Georeferencing_ID = -1;
				geoMap = (KeyNamePair) mapsCombobox.getSelectedItem().toKeyNamePair();	
				if (geoMap != null)
					BXS_Georeferencing_ID = geoMap.getKey();
				refresh();
			}
		} else if (Events.ON_CLICK.equals(event.getName()) && event.getTarget() instanceof Button) 
			refresh();
		else if (Events.ON_SELECT.equals(event.getName()) && event.getTarget() == mapsProviderCombobox) {
			if (mapsProviderCombobox.getSelectedItem() == null) {
				mapProviderName = null;
			}else {
				mapProviderName = mapsProviderCombobox.getSelectedItem().toValueNamePair().getValue();
			}
			refresh();
		}
	}
	
	/**
	 * If the configuration is set directly, f.e. from a process
	 * hide the selection list
	 * @param geoConfig Geoconfiguration object
	 */
	public void setContent(MBXSGeoreferencing geoConfig) {
		if (geoConfig != null) {
			setMap(geoConfig);
			BXS_Georeferencing_ID = geoConfig.getBXS_Georeferencing_ID();
			lMaps.setVisible(false);
			mapsCombobox.setVisible(false);
			setContent(getHTMLCode(mapProviderName));
		}
	}
	
	private void setContent(String htmlCode) {
		AMedia media = new AMedia("Help", "html", "text/html", htmlCode.getBytes());
		markersMap.setWidth("100%");
		markersMap.setHeight("100%");
		markersMap.setContent(media);
		if (getGeoConfiguration() != null)
			SessionManager.getAppDesktop().updateHelpTooltip(getGeoConfiguration().get_Translation(MBXSGeoreferencing.COLUMNNAME_Description),
					getGeoConfiguration().get_Translation(MBXSGeoreferencing.COLUMNNAME_Help),null,null);
	}
	
	@Override
	public ADForm getForm() {
		return mainForm;
	}
}