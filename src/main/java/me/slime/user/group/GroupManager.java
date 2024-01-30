package me.slime.user.group;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by Kevloe on 10.04.2020.
 */

public class GroupManager {

  public final Group DEFAULT_GROUP = new Group(0,"user", "User", null, null, null);

  public final Gson gson = new Gson();

  private Map<Short, Group> groups = new HashMap<>();

  private ExecutorService executorService;

  public GroupManager(ExecutorService executorService){
    this.executorService = executorService;
    this.init();
  }
  private void init(){
    this.executorService.execute(new Runnable() {
      @Override
      public void run() {

      }
    });
  }
  public Group getGroupByID(short id){
    if(this.DEFAULT_GROUP.getID() == id)
      return this.DEFAULT_GROUP;
    return this.groups.get(Short.valueOf(id));
  }
}
