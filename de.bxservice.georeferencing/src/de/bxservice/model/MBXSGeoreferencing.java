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
package de.bxservice.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.AccessSqlParser;
import org.compiere.model.AccessSqlParser.TableInfo;
import org.compiere.print.MPrintColor;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.MLocation;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

public class MBXSGeoreferencing extends X_BXS_Georeferencing {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5590108858426943020L;

	private List<MBXSMarker> mapMarkers;
	private String additionalWhereClause;
	
	public MBXSGeoreferencing(Properties ctx, int BXS_Georeferencing_ID, String trxName) {
		super(ctx, BXS_Georeferencing_ID, trxName);
	}
	
	public MBXSGeoreferencing(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public List<MBXSMarker> getMapMarkers() {
		if (mapMarkers == null || mapMarkers.isEmpty()) {
			mapMarkers = new ArrayList<>();
			String sql = getSQL();
			
			PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        try 	        {
	            pstmt = DB.prepareStatement(sql, get_TrxName());
	            pstmt.setInt(1, Env.getAD_Client_ID(getCtx()));
	            if (Env.getAD_Org_ID(getCtx()) > 0)
	            	pstmt.setInt(2, Env.getAD_Org_ID(getCtx()));
	            rs = pstmt.executeQuery();
	            while (rs.next()) {
	            	MBXSMarker marker = new MBXSMarker();
	            	marker.setLatitude(rs.getString("latitude"));
	            	marker.setLongitude(rs.getString("longitude"));
	            	marker.setTitle(rs.getString("markerTitle"));
	            	marker.setDescription(rs.getString("markerDescription"));
	            	marker.setColor(rs.getString("markerColor"));
	            	mapMarkers.add(marker);
	            }
	            
	            //Set the organization marker
	            if (isBXS_IsOrgIncluded() && Env.getAD_Org_ID(getCtx()) != 0) {
	            	
	        		MOrgInfo orgInfo = MOrgInfo.get(Env.getCtx(), Env.getAD_Org_ID(getCtx()), null);
	        		if (orgInfo != null) {
	        			MLocation orgLoc = MLocation.get(Env.getCtx(), orgInfo.getC_Location_ID(), get_TrxName());
	        			if (orgLoc != null && orgLoc.get_Value("Latitude") != null &&
		        				orgLoc.get_Value("Longitude") != null) {
	        				MBXSMarker marker = new MBXSMarker();
	    	            	marker.setLatitude((String) orgLoc.get_Value("Latitude"));
	    	            	marker.setLongitude((String) orgLoc.get_Value("Longitude"));
	    	            	marker.setTitle(MOrg.get(getCtx(), Env.getAD_Org_ID(getCtx())).getName());
	    	            	marker.setDescription("");
	    	            	
	    	            	String color = "red"; //Default value
	    	            	if (getBXS_OrgColor_ID() > 0) {
		    	            	MPrintColor printColor = new MPrintColor(Env.getCtx(), getBXS_OrgColor_ID(), get_TrxName());
		    	            	if (printColor!= null)
		    	            		color = printColor.getName();
	    	            	}
	    	            	marker.setColor(color);
	    	            	mapMarkers.add(marker);
	        			}
	        		}
	            }
	        } catch (Exception e) {
	            log.log(Level.SEVERE, sql);
	            throw new AdempiereException(e);
	        } finally {
	        	if (rs != null)
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
	        	if (pstmt != null)
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
	        }
		}

		return mapMarkers;
	}

	public void setMapMarkers(List<MBXSMarker> mapMarkers) {
		this.mapMarkers = mapMarkers;
	}
	
	private String getSQL() {
		StringBuilder sql = new StringBuilder();

		AccessSqlParser sqlParser = new AccessSqlParser("SELECT * FROM " + getFromClause());
		TableInfo[] tableInfos = sqlParser.getTableInfo(0);
		String keyTableAlias = !Util.isEmpty(tableInfos[0].getSynonym())
				? tableInfos[0].getSynonym() : tableInfos[0].getTableName();

		sql.append(getSelectClause());

		// build from clause
		sql.append( " FROM ").append(getFromClause());

		// build where clause add (1=2) because to decrease load 
		sql.append(" WHERE ").append(keyTableAlias).append(".AD_Client_ID IN (0,?) ");
		if (Env.getAD_Org_ID(getCtx()) > 0)
			 sql.append(" AND ").append(keyTableAlias).append(".AD_Org_ID IN (0,?) ");

		if (!Util.isEmpty(getWhereClause())) {
			sql.append(" AND (").append(getWhereClause()).append(")");
		}
		if (!Util.isEmpty(additionalWhereClause)) {
			sql.append(" AND (").append(additionalWhereClause).append(")");
		}

		// build other clause
		if (!Util.isEmpty(getOtherClause())) {
			sql.append(" ").append(getOtherClause());
		}
		
		return sql.toString();
	}
	
	private String getSelectClause() {
		StringBuilder selectClause = new StringBuilder("SELECT DISTINCT ");
		selectClause.append(getBXS_LatitudeSQL());
		selectClause.append(" AS latitude, ");
		selectClause.append(getBXS_LongitudeSQL());
		selectClause.append(" AS longitude, ");
		
		String titleSQL = Util.isEmpty(getBXS_TitleSQL()) ? "''": getBXS_TitleSQL();
		selectClause.append(titleSQL);
		selectClause.append(" AS markerTitle, ");
		
		String descriptionSQL = Util.isEmpty(getBXS_DescriptionSQL()) ? "''": getBXS_DescriptionSQL();
		selectClause.append(descriptionSQL);
		selectClause.append(" AS markerDescription, ");
		
		String colorSQL = Util.isEmpty(getBXS_ColorSQL()) ? "''": getBXS_ColorSQL();
		selectClause.append(colorSQL);
		selectClause.append(" AS markerColor ");
		
		return selectClause.toString();
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		AccessSqlParser parser = new AccessSqlParser("SELECT * FROM " + getFromClause());
		TableInfo[] tableInfos = parser.getTableInfo(0);
		if (tableInfos == null || tableInfos.length == 0) {
			log.saveError("ParseFromClauseError", "Failed to parse from clause");
			return false;
		}
		
		// evaluate need valid
		if (is_new() || is_ValueChanged(COLUMNNAME_AD_Table_ID) || is_ValueChanged(COLUMNNAME_WhereClause) ||
								is_ValueChanged(COLUMNNAME_FromClause) || is_ValueChanged(I_AD_InfoWindow.COLUMNNAME_OtherClause) || 
								is_ValueChanged(COLUMNNAME_BXS_TitleSQL) || is_ValueChanged(COLUMNNAME_BXS_ColorSQL) ||
								is_ValueChanged(COLUMNNAME_BXS_DescriptionSQL) || is_ValueChanged(COLUMNNAME_BXS_LatitudeSQL) ||
								is_ValueChanged(COLUMNNAME_BXS_LongitudeSQL))
			return validate();
		
		return true;
	}
	
	public boolean validate () {
	
		// build select clause
		if (Util.isEmpty(getBXS_LatitudeSQL())) {
			log.saveError("FillMandatory", "@BXS_LatitudeSQL@");
			return false;
		}
		
		if (Util.isEmpty(getBXS_LongitudeSQL())) {
			log.saveError("FillMandatory", "@BXS_LongitudeSQL@");
			return false;
		}

		// add DISTINCT clause
		StringBuilder builder = new StringBuilder();
		builder.append(getSelectClause());

		// build from clause
		builder.append( " FROM ").append(getFromClause());

		// build where clause add (1=2) because to decrease load 
		if (!Util.isEmpty(getWhereClause())) {
			builder.append(" WHERE (1=2) AND (").append(getWhereClause()).append(")");
		} else {
			builder.append(" WHERE 1=2");
		}

		// build other clause
		if (!Util.isEmpty(getOtherClause())) {
			builder.append(" ").append(getOtherClause());
		}
		
		// replace Env variables by dummy values
		while(builder.indexOf("@") >= 0) {
			int start = builder.indexOf("@");
			int end = builder.indexOf("@", start+1);
			if (start >=0 && end > start) {
				builder.replace(start, end+1, "0");
			} else {
				break;
			}
		}
		
		// try run sql
		PreparedStatement pstmt = null;
		try {
			pstmt = DB.prepareStatement(builder.toString(), get_TrxName());
			pstmt.executeQuery();
		}catch (Exception ex){
			log.log(Level.WARNING, ex.getMessage());
			throw new AdempiereException(ex);
		} finally {
			DB.close(pstmt);
		}
		
		return true;
	}

	public String getAdditionalWhereClause() {
		return additionalWhereClause;
	}

	public void setAdditionalWhereClause(String additionalWhereClause) {
		this.additionalWhereClause = additionalWhereClause;
	}
	
}