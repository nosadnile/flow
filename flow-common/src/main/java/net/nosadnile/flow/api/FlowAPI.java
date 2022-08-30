package net.nosadnile.flow.api;

import net.nosadnile.flow.api.database.FlowDatabaseAPI;

public class FlowAPI {
    private static final FlowAPI INSTANCE = new FlowAPI();

    private final FlowDatabaseAPI databaseAPI = new FlowDatabaseAPI();

    public FlowDatabaseAPI getDatabaseAPI() {
        return this.databaseAPI;
    }

    public static FlowAPI getInstance() {
        return FlowAPI.INSTANCE;
    }
}
