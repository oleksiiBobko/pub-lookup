package com.pub.lookup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.ComponentFilter;
import com.google.maps.model.GeocodingResult;
import com.pub.lookup.dao.interfaces.GeoResultDao;
import com.pub.lookup.dao.interfaces.GeoSearchDao;
import com.pub.lookup.domain.GeoResult;
import com.pub.lookup.domain.GeoSearch;
import com.pub.lookup.service.GeoApiService;

@Service
@Transactional
public class GeoApiServiceImpl implements GeoApiService {

    @Value("${geo.api.key}")
    private String apiKey;
    
    @Autowired
    private GeoSearchDao geoSearchDao;
    
    @Autowired
    private GeoResultDao geoResultDao;
    
    @Override
    public GeoSearch validateSearch(String search) throws Exception {
        
        GeoSearch valid = geoSearchDao.find(search.toUpperCase());
        
        if(valid != null) {
            return valid;
        }
        
        valid = new GeoSearch();
        valid.setSearchId(search.toUpperCase());
        geoSearchDao.add(valid);
        
        GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
        GeocodingResult[] results = GeocodingApi.geocode(context, search)
                .components(ComponentFilter.country("UK")).await();
        
        createGeoResults(results, valid);
        
        return valid;
    }

    private void createGeoResults(GeocodingResult[] results, GeoSearch geoSearch) {
        for(GeocodingResult result : results) {
            
            //stub for empty results
            if(result.formattedAddress.equalsIgnoreCase("United Kingdom")) {
                continue;
            }
            
            GeoResult geoResult = new GeoResult();
            geoResult.setGeoSearch(geoSearch);
            geoSearch.getGeoResults().add(geoResult);
            geoResult.setFormatted(result.formattedAddress);
            for(AddressComponent ac : result.addressComponents) {
                for(AddressComponentType act : ac.types) {
                    if(AddressComponentType.LOCALITY == act) {
                        geoResult.setLocality(ac.longName);
                    }
                    if(AddressComponentType.POSTAL_TOWN == act) {
                        geoResult.setPostalTown(ac.longName);
                    }
                    if(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_5 == act) {
                        geoResult.setAdministrativeAreaLevel5(ac.longName);
                    }
                    if(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_4 == act) {
                        geoResult.setAdministrativeAreaLevel4(ac.longName);
                    }
                    if(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_3 == act) {
                        geoResult.setAdministrativeAreaLevel3(ac.longName);
                    }
                    if(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_2 == act) {
                        geoResult.setAdministrativeAreaLevel2(ac.longName);
                    }
                    if(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1 == act) {
                        geoResult.setAdministrativeAreaLevel1(ac.longName);
                    }
                    if(AddressComponentType.COUNTRY == act) {
                        geoResult.setCountry(ac.longName);
                    }
                    if(AddressComponentType.POSTAL_CODE == act) {
                        geoResult.setPostalCode(ac.longName);
                    }
                }
            }
            geoResultDao.add(geoResult);
        }
        
    }

    @Override
    public String getSearchStringByPriority(String search) {
        GeoResult geoResult = geoResultDao.find(Integer.valueOf(search));
        
        if (geoResult.getPostalCode() != null
                && !geoResult.getPostalCode().isEmpty()
                && geoResult.getPostalCode().contains(" ")) {
            return geoResult.getPostalCode();
            
        } else if (geoResult.getLocality() != null
                && !geoResult.getLocality().isEmpty()) {
            return geoResult.getLocality();
            
        } else if (geoResult.getPostalTown() != null
                && !geoResult.getPostalTown().isEmpty()) {
            return geoResult.getPostalTown();
            
        } else if (geoResult.getAdministrativeAreaLevel5() != null
                && !geoResult.getAdministrativeAreaLevel5().isEmpty()) {
            return geoResult.getAdministrativeAreaLevel1();
            
        } else if (geoResult.getAdministrativeAreaLevel4() != null
                && !geoResult.getAdministrativeAreaLevel4().isEmpty()) {
            return geoResult.getAdministrativeAreaLevel4();
            
        } else if (geoResult.getAdministrativeAreaLevel3() != null
                && !geoResult.getAdministrativeAreaLevel3().isEmpty()) {
            return geoResult.getAdministrativeAreaLevel3();
            
        } else if (geoResult.getAdministrativeAreaLevel2() != null
                && !geoResult.getAdministrativeAreaLevel2().isEmpty()) {
            return geoResult.getAdministrativeAreaLevel2();
            
        } else if (geoResult.getAdministrativeAreaLevel1() != null
                && !geoResult.getAdministrativeAreaLevel1().isEmpty()) {
            return geoResult.getAdministrativeAreaLevel1();
            
        }
        return search;
    }

    @Override
    public String getSearchView(String search) {
        GeoResult geoResult = geoResultDao.find(Integer.valueOf(search));
        return geoResult == null ? "" : geoResult.getFormatted();
    }

}
