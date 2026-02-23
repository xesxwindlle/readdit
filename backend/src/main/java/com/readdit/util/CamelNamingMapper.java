// package com.readdit.util;

// import org.springframework.data.relational.core.mapping.NamingStrategy;
// import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;

// public class CamelNamingMapper implements NamingStrategy {

//     @Override
//     public String getColumnName(RelationalPersistentProperty property) {
//         return property.getName(); 
//     }

//     @Override
//     public String getTableName(Class<?> type) {
//         return type.getSimpleName();
//     }
// }