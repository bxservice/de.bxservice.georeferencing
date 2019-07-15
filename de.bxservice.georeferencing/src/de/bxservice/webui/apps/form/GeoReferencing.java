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
 * - Diego Ruiz - BX Service GmbH                                      *
 **********************************************************************/
package de.bxservice.webui.apps.form;



import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import de.bxservice.model.MBXSGeoreferencing;
import de.bxservice.tools.GeoreferencingHelperFactory;
import de.bxservice.tools.IGeoreferencingHelper;

public class GeoReferencing {

	public static CLogger log = CLogger.getCLogger(GeoReferencing.class);
	
	protected int windowNo = 0;
	private MBXSGeoreferencing geoConfiguration;
	
	protected void setMap(int BXS_Georeferencing_ID) {
		if (BXS_Georeferencing_ID==-1)
			geoConfiguration = null;
		else if (geoConfiguration == null || BXS_Georeferencing_ID != geoConfiguration.get_ID()) {
			geoConfiguration = new MBXSGeoreferencing(Env.getCtx(), BXS_Georeferencing_ID, null);
		}
	}
	
	protected void setMap(MBXSGeoreferencing geoConfiguration) {
		if (this.geoConfiguration != null)
			this.geoConfiguration = null;

		this.geoConfiguration = geoConfiguration;
	}
	
	public KeyNamePair[] getGeoMapList() {
		String sql = null;
		KeyNamePair[] list;
		boolean baseLanguage = Env.isBaseLanguage(Env.getCtx(), MBXSGeoreferencing.Table_Name);
		if (baseLanguage) {
			sql = "SELECT g.BXS_Georeferencing_ID, g.Name "
					+ "FROM BXS_Georeferencing g "
					+ "WHERE g.AD_Client_ID IN (0, ?) AND g.IsActive='Y' "
					+ "ORDER BY g.Name";

			list = DB.getKeyNamePairs(null, sql, true, Env.getAD_Client_ID(Env.getCtx()));
		} else {
			sql = "SELECT g.BXS_Georeferencing_ID, gt.Name "
					+ "FROM BXS_Georeferencing g "
					+ "JOIN BXS_Georeferencing_Trl gt ON (g.BXS_Georeferencing_ID=gt.BXS_Georeferencing_ID) "
					+ "WHERE g.AD_Client_ID IN (0, ?) AND g.IsActive='Y' "
					+ "AND gt.AD_Language=? "
					+ "ORDER BY gt.Name";
			
			list = DB.getKeyNamePairs(null, sql, true, Env.getAD_Client_ID(Env.getCtx()), Env.getAD_Role_ID(Env.getCtx()));
		}
		return list;
	}

	public MBXSGeoreferencing getGeoConfiguration() {
		return geoConfiguration;
	}
	
	protected String getHTMLCode() {
		IGeoreferencingHelper mapHelper = GeoreferencingHelperFactory.getGeoreferencingHelper(null);
		mapHelper.setGeoconfiguration(getGeoConfiguration());
		return mapHelper.getMapMarkers();
	}
}
