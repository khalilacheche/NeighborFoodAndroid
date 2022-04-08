package com.epfl.neighborfood.neighborfoodandroid;

import android.app.Application;

public class NeighborFoodApplication extends Application {
    protected AppContainer appContainer;
    public AppContainer getAppContainer(){
        if(appContainer == null){
            appContainer = new AppContainerImplementation();
        }
        return appContainer;
    }
}