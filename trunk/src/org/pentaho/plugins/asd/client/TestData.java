package org.pentaho.plugins.asd.client;

import java.util.ArrayList;

public class TestData {

  public static ArrayList<ActionItem> getAvailableActions() {
    ArrayList<ActionItem> actionItems = new ArrayList<ActionItem>();
    actionItems.add(new ActionItem("../images/SQL.png", "SQL Lookup", "Input Actions"));
    actionItems.add(new ActionItem("../images/GenericTransform.png", "Run PDI Trans", "Input Actions"));
    actionItems.add(new ActionItem("../images/GenericJob.png", "Run PDI Job", "Input Actions"));
    actionItems.add(new ActionItem("../images/US.png", "Create Chart", "Input Actions"));
    actionItems.add(new ActionItem("../images/GETPOP.png", "Email", "Input Actions"));
    actionItems.add(new ActionItem("../images/Plugin.png", "Unknown", "Input Actions"));
    return actionItems;
  }

}
