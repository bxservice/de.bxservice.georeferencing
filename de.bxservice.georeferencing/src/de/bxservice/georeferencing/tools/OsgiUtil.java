package de.bxservice.georeferencing.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class OsgiUtil {
	public static <T extends Object> Collection<ServiceReference<T>> getService(final Class<T> clazz) {
	    final BundleContext bundleContext = FrameworkUtil.getBundle(clazz).getBundleContext();
	    try {
	    	return bundleContext.getServiceReferences(clazz, null);
	    }catch (Exception e) {
			throw new AdempiereException(e);
		}
	    
	  }
	
	public static List<String> getMapProviders (){
		Collection<ServiceReference<IGeoreferencingHelper>> referenceMapServices = getService(IGeoreferencingHelper.class);
		List<String> mapProviderNames = new ArrayList<String>();
		for (ServiceReference<IGeoreferencingHelper> referenceMapService : referenceMapServices) {
			mapProviderNames.add(referenceMapService.getProperty("helperType").toString());
		}
		return mapProviderNames;
	}
}
