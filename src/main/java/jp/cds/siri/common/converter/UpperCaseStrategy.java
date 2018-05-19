package jp.cds.siri.common.converter;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.PropertyNamingStrategyBase;

public class UpperCaseStrategy extends PropertyNamingStrategyBase {

    private static final long serialVersionUID = -4905483777970900797L;

    @Override
    public String translate(String propertyName) {
        return propertyName.toUpperCase();
    }
}
